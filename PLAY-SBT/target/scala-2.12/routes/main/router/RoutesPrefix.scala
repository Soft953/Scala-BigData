// @GENERATOR:play-routes-compiler
// @SOURCE:/home/dude/project/scala/Scala-BigData/PLAY-SBT/conf/routes
// @DATE:Wed May 01 17:19:29 CEST 2019


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
