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
    resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    version := "1.0.0",
    scalaVersion := "2.9.2")

  lazy val sharedSettings: Seq[Setting[_]] = commonSettings ++ Seq(
    name := "akka-shared")

  lazy val serverSettings: Seq[Setting[_]] = commonSettings ++ AkkaKernelPlugin.distSettings ++ Seq(
    name := "akka-server",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-actor" % "2.0.2",
      "com.typesafe.akka" % "akka-remote" % "2.0.2",
      "com.typesafe.akka" % "akka-kernel" % "2.0.2"),
      dist <<= dist.dependsOn(Keys.`package` in (shared, Compile)))

  lazy val clientSettings: Seq[Setting[_]] = commonSettings ++ Seq(
    name := "akka-client",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" % "akka-actor" % "2.0.2",
      "com.typesafe.akka" % "akka-remote" % "2.0.2"))
}

