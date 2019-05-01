// Make sure it goes in the models package
package models

// Create our Post type as a standard case class
case class Response(code : Int, message : String, content : String)

