package RESTService

import akka.actor.ActorSystem
import akka.http.javadsl.model._
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.ConfigFactory
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * Created by nehba_000 on 5/4/2017.
  */
object RestMicroservice extends App with DefaultJsonProtocol{

  case class search()
 // case class requestService()
  case class searchInfo(first:String, second: String )
  val config = ConfigFactory.load()
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()



def HttpConnectionFlow: Flow[HttpRequest, HttpResponse, Future[Any]]= {
Http().outgoingConnection(config.getString("elasticSearch.url"), config.getInt("elasticSearch.port"))
}

def fetchInfoFromElastic(term:String): Future[Either[String, String]] = {
  Source.single(buildRequest(term)).via(HttpConnectionFlow).runWith(Sink.head).flatMap {
    response => response.status match {
      case OK => Future.successful(Right(response.entity.toString()))
      case BadRequest => Future.failed(new Exception("Incorrect Query"))
      case NotFound => Future.successful(Right("Not Found"))
      case _ => {println(response.status)
        Future.failed(new Exception("Failed"))}
    }
  }
}

  def buildRequest(term: String): HttpRequest = {
        println("Inside Search...Searching NOW" + term)
        val request = RequestBuilding.Get(config.getString("elasticSearch.uri") + "/" + term)
        println(request)
        request



  }
  val routes = {
    pathPrefix("colaberry" / "id") {
      (get & path(Segment)) { term =>
        complete {
          val aa: Future[Either[String, String]] = fetchInfoFromElastic(term)
          aa.map[String] {
            case Right(s) => s

          }

        }
      }

    }
  }
//    //Binds and listens for incoming requests on the host and post specified
       Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

}