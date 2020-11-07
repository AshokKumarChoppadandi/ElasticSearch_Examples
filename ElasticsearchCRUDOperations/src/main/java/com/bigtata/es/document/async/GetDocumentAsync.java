package com.bigtata.es.document.async;

import com.bigtata.es.document.listener.GetDocumentListener;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class GetDocumentAsync {
    public static void main(String[] args) throws IOException, InterruptedException {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        GetRequest getRequest = new GetRequest(indexName, "101");
        client.getAsync(getRequest, RequestOptions.DEFAULT, new GetDocumentListener());

        // Sleeping for 1 second, otherwise the client connection is closing because of Asynchronous Execution.
        // Otherwise the listener methods are not invoking
        Thread.sleep(1000L);
        client.close();
    }
}
