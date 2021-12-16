# JSON validation service

JSON validation service is a REST-service used to upload JSON Schemas and store them at unique URI and validate JSON documents against previously uploaded schemas.

## Requirements
Scala version: 2.11.8

Sbt version: 1.2.8

JDK version: 11

## Build and run 

To build and run the server on localhost execute 

`sbt run`


## Usage

#### Upload Schema 
Send an HTTP Post request to "/schema/(schema_id)" with the schema to upload in the body

Example: 

```curl http://localhost/schema/config-schema -X POST -d @config-schema.json```

#### Download Schema
Send an HTTP GET request to "/schema/(schema_id)"

Example

```curl http://localhost/schema/config-schema```

#### Validate JSON
Send an HTTP POST request to "/schema/(schema_id)" with the JSON to validate in the body

Example

```curl http://localhost/validate/config-schema -X POST -d @config.json```