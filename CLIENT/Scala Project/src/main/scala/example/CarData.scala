package example

class CarData (val id : String, val location : String, val speed : Float,
               val acceleration : Float, val fuel : Float, val engineTemp : Float,
               val nextStep : String) {

  val _id = id
  val _location = location
  val _speed = speed
  val _acceleration = acceleration
  val _fuel = fuel
  val _engineTemp = engineTemp
  val _nextStep = nextStep

  def toJSON = "{\"id\": " + _id + ", \"location\": \"" + _location.toString + "\", \"speed\":" + _speed.toString + "" +
    ", "+ "\"acceleration\": " +  _acceleration.toString + ", \"fuel\":" + _fuel + ", \"engineTemp\": " + _engineTemp.toString + ", \"nextStep\": \"" + _nextStep + "\"}";
  
  override def toString = s"${_id};${_location};${_speed.toString};" +
    s"${_acceleration.toString};${_fuel.toString};${_engineTemp.toString};${_nextStep}"
}
