package com.nekopiano.scala

class CaseClassSpecs extends org.specs2.mutable.Specification {

  "This is a specification for the 'Hello world' string".txt

  "The 'Hello world' string should" >> {
    "contain 11 characters" >> {
      "Hello world" must haveSize(11)
    }
    "start with 'Hello'" >> {
      "Hello world" must startWith("Hello")
    }
    "end with 'world'" >> {
      "Hello world" must endWith("world")
    }
  }

  "Case class should" >> {
    "be immutable" >> {

      val a = CaseClass("a")
      // a.str = "b"
      // a compilation error
      // Reassignment to val

      true
    }

    "be immutable" >> {

      // val a = CaseClassWithImplicitVal()
      // a compilation error
      // Error:(32, 39) could not find implicit value for parameter str: String

      implicit val str = "string value"
      val b = CaseClassWithImplicitVal()
      println("b=" + b)

      // a.str = "b"
      // a compilation error
      // Reassignment to val

      true
    }
  }

  case class CaseClass(str: String)
  case class CaseClassWithImplicitVal(implicit val str: String)

}
