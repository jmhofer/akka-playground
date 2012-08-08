package de.johoop.akka

import akka.actor._
import akka.event.Logging
import akka.kernel.Bootable

class Server extends Bootable {
  val system = ActorSystem("Server")

  def startup = system.actorOf(Props[ServerActor], "server")
  def shutdown = system.shutdown
}

class ServerActor extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    case Hello => {
      log.info("Someone said hello to me! Yay!")
      sender ! HelloClient
    }
  }
}
