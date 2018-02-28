
organization := "com.nekopiano.scala"

name := "seleniumspecs"

version := "0.1.1"

//scalaVersion := "2.11.1"
//scalaVersion := "2.10.4"
scalaVersion := "2.12.4"

scalacOptions += "-Ypartial-unification"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1" withSources()
libraryDependencies += "org.typelevel" %% "cats-free" % "1.0.1" withSources()

libraryDependencies += "org.specs2" %% "specs2-core" % "4.0.3" withSources()

libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "3.9.1" withSources()
libraryDependencies += "org.seleniumhq.selenium" % "selenium-support" % "3.9.1" withSources()

libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.4.0" withSources()

//libraryDependencies ++= List(
////  "org.specs2" %% "specs2" % "2.3.12" % "test"
//  "org.specs2" %% "specs2" % "2.3.12",
//  "org.seleniumhq.selenium" % "selenium-java" % "2.42.0",
//  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"
//)

scalacOptions in Test ++= Seq("-Yrangepos")
