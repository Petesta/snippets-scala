package org.petesta.avionics

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.ask
import scala.concurrent.Await
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Avionics {
  implicit val timeout = Timeout(5.seconds)
  def main(args: Array[String])
}
