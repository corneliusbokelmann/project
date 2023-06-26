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
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC5",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.1.0",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
    coverageEnabled := true
  )
