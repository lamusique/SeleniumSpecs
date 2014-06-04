
organization := "com.nekopiano.scala"

name := "seleniumspecs"

version := "1.0"

//scalaVersion := "2.11.1"
scalaVersion := "2.10.4"

libraryDependencies ++= List(
//  "org.specs2" %% "specs2" % "2.3.12" % "test"
  "org.specs2" %% "specs2" % "2.3.12",
  "org.seleniumhq.selenium" % "selenium-java" % "2.42.0",
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"
)

scalacOptions in Test ++= Seq("-Yrangepos")

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.withSource := true

