package org.purbon.scalastash.actors

import akka.actor._;

import org.purbon.scalastash.Event;

class StdinActor(fsmActor: ActorRef) extends Actor {

  def receive = {
    case "start" => {
      println("start stdin actor");
      while (true) {
        var line = readLine();
        var e = new Event();
        e.append("message", line);
        fsmActor ! EnvMessage(e);
      }
    }
    case _ => println("[stdinActor] unkown message");
  }
}