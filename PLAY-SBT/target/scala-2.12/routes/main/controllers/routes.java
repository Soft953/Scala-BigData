// @GENERATOR:play-routes-compiler
// @SOURCE:/home/dude/project/scala/Scala-BigData/PLAY-SBT/conf/routes
// @DATE:Wed May 01 17:19:29 CEST 2019

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseApiController ApiController = new controllers.ReverseApiController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseApiController ApiController = new controllers.javascript.ReverseApiController(RoutesPrefix.byNamePrefix());
  }

}
