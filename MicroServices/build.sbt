name := "FileLoaderMicroService"

version := "1.0"

scalaVersion := "2.12.2"

val akkaVersion = "2.4.12"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.13",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.12",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,

"com.typesafe.akka" %% "akka-http-core" % "10.0.5",
"com.typesafe.akka" %% "akka-http" % "10.0.5",
"com.typesafe.akka" %% "akka-http-testkit" % "10.0.5",

  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.3",
  "io.spray"          %%  "spray-json"    % "1.2.5",

"com.typesafe.akka" %% "akka-http-jackson" % "10.0.5",
"com.typesafe.akka" %% "akka-http-xml" % "10.0.5",

  "net.liftweb" % "lift-json_2.11" % "3.1.0-M2"

  exclude("log4j", "log4j")
)
        