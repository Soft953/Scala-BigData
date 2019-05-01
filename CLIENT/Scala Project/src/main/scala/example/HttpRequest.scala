package example
import scalaj.http._

object HttpRequest {

  def get(arg : CarData) {

    val url = "http://localhost:8000/gettest";

    Http(url).param("data", arg.toString).asString
  }

  def post(arg: CarData) {

    val url = "http://localhost:8000/posttest";

    println(arg.toString)
      Http(url).postData(arg.toString)
        .header("Content-Type", "application/json")
        .header("Charset", "UTF-8")
        .option(HttpOptions.readTimeout(10000)).asString

  }
}
