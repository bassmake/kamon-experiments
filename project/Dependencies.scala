import sbt._

object Dependencies {

  object Version {
    val scalaLoggingVersion = "3.9.2"
    val logbackVersion = "1.2.3"
    val akkaVersion = "2.5.21"
  }

  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLoggingVersion
  val logback = "ch.qos.logback" % "logback-classic" % Version.logbackVersion

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % Version.akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % Version.akkaVersion

}
