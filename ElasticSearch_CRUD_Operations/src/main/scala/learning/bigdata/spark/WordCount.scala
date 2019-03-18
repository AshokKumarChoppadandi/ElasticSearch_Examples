package learning.bigdata.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WordCount")//.set("spark.extraListeners", "learning.bigdata.spark.MySparkListener")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    //val listner = new MySparkListener
    //sc.addSparkListener(listner)

    val lines = List(
      "In order to work the DAGScheduler works with events (like StageCompleted)",
      "There are many callbacks fired in the core of Spark",
      "As an example, when an executor sends the results",
      "the DAGScheduler deserializes the answer and fires an event"
    )

    val rdd = sc.parallelize(lines, 3)
    val wordCount = rdd.flatMap(x => x.split("\\W+")).map(x => (x, 1)).reduceByKey(_ + _)
    wordCount.collect.foreach(println)
  }
}
