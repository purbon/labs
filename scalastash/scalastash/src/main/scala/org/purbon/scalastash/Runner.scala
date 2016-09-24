package org.purbon.scalastash

import org.purbon.scalastash.plugins._

class Runner {

  def start(): Unit = {
    var pipeline = new ActorsPipeline();
    pipeline.run();
    
    sys.addShutdownHook({
      println("shutdown....");
      pipeline.shutdown();
      println("Bye...");
    })
  }
}