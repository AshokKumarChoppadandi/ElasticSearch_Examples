package com.bigtata.es.document.async;

import com.bigtata.es.document.listener.DocumentExistsListener;
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

public class DocumentExistsAsync {
    public static void main(String[] args) {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        GetRequest getRequest = new GetRequest(indexName, Integer.toString(101));
        // Disable fetching source
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        // Disable fetching stored fields
        getRequest.storedFields("_none_");

        client.existsAsync(getRequest, RequestOptions.DEFAULT, new DocumentExistsListener());

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
