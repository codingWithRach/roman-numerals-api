name := """roman-numerals-api"""
organization := "com.techreturners"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"

libraryDependencies += guice
libraryDependencies += "com.google.inject" % "guice" % "5.1.0"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies += "org.scalatestplus" %% "mockito-3-12" % "3.2.10.0" % Test


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.techreturners.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.techreturners.binders._"
