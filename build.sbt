
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

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.18.0" withSources()


libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" withSources()
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0" withSources()



scalacOptions in Test ++= Seq("-Yrangepos")
