package dev.mahendran.templates

import com.holdenkarau.spark.testing.SharedSparkContext
import org.scalatest.FunSuite

class YourYourApplicationTest extends FunSuite with SharedSparkContext {

   override def beforeAll(): Unit = super.beforeAll()

   override def afterAll(): Unit = super.afterAll()

   test("blah blah blah"){

      val yourApplicationObject = new YourApplication(sc)
      yourApplicationObject.doSomething()
   }
}
