# Scala-Projects-Colaberry

![High Level Architecture](./Scala-Project-Wire.PNG "High Level Architecture")

##Stage/Project 1:

Micro‐Service to ingest data from a flat file to Kafka
 *Genome sample data set hosted by Google here shall be used as the sample ingest data.
 *File location, file name shall be configurable
 *Topic name, Kafka stream end points shall be configurable


##Stage/Project 2: 

Micro‐Service to consume Kafka stream on a configured topic to enhance the
data and add to a new topic.
 *Input topic for the raw data stream and output topic for the enhanced data stream shall be configurable
 *Kafka end points shall be configurable


##Stage/Project 3: 

Micro‐Service to consume enhanced data records from Kafka stream and ingest
to Elastic Search
 *Input topic for the enhanced data stream shall be configurable
 *End points for the Elastic Search shall be configurable
 *Partition/Index/Object Types shall be configurable


##Stage/Project 4:

HTTP Micro‐Service that offers API to search for data stored in Elastic Search
 *Elastic Search end points shall be configurable
 *HTTP Endpoints shall be configurable
 *Must list APIs offered by the service
