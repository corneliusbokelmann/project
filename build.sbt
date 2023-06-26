val scala3Version = "3.2.2"
val javafxSdkPath = "C:/Program Files/OpenJFX/javafx-sdk-20.0.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "game",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += ("org.scalactic" %% "scalactic" % "3.2.14"),
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
    libraryDependencies += ("org.scalatest" %% "scalatest" % "3.2.14" % "test"),
    libraryDependencies += "com.google.inject" % "guice" % "5.0.1",
    coverageEnabled := true
  )
