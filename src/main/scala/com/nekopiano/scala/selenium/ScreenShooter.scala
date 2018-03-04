/**
  * Copyright (c) 2013-2018 nekopiano, Neko Piano
  * All rights reserved.
  * http://www.nekopiano.com
  */
package com.nekopiano.scala.selenium

import java.awt.RenderingHints
import java.io.ByteArrayInputStream
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger

import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.OutputType
import better.files._
import better.files.Dsl._
import com.nekopiano.scala.chars.CharUtil

// to evade a naming conflict
//import com.github.nscala_time.time.Imports._
import com.github.nscala_time.time.Imports.DateTime
import com.github.nscala_time.time.Imports.DateTimeFormat

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent._
import scala.concurrent.duration._

/**
  * ScreenShooter.
  *
  * @author nekopiano
  */
case class ScreenShooter(testName:String, imageFileDirPath: String, shouldStamp: Boolean = false)(implicit driver: RemoteWebDriver) extends LazyLogging {

  {
    val imageDir = File(imageFileDirPath)
    //      imageDir.createIfNotExists(createParents = true)
    if (!imageDir.exists) {
      mkdirs(imageDir)
      logger.debug("Create Folder:" + imageDir)
    }
  }


  object Counter {
    val counter = new AtomicInteger()

    def count(): Int = counter.incrementAndGet()

    def countFixedDigit(): String = {
      "%03d".format(counter.incrementAndGet())
    }
  }


  def takeScreenShot(message: String) {
    takeScreenShot(true, message)
  }

  def takeScreenShot(shouldStamp: Boolean = this.shouldStamp, message: String = null) {

    val takesScreenShot = driver.asInstanceOf[TakesScreenshot]
    val bytes = takesScreenShot.getScreenshotAs(OutputType.BYTES)
    val shotDateTime = DateTime.now
    val startLocaleTimestamp = DateTimeFormat.forPattern("HH:mm:ss dd MMM yyyy").withLocale(Locale.UK).print(shotDateTime)
    val number = Counter.countFixedDigit
    val testNo = testName + '-' + number


    import javax.imageio.ImageIO
    val bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes))

    if (shouldStamp) {
      val g = bufferedImage.createGraphics
      g.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

      import java.awt.{Color, Font}

      g.setFont(new Font("SansSerif", Font.BOLD, 21))

      val optMessage = Option(message)
      val numberOfTheLongestChars = List(startLocaleTimestamp, testNo, optMessage.getOrElse(""))
        .map(_.toList.map(CharUtil.isFullWidth(_)).map(if(_) 2.2 else 1).sum).max
      // 21 points = 28 pixels
      val oneCharPixels = 28
      val fontHeight = (oneCharPixels * .8).toInt
      val width = (numberOfTheLongestChars * oneCharPixels / 2.5).toInt
      val margin = 10
      val descender = 4

      optMessage match {
        case Some(message) => {
          g.setColor(new Color(255, 0, 0, 127))
          //g.fillRect(bufferedImage.getWidth - width + margin, 5, width + margin, 80)
          g.fillRect(0, bufferedImage.getHeight - fontHeight * 3, width + margin * 2, fontHeight * 3)
          g.setColor(new Color(255, 255, 255, 255))
//          g.drawString(testNo, bufferedImage.getWidth - width, 50)
//          g.drawString(message, bufferedImage.getWidth - width, 75)
          g.drawString(testNo, 0, bufferedImage.getHeight - fontHeight - descender)
          g.drawString(message, 0, bufferedImage.getHeight - descender)

          //      g.drawString(startLocaleTimestamp, bufferedImage.getWidth - width, 25)
          g.drawString(startLocaleTimestamp, 0, bufferedImage.getHeight - fontHeight * 2 - descender)
        }
        case None => {
          g.setColor(new Color(255, 0, 0, 127))
//          g.fillRect(bufferedImage.getWidth - width + margin, 5, width + margin, 30)
          g.fillRect(0, bufferedImage.getHeight - fontHeight * 2, width + margin * 2, fontHeight * 2)

          g.setColor(new Color(255, 255, 255, 255))
          g.drawString(testNo, 0, bufferedImage.getHeight - descender)
          //      g.drawString(startLocaleTimestamp, bufferedImage.getWidth - width, 25)
          g.drawString(startLocaleTimestamp, 0, bufferedImage.getHeight - fontHeight - descender)
        }
      }

      g.dispose()
    }

    val file = imageFileDirPath / (testNo + ".png")
    import javax.imageio.ImageIO
    ImageIO.write(bufferedImage, "png", file.toJava)

    logger.debug("shot: " + file)
  }


}