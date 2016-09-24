package org.purbon.scalastash

import akka.actor._;
import org.purbon.scalastash.actors._;
import org.purbon.scalastash.actors.outputs._;

import scala.collection.mutable._;

class ActorsPipeline {

  var system = ActorSystem("PipelineSystem");
  
  def run(): Unit = {
    
   val outputs = List[String]("org.purbon.scalastash.actors.outputs.StdoutActor", 
                              "org.purbon.scalastash.actors.outputs.ElasticsearchOutputActor"); 
   
   val fsmActor     = system.actorOf(Props(new Machine(outputActors(outputs))), name = "fsm");
 
   val stdinActor  = system.actorOf(Props(new StdinActor(fsmActor)), name = "stdin");
   stdinActor ! "start"
  }
  
  def shutdown(): Unit = {
    system.shutdown();
  }
 
  private
  
  def outputActors(outputs: List[String]): List[ActorRef] = {    
     outputs.map { output => 
       system.actorOf(Props(Class.forName(output).newInstance().asInstanceOf[Actor]))
     }
  }
}