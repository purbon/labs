package org.purbon.scalastash.plugins

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}
import scala.collection.mutable.Queue;
import org.purbon.scalastash._;

class Stdin() extends InputPlugin {
  
  def run(queue: LinkedBlockingQueue[Event]): Unit = {
    
    println("start stdin with "+queue);
    while(true) {
      var line = readLine();
      println("stdin "+line);
      var e = new Event();
      e.append("message", line);
      queue.put(e);
    }
  }
}