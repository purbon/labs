package org.purbon.scalastash.actors.outputs

import akka.actor._;
import org.purbon.scalastash.actors.EnvMessage

class ElasticsearchOutputActor extends Actor {
  
  def receive = { 
    case EnvMessage(event) => { 
      println("[ElasticsearchOutputActor] event="+event);
    }
    case _ => println("[ElasticsearchOutputActor] unkown message"); 
  }
}