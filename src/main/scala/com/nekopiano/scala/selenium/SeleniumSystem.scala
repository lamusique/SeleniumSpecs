/**
  * Copyright (c) 2013-2018 nekopiano, Neko Piano
  * All rights reserved.
  * http://www.nekopiano.com
  */
package com.nekopiano.scala.selenium

import com.nekopiano.scala.selenium.SeleniumSystem.Browser
import com.typesafe.scalalogging.LazyLogging
//import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.RemoteWebDriver

case class SeleniumSystem(browser: Browser.Value, driverDirectoryPath: String) extends LazyLogging {

  lazy val os = OS.currentOS

  private lazy val driver: RemoteWebDriver = browser match {
    case Browser.Chrome => new ChromeDriver()
    case Browser.Firefox => new FirefoxDriver()
    case Browser.InternetExprolor => new InternetExplorerDriver()
    case _ => new ChromeDriver()
  }

  private def setBrowserInProperty(driverDirectoryPath: String) = {
    logger.debug("try to set into properties: " + driverDirectoryPath + os.separator + browser.getDriverFileName(os))
    System.setProperty("webdriver." + browser.propertyName + ".driver", driverDirectoryPath + os.separator + browser.getDriverFileName(os))
    logger.debug("webdriver." + browser.propertyName + ".driver" + "=" + System.getProperty("webdriver." + browser.propertyName + ".driver"))
  }

  def createDriver() = {
    setBrowserInProperty(driverDirectoryPath)
    driver
  }

  def exit(): Unit = {
    driver.close
    driver.quit
  }
}
object SeleniumSystem {


  object Browser extends Enumeration {
    protected case class Val(driverName: String, propertyName: String) extends super.Val {
      def getDriverFileName(os:OS) = {
        os match {
          case OS.Mac => driverName
          case OS.Windows =>  driverName + ".exe"
          case OS.Linux => driverName
          case _ => driverName
        }
      }
    }
    implicit def valueToVal(x: Value): Val = x.asInstanceOf[Val]

    val Firefox = Val("geckodriver", "gecko")
    val InternetExprolor = Val("IEDriverServer", "ie")
    val Chrome = Val("chromedriver", "chrome")
    //val Safari = Val("safari")
    //val Opera = Val("opera")

  }

  def apply(browser: Browser.Value, driverDirectoryPath: String): SeleniumSystem = new SeleniumSystem(browser, driverDirectoryPath)

}

