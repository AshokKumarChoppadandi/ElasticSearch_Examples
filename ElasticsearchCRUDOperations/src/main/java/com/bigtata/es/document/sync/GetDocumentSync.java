package com.bigtata.es.document.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class GetDocumentSync {
    public static void main(String[] args) throws IOException {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        GetRequest getRequest = new GetRequest(indexName, "101");

        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);

        System.out.println("Index :: " + getResponse.getIndex());
        System.out.println("ID :: " + getResponse.getId());
        if(getResponse.isExists()) {
            System.out.println("Version :: " + getResponse.getVersion());
            System.out.println("Document :: " + getResponse.getSourceAsString());
            System.out.println("Document :: " + getResponse.getSourceAsMap());
        }

        client.close();
    }
}
