package dev.mahendran.templates

import org.apache.spark.{SparkConf, SparkContext}

case class data(customer_id: Long, acct_num: Long, customer_profile: String,
                 trans_num: Long, trans_date: String, trans_time: String,
                 unix_time: Long, category: String, amt: Double, is_fraud: Short)

object StreamingApplication extends App {

  val sparkConf = new SparkConf()
  val sparkContext = new SparkContext(sparkConf)

  val lines = sparkContext.textFile("")

  sparkContext.stop()

}
