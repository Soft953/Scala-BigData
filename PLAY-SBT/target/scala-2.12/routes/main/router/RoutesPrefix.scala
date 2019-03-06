// @GENERATOR:play-routes-compiler
// @SOURCE:/home/dude/project/scala/scala-api/conf/routes
// @DATE:Wed Mar 06 17:46:53 CET 2019


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
