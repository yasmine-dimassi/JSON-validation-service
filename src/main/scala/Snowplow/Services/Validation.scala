package Snowplow.Services

import Snowplow.Models.apiResponse
import Snowplow.Models.apiResponseJsonProtocol._
import Snowplow.Services.Schema.downloadSchema

import com.github.fge.jsonschema.core.report.{LogLevel, ProcessingReport}
import com.github.fge.jsonschema.main.{JsonSchemaFactory, JsonValidator}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.JsonInclude.Include
import spray.json._

object Validation {
  /**
   * validating a JSON instance against a unique JSON schema.
   * @param instance
   * @param SCHEMAID
   * @return response JSON string
   */
  def validateJson(instance: String, SCHEMAID: String): String = {
    //Jackson mapper init
    val mapper = new ObjectMapper
    mapper.setSerializationInclusion(Include.NON_NULL)

    //validator init
    val factory: JsonSchemaFactory = JsonSchemaFactory.byDefault()
    val validator: JsonValidator = factory.getValidator

    //get schema
    val schema = downloadSchema(SCHEMAID)

    //convert schema and instance into JsonNodes
    val schemaJsonNode = mapper.readTree(schema)
    val instanceJsonNode = mapper.readTree(instance)

    //validation
    val report: ProcessingReport = validator.validate(schemaJsonNode, instanceJsonNode)
    val status = if (report.isSuccess) "success" else "error"

    apiResponse("validateDocument", SCHEMAID, status, getReportMessage(report)).toJson.toString()
  }

  /**
   * Extract message from a ProcessingReport
   * @param report
   * @return message string
   */
  def getReportMessage(report: ProcessingReport): String = {
    val itr = report.iterator
    var message = ""

    while (itr.hasNext) {
      val processingMessage = itr.next
      if (processingMessage.getLogLevel == LogLevel.ERROR)
        message = processingMessage.toString
    }
    message
  }
}
