package org.purbon.scalastash
import java.util.concurrent._;

import org.purbon.scalastash.plugins._

object Pipeline {
  
  var queue          = new LinkedBlockingQueue[Event]()
  var input_threads  = List[Thread]()
  var output_threads = List[Thread]()

}
class Pipeline {

  class Worker extends Runnable {
    
    var running = false;
    
    def run() {
      start;
      while(running) {
        
      }      
    }
    
    def stop = running = false; 
    def start = running = true;
   
  }
  
  def run(): Unit = {
    val pool = Executors.newFixedThreadPool(8) 
    start_outputs();
    start_inputs();
    wait_inputs();
  }
  
  def shutdown(): Unit = {
    
  }
  
  private
  
  def wait_inputs() {
    println(Pipeline.input_threads.head.getState);
    Pipeline.input_threads.head.join()
  }
  
  def start_inputs(): Unit = {
    inputs.foreach { input =>
     Pipeline.input_threads = Pipeline.input_threads:+(start_plugin(input)); 
    }
  }
  
  def start_outputs(): Unit = { 
   for(output <- outputs()) {
      Pipeline.output_threads = Pipeline.output_threads:+(start_plugin(output));
    }
  }
  
  def start_plugin(i: Plugin): Thread = {
    var t = new Thread {
      override def run {
        i.register();
        i.run(Pipeline.queue)
      }
    }
    t.start();
    return t;
  }
  
  
  def inputs: List[InputPlugin] = {
     List[InputPlugin](new Stdin());
  }
  
  def mappers(): List[MapperPlugin] = {
     List[MapperPlugin](new Dns());
  }
  
  
  def outputs(): List[OutputPlugin] = {
     List[OutputPlugin](new Stdout());
  }
}