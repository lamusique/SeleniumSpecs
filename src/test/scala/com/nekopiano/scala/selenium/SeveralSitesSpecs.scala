/**
 * Copyright (c) 2013-2018 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.selenium

import com.nekopiano.scala.selenium.SeleniumSystem.Browser
import org.openqa.selenium.{Dimension, Point}
import org.specs2.mutable.{After, Specification}
import org.specs2.specification.Scope


/**
 * Several Sites Specs.<br>
 * This shows the usage.
 *
 * @author nekopiano
 */
class SeveralSitesSpecs extends Specification with SeleniumUtilityTrait {

  sequential

  trait scope extends Scope with After {

    val seleniumSystem = SeleniumSystem(Browser.Chrome, path)

    lazy val path = OS.currentPath + OS.currentOS.separator + "webdrivers"

    implicit val driver = seleniumSystem.createDriver()
    driver.manage.window.maximize
    driver.manage.window.setPosition(new Point(0, 0))
    driver.manage.window.setSize(new Dimension(1024, 768))

    val subDirName = getTestClassName()
    val screenShotsBaseDirPath = OS.currentPath + OS.currentOS.separator + "ScreenShots" + OS.currentOS.separator + subDirName

    def after = {
      seleniumSystem.exit
    }
  }

  "Selenium and Specs2 on to several sites" should {

    "work on Google" in new scope {

      go to "http://www.google.com"

      waitVisibility("//input[@type='submit']")

      val screenShooter = ScreenShooter("view-google", screenShotsBaseDirPath, true)
      screenShooter.takeScreenShot()

      val form = waitAndGetFirstElement("//input[@id='lst-ib']")
      form.sendKeys("nekopiano")
      waitAndGetFirstElement("//input[@name='btnK']").submit

      waitVisibility("//div[@id='search']")
      screenShooter.takeScreenShot("search")

      scroll(1400)
      waitVisibility("//td[@class='b navend']")
      screenShooter.takeScreenShot("search results")
    }

    "work on Bing" in new scope {

      go to "http://www.bing.com"

      waitVisibility("//input[@type='search']")

      val screenShooter = ScreenShooter("view-bing", screenShotsBaseDirPath, true)
      screenShooter.takeScreenShot()

      val form = waitAndGetFirstElement("//input[@type='search']")
      form.sendKeys("nekopiano")
      waitAndGetFirstElement("//input[@type='submit']").submit

      waitVisibility("//ol[@id='b_results']")
      screenShooter.takeScreenShot("search")

      scroll(1400)
      waitVisibility("//nav[@role='navigation']")
      screenShooter.takeScreenShot("search results")
    }


  }

}
