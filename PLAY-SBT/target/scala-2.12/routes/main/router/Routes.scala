// @GENERATOR:play-routes-compiler
// @SOURCE:/home/dude/project/scala/Scala-BigData/PLAY-SBT/conf/routes
// @DATE:Wed May 01 17:19:29 CEST 2019

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:3
  ApiController_0: controllers.ApiController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:3
    ApiController_0: controllers.ApiController
  ) = this(errorHandler, ApiController_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, ApiController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/ping""", """controllers.ApiController.ping"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """info""", """controllers.ApiController.POST_info"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:3
  private[this] lazy val controllers_ApiController_ping0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/ping")))
  )
  private[this] lazy val controllers_ApiController_ping0_invoker = createInvoker(
    ApiController_0.ping,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "ping",
      Nil,
      "GET",
      this.prefix + """api/ping""",
      """""",
      Seq()
    )
  )

  // @LINE:4
  private[this] lazy val controllers_ApiController_POST_info1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("info")))
  )
  private[this] lazy val controllers_ApiController_POST_info1_invoker = createInvoker(
    ApiController_0.POST_info,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApiController",
      "POST_info",
      Nil,
      "GET",
      this.prefix + """info""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:3
    case controllers_ApiController_ping0_route(params@_) =>
      call { 
        controllers_ApiController_ping0_invoker.call(ApiController_0.ping)
      }
  
    // @LINE:4
    case controllers_ApiController_POST_info1_route(params@_) =>
      call { 
        controllers_ApiController_POST_info1_invoker.call(ApiController_0.POST_info)
      }
  }
}
