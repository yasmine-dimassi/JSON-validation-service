package Snowplow.Models

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class apiResponse(action: String, id: String, status: String, message: String)

object apiResponseJsonProtocol extends DefaultJsonProtocol {
  implicit val apiResponseJsonFormat: RootJsonFormat[apiResponse] = jsonFormat4(apiResponse.apply)
}
