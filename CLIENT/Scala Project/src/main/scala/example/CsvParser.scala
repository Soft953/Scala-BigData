package example

object CsvParser {

  def splitString(l : Array[String], delim : Char) = l.map( x => x.split(delim))

  def createCarData(v : Array[String]) = v match {
    case v if v.length < 7 => throw new Exception("Bad csv format")
    case v => new CarData(v(0), v(1), v(2).toFloat, v(3).toFloat, v(4).toFloat, v(5).toFloat, v(6))
  }

  def parseCSV( value : Array[String], delimiter : Char = ';') = {
    val tmp = splitString(value, delimiter)
    tmp.drop(1).map( e => createCarData(e))
  }
}
