package dev.mahendran.templates

import org.apache.spark.{SparkConf, SparkContext}


class YourApplication(@transient val sc:SparkContext) {


  def doSomething(): Unit ={

    //do something
  }
  sc.stop()

}
object YourApplication extends App {

  val sparkConf = new SparkConf()
  val sc = new SparkContext(sparkConf)

  val result = new YourApplication(sc)

}
