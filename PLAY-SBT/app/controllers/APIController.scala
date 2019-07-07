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

            val car = new CarData(report.id.toString, report.location.toString, report.speed.toFloat, report.acceleration.toFloat, report.fuel.toFloat, report.engineTemp.toFloat, report.nextStep.toString)
            CarProducer.sendToKafka("raw_carData", "localhost:9092", Array(car.toString()))

            /* Code */

            if(report.engineTemp > 99.0)
            {
                if(report.fuel < 10.0)
                {
                    if(report.speed > 130.0)
                    {
                        val code = 2
                        val message = "WARNING: engine temperature too high, fuel is low, speed too high."
                        val content = "go to the gaz station, take a break and slow down."
                        val res = Response(code, message, content) 
                        Ok(Json.toJson(res))
                    }
                    else
                    {
                        val code = 2
                        val message = "WARNING: engine temperature too high, fuel is low."
                        val content = "go to the gaz station and take a break."
                        val res = Response(code, message, content) 
                        Ok(Json.toJson(res))
                    }
                }
                else
                {
                    if(report.speed > 130.0)
                    {
                        val code = 2
                        val message = "WARNING: engine temperature too high, speed too high."
                        val content = "slow down and take a break and."
                        val res = Response(code, message, content) 
                        Ok(Json.toJson(res))
                    }
                    else
                    {
                        val code = 2
                        val message = "WARNING: engine temperature too high."
                        val content = "take a break."
                        val res = Response(code, message, content) 
                        Ok(Json.toJson(res))
                    }
                }
            }
            else
            {
                if(report.fuel < 10.0)
                {
                    if(report.speed > 130.0)
                    {
                        val code = 2
                        val message = "WARNING: fuel is low, speed too high."
                        val content = "go to the gaz station and slow down."
                        val res = Response(code, message, content) 
                        Ok(Json.toJson(res))
                    }
                    else
                    {
                        val code = 2
                        val message = "WARNING: fuel is low."
                        val content = "go to the gaz station."
                        val res = Response(code, message, content) 
                        Ok(Json.toJson(res))
                    }
                }
                else
                {
                    if(report.speed > 130.0)
                    {
                        val code = 2
                        val message = "WARNING: fuel is low."
                        val content = "go to the gaz station."
                        val res = Response(code, message, content) 
                        Ok(Json.toJson(res))
                    }
                    else
                    {
                        val code = 0
                        val message = "Everything is ok."
                        val content = "continu."
                        val res = Response(code, message, content) 
                        Ok(Json.toJson(res))
                    }
                }
            }

           /*if(report.engineTemp > 99.0)
           {
               val code = 2
               val message = "WARNING: engine temperature too high."
               val content = "take a break."
               val res = Response(code, message, content) 
               Ok(Json.toJson(res))

           }
           else if (report.fuel < 10.0)
           {
               val code = 2
               val message = "WARNING: fuel is low."
               val content = "go to the gaz station."
               val res = Response(code, message, content) 
               Ok(Json.toJson(res))

           }
           else if(report.speed > 130.0)
           {
               val code = 2
               val message = "WARNING: speed too high."
               val content = "slow down."
               val res = Response(code, message, content)
               Ok(Json.toJson(res))

           }
           else
           {
               val code = 0
               val message = "Everything is ok."
               val content = "continu."
               val res = Response(code, message, content) 
               Ok(Json.toJson(res))

           }*/
           /* Code */




        }
        else
        {
            BadRequest("Nope")
        }


    }




}
