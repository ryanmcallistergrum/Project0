ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.11.12"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.25"
libraryDependencies += "com.lihaoyi" %% "upickle" % "1.4.2"
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.7.8"

assemblyJarName := "FarmSim v0.1.jar"

lazy val root = (project in file("."))
  .settings(
    name := "Project0",
    version := "0.1",
    scalaVersion := "2.11.12",
    mainClass := Some("Main")
)

assemblyMergeStrategy := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
}