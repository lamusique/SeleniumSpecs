package com.nekopiano.scala.chars

class CharUtilSpecs extends org.specs2.mutable.Specification {

  import CharUtil._

  "Width detecting should" >> {
    "work in case a" >> {

      val text = "全角han漢字\\~～ｶﾅ"
      //全全半半半全全半半全半半
      val expected = List(true, true, false, false, false, true, true, false, false, true, false, false)

      //val a = text.toList.map(c -> isFullWidth(c))
      val result = text.toList.map(isFullWidth(_))
      println("result=" + result)

      expected == result
    }

    "work in case b" >> {

      val text = "㐀㐂hän㔰㔓-$～ﾰ￩"
      //全全半半半全全半半全半半
      val expected = List(true, true, false, false, false, true, true, false, false, true, false, false)

      //val a = text.toList.map(c -> isFullWidth(c))
      val result = text.toList.map(isFullWidth(_))
      println("result=" + result)

      expected == result
    }

    "work in case c" >> {

      val text = "루ㅚhan漢字_=～ｶﾅ"
      //全全半半半全全半半全半半
      val expected = List(true, true, false, false, false, true, true, false, false, true, false, false)

      //val a = text.toList.map(c -> isFullWidth(c))
      val result = text.toList.map(isFullWidth(_))
      println("result=" + result)

      expected == result
    }

    "work in case d" >> {

      //鿍鿎鿏
      val text = "\u9FCD\u9FCE\u9FCF"
      val expected = List(true, true, true)

      //val a = text.toList.map(c -> isFullWidth(c))
      val result = text.toList.map(isFullWidth(_))
      println("result=" + result)

      expected == result
    }

  }

}
