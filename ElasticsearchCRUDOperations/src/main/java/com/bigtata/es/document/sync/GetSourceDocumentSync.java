package com.bigtata.es.document.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;

import java.io.IOException;

public class GetSourceDocumentSync {
    public static void main(String[] args) {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        GetSourceRequest getSourceRequest = new GetSourceRequest(indexName, Integer.toString(101));
        try {
            GetSourceResponse getSourceResponse = client.getSource(getSourceRequest, RequestOptions.DEFAULT);
            System.out.println(getSourceResponse.getSource().toString());
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
