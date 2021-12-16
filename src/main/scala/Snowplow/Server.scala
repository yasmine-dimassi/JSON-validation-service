package Snowplow

import Snowplow.Routes.{SchemaRouter, ValidationRouter}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import scala.util.{Failure, Success}

object Server extends App  {
  implicit val system = ActorSystem("api")
  implicit val materialiser = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val host = "0.0.0.0"
  val port = 80
  val routes = SchemaRouter.route ~ ValidationRouter.route

  val bindingFuture = Http().bindAndHandle(routes, host, port)

  bindingFuture.onComplete {
    case Success(_) =>   println(s"- Server online at http://$host:$port/\nPress ENTER to stop...")
    case Failure(error) => println(s"Failed: ${error.getMessage}")
  }

  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}

