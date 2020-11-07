package com.bigtata.es.index.async;

import com.bigtata.es.listener.GetIndexListener;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;

public class GetIndexAsync {
    public static void main(String[] args) {
        String indexName = "employees";
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);

        try {
            client.indices().getAsync(getIndexRequest, RequestOptions.DEFAULT, new GetIndexListener(indexName));

            // Sleeping for 1 second, otherwise the client connection is closing because of Asynchronous Execution.
            // Hence the listener methods are not invoking
            Thread.sleep(1000L);
            client.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
