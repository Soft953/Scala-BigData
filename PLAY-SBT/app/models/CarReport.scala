// app/models/Post.scala

// Make sure it goes in the models package
package models

// Create our Post type as a standard case class
case class CarReport(id: Int, location : String, speed: Float, acceleration: Float, fuel: Float, engineTemp: Float, nextStep: String)

