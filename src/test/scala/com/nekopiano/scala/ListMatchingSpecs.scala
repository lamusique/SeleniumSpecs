package com.nekopiano.scala

class ListMatchingSpecs extends org.specs2.mutable.Specification {

  "This is a specification for the 'Hello world' string".txt

  "Matching should" >> {
    "work with list" >> {

      val list = List("A", "B", "C", "D")

      list match {
        case a :: b :: c :: d :: Nil => {
          println("first matching: " + a + ", " + b + ", " + c + ", " + d)
        }
        case a :: e :: Nil => {
          println("second matching: " + e)
        }
        case _ :: e :: Nil => {
          println("third matching: " + e)
        }
        case e :: _ :: Nil => {
          println("fourth matching: " + e)
        }
        case _ => {
          println("default")
        }
      }

      true
    }

  }


}
