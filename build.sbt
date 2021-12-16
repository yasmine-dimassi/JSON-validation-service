name := "Snowplow_Test"

version := "0.1"

scalaVersion := "2.11.8"

val akkaActorsVersion = "2.5.20"
val akkaStreamsVersion = "2.5.20"
val akkaHttpVersion = "10.1.7"


libraryDependencies ++= Seq(
  "com.github.fge" % "json-schema-validator" % "2.2.6",
  "com.typesafe.akka" %% "akka-actor" % akkaActorsVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaStreamsVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "io.spray" %%  "spray-json" % "1.3.6",
  "com.lihaoyi" %% "upickle" % "0.7.1",
  "com.lihaoyi" %% "os-lib" % "0.2.7"
)
