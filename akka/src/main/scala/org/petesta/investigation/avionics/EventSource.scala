package org.petesta.avionics

import akka.actor.{Actor, ActorRef}

object EventSource {
  case class RegisteredListener(listener: ActorRef)
  case class UnregisteredListener(listener: ActorRef)
}

trait EventSource { this: Actor =>
  import EventSource._
  var listeners = Vector.empty[ActorRef]

  def sendEvent[T](event: T): Unit = listeners foreach { _ ! event }

  def eventSourceReceive: Receive = {
    case RegisteredListener(listener) => {
      listeners = listeners :+ listener
    }
    case UnregisteredListener(listener) => {
      listeners = listeners filter { _ != listener }
    }
  }
}
