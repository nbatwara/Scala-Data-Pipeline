package ElasticSearch

import akka.actor.{Actor, ActorLogging, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, _}
import akka.kafka.ConsumerMessage.CommittableMessage
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink}
import akka.util.ByteString
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.language.postfixOps
/**
  * Created by nehba_000 on 5/2/2017.
  */
object ElasticConsumer extends App {
  type Message = CommittableMessage[Array[Byte], String]
  case object Start
  case object Stop

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit  val executor = system.dispatcher

  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  val initialActor = classOf[ElasticConsumer].getName
  akka.Main.main(Array(initialActor))
}

class ElasticConsumer extends Actor with ActorLogging {

  import ElasticConsumer._


  val headerString = scala.io.Source.fromFile(config.getString("file.filename")).getLines().next()
  val listHeader = CSVParser.parse(headerString, '\\', ',', '"')

  override def preStart(): Unit = {
    super.preStart()
    self ! Start
  }

  override def receive: Receive = {
    case Start =>
      log.info("Initializing Topic 2 Consumer")
      val control = TopicTwoConsumerSource.create("TopicTwoConsumer")(context.system)
        .mapAsync(1)(processMessage)
        .via(HttpConnectionFlow)
        .runWith(Sink.ignore)

  }

  private def writeToElasticSearch(jsonString: String, id: String): HttpRequest = {
    val request = HttpRequest(
      POST,
      uri = config.getString("apiUrl.path") + "/" + id,
      entity = HttpEntity(ContentTypes.`application/json`, ByteString(jsonString))
    )
    println(request)
    request
  }

  def processMessage(msg: Message): Future[HttpRequest] = {

    val listElement = CSVParser.parse(msg.record.value(), '\\', ',', '"').get
    val id = listElement(0).toString.replace("\"", "")
    val listMatch = listHeader.get.zip(listElement)
    val listMatchFormatted = listMatch.map(item => "\"" + item._1.toString().replace("\"", "") + "\":\"" + JsonValueFormatter(item._2.toString()) + "\"")
    val jsonString = "{ " + listMatchFormatted.mkString(",") + "}"
    Future.successful(writeToElasticSearch(jsonString, id))

  }

  private def JsonValueFormatter(str: String ): String = {
    if (str.indexOf("\"").equals(-1)) {
      if (str.length() == 0) ""
      else str
    } else {
      "\"" + str.replace("\"", "")
    }
  }

  def HttpConnectionFlow: Flow[HttpRequest, HttpResponse, Any] = {
    Http().outgoingConnection(config.getString("elasticSearch.url"), config.getInt("elasticSearch.port"))
  }

}
