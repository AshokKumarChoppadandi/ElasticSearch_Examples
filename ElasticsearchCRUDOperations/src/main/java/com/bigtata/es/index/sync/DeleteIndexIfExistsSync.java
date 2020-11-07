package com.bigtata.es.index.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;

public class DeleteIndexIfExistsSync {
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
                AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                if(response.isAcknowledged()) {
                    System.out.println("Index - " + indexName + " deleted successfully...!!!");
                }
            } else {
                System.out.println("Index - " + indexName + " doesn't exists");
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
