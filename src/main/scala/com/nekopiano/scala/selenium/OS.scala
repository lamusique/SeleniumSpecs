/**
  * Copyright (c) 2013-2018 nekopiano, Neko Piano
  * All rights reserved.
  * http://www.nekopiano.com
  */
package com.nekopiano.scala.selenium

import com.typesafe.scalalogging.LazyLogging

/**
  * Created on 13/03/2017.
  */
object OS extends LazyLogging {
  case object Mac extends OS("mac", "/")
  case object Windows extends OS("win", "\\")
  case object Linux extends OS("nux", "/")
  case object Others extends OS("others", "/")

  logger.debug("System.getProperty(\"os.name\")=" + System.getProperty("os.name"))

  lazy val currentOS = System.getProperty("os.name").toLowerCase match {
      case OS.Mac.pattern(a) => OS.Mac
      case OS.Windows.pattern(a) => OS.Windows
      case OS.Linux.pattern(a) => OS.Linux
      case _ => OS.Others
  }
  def currentPath = System.getProperty("user.dir")
}
sealed abstract class OS(val code:String, val separator:String) {
  val name = toString
  val pattern = (".*(?i)(" + code + ").*").r
}
