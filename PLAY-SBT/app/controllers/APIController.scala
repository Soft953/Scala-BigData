// app/controllers/ApiController.scl

// Make sure it's in the 'controllers' package
package controllers
//package com.gu.sqlite

import javax.inject.{Inject, Singleton}
import play.api.mvc._
import play.api.libs.functional.syntax._

// NEW - import JSON functionality and our data repository
import play.api.libs.json._

import models._



@Singleton
class ApiController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

    implicit val responseWrites = new Writes[Response] {
    def writes(res: Response) = Json.obj(
      "code"    -> res.code,
      "message" -> res.message,
      "content" -> res.content
    )
  }

  implicit val carReportReads: Reads[CarReport] = (
    (__ \ "id").read[Int] and
    (__ \ "location").read[String] and
    (__ \ "speed").read[Float] and
    (__ \ "acceleration").read[Float] and
    (__ \ "fuel").read[Float] and
    (__ \ "engineTemp").read[Float] and
    (__ \ "nextStep").read[String] 
    )(CarReport.apply _)


  // Create a simple 'ping' endpoint for now, so that we
  // can get up and running with a basic implementation
  def ping = Action { implicit request =>
    Ok("Hello, Scala!")
  }

  // Create a simple 'ping' endpoint for now, so that we
  // can get up and running with a basic implementation
  def POST_info = Action { implicit request =>

      if (request.body.asJson != None) { 
        val req_report = Json.fromJson[CarReport](request.body.asJson.get).get
        val report = CarReport(req_report.id, 
                              req_report.location, 
                              req_report.speed, 
                              req_report.acceleration, 
                              req_report.fuel, 
                              req_report.engineTemp,
                              req_report.nextStep)
        
        /* Code */
    
        val code=0
        val message=""
        val content=""
        if(report.enginTemp > 99.0)
        {
            code = 2
            message = "WARNING: engine temperature too high."
            content = "take a break."
        }
        else if (report.fuel < 10.0)
        {
            code = 2
            message = "WARNING: fuel is low."
            content = "go to the gaz station."
        }
        else if(report.speed > 130.0)
        {
            code = 2
            message = "WARNING: speed too high."
            content = "slow down."
        }
        else
        {
            code = 0
            message = "Everything is ok."
            content = "continu."
        }
        /* Code */

        

        val res = Response(code, message, content)  
         Ok(Json.toJson(res))
      }
      else
      {
        BadRequest("Nope")
      }
                             
     
  }


  

}
