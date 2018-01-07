import sbtcrossproject.{crossProject, CrossType}

organization := "org.indyscala.try.scalajs"
version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.4"

homepage := Some(url("https://github.com/indyscala/trying-scalajs"))
licenses := Seq(
  "MIT" -> url("http://opensource.org/licenses/MIT")
) 

lazy val pwned =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Full)
    .settings(
      libraryDependencies ++= Seq(
        "io.circe" %%% "circe-core" % "0.9.0"
      ),
    )
    .jsSettings(
      name := "pwned-app",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.4"
      ),
    )
    .jvmSettings(
      name := "pwned-server",
      description := "Unimplemented"
    )

val pwnedJS = pwned.js
val pwnedJVM = pwned.jvm
