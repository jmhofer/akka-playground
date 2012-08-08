package de.johoop.akka

import akka.actor._
import akka.pattern.ask
import akka.dispatch.{ Await, Future }
import akka.util.{ Timeout, Duration }
import akka.util.duration._

object Main extends App {

  implicit val timeout = Timeout(5 seconds)

  val system = ActorSystem("Client")
  val server = system.actorFor("akka://Server@localhost:2554/user/server")

  try {
    val response = server ? Hello
    val result = Await.result(response, timeout.duration)
    println(result)

  } finally {
    system.shutdown
  }
}


