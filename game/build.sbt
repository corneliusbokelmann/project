val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "game",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += ("org.scalactic" %% "scalactic" % "3.2.14"),
    libraryDependencies += ("org.scalatest" %% "scalatest" % "3.2.14" % "test"),
    libraryDependencies += "org.scoverage" %% "sbt-scoverage" % "1.8.2",
    coverageEnabled := true
  )
