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
import java.util.concurrent.atomic.AtomicInteger

import better.files.Dsl.mkdirs

/**
 * ScreenShooter.
 *
 * @author nekopiano
 */
case class ScreenShooter(baseDirPath: String, testName: String) {

  // init the directory
  mkdir(baseDirPath)

  object Counter {
    val counter = new AtomicInteger(0)
    def count(): Int = {
      counter.addAndGet(1)
    }
    def countFixedDigit(): String = {
      counter.addAndGet(1)
      "%03d".format(counter)
    }
  }

  def takeScreenShot()(implicit driver: RemoteWebDriver) {
    val takesScreenShot = driver.asInstanceOf[TakesScreenshot]
    val scrFile = takesScreenShot.getScreenshotAs(OutputType.FILE)
    println("scrFile: " + scrFile)
    val tarFileName = baseDirPath + '/' + testName + '-' + Counter.countFixedDigit + ".png"
    scrFile.toScala.copyTo(File(tarFileName))

    println("shot: " + tarFileName)
  }

  def mkdir(path:String): Unit ={
    val imageDir = File(path)
    if (!imageDir.exists) {
      mkdirs(imageDir)
      // TODO logger
      System.out.println("Create Folder:" + imageDir.path)
    }
  }

}
