
organization := "com.nekopiano.scala"

name := "seleniumspecs"

version := "0.1.1"

scalaVersion := "2.12.1"


//libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.8.9" % "test")
libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.9" withSources()
libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "3.3.0" withSources()
libraryDependencies += "org.seleniumhq.selenium" % "selenium-support" % "3.3.0" withSources()

//libraryDependencies += "org.seleniumhq.selenium" % "selenium-chrome-driver" % "3.3.0"


libraryDependencies += "com.github.pathikrit" %% "better-files" % "2.17.1" withSources()

scalacOptions in Test ++= Seq("-Yrangepos")


//EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
//EclipseKeys.withSource := true
