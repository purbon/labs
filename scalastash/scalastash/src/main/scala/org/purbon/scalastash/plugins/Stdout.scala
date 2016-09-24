package org.purbon.scalastash.plugins

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}
import org.purbon.scalastash.OutputPlugin;
import org.purbon.scalastash.Event;

class Stdout() extends OutputPlugin {
  
   def run(queue: LinkedBlockingQueue[Event]): Unit = {
     while(true) {
       var event = queue.poll();
       if (event != null)
         println(event); 
     }
  }
}