package com.bigtata.es.index.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class DeleteIndexSync {
    public static void main(String[] args) {
        String indexName = "employees";
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")
                )
        );

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);

        try {
            AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            if(response.isAcknowledged()) {
                System.out.println("Index - " + indexName + " deleted successfully...!!!");
            }
            client.close();
        } catch (IOException e) {
            System.out.println("Exception Occurred while deleting the index - " + e);
        }
    }
}
