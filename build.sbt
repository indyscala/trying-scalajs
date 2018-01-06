enablePlugins(ScalaJSPlugin)

name := "trying-scalajs"
organization := "org.indyscala.try.scalajs"
version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.4"

homepage := Some(url("https://github.com/indyscala/trying-scalajs"))
licenses := Seq(
  "MIT" -> url("http://opensource.org/licenses/MIT")
) 

// This is an application with a main method
scalaJSUseMainModuleInitializer := true
