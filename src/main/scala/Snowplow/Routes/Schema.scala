package Snowplow.Routes

import Snowplow.Services.Schema.{downloadSchema, uploadSchema}

import akka.http.scaladsl.server.Directives._

object SchemaRouter {
  val route =
    path("schema" / Segment) { SCHEMAID =>
      concat(
        get {
          complete(downloadSchema(SCHEMAID))
        },
        post {
          entity(as[String]) { schema =>
            complete(uploadSchema(schema, SCHEMAID))
          }
        }
      )
    }
}
