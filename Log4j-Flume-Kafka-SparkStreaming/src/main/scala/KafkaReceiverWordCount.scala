import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaReceiverWordCount {
  def main(args: Array[String]): Unit = {
    if (args.length != 4) {
      System.err.println("Error input")
      System.exit(1)
    }
    val sparkConf = new SparkConf().setAppName("sc").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val Array(zkQuorum, group, topics, numThreads) = args
    //TODO.. Kafka
    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val messages = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap)
    messages.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()
    ssc.start()
    ssc.awaitTermination()

  }
}

/*
  Start kafka
    1. start ZooKeeper in Zookeeper Directory: ./bin/zookeeper-server-start.sh config/zookeeper.conf
    2. start Kafka in Kafka Directory: ./bin/kafka-server-start.sh config/server.properties

  Create a topic:
  ./bin/kafka-topics.sh --create --zookeeper localhost:9092 --replication-factor 1 --partitions 1 --topic kafka_streaming_topic

  kafka producer
  ./kafka-console-producer.sh --broker-list localhost:9092 --topic kafka_streaming_topic

  kafka concole consumer (this program is another consumer)
  ./kafka-console-consumer.sh --zookeeper localhost:2181 --topic kafka_streaming_topic

  spark submit on server:
  spark-submit \
  --class KafkaReceiverWordCount \
  --master local[2] \
  --name KafkaReceiverWordCount \
  --packages org.apache.spark:spark-streaming-kafka-0-8_2.11:2.2.0 \
  /xxxxx.jar server:2181 test kafka_streaming_topic

 */