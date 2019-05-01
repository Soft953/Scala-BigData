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


  override def toString = s"${_id};${_location};${_speed.toString};" +
    s"${_acceleration.toString};${_fuel.toString};${_engineTemp.toString};${_nextStep}"
}
