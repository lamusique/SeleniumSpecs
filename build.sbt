
organization := "com.nekopiano.scala"

name := "seleniumspecs"

version := "0.1.2"

scalaVersion := "2.12.2"


//libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.8.9" % "test")
libraryDependencies += "org.specs2" %% "specs2-core" % "3.9.4" withSources()
libraryDependencies += "org.seleniumhq.selenium" % "selenium-java" % "3.4.0" withSources()
libraryDependencies += "org.seleniumhq.selenium" % "selenium-support" % "3.4.0" withSources()

//libraryDependencies += "org.seleniumhq.selenium" % "selenium-chrome-driver" % "3.3.0"


libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.0.0" withSources()

libraryDependencies += "org.typelevel" %% "cats" % "0.9.0" withSources()


scalacOptions in Test ++= Seq("-Yrangepos")


//EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
//EclipseKeys.withSource := true
