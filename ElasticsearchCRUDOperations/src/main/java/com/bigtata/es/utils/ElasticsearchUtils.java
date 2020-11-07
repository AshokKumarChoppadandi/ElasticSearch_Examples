package com.bigtata.es.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class ElasticsearchUtils {
    private String DEFAULT_ES_HOST = "127.0.0.1";
    private int DEFAULT_ES_PORT = 9200;
    private String DEFAULT_ES_CLIENT_SCHEME = "http";
    private String INDEX_NUMBER_OF_SHARDS = "index.number_of_shards";
    private String INDEX_NUMBER_OF_REPLICAS = "index.number_of_replicas";
    private int defaultNumberOfShards = 1;
    private int defaultNumberOfReplicas = 0;

    private String indexName;
    private String esHostname;
    private int esPort;
    private String esClientScheme;
    private RestHighLevelClient client;

    public ElasticsearchUtils() {}

    public ElasticsearchUtils(String indexName, String esHostName, int esPort, String esClientScheme) {
        this.indexName = indexName;
        this.esHostname = esHostName;
        this.esPort = esPort;
        this.esClientScheme = esClientScheme;
    }

    public RestHighLevelClient getDefaultRestHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(DEFAULT_ES_HOST, DEFAULT_ES_PORT, DEFAULT_ES_CLIENT_SCHEME)
                )
        );
        this.client = client;
        return client;
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(this.esHostname, this.esPort, this.esClientScheme)
                )
        );
    }

    public RestHighLevelClient getRestHighLevelClient(String esHostname, int esPort, String esClientScheme) {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(esHostname, esPort, esClientScheme)
                )
        );
    }

    public Builder getDefaultSettings() {
        return Settings
                .builder()
                .put(INDEX_NUMBER_OF_SHARDS, defaultNumberOfShards)
                .put(INDEX_NUMBER_OF_REPLICAS, defaultNumberOfReplicas);
    }

    public XContentBuilder getDefaultMappings() {
        XContentBuilder builder;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    builder.startObject("message");
                    {
                        builder.field("type", "text");
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
        } catch (IOException e) {
            throw new RuntimeException("Unable to build the XContent Builder...!!!");
        }

        return builder;
    }

    public CreateIndexRequest getCreateIndexRequest() {
        return new CreateIndexRequest(this.indexName);
    }

    public CreateIndexRequest getCreateIndexRequest(String indexName) {
        return new CreateIndexRequest(indexName);
    }

    public CreateIndexRequest getCreateIndexRequestWithDefaultSettingsAndMappings() {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(this.indexName);
        createIndexRequest.settings(getDefaultSettings());
        createIndexRequest.mapping(getDefaultMappings());

        return createIndexRequest;
    }

    public CreateIndexRequest getCreateIndexRequestWithDefaultSettings() {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(this.indexName);
        createIndexRequest.settings(getDefaultSettings());

        return createIndexRequest;
    }

    public CreateIndexRequest getCreateIndexRequestWithDefaultMappings() {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(this.indexName);
        createIndexRequest.mapping(getDefaultMappings());

        return createIndexRequest;
    }

    public CreateIndexRequest getCreateIndexRequestWithDefaultSettingsAndMappings(String indexName) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(getDefaultSettings());
        createIndexRequest.mapping(getDefaultMappings());

        return createIndexRequest;
    }

    public CreateIndexRequest getCreateIndexRequestWithSettingsAndMappings(String indexName, Builder settings, XContentBuilder mappings) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(settings);
        createIndexRequest.mapping(mappings);

        return createIndexRequest;
    }

    public CreateIndexRequest getCreateIndexRequestWithSettings(String indexName, Settings settings) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(settings);

        return createIndexRequest;
    }

    public CreateIndexRequest getCreateIndexRequestWithSettingAndMappings(String indexName, XContentBuilder mappings) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.mapping(mappings);

        return createIndexRequest;
    }

    public CreateIndexResponse createIndexSynchronously(RestHighLevelClient client, CreateIndexRequest createIndexRequest) {
        try {
            return client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create index - " + e);
        }
    }

    public void closeDefaultRestClient() {
        try {
            this.client.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close default client - " + e);
        }
    }

    public void closeRestClient(RestHighLevelClient client) {
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close default client - " + e);
        }
    }
}
