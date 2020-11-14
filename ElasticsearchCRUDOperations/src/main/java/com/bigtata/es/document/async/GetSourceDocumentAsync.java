package com.bigtata.es.document.async;

import com.bigtata.es.document.listener.GetSourceDocumentListener;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;

import java.io.IOException;

public class GetSourceDocumentAsync {
    public static void main(String[] args) {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        GetSourceRequest getSourceRequest = new GetSourceRequest(indexName, Integer.toString(101));
        client.getSourceAsync(getSourceRequest, RequestOptions.DEFAULT, new GetSourceDocumentListener());

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
