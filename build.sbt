ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.7"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.25"

lazy val root = (project in file("."))
  .settings(
    name := "Project0"
  )