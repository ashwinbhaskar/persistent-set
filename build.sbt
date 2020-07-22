import Dependencies._

ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "io.github.ashwinbhaskar"

lazy val root = (project in file("."))
  .settings(
    name := "persistent-hash-set",
    scalaVersion := "0.24.0",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.5",
    testFrameworks += new TestFramework("munit.Framework")
  )