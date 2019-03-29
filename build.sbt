import Dependencies._

ThisBuild / name := "kamon-experiments"
ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.12.8"

lazy val `kamon-experiments` = (project in file("."))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      scalaLogging,
      logback,
      akkaActor,
      akkaTestkit % Test
    )
  )

lazy val commonSettings = smlBuildSettings ++ Seq(
  
)