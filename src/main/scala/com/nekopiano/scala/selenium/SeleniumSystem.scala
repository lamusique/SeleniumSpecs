package com.nekopiano.scala.selenium

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

case class SeleniumSystem(driver: WebDriver) {

  def exit(): Unit = {
    driver.close
    driver.quit
  }
}
object SeleniumSystem {

  lazy val path = OS.currentOS match {
    case OS.Mac => "/webdrivers/chromedriver"
    case OS.Windows => "\\webdrivers\\chromedriver.exe"
    case OS.Linux => "/webdrivers/chromedriver"
    case _ => "/webdrivers/chromedriver"
  }

  def getChrome() = {
    System.setProperty("webdriver.chrome.driver",
      OS.currentPath + SeleniumSystem.path)
    new ChromeDriver()
  }

}
