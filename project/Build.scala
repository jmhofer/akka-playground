import sbt._
import Keys._
import akka.sbt.AkkaKernelPlugin
import akka.sbt.AkkaKernelPlugin._

object AkkaBuild extends Build {
  lazy val root = Project(id = "akka", base = file(".")) aggregate(shared, server, client)

  lazy val shared = Project(id = "akka-shared", base = file("shared")) settings(sharedSettings: _*)

  lazy val server = Project(id = "akka-server", base = file("server")) settings(serverSettings: _*) dependsOn(shared)

  lazy val client = Project(id = "akka-client", base = file("client")) settings(clientSettings: _*) dependsOn(shared)

  lazy val commonSettings: Seq[Setting[_]] = Seq(
    organization := "de.johoop",
    version := "1.0.0",
    scalaVersion := "2.10.3",
    scalacOptions ++= Seq("-deprecation", "-language:_"))

  lazy val sharedSettings: Seq[Setting[_]] = commonSettings ++ Seq(
    name := "akka-shared")

  lazy val serverSettings: Seq[Setting[_]] = commonSettings ++ AkkaKernelPlugin.distSettings ++ Seq(
    name := "akka-server",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.2.1",
      "com.typesafe.akka" %% "akka-remote" % "2.2.1",
      "com.typesafe.akka" %% "akka-kernel" % "2.2.1"),
      dist <<= dist.dependsOn(Keys.`package` in (shared, Compile)))

  lazy val clientSettings: Seq[Setting[_]] = commonSettings ++ Seq(
    name := "akka-client",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.2.1",
      "com.typesafe.akka" %% "akka-remote" % "2.2.1",
      "com.netflix.rxjava" % "rxjava-core" % "0.14.5",
      "com.netflix.rxjava" % "rxjava-scala" % "0.14.5"))
}

