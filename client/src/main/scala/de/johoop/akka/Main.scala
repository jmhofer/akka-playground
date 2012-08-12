package de.johoop.akka

import akka.actor._
import akka.pattern.ask
import akka.dispatch.{ Await, Future }
import akka.util.{ Timeout, Duration }
import akka.util.duration._

object Main extends App {

  implicit val timeout = Timeout(5 seconds)

  val system = ActorSystem("Client")
  val auth = system.actorFor("akka://Server@localhost:2554/user/secure")

  try {
    val response = auth ? "auth"
    val result = Await.result(response, timeout.duration).asInstanceOf[ActorRef]
    result ! Hello

    println(result)
    
  } finally {
    system.shutdown
  }
}
