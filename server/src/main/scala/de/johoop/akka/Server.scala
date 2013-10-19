package de.johoop.akka

import akka.actor._
import akka.event.Logging
import akka.kernel.Bootable
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.util.Random

class Server extends Bootable {
  val system = ActorSystem("server")

  def startup = system actorOf (Props[HelloActor], "hello")
  def shutdown = system.shutdown
}

class HelloActor extends Actor with ActorLogging {
  def receive = {
    case Hello =>
      log info "Someone said hello to me! Yay!"
      sender ! HelloClient
  }
}
