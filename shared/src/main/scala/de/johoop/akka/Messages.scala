package de.johoop.akka

sealed trait ServerMessage
case object Hello extends ServerMessage

sealed trait ClientMessage
case object HelloClient extends ClientMessage

