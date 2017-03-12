package com.nekopiano.scala.selenium

/**
  * Created on 13/03/2017.
  */
object OS {
  case object Mac extends OS("mac", "/")
  case object Windows extends OS("win", "\\")
  case object Linux extends OS("nux", "/")
  case object Others extends OS("others", "/")

  def currentOS = System.getProperty("os.name").toLowerCase match {
      case OS.Mac.pattern(a) => OS.Mac
      case OS.Windows.pattern(a) => OS.Windows
      case OS.Linux.pattern(a) => OS.Linux
      case _ => OS.Others
  }
}
sealed abstract class OS(val code:String, val separator:String) {
  val name = toString
  val pattern = (".*" + code + ".*").r
}
