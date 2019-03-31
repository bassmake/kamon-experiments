import Dependencies._

ThisBuild / name := "kamon-experiments"
ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.12.8"
ThisBuild / scalafmtOnCompile := true

lazy val `kamon-experiments` = (project in file("."))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      scalaLogging,
      logback,
      uuidGenerator,
      akkaActor,
      kamonAkka,
      kamonPrometheus,
      kamonZipkin,
      
      akkaTestkit % Test
    )
  )

lazy val commonSettings = smlBuildSettings ++ Seq(
  
)