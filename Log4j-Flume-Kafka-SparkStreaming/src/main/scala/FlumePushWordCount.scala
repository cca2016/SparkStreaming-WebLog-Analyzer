import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FlumePushWordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("sa")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    //TODO.. use spark streaming with flume
    val flumeStream = FlumeUtils.createStream(ssc, "0.0.0.0", 41414)
    flumeStream.map(x => new String(x.event.getBody.array()).trim).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}


/*
Flume Start:
./bin/flume-ng agent --conf conf --conf-file conf/flume-spark.conf --name sa -Dflume.root.logger=INFO,console
("sa" is only a name)

flume agent conf

sa.sources = netcat-source
sa.sinks = avro-sink
sa.channels = memory-channel

sa.sources.netcat-source.type = netcat
sa.sources.netcat-source.bind = localhost
sa.sources.netcat-source.port = 44444

sa.sinks.avro-sink.type = avro
sa.sinks.avro-sink.hostname = 192.168.0.5
sa.sinks.avro-sink.port = 41414

sa.channels.memory-channel.type = memory

sa.sources.netcat-source.channels = memory-channel
sa.sinks.avro-sink.channel = memory-channel


 */