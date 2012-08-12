package de.johoop.akka

import akka.actor._
import akka.event.Logging
import akka.kernel.Bootable
import com.typesafe.config.ConfigFactory
import scala.util.Random

class Server extends Bootable {
  val system = ActorSystem("Server")

  def startup = {
    system.actorOf(Props[AuthActor], "secure")
  }
  def shutdown = system.shutdown
}

class AuthActor extends Actor {
  val log = Logging(context.system, this)
  val random = new Random
  
  def receive = {
    case "auth" => {
      log.info("auth") // normally, authentication would happen here...
      sender ! context.actorOf(Props[HelloActor], randomActorName)
    }
  }
  
  def randomActorName: String = {
    // akka says: must conform to [-\w:@&=+,.!~*'_;][-\w:@&=+,.!~*'$_;]*
    val validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:@&=+,.!~*'_;"
    Array.fill(256) { validChars.charAt(random.nextInt(validChars.size)) } mkString
  }
}

class HelloActor extends Actor {
  val log = Logging(context.system, this)
  
  def receive = {
    case Hello => {
      log.info("Someone said hello to me! Yay!")
      sender ! HelloClient
    }
  }
}
