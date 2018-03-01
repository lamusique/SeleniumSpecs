package com.nekopiano.scala

class NullSafeSpecs extends org.specs2.mutable.Specification {

  "This is a specification for the 'Hello world' string".txt

  "NullSafe method should" >> {
    "be compiled with not null" >> {

      val a = foo(Option(1))
      true
    }

    "not be compiled with null" >> {

      //val a = foo(null)
      // compilation errors
//      Error:(28, 18) could not find implicit value for parameter n: NullSafeSpecs.this.NonExistent
//      val a = foo(null)
//      Error:(28, 18) not enough arguments for method foo: (implicit n: NullSafeSpecs.this.NonExistent)Unit.
//        Unspecified value parameter n.
//      val a = foo(null)

      true
    }

    "not be compiled with null" >> {

      val nullable:Option[Int] = null
      val a = foo(nullable)
      // compilable...

      true
    }

  }

  def foo(m: Option[Int]): Option[Int] = ???
  def foo(m : Null)(implicit n: NonExistent): Unit = ???
  sealed abstract class NonExistent


}
