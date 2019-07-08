package example
import scalaj.http._

object HttpRequest {

  def get(arg : CarData) : String = {

    val url = "http://localhost:9000/info";

    val response : HttpResponse[String] = Http(url).param("data", arg.toString).asString
    return response.body
  }

  def post(arg: CarData) : String = {

    val url = "http://localhost:9443/info";

    println(arg.toJSON)
    val response : HttpResponse[String] = Http(url).postData(arg.toJSON)
        .header("Content-Type", "application/json")
        .header("Charset", "UTF-8")
        .option(HttpOptions.readTimeout(30000)).asString
    return response.body
  }
}
