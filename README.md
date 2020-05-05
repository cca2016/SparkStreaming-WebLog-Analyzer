# SparkStreaming-WebLog-Analyzer

## Learning Demo
Log4j -> Flume Avro source -> Flume Kafka sink -> Spark Streaming

'''
#Start kafka
    #1. start ZooKeeper in Zookeeper Directory: 
    ./bin/zkServer.sh start
    #2. start Kafka in Kafka Directory: 
    ./bin/kafka-server-start.sh config/server.properties

#Create a topic:
  ./bin/kafka-topics.sh --create --zookeeper localhost:9092 --replication-factor 1 --partitions 1 --topic kafka_streaming_topic

  #kafka producer
  ./kafka-console-producer.sh --broker-list localhost:9092 --topic kafka_streaming_topic

  #kafka concole consumer (this program is another consumer)
  ./kafka-console-consumer.sh --zookeeper localhost:2181 --topic kafka_streaming_topic

#spark submit on server:
  spark-submit \
  --class KafkaReceiverWordCount \
  --master local[2] \
  --name KafkaReceiverWordCount \
  --packages org.apache.spark:spark-streaming-kafka-0-8_2.11:2.2.0 \
  /xxxxx.jar server:2181 test kafka_streaming_topic
'''
