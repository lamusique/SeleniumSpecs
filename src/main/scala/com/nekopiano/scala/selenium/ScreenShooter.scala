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
case class ScreenShooter(testName:String, imageFileDirPath: String)(implicit driver: RemoteWebDriver) extends LazyLogging {

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

  def takeScreenShot(shouldStamp: Boolean = false, message: String = null) {

    val takesScreenShot = driver.asInstanceOf[TakesScreenshot]
    val bytes = takesScreenShot.getScreenshotAs(OutputType.BYTES)
    val shotDateTime = DateTime.now
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

      val optMessage = Option(message) match {
        case Some(message) => {
          g.setColor(new Color(255, 0, 0, 127))
          g.fillRect(bufferedImage.getWidth - 420, 5, 420, 80)
          g.setColor(new Color(255, 255, 255, 255))
          g.drawString(testNo, bufferedImage.getWidth - 400, 50)
          g.drawString(message, bufferedImage.getWidth - 400, 75)
        }
        case None => {
          g.setColor(new Color(255, 0, 0, 127))
          g.fillRect(bufferedImage.getWidth - 420, 5, 420, 30)
        }
      }

      g.setColor(new Color(255, 255, 255, 255))
      val startLocaleTimestamp = DateTimeFormat.forPattern("HH:mm:ss dd MMM yyyy").withLocale(Locale.UK).print(shotDateTime)
      g.drawString(startLocaleTimestamp, bufferedImage.getWidth - 400, 25)

      g.dispose()
    }

    val file = imageFileDirPath / (testNo + ".png")
    import javax.imageio.ImageIO
    ImageIO.write(bufferedImage, "png", file.toJava)

    logger.debug("shot: " + file)
  }

}
