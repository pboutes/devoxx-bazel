package fetcher

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{HttpApp, Route}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

final case class UselessData(name: String, lastname: String)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val uselessFormat: RootJsonFormat[UselessData] = jsonFormat2(UselessData)
}

object WebServer extends HttpApp with JsonSupport {
  override protected def routes: Route = {
    path("hello") {
      get {
        parameter('name.as[String]) { name =>
          println(s"Processing get request")
          complete(s"Hello $name !")
        }
      }
    }
  }
}
