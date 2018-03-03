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
import com.nekopiano.scala.selenium.SeleniumSystem.Browser


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

  // Every scope is in a new instance of this class by Specs2.




  trait scope extends Scope with After {

//    val path = OS.currentOS match {
//      case OS.Mac => "/webdrivers/chromedriver"
//      case OS.Windows => "\\webdrivers\\chromedriver.exe"
//      case OS.Linux => "/webdrivers/chromedriver"
//      case _ => "/webdrivers/chromedriver"
//    }

    // Any values are shared into scopes afterwards.
    val hereIsAScopeBeforeExcuting = "Here is a scope before excuting."


    //seleniumSystemPromise success SeleniumSystem(Browser.Chrome, path)
    val seleniumSystem = SeleniumSystem(Browser.Chrome, path)

    lazy val path = OS.currentPath + OS.currentOS.separator + "webdrivers"
    // if without lazy, forward reference will be null
    //val path = OS.currentPath + OS.currentOS.separator + "webdrivers"



//    System.setProperty("webdriver.chrome.driver",
//      OS.currentPath + path)
//
//        driver = new ChromeDriver

    //    driver success new ChromeDriver
    //    driver.future

    implicit val driver = seleniumSystem.createDriver()
    driver.manage.window.maximize
    driver.manage.window.setPosition(new Point(0, 0))
    driver.manage.window.setSize(new Dimension(1024, 768))

    val subDirName = getTestClassName()

    val screenShotsBaseDirPath = OS.currentPath + OS.currentOS.separator + "ScreenShots" + OS.currentOS.separator + subDirName
    val screenShooter = ScreenShooter("view-google", screenShotsBaseDirPath)

    def after = {
      // post process
      seleniumSystem.exit
    }
  }

  "Google web site" should {

    "be searched" in new scope {

      logger.debug(hereIsAScopeBeforeExcuting)

      val testScenario = "view-google"

      logger.debug("Try to go to Google.")

      //driver.get("http://www.google.com")
      go to "http://www.google.com"

      // Doodle hides the default logo.
      //waitVisibility("//img[@id='hplogo']")
      waitVisibility("//input[@type='submit']")

      screenShooter.takeScreenShot()
      screenShooter.takeScreenShot(true)
      screenShooter.takeScreenShot("open a Google front page")
      screenShooter.takeScreenShot("日本語: グーグル")

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
