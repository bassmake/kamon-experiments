import sbt._

object Dependencies {

  object Version {
    val scalaLoggingVersion = "3.9.2"
    val logbackVersion = "1.2.3"
    val uuidGeneratorVersion = "3.2.0"
    val akkaVersion = "2.5.21"
    val kamonVersion = "1.1.3"
    val kamonLogbackVersion = "1.0.6"
  }

  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLoggingVersion
  val logback = "ch.qos.logback" % "logback-classic" % Version.logbackVersion
  val uuidGenerator = "com.fasterxml.uuid" % "java-uuid-generator" % Version.uuidGeneratorVersion

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % Version.akkaVersion
  val akkaCluster = "com.typesafe.akka" % "akka-cluster" % Version.akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % Version.akkaVersion

  val kamon = "io.kamon" %% "kamon-core" % Version.kamonVersion
  val kamonLogback = "io.kamon" %% "kamon-logback" % Version.kamonLogbackVersion
  val kamonAkka = "io.kamon" %% "kamon-akka-2.5" % Version.kamonVersion

}
