package example
import scalaj.http._

object HttpRequest {

  def get(arg : CarData) : String = {

    val url = "http://localhost:8000/gettest";

    val response : HttpResponse[String] = Http(url).param("data", arg.toString).asString
    return response.body
  }

  def post(arg: CarData) : String = {

    val url = "http://localhost:8000/posttest";

    println(arg.toString)
    val response : HttpResponse[String] = Http(url).postData(arg.toString)
        .header("Content-Type", "application/json")
        .header("Charset", "UTF-8")
        .option(HttpOptions.readTimeout(10000)).asString
    return response.body
  }
}
