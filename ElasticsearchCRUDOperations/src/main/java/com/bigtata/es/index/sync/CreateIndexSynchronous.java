package com.bigtata.es.index.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class CreateIndexSynchronous {
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

        CreateIndexResponse response;
        try {
            // Synchronous Execution
            response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create Index - " + indexName);
        }

        boolean acknowledged = response.isAcknowledged();
        boolean shardsAcknowledged = response.isShardsAcknowledged();

        if(acknowledged && shardsAcknowledged) {
            System.out.println("Index - " + indexName + " created successfully...!!!");
        } else {
            System.out.println("Acknowledgements for creating index - " + indexName + " doesn't received.");
        }

        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close the connection...!!!");
        }
    }
}
