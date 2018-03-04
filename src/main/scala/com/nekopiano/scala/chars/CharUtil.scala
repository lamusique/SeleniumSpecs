/**
  * Copyright (c) 2013-2018 nekopiano, Neko Piano
  * All rights reserved.
  * http://www.nekopiano.com
  */
package com.nekopiano.scala.chars

object CharUtil {

  def isFullWidth(c: Char) = {
    // To be more precise than bytes or ranges.
    Character.getName(c) match {
      // no name coerces to regard as full width
      case null => true
      case c if c.startsWith("FULLWIDTH") => true
      case c if c.startsWith("HALFWIDTH") => false
      case c if c.startsWith("HIRAGANA") => true
      case c if c.startsWith("KATAKANA") => true
      case c if c.startsWith("HANGUL") => true
      case c if c.startsWith("CJK") => true
      // ignore emojis at this time
      // regard others as half widths
      case _ => false
    }
  }

}
