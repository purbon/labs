package org.purbon.scalastash

import scala.collection.mutable.LinkedHashMap;
import org.json4s._;
import org.json4s.JsonDSL._; 
import org.json4s.JsonDSL.WithBigDecimal._
import org.json4s.jackson.Serialization


class Event {

  implicit val formats = org.json4s.DefaultFormats

  var data = Map[String, Any]();
  
  def append(e: String, o: Any):Unit = {
    data.+=((e,o));
  }
  
  override def toString(): String = {
    var sb = new StringBuilder();
    sb.append(data.head);
    
    return Serialization.write(data);
  }
}