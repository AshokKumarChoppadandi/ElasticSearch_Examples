package com.bigtata.es.document.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

/**
 * Exists API:
 * The exists API returns true if a document exists, and false otherwise.
 * It uses GetRequest just like the Get API.
 * All of its optional arguments are supported.
 * Since exists() only returns true or false, it is recommend to turn off fetching _source and any stored fields so the request is slightly lighter:
 */

public class DocumentExistsSync {
    public static void main(String[] args) {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        GetRequest getRequest = new GetRequest(indexName, Integer.toString(105));
        // Disable fetching source
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        // Disable fetching stored fields
        getRequest.storedFields("_none_");

        try {
            boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
            if(exists)
                System.out.println("Document exists...!!!");
            else
                System.out.println("Document doesn't exists...!!!");
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
