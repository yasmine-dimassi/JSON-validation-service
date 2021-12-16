package Snowplow.Services

import Snowplow.Models.apiResponse
import Snowplow.Models.apiResponseJsonProtocol.apiResponseJsonFormat

import java.io.{FileNotFoundException, IOException}
import com.fasterxml.jackson.databind.ObjectMapper
import spray.json.enrichAny

object Schema {
  /**
   * Upload a schema by storing it in a local json file
   * @param schema
   * @param SCHEMAID
   * @return response string
   */
  def uploadSchema(schema: String, SCHEMAID: String): String = {
    val fileName = SCHEMAID + ".json"
    var status = "Success"
    var message = ""
    println("schema" + schema)

    if (isJSONValid(schema)) {
      try {
        os.write(os.pwd/"tmp"/fileName, schema)
      }
      catch {
        case e: Throwable => {
          println(os.pwd)
          status = "Error"
          message = e.getMessage
        }
      }
    }
    else {
      status = "Error"
      message = "Invalid JSON"
    }
    apiResponse("uploadSchema", SCHEMAID, status, message).toJson.toString()
  }

  /**
   * download a schema from the schema local files
   * @param SCHEMAID
   * @return schema JSON string
   */
  def downloadSchema(SCHEMAID: String): String = {
    val fileName = SCHEMAID + ".json"
    var response = ""

    try {
      val jsonString = os.read(os.pwd/"tmp"/fileName)
      response = ujson.read(jsonString).toString()
    }
    catch {
      case e: FileNotFoundException => response = "Schema ID not found"
      case _ => response = "Error reading schema"
    }
    response
  }

  /**
   * Verify if a JSON string is valid or not
   * @param jsonInString
   * @return Boolean
   */
  def isJSONValid(jsonInString: String): Boolean = {
    try {
      val mapper = new ObjectMapper
      mapper.readTree(jsonInString)
      true
    }
    catch {
      case e: IOException => false
    }
  }
}
