package org.indyscala.`try`.scalajs

import scala.scalajs.js.annotation._
import org.scalajs.dom._

@JSExportTopLevel("PwnedApp")
object PwnedApp {

  @JSExport
  def main(container: html.Div): Unit = {
    println("Have you been pwned?")
  }
}
