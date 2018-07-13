package dev.mahendran.templates

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext

object StreamingApplication extends App {
  val sparkConf = new SparkConf()
  val sparkContext = new SparkContext(sparkConf)

  val ssc = new StreamingContext(sparkContext, Seconds(20))

  ssc.start()
  ssc.awaitTermination()
}
