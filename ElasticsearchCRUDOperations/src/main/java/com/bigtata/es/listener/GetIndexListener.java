package com.bigtata.es.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;

import java.util.List;
import java.util.Map;

public class GetIndexListener implements ActionListener<GetIndexResponse> {

    private String indexName;

    public GetIndexListener (String indexName) {
        this.indexName = indexName;
    }

    @Override
    public void onResponse(GetIndexResponse getIndexResponse) {
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
    }

    @Override
    public void onFailure(Exception e) {

    }
}
