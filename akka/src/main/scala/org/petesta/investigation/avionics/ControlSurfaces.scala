package org.petesta.avionics

import akka.actor.{Actor, ActorRef}

object ControlSurfaces {
  case class StickBack(amount: Float)
  case class StickForward(amount: Float)
}

class ControlSurfaces(altimeter: ActorRef) extends Actor {
  import ControlSurfaces._
  import Altimeter._

  def receive = {
    case StickBack(forward) => {
      altimeter ! RateChange(amount)
    }
    case StickForward(forward) => {
      altimeter ! RateChange(-1 * amount)
    }
  }
}
