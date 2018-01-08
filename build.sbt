import sbtcrossproject.{crossProject, CrossType}

organization := "org.indyscala.try.scalajs"
version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.4"

homepage := Some(url("https://github.com/indyscala/trying-scalajs"))
licenses := Seq(
  "MIT" -> url("http://opensource.org/licenses/MIT")
) 

val circeVersion = "0.9.0"

lazy val pwned =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Full)
    .settings(
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-core" % circeVersion,
        "io.circe" %%% "circe-generic" % circeVersion,
        "io.circe" %%% "circe-parser" % circeVersion,
      ),
    )
    .jsSettings(
      name := "pwned-app",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.4",
        "com.lihaoyi" %%% "scalatags" % "0.6.7",
      ),
    )
    .jvmSettings(
      name := "pwned-server",
      description := "Unimplemented"
    )

val pwnedJS = pwned.js
val pwnedJVM = pwned.jvm
