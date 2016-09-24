package org.purbon.scalastash.actors.outputs

import akka.actor._;
import org.purbon.scalastash.actors.EnvMessage

class StdoutActor extends Actor {
  
  def receive = { 
    case EnvMessage(event) => { 
      println("[StdoutActor] event="+event);
    }
    case _ => println("[StdoutActor] unkown message"); 
  }
}