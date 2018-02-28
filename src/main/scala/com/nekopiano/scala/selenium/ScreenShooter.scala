/**
 * Copyright (c) 2013-2014 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.selenium

import java.util.concurrent.atomic.AtomicInteger

import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.OutputType
import better.files._
import better.files.Dsl._

/**
 * ScreenShooter.
 *
 * @author nekopiano
 */
case class ScreenShooter(testName: String, baseImageDirPath: String)(implicit driver: RemoteWebDriver) {

  object Counter {
    val counter = new AtomicInteger()
    def count(): Int = counter.incrementAndGet()
    def countFixedDigit(): String = {
      "%03d".format(counter.incrementAndGet())
    }
  }

  def takeScreenShot() {
    val takesScreenShot = driver.asInstanceOf[TakesScreenshot]
    val srcBytes = takesScreenShot.getScreenshotAs(OutputType.BYTES)

    val file = baseImageDirPath / (testName + '-' + Counter.countFixedDigit + ".png")
    file.touch()
    file.writeBytes(srcBytes.iterator)

    println("shot: " + file)
  }

}
