package de.johoop.akka

import akka.actor._
import akka.pattern.ask
import scala.concurrent.{ Await, Future }
import akka.util.Timeout
import scala.concurrent.duration._

object Main extends App {

  implicit val timeout = Timeout(5 seconds)

  val system = ActorSystem("client")
  val hello = system actorSelection "akka.tcp://server@localhost:2554/user/hello"

  try {
    val response = hello ? Hello
    val back = Await.result(response, timeout.duration).asInstanceOf[ClientMessage]
    println(back)

  } finally {
    system.shutdown
  }
}
