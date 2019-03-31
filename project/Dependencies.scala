import sbt._

object Dependencies {

  object Version {
    val scalaLoggingVersion = "3.9.2"
    val logbackVersion = "1.2.3"
    val uuidGeneratorVersion = "3.2.0"
    val akkaVersion = "2.5.21"
    val kamonVersion = "1.1.3"
    val kamonPrometheusVersion = "1.1.1"
    val kamonZipkinVersion = "1.0.0"
  }

  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLoggingVersion
  val logback = "ch.qos.logback" % "logback-classic" % Version.logbackVersion
  val uuidGenerator = "com.fasterxml.uuid" % "java-uuid-generator" % Version.uuidGeneratorVersion

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % Version.akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % Version.akkaVersion

  val kamon = "io.kamon" %% "kamon-core" % Version.kamonVersion
  val kamonAkka = "io.kamon" %% "kamon-akka-2.5" % Version.kamonVersion
  val kamonPrometheus = "io.kamon" %% "kamon-prometheus" % Version.kamonPrometheusVersion
  val kamonZipkin = "io.kamon" %% "kamon-zipkin" % Version.kamonZipkinVersion

}
