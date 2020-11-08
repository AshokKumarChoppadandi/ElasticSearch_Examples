package com.bigtata.es.document.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

public class DeleteDocumentSync {
    public static void main(String[] args) {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        DeleteRequest deleteRequest = new DeleteRequest(indexName, Integer.toString(102));

        try {
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            RestStatus status = deleteResponse.status();
            if(status == RestStatus.OK) {
                System.out.println("Document deleted successfully...!!!");
            } else if (status == RestStatus.NOT_FOUND) {
                System.out.println("Document doesn't exists to delete...!!!");
            } else {
                System.out.println("Something happened with the status - " + status);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
