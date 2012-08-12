package de.johoop.akka

import akka.actor._
import akka.pattern.ask
import akka.dispatch.{ Await, Future }
import akka.util.{ Timeout, Duration }
import akka.util.duration._

object Main extends App {

  implicit val timeout = Timeout(5 seconds)

  val system = ActorSystem("Client")
  val login = system.actorFor("akka://Server@localhost:2554/user/secure")

  try {
    val response = login ? Authenticate("user", "password")
    val (hello, token) = Await.result(response, timeout.duration).asInstanceOf[(ActorRef, String)]
    hello ! Hello(token)
    println(hello)

    // attempt to look up hello directly...
    val hello2 = system.actorFor("akka://Server@localhost:2554/user/secure/$a")
    hello2 ! Hello(token)
    
    // attempt with invalid token
    hello2 ! Hello("bla")
    
  } finally {
    system.shutdown
  }
}
