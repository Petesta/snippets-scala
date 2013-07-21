package org.petesta.avionics

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import scala.concurrent.duration._

object Altimeter {
  case class AltitudeUpdate(altitude: Double)
  case class RateChange(amount: Float)
}

class Altimeter extends Actor with ActorLogging with EventSource{
  import Altimeter._

  implicit val ec = context.dispatcher
  val ceiling = 4300
  val maxRateOfClimb = 5000
  var rateOfClimb = 0f
  var altitude = 0d
  var lastTick = System.currentTimeMillis
  val ticker = context.system.scheduler.schedule(
                      100.millis, 100.millis, self, Tick)

  case object Tick

  def altimeterReceive: Receive = {
    case RateChange(amount) => {
      rateOfClimb = amount.min(1.0f).max(-1.0f) * maxRateOfClimb
      log info(s"Altimeter changed rate of climb to $rateOfClimb")
    }
    case Tick => {
      val tick = System.currentTimeMillis
      altitude += ((tick - lastTick) / 6000.0) * rateOfClimb
      lastTick = tick
      sendEvent(AltitudeUpdate(altitude))
    }
  }

  def receive = eventSourceReceive orElse altimeterReceive
  //type Receive = PartialFunction[Any, Unit]

  override def postStop(): Unit = ticker.cancel
}
