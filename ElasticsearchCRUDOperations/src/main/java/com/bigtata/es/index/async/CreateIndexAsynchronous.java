package com.bigtata.es.index.async;

import com.bigtata.es.listener.CreateIndexListener;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class CreateIndexAsynchronous {
    public static void main(String[] args) {
        String indexName = "employees";

        // Creating an Elasticsearch Client
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"))
        );

        // Optional - Setting for the Index
        Settings.Builder indexSettings = Settings
                .builder()
                .put("index.number_of_shards", "1")
                .put("index.number_of_replicas", "0");

        // Optional - Mappings for the Index
        // There are multiple ways to build the Mapping - Using a JSON String, Map and the Elasticsearch built-in helpers
        // Using built-in helpers here:

        XContentBuilder builder;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.startObject("properties");
            builder.startObject("message");
            builder.field("type", "text");
            builder.endObject();
            builder.endObject();
            builder.endObject();
        } catch (IOException e) {
            throw new RuntimeException("Unable to build the XContent Builder...!!!");
        }

        /**
         * Create an Index with Settings & Mappings in a Synchronised matter
         */
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        // Optional - Adding Index Settings for the Index
        createIndexRequest.settings(indexSettings);

        // Optional - Adding Mappings
        createIndexRequest.mapping(builder);

        // Creating Listener
        ActionListener<CreateIndexResponse> listener = new CreateIndexListener();

        try {
            System.out.println("Creating Index Asynchronously");
            // Asynchronous Execution
            client.indices().createAsync(createIndexRequest, RequestOptions.DEFAULT, listener);

            // Sleeping for 1 second, otherwise the client connection is closing because of Asynchronous Execution.
            // Hence the listener methods are not invoking
            Thread.sleep(1000L);
            client.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close the connection...!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
