package de.johoop.akka

import akka.actor._
import akka.event.Logging
import akka.kernel.Bootable
import akka.pattern.ask
import akka.util.{ Timeout, Duration }
import akka.util.duration._

import scala.util.Random

class Server extends Bootable {
  val system = ActorSystem("Server")

  def startup = system.actorOf(Props[AuthActor], "secure")
  def shutdown = system.shutdown
}

class AuthActor extends Actor {
  val log = Logging(context.system, this)
  private val random = new Random
    
  def receive = {
    case Authenticate(user, password) => {
      log.info("auth(%s, %s)".format(user, password)) // normally, authentication would happen here...
      val token = random.nextString(256)
      
      sender ! (context.actorOf(Props(new HelloActor(token))), token)
    }
  }
}

class HelloActor(token: String) extends Actor {
  val log = Logging(context.system, this)
  
  def receive = {
    case Hello(token: String) => {
      if (this.token == token) {
        log.info("Someone said hello to me! Yay!")
        sender ! HelloClient
      } 
      else throw new SecurityException("invalid access!")
    }
  }
}
