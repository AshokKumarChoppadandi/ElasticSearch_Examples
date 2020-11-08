package com.bigtata.es.document.async;

import com.bigtata.es.document.listener.DeleteDocumentListener;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class DeleteDocumentAsync {
    public static void main(String[] args) {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        DeleteRequest deleteRequest = new DeleteRequest(indexName, Integer.toString(103));

        client.deleteAsync(deleteRequest, RequestOptions.DEFAULT, new DeleteDocumentListener());

        try {
            // Sleeping for 1 second, otherwise the client connection is closing because of Asynchronous Execution.
            // Otherwise the listener methods are not invoking
            Thread.sleep(1000L);
            client.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
