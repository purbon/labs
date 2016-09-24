package org.purbon.scalastash.actors

import akka.routing._;
import akka.actor._;
import org.purbon.scalastash.actors.outputs.StdoutActor


abstract class MapperActor extends Actor {
  
}

class Router() extends Actor {
  
  def receive = { 
    case "start" => {
      val stdinActor  = context.actorOf(Props[StdinActor], name = "stdin");
      stdinActor ! "start"  
    }
    case EnvMessage(event) => { 
      var stdout = context.actorOf(Props[StdoutActor])
      stdout ! EnvMessage(event)
    }
    case _ => println("[router] unkown message");
  }
}