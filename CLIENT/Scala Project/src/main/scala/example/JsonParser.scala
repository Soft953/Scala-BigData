package example

object JsonParser {


  def splitString(l : String) = l.substring(1, l.length - 1).trim().split(",").map(i => i.split(':')).flatten.toList

  def createSet(s : List[String])= {
    //val key = s.zipWithIndex.collect({ case (x, i) if (i + 1) % 2 != 0 => x })
    //val value= s.zipWithIndex.collect({ case (x, i) if (i + 1) % 2 == 0 => x })
    //(key zip value).toMap
    s.grouped(2).collect { case List(k, v) => (k -> v) }.toMap
  }

  def parseJSON(value : String) = {
    createSet(splitString(value))
  }
}
