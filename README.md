# Scala-Projects-Colaberry

![High Level Architecture](./Scala-Project-Wire.PNG "High Level Architecture")

## Mircroservice/Project 1:
This project implements the first stage of the data pipeline being built. Akka streams are used to build each of the micro services. Akka stream mainly need three components to be configured Source, Sink and Flow. In this project the source if file. An iterator to the lines from file is given as source and the sink is configured to kafka producer.
 * Genome sample data set hosted by Google here shall be used as the sample ingest data.
 * File location, file name shall be configurable
 * Topic name, Kafka stream end points shall be configurable


## Mircroservice/Project 2: 
The data that is read to kafka will be transformed into kafka again in this stage. A commitable source is created which will subscribe to the topic defined in Project 1 and then process it asyncronously using mapAsync on each message. The transformation that is done here is to replace empty values in relationship column and make all the values in this column to lower case. After transforming data this data is put to another kafka topic using a method that is called in Flow part of the stream and the sink is ignored in this case.

 * Input topic for the raw data stream and output topic for the enhanced data stream shall be configurable
 * Kafka end points shall be configurable


## Mircroservice/Project 3: 
This project sends the data to Elastic search from the kafka source by listening to topics created in previous stages. To send data to Elastic seach the REST api is used. The akka stream is built with the kafka consumer as source that listens on the data coming on the topic created in previoud stage and bulds HttpRequest to send the data to Elastic search using REST end points.

 * Input topic for the enhanced data stream shall be configurable
 * End points for the Elastic Search shall be configurable
 * Partition/Index/Object Types shall be configurable


## Mircroservice/Project 4:
This project implements a REST service that provide end point to search for the data that is stored in the file. This project uses Akka HTTP DSL to bind and listen to incoming requests and query elastic search.

Users can query data on the elastic search using two end points: One to search based on id({indexname}\search{id}) which results in the record, to fuzzy match based on the term({indexname}\fuzzy{search_term}), this result in the success or failure. To implement the Http request is created to query elastic search based on the input end point and query the elastic search whose output is sent as response to browser.
 * Elastic Search end points shall be configurable
 * HTTP Endpoints shall be configurable
 * Must list APIs offered by the service

## How to Build and Run
 * Download and import into IDE (created in IntelliJ)
 * Run each Microservice in order -- Run with docker(download docker-compose.yml file provided in project). Open docker quicstart terminal and run:
 ```scala
 docker-compose up
 ```
 ```scala
 sbt run
 ```
 * Run each microservice in order. 
 ## Order to run: 3-4-1-5
 * [1] ElasticSearch.ElasticConsumer
 * [2] KafkaMain.FileConsumer
 * [3] KafkaMain.FileWriter
 * [4] KafkaMain.KafkaToKafka
 * [5] RESTService.RestMicroservice


:octocat: :octocat:
