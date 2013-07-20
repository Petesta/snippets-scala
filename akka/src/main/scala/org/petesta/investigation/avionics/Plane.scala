package org.petesta.avionics

import akka.actor.{Actor, ActorLogging, Props}

object Plane {
  case object GiveMeControl
}

class Plane extends Actor with ActorLogging {
  import Altimeter._
  import Plane._

  val altimeter = context.actorOf(Props[Altimeter], "Altimeter")
  val props = context.actorOf(Props(new ControlSurfaces(altimeter)), "ControlSurfaces") 

  def receive = {
    case GiveMeControl => {
      long info("Plane giving control.")
      sender ! controls
    }
  }
}