package Snowplow.Routes

import Snowplow.Services.Validation.validateJson

import akka.http.scaladsl.server.Directives._

object ValidationRouter {
  val route =
    path("validate" / Segment) { SCHEMAID =>
      post {
        entity(as[String]) { instance =>
          complete(validateJson(instance, SCHEMAID))
        }
      }
    }
}
