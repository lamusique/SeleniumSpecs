/**
 * Copyright (c) 2013-2018 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.specs2

import org.specs2.mutable.{After, Specification}
import org.specs2.specification.Scope


class InstanceSpec extends Specification {

  // This prevents parallel execution.
  sequential

  trait scope extends Scope with After {

    println("In scope this=" + this)

    def after = {
      println("after() in scope this=" + this)
    }
  }

  "Feature A" should {

    "be right with a" in new scope {

      // Every scope is in a new instance of this class.

      println("A spec a this=" + this)
    }
    "be right with aa" in new scope {
      println("A spec aa this=" + this)
    }

  }

  "Feature B" should {

    "be right with b" in new scope {
      println("B spec b this=" + this)
    }
    "be right with bb" in new scope {
      println("B spec bb this=" + this)
    }

  }

}
