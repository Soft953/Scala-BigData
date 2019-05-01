package example

object JsonParser {


  def splitString(l : String) = l.substring(1, l.length - 1).trim().split(",").map(i => i.split(':')).flatten.toList

  def createSet(s : List[String]) = {
    val key = s.zipWithIndex.collect({ case (x, i) if (i + 1) % 2 != 0 => x })
    val value= s.zipWithIndex.collect({ case (x, i) if (i + 1) % 2 == 0 => x })
    (key zip value).toMap
  }

  def parseJSON(value : List[String]) = {
    value.map(splitString).map(createSet)
  }
}
