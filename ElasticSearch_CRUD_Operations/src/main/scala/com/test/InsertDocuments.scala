package com.test

import java.net.InetAddress

import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.transport.client.PreBuiltTransportClient

object InsertDocuments {
  def postDocument(id: String): Unit = {
    val settings  = Settings.builder().put("cluster.name", "elasticsearch").build()
    val client = new PreBuiltTransportClient(settings)
      .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300))

    val builder = XContentFactory.jsonBuilder().startObject()
      .field("job_id", "9876")
      .field("job_name", "TestJob2")
      .field("job_time", "1242432432")
      .field("job_status", "COMPLETED").endObject()

    val response = client.prepareIndex("job_status", "job", id).setSource(builder).get()
    println(response.status())
  }
}
