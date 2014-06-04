/**
 * Copyright (c) 2013-2014 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.selenium

import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.remote.RemoteWebDriver
import scalax.io.Resource
import org.openqa.selenium.OutputType

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
    val scrFile = takesScreenShot.getScreenshotAs(OutputType.FILE);
    val tarFileName = baseImageDirPath + '/' + testName + '-' + Counter.countFixedDigit + ".png"
    Resource.fromFile(scrFile) copyDataTo Resource.fromFile(tarFileName)
    println("shot: " + tarFileName)
  }

}
