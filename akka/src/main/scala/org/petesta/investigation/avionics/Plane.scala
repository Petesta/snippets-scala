package org.petesta.avionics

import akka.actor.{Actor, ActorLogging, Props}

object Plane {
  case object GiveMeControl
}

class Plane extends Actor with ActorLogging {
  import Altimeter._
  import EventSource._
  import Plane._

  val altimeter = context.actorOf(Props[Altimeter], "Altimeter")
  val controls = context.actorOf(Props(new ControlSurfaces(altimeter)), "ControlSurfaces") 

  def receive = {
    case AltitudeUpdate(altitude) => {
      log info(s"Altitude is now $altitude")
    }
    case GiveMeControl => {
      log info("Plane giving control.")
      sender ! controls
    }
  }

  override def preStart() {
    altimeter ! RegisteredListener(self)
  }
}
