import sbt._
import sbt.Keys._

object AkkaBuild extends Build {

  lazy val akka = Project(
    id = "akka",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "akka",
      organization := "org.petesta",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.0",
      scalacOptions ++= Seq("-feature", "-deprecation"),
      resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases",
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "2.0.M6-SNAP9" % "test",
        "com.typesafe.akka" %% "akka-testkit" % "2.1.0",
        "com.typesafe.akka" %% "akka-actor" % "2.1.2"
      )
    )
  )
}
