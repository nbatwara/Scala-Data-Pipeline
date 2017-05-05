package ElasticSearch
import akka.actor.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerMessage, ConsumerSettings, Subscriptions}
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}
/**
  * Created by nehba_000 on 5/2/2017.
  */
object TopicTwoConsumerSource {
  def create(groupId: String)(implicit system: ActorSystem): Source[ConsumerMessage.CommittableMessage[Array[Byte], String], Consumer.Control] = {
    val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
      .withBootstrapServers("192.168.99.100:9092")
      .withGroupId(groupId)
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")


    Consumer.committableSource(consumerSettings, Subscriptions.topics(TopicDefinition.TopicB))
  }
}

object TopicDefinition {
  val TopicB = "TopicB"

}
