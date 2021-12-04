ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.7"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.4.0"

lazy val root = (project in file("."))
  .settings(
    name := "Project0"
  )
