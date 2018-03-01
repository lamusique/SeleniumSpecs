/**
 * Copyright (c) 2013-2014 nekopiano, Neko Piano
 * All rights reserved.
 * http://www.nekopiano.com
 */
package com.nekopiano.scala.selenium

import java.io.ByteArrayInputStream
import java.util.Locale
import java.util.concurrent.atomic.AtomicInteger

import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.OutputType
import better.files._
import better.files.Dsl._
import com.github.nscala_time.time.Imports._

/**
 * ScreenShooter.
 *
 * @author nekopiano
 */
case class ScreenShooter(testName: String, baseImageDirPath: String)(implicit driver: RemoteWebDriver) {

  object Counter {
    val counter = new AtomicInteger()
    def count(): Int = counter.incrementAndGet()
    def countFixedDigit(): String = {
      "%03d".format(counter.incrementAndGet())
    }
  }

  def takeScreenShot(message:String = null) {

    val takesScreenShot = driver.asInstanceOf[TakesScreenshot]
    val bytes = takesScreenShot.getScreenshotAs(OutputType.BYTES)
    val shotDateTime = DateTime.now
    val number = Counter.countFixedDigit
    val testNo = testName + '-' + number


    import javax.imageio.ImageIO
    val bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes))

    val g = bufferedImage.getGraphics
    import java.awt.{Color, Font}
    g.setColor(Color.red)
    g.setFont(new Font("SansSerif", Font.BOLD, 21))
    val startLocaleTimestamp = DateTimeFormat.forPattern("HH:mm:ss dd MMM yyyy").withLocale(Locale.UK).print(shotDateTime)
    g.drawString(startLocaleTimestamp, bufferedImage.getWidth - 400, 25)
    g.drawRect(bufferedImage.getWidth - 410, 5, 270, 30)
    val optMessage = Option(message) match {
      case Some(message) => {
        g.drawString(testNo, bufferedImage.getWidth - 400, 50)
        g.drawString(message, bufferedImage.getWidth - 400, 75)
      }
      case None => // do nothing
    }
    g.dispose()


    val file = baseImageDirPath / (testNo + ".png")
    import javax.imageio.ImageIO
    ImageIO.write(bufferedImage, "png", file.toJava)

    println("shot: " + file)
  }

}
