ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.11.12"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.25"
libraryDependencies += "com.lihaoyi" %% "upickle" % "1.4.2"
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.7.8"

lazy val root = (project in file("."))
  .settings(
    name := "Project0"
  )