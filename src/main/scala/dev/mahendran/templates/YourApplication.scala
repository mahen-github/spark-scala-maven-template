package dev.mahendran.templates

import java.io.FileReader

import dev.mahendran.templates.logging.Logging
import org.apache.spark.{SparkConf, SparkContext}


class YourApplication(@transient val sc:SparkContext) extends Logging{

  logger.withSetIds("SESSION ID", "CLIENTID")
  private val TRACEID="YourApplication"
  def doSomething(): Unit ={
    logger.infoWithTraceId(TRACEID, "doing something")
    //do something

    try {
      //do something that triggers failure
      new FileReader("SOME_FILE_THAT_DOESN'T_EXISTS")
    } catch {
      case e:Exception  => logger.error(TRACEID, "Producing fake error", e)
    }
  }
  sc.stop()

}
object YourApplication extends App{

  val sparkConf = new SparkConf()
  val sc = new SparkContext(sparkConf)
  val result = new YourApplication(sc)

}
