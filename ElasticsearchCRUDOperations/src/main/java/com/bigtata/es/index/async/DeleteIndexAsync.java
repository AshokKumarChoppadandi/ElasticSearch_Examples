package com.bigtata.es.index.async;

import com.bigtata.es.index.listener.DeleteIndexListener;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class DeleteIndexAsync {
    public static void main(String[] args) {
        String indexName = "employees";
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")
                )
        );

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);

        try {
            client.indices().deleteAsync(deleteIndexRequest, RequestOptions.DEFAULT, new DeleteIndexListener());

            // Sleeping for 1 second, otherwise the client connection is closing because of Asynchronous Execution.
            // Otherwise the listener methods are not invoking
            Thread.sleep(1000L);
            client.close();
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception Occurred while deleting the index - " + e);
        }
    }
}
