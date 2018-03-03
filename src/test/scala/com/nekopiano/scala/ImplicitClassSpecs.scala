package com.nekopiano.scala

class ImplicitClassSpecs extends org.specs2.mutable.Specification {

  //https://docs.scala-lang.org/overviews/core/implicit-classes.html

  "This is a specification for the 'Hello world' string".txt

  "an implicit class should" >> {
    "work well" >> {

      implicit class RichString(val src: String) {
           def smile: String = src + ":-)"
        }

      println("Hi, ".smile)


      implicit class IntWithTimes(x: Int) {
        def times[A](f: => A): Unit = {
          def loop(current: Int): Unit =
            if(current > 0) {
              f
              loop(current - 1)
            }
          loop(x)
        }
      }

      5 times println("Hi!")


      true
    }

    "not be compiled with extending AnyVal" >> {

      // AnyVal doesn't instantiate itself.

      // a compilation error:
      // value class may not be a local class
//      implicit class Converter(val src: String) extends AnyVal {
//      }

      true
    }

  }

  // a compilation error:
  // value class may not be member of another class
//  implicit class Converter(val x: Double) extends AnyVal {
//  }

}

// a compilation error:
// 'implicit' modifier cannot be used for top-level objects
//  implicit class Converter(val x: Double) extends AnyVal {
//  }
