// @GENERATOR:play-routes-compiler
// @SOURCE:/home/dude/project/scala/Scala-BigData/PLAY-SBT/conf/routes
// @DATE:Wed May 01 17:19:29 CEST 2019

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset

// @LINE:3
package controllers.javascript {

  // @LINE:3
  class ReverseApiController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:3
    def ping: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiController.ping",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api/ping"})
        }
      """
    )
  
    // @LINE:4
    def POST_info: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ApiController.POST_info",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "info"})
        }
      """
    )
  
  }


}
