package org.purbon.scalastash.actors

import akka.actor._;
import scala.concurrent.duration._
import org.purbon.scalastash.Event;
import org.purbon.scalastash.actors.outputs.StdoutActor
 
class Machine(outputList: List[ActorRef]) extends Actor {
  
  import context._
    
  def filters: Receive = {
    case EnvMessage(event) => {
      println("filters")
      become(outputs)
      var dnsActor = context.actorOf(Props[DnsActor])
      dnsActor ! EnvMessage(event)
    }
  }
  
  def outputs: Receive = {
    case EnvMessage(event) => {
      println("outputs")
      become(filters)
      for(output <- outputList) {
         output ! EnvMessage(event)
      }
    }
  }
  
  def receive = filters
}