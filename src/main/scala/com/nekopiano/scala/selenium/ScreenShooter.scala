/**
 * Copyright (c) 2013-2017 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.selenium

import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.OutputType

import better.files._
import java.io.{File => JFile}

/**
 * ScreenShooter.
 *
 * @author nekopiano
 */
object ScreenShooter {

  object Counter {
    var counter = 0;
    def count(): Int = synchronized {
      counter += 1
      counter
    }
    def countFixedDigit(): String = synchronized {
      counter += 1
      "%03d".format(counter)
    }
  }

  def takeScreenShot(baseImageDirPath: String, testName: String)(implicit driver: RemoteWebDriver) {
    val takesScreenShot = driver.asInstanceOf[TakesScreenshot]
    val scrFile = takesScreenShot.getScreenshotAs(OutputType.FILE)
    println("scrFile: " + scrFile)
    val tarFileName = baseImageDirPath + '/' + testName + '-' + Counter.countFixedDigit + ".png"
    scrFile.toScala.copyTo(File(tarFileName))

    println("shot: " + tarFileName)
  }

}
