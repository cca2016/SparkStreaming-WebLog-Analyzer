import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaStreaming {
  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      System.err.println("Error input")
      System.exit(1)
    }
    val sparkConf = new SparkConf().setAppName("sc").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val Array(brokers, topics) = args

    val KafkaParams = Map[String, String]("bootstrap.servers" -> brokers)
    val topicsSet =topics.split(",").toSet
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, KafkaParams, topicsSet)
//    messages.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()
    messages.map(_._2).print()

    ssc.start()
    ssc.awaitTermination()
  }
}


