package learning.bigdata.spark

import java.text.SimpleDateFormat
import java.util.Date

import com.test.InsertDocuments
import org.apache.spark.scheduler.{SparkListener, SparkListenerApplicationEnd, SparkListenerApplicationStart}
import org.apache.spark.scheduler.{SparkListenerApplicationEnd, SparkListenerApplicationStart}

class MySparkListener extends SparkListener {
  var appName: String = null
  var jobId: Option[String] = null

  //var obj: DocumentInsert = null

  override def onApplicationStart(applicationStart: SparkListenerApplicationStart): Unit = {
    println("My Custom Spark Listener is Running when Application starts...!!!")
    val appStartTime = applicationStart.time
    println("Start Time :: " + appStartTime)
    jobId = applicationStart.appId
    println("Job Id :: " + jobId.get)
    appName = applicationStart.appName
    println("Job Name :: " + appName)
    val appStatus = "STARTED"
    println("Job Status :: " + appStatus)

    //obj = new DocumentInsert("localhost", 9300)
    //ElasticSearch_CRUD.postDocument(jobId.get, appName, getDate(), appStatus)
    //obj.insertDocument(jobId.get + "" + appStartTime, jobId.get, appName, getDate(), appStatus)
    InsertDocuments.postDocument("123442343")
    super.onApplicationStart(applicationStart)
  }

  override def onApplicationEnd(applicationEnd: SparkListenerApplicationEnd): Unit = {
    println("My Custom Spark Listener is also Stopped when Application Ends...!!!")
    val appEndTime = applicationEnd.time
    println("End Time :: " + appEndTime)
    println("Job Id :: " + jobId.get)
    println("Job Name :: " + appName)
    val appStatus = "COMPLETED"
    println("Job Status :: " + appStatus)
    //ElasticSearch_CRUD.postDocument(jobId.get, appName, getDate(), appStatus)
    //obj.insertDocument(jobId.get + "" + appEndTime, jobId.get, appName, getDate(), appStatus)
    InsertDocuments.postDocument("12344234323")
    super.onApplicationEnd(applicationEnd)
  }

  def getDate(): String = {
    val date = new Date()
    val df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    df.format(date)
  }
}
