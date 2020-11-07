package com.bigtata.es.index.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetIndexSync {
    public static void main(String[] args) {
        String indexName = "employees";
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);

        try {
            GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
            MappingMetadata indexMappings = getIndexResponse.getMappings().get(indexName);
            Map<String, Object> indexTypeMappings = indexMappings.getSourceAsMap();
            System.out.println("Index Type Mappings ::\n" + indexTypeMappings);

            List<AliasMetadata> aliases = getIndexResponse.getAliases().get(indexName);
            System.out.println("Index Aliases - " + aliases);

            // Reading settings from Setting Object
            Settings settings = getIndexResponse.getSettings().get(indexName);
            Integer numOfShards = settings.getAsInt("index.number_of_shards", null);
            Integer numOfReplicas = settings.getAsInt("index.number_of_replicas", null);
            System.out.println("Index " + indexName + " settings :: \nNumber of Shards - " + numOfShards + "\nNumber of Replicas - " + numOfReplicas);

            // Reading settings directly
            String numOfShards2 = getIndexResponse.getSetting(indexName, "index.number_of_shards");
            System.out.println("Number of Shards :: " + numOfShards2);

            // Reading Default Settings
            Settings defaultSettings = getIndexResponse.getDefaultSettings()
                    .getOrDefault("index", null);

            if (defaultSettings != null) {
                TimeValue timeValue = defaultSettings.getAsTime("index.refresh_interval", TimeValue.timeValueSeconds(0L));
                System.out.println("Default Settings :: \n Refresh Interval - " + timeValue);
            }

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
