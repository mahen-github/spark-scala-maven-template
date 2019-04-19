package dev.mahendran.templates.logging


import dev.mahendran.templates.logging.Logger.{ERROR_STACK_FIELD_NAME, ERROR_TYPE_FIELD_NAME}
import org.apache.logging.log4j.message.StringMapMessage

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

/**
  * A properly structured and formatted message for the log that meets the <a
  * href="https://gitlab.nordstrom.com/engineering-standards/standards/blob/master/docs/logging.md#logging-schema">engineering
  * standard for logging</a>. Field names and definitions come from the standard.
  */
trait Logging{
  protected def logger = Logger
}

final class LogEntryBuilder(){

  private val log = new StringMapMessage

  private final val fields = ListBuffer[String]()
  private val errorMap = scala.collection.mutable.Map[String, String]()

  val ERRORS_FIELD_NAME = "errors"

  val UNKNOWN_REQUEST_ID = "missing-request-id"
  val UNKNOWN_SESSION_ID = "missing-session-id"
  val UNKNOWN_CLIENT_ID = "missing-client-id"

  val REQUEST_ID_FIELD_NAME = "request-id"
  val SESSION_ID_FIELD_NAME = "session-id"

  var SESSION_ID = UNKNOWN_SESSION_ID
  var CLIENT_ID = UNKNOWN_CLIENT_ID

  val CLIENT_ID_FIELD_NAME = "client-id"

  val MESSAGE_FIELD_NAME = "message"

  def withRequestId(requestId: String) = {
    log.`with`(REQUEST_ID_FIELD_NAME,requestId)
    this
  }

  def withSessionId(sessionId: String) = {
    log.`with`(SESSION_ID_FIELD_NAME, sessionId)
    this
  }

  def withClientId(clientId: String) = {
    log.`with`(CLIENT_ID_FIELD_NAME, clientId)
    this
  }

  def withMessage(message: String) = {
    log.`with`(MESSAGE_FIELD_NAME,message)
    this
  }

  def withException(e: Exception) = {
    if (e != null) {
      errorMap += (ERROR_TYPE_FIELD_NAME -> "")
      errorMap += (ERROR_STACK_FIELD_NAME -> e.getStackTrace.mkString("\n"))
    }
    this
  }

  private def addField(key: String, value: String): Unit = {
    fields.add(key + "=" + value)
  }

  def buildString = {
    val mapMessage = log.getData

    val sb = new StringBuilder
    //Adding %{request-id},%{session-id},%{client-id}
    sb.append(s"""[${mapMessage.get(REQUEST_ID_FIELD_NAME)}, ${mapMessage.get(SESSION_ID_FIELD_NAME)}, ${mapMessage.get(CLIENT_ID_FIELD_NAME)}] """)

    //Adding fields if exists
    if(!fields.isEmpty) { sb.append(fields.mkString(", ")) }

    //Adding message
    sb.append(mapMessage.get(MESSAGE_FIELD_NAME))

    //Adding error.stack=error.stack
    if(!errorMap.isEmpty) { sb.append(s" ${ERROR_STACK_FIELD_NAME}=${errorMap.get(ERROR_STACK_FIELD_NAME).get}") }
    sb.toString()
  }

  def build = {
    if(!fields.isEmpty) log.`with`("fields",fields.mkString(", "))
    log
  }

}
object Logger{


  private final val logger: org.apache.logging.log4j.Logger = logger


  val UNKNOWN_SESSION_ID = "missing-session-id"
  val UNKNOWN_CLIENT_ID = "missing-client-id"

  var SESSION_ID = UNKNOWN_SESSION_ID
  var CLIENT_ID = UNKNOWN_CLIENT_ID

  /**
    * If logging error, this field MUST be a short string used to group errors in the application.
    * E.g.SQLException,JSONFormatError, etc. Often it can be taken from the type of Exception. DO NOT
    * add any variables or uuids in this field which can increase its cardinality.
    */
  val ERROR_TYPE_FIELD_NAME = "error.type"
  /**
    * This field SHOULD have the Stack trace of the error.
    */
  val ERROR_STACK_FIELD_NAME = "error.stack"

  def withSetIds(sessionId: String, clientId: String) = {
    SESSION_ID = sessionId
    CLIENT_ID = clientId
  }

  def infoWithTraceId(traceId:String, message:String): Unit ={
    val log = basicEntry(traceId).withRequestId(traceId).withMessage(message).build
    logger.info(log)
  }

  def error(traceId: String, message: String, e: Exception) = {
    val log = basicEntry(traceId).withException(e).withMessage(message + ": " + e.getMessage).build
    logger.error(log)
  }

  def basicEntry(traceId: String) = {
    new LogEntryBuilder().withRequestId(traceId)
      .withSessionId(SESSION_ID)
      .withClientId(CLIENT_ID)
  }


}
