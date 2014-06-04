/**
 * Copyright (c) 2013-2014 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.selenium

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.specs2.specification.Scope
import org.specs2.mutable.After
import java.io.File
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

/**
 * GoogleSpecs.<br>
 * This shows the usage.
 *
 * @author nekopiano
 */
@RunWith(classOf[JUnitRunner])
class GoogleSpecs extends Specification with SeleniumUtilityTrait {

  // This prevents parallel execution.
  sequential

  var baseImageDirPath = ""
  var driver: RemoteWebDriver = null

  trait scope extends Scope with After {
    // pre process
    val currentPath = System.getProperty("user.dir")

    System.setProperty("webdriver.chrome.driver",
      currentPath + "\\webdrivers\\chromedriver.exe")
    driver = new ChromeDriver()

    driver.manage.window.maximize
    driver.manage.window.setPosition(new Point(0, 0))
    driver.manage.window.setSize(new Dimension(1024, 768))

    val q = "TC-01"
    baseImageDirPath = currentPath + "\\ScreenShots\\" + q

    val imageDir = new File(baseImageDirPath)
    if (!imageDir.exists) {
      imageDir.mkdir
      System.out.println("Create Folder:" + imageDir.getPath())
    }

    def after = {
      // post process
      driver.close
      driver.quit
    }
  }

  "Google web site" should {

    "be searched" in new scope {

      val testName = "view-google"

      implicit val implicitDriver: RemoteWebDriver = driver

      //driver.get("http://www.google.com")     
      go to "http://www.google.com"
      
      waitVisibility("//div[@title='Google']")
      takeScreenShot(baseImageDirPath, testName)

      val form = waitAndGetFirstElement("//input[@id='gbqfq']")
      form.sendKeys("nekopiano")
      waitAndGetFirstElement("//button[@id='gbqfb']").click

      waitVisibility("//table[@id='nav']")
      takeScreenShot(baseImageDirPath, testName)
    }
  }

}
