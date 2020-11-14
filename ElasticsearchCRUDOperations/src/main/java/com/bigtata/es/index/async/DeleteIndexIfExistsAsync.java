package com.bigtata.es.index.async;

import com.bigtata.es.index.listener.DeleteIndexListener;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;

public class DeleteIndexIfExistsAsync {
    public static void main(String[] args) {
        String indexName = "employees";
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")
                )
        );

        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);

        try {
            boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if(exists) {
                client.indices().deleteAsync(deleteIndexRequest, RequestOptions.DEFAULT, new DeleteIndexListener());
            } else {
                System.out.println("Index - " + indexName + " doesn't exists");
            }

            // Sleeping for 1 second, otherwise the client connection is closing because of Asynchronous Execution.
            // Otherwise the listener methods are not invoking
            Thread.sleep(1000L);
            client.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
