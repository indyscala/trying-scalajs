package org.indyscala.`try`.scalajs

import io.circe.Error
import org.scalajs.dom
import org.scalajs.dom.ext.AjaxException

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.util.{Failure, Success}

@JSExportTopLevel("PwnedApp")
object PwnedApp {

  val PwnedApi = "https://haveibeenpwned.com/api"
  val StandardHeaders = Map(
    "Accept" -> "application/vnd.haveibeenpwned.v2+json",
  )

  val BreachListId = "breachList"
  val DebugPanelId = "debugPanel"
  val EmailInputId = "emailInput"

  @JSExport
  def main(container: dom.html.Div): Unit = {
    println("Have you been pwned?  Let's find out...")
    container.appendChild(html())
  }

  @JSExport
  def checkPwnage(): Unit = {
    import org.scalajs.dom.ext.Ajax
    import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

    val email = dom.document.getElementById(EmailInputId).asInstanceOf[dom.html.Input].value
    Ajax.get(url = s"$PwnedApi/breachedaccount/$email", headers = StandardHeaders)
      .onComplete {
        case Success(xhr) =>
          updateDebugPanel(xhr.responseText)
          if (xhr.status == 200) {
            breaches(xhr.responseText) match {
              case Left(e) => println(s"JSON parsing failed: $e")
              case Right(list) => updateBreachList(list)
            }
          }
        case Failure(e: AjaxException) =>
          val msg = if (e.xhr.status == 404)
            "Not pwned!**\n\n\n**Yet...as far as we know."
          else
            s"Failed with status: ${e.xhr.status}"
          updateDebugPanel(msg)
          updateBreachList(List())
        case Failure(t: Throwable) =>
          updateDebugPanel(s"Unexpected error: ${t.getMessage}")
          updateBreachList(List())
      }
  }

  def html(): dom.Node = {
    import scalatags.JsDom.all._

    div(
      p("Let's find out...  Enter an email address or username below to find out if any of your accounts have been breached."),
      label("Email or username: "), input(id := EmailInputId),
      button(onclick := "PwnedApp.checkPwnage()",
        "Check"),
      ul(id := BreachListId),
      hr(marginTop := "100px"),
      b("Debug:"),
      br,
      div(id := DebugPanelId, width := "800px"),
    ).render
  }

  def updateBreachList(breaches: List[Breach]): Unit = {
    import scalatags.JsDom.all._

    val ul = dom.document.getElementById(BreachListId).asInstanceOf[dom.html.UList]
    ul.innerHTML = ""
    breaches.foreach(b => ul.appendChild(li(span(s"${b.Name} on ${b.BreachDate}")).render))
  }

  def updateDebugPanel(response: String): Unit = {
    import scalatags.JsDom.all._

    val debugText = Option(response).getOrElse("")

    val panel = dom.document.getElementById(DebugPanelId).asInstanceOf[dom.html.Div]
    panel.innerHTML = ""
    panel.appendChild(pre(debugText).render)
  }

  def breaches(json: String): Either[Error, List[Breach]] = {
    import io.circe.generic.auto._
    import io.circe.parser.decode
    decode[List[Breach]](json)
  }
}
