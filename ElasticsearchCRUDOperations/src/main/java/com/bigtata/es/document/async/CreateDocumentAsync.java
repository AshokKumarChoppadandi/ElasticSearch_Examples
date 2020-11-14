package com.bigtata.es.document.async;

import com.bigtata.es.document.listener.CreateDocumentListener;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateDocumentAsync {
    public static void main(String[] args) throws IOException, InterruptedException {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        // Creating the IndexResponse ActionListener for Asynchronous Execution
        ActionListener<IndexResponse> listener = new CreateDocumentListener();

        // Creating the Index / Create Document request
        // 1. Creating document with String
        IndexRequest indexRequest1 = new IndexRequest(indexName);
        indexRequest1.id("101");
        String document1 = "{\"eid\":101,\"ename\":\"Alice\",\"edept\":\"IT\",\"esalary\":10000}";
        indexRequest1.source(document1, XContentType.JSON);

        client.indexAsync(indexRequest1, RequestOptions.DEFAULT, listener);

        // 2. Creating document with Map
        IndexRequest indexRequest2 = new IndexRequest(indexName);
        indexRequest2.id("102");
        Map<String, Object> document2 = new HashMap<>();
        document2.put("eid", 102);
        document2.put("ename", "Bob");
        document2.put("edept", "FI");
        document2.put("esalary", 20000);
        indexRequest2.source(document2);

        client.indexAsync(indexRequest2, RequestOptions.DEFAULT, listener);

        // 3. Creating document with Elasticsearch Helpers
        IndexRequest indexRequest3 = new IndexRequest(indexName);
        indexRequest3.id("103");
        try {
            XContentBuilder document3 = XContentFactory.jsonBuilder();
            document3.startObject();
            {
                document3.field("eid", 103);
                document3.field("ename", "Charlie");
                document3.field("edept", "HR");
                document3.field("esalary", 15000);

            }
            document3.endObject();
            indexRequest3.source(document3);

        } catch (IOException e) {
            e.printStackTrace();
        }

        client.indexAsync(indexRequest3, RequestOptions.DEFAULT, listener);

        // 4. Creating document with Key Value pairs
        IndexRequest indexRequest4 = new IndexRequest(indexName);
        indexRequest4.id("104");
        indexRequest4.source("eid", 104, "ename", "Dave", "edept", "AC", "esalary", 20000);

        client.indexAsync(indexRequest4, RequestOptions.DEFAULT, listener);

        // Sleeping for 1 second, otherwise the client connection is closing because of Asynchronous Execution.
        // Otherwise the listener methods are not invoking
        Thread.sleep(1000L);
        client.close();
        System.out.println("Client connection closed.");

    }
}
