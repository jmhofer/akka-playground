package de.johoop.akka

sealed trait ServerMessage
case class Hello(token: String) extends ServerMessage
case class Authenticate(user: String, password: String) extends ServerMessage

sealed trait ClientMessage
case object HelloClient extends ClientMessage

