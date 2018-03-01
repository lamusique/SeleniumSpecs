/**
 * Copyright (c) 2013-2018 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.selenium

import org.specs2.mutable.{After, Before, Specification}
import org.openqa.selenium.chrome.ChromeDriver
import org.specs2.specification.Scope
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import better.files._
import better.files.Dsl._


/**
 * GoogleSpecs.<br>
 * This shows the usage.
 *
 * @author nekopiano
 */
//@RunWith(classOf[JUnitRunner])
class GoogleSpecs extends Specification with SeleniumUtilityTrait {

  // This prevents parallel execution.
  sequential

  lazy val currentOS = OS.currentOS

  trait scope extends Scope with After {

    val path = currentOS match {
      case OS.Mac => "/webdrivers/chromedriver"
      case OS.Windows => "\\webdrivers\\chromedriver.exe"
      case OS.Linux => "/webdrivers/chromedriver"
      case _ => "/webdrivers/chromedriver"
    }

    System.setProperty("webdriver.chrome.driver",
      OS.currentPath + path)
    driver = new ChromeDriver()

    driver.manage.window.maximize
    driver.manage.window.setPosition(new Point(0, 0))
    driver.manage.window.setSize(new Dimension(1024, 768))

    val q = "TC-01"
    screenShotsBaseDirPath = OS.currentPath + currentOS.separator + "ScreenShots" + currentOS.separator + q

    val imageDir = File(screenShotsBaseDirPath)
//      imageDir.createIfNotExists(createParents = true)
    if (!imageDir.exists) {
      mkdirs(imageDir)
      logger.debug("Create Folder:" + imageDir)
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
      val screenShooter = ScreenShooter(testName, screenShotsBaseDirPath)

      logger.debug("Try to go to Google.")

      //driver.get("http://www.google.com")
      go to "http://www.google.com"

      waitVisibility("//img[@id='hplogo']")
      screenShooter.takeScreenShot("open a Google front page")

      val form = waitAndGetFirstElement("//input[@id='lst-ib']")
      form.sendKeys("nekopiano")
      waitAndGetFirstElement("//input[@name='btnK']").submit

      waitVisibility("//div[@id='search']")
      screenShooter.takeScreenShot("search")

      scroll(1400)
      waitVisibility("//td[@class='b navend']")
      screenShooter.takeScreenShot("search results")
    }
  }

}
