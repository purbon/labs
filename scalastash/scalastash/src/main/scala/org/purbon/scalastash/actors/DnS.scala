package org.purbon.scalastash.actors

import akka.actor._;

class DnsActor extends Actor {
  
  def receive = { 
    case EnvMessage(event) => { 
      event.append("dns", "8.8.8.8");
      sender ! EnvMessage(event);
    }
    case _ => println("unkown message");
  }
}