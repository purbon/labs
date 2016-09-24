package org.purbon.scalastash

import java.util.concurrent._;

abstract class Plugin {
 
  def register(): Unit = {
      
  }
  
  def run(queue: LinkedBlockingQueue[Event]);

}

abstract class InputPlugin extends Plugin {
    
}

abstract class MapperPlugin extends Plugin {

}

abstract class OutputPlugin extends Plugin {
    
}