package org.purbon

import org.purbon.scalastash._

object ScalaStash {
  
  def main(args: Array[String]): Unit = {
    println("Welcome to Scalastash");
    val runner = new Runner();
    runner.start();    
  }
}