/**
 * Copyright (c) 2013-2018 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.selenium

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.WebDriver
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.Select

/**
 * SeleniumUtilityTrait.
 *
 * @author nekopiano
 */
trait SeleniumUtilityTrait extends LazyLogging {

  implicit var screenShotsBaseDirPath = ""
  implicit var driver: RemoteWebDriver = null

  // element operations

  def waitVisibility(xPath: String, sec: Long = 5)(implicit driver: RemoteWebDriver): Unit = {
    waitVisibilityBy(By.xpath(xPath), sec)
  }

  def waitInvisibility(xPath: String, sec: Long = 5)(implicit driver: RemoteWebDriver): Unit = {
    waitInvisibilityBy(By.xpath(xPath), sec)
  }

  def waitVisibilityBy(by: By, sec: Long = 5)(implicit driver: RemoteWebDriver) = {
    val myDynamicElement = (new WebDriverWait(driver, sec))
      .until(ExpectedConditions.visibilityOfElementLocated(by))
    println("appeared: myDynamicElement=" + myDynamicElement)
  }

  def waitInvisibilityBy(by: By, sec: Long = 5)(implicit driver: RemoteWebDriver) = {
    val myDynamicElement = (new WebDriverWait(driver, sec))
      .until(ExpectedConditions.invisibilityOfElementLocated(by))
    println("disappeared: myDynamicElement=" + myDynamicElement)
  }

  def getElements(xPath: String)(implicit driver: RemoteWebDriver): Seq[WebElement] = {
    getElements(By.xpath(xPath))
  }

  def getFirstElement(xPath: String)(implicit driver: RemoteWebDriver): WebElement = {
    getFirstElement(By.xpath(xPath))
  }

  def getSingleElement(xPath: String)(implicit driver: RemoteWebDriver): WebElement = {
    getSingleElement(By.xpath(xPath))
  }

  def getElements(by: By)(implicit driver: RemoteWebDriver) = {
    import collection.JavaConversions._
    val elements = driver.findElements(by)
    println("getElements: " + by + " got elements=" + elements)
    elements.toSeq
  }

  def getFirstElement(by: By)(implicit driver: RemoteWebDriver) = {
    import collection.JavaConversions._
    val element = driver.findElements(by).head
    println("getFirstElement: " + by + " got an element=" + element)
    element
  }

  def getSingleElement(by: By)(implicit driver: RemoteWebDriver) = {
    import collection.JavaConversions._
    val elements = driver.findElements(by)
    if (elements.size > 1) { throw new IllegalArgumentException(by + " could get several elements! Please specify one element. elements=" + elements) }
    val element = elements.head
    println("getSingleElement: " + by + " got an element=" + element)
    element
  }

  def existsElement(driver: RemoteWebDriver, xPath: String) = {
    val element = driver.findElements(By.xpath(xPath))
    println("check element=" + element)
    !element.isEmpty
  }

  def waitAndGetElements(xPath: String, sec: Long = 5)(implicit driver: RemoteWebDriver): Seq[WebElement] = {
    waitVisibility(xPath, sec)
    getElements(xPath)
  }

  def waitAndGetSingleElement(xPath: String, sec: Long = 5)(implicit driver: RemoteWebDriver): WebElement = {
    waitVisibility(xPath, sec)
    getSingleElement(xPath)
  }

  def waitAndGetFirstElement(xPath: String, sec: Long = 5)(implicit driver: RemoteWebDriver): WebElement = {
    waitVisibility(xPath, sec)
    getFirstElement(xPath)
  }

  def waitAndGetSingleElementBy(by: By, sec: Long = 5)(implicit driver: RemoteWebDriver) = {
    waitVisibilityBy(by, sec)
    getSingleElement(by)
  }

  def waitAndGetFirstElementBy(by: By, sec: Long = 5)(implicit driver: RemoteWebDriver) = {
    waitVisibilityBy(by, sec)
    getFirstElement(by)
  }

  // miscellaneous

  def createSelect(element: WebElement) = new Select(element)

  // move operations

  def hoverElement(element: WebElement)(implicit driver: RemoteWebDriver) = {
    val actions = new Actions(driver)
    actions.moveToElement(element).perform
    element
  }

  def scroll(vertical: Int = 0, horizontal: Int = 0)(implicit driver: RemoteWebDriver) {
    val jsExe = driver.asInstanceOf[JavascriptExecutor]
    // The following doesn't work...
    // jsExe.executeScript("javascript:document.getElementsByClassName('sectionheader')[2].scrollIntoView(true);");
    jsExe.executeScript("window.scrollBy(" + horizontal + "," + vertical + ");");
  }

  def swithToSubWindow(implicit driver: RemoteWebDriver) = {
    // examples of handles (String):
    // handle=CDwindow-5A9189A2-0D07-42C1-B76B-A82A26FA0D3D
    // handle=CDwindow-CC6F1AB5-7EBC-4C90-B958-5CD984219683

    val currentHandle = driver.getWindowHandle
    println("currentHandle=" + currentHandle)

    import scala.collection.JavaConversions._
    //Get all the window handles in a set
    val handles = driver.getWindowHandles
    println("handles=" + handles)
    val subWindowHandle = handles.collectFirst {
      case handle: String if (handle != currentHandle) => { handle }
    }.get

    println("subWindowHandle=" + subWindowHandle)
    driver.switchTo.window(subWindowHandle)
    // driver.switchTo.activeElement
    println("driver.getCurrentUrl=" + driver.getCurrentUrl)
    println("driver.getTitle=" + driver.getTitle)
    currentHandle
  }

  /**
   * This object is part of Selenium DSL.
   */
  object go {

    /**
     * Sends the browser to the passed URL.
     *
     * @param url the URL to which to send the browser
     * @param driver the <code>WebDriver</code> with which to drive the browser
     */
    def to(url: String)(implicit driver: WebDriver) {
      driver.get(url)
    }
  }

}
