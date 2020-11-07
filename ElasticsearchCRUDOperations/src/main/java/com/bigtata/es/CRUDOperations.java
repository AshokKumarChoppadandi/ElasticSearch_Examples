package com.bigtata.es;

import com.bigtata.es.utils.ElasticsearchUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

public class CRUDOperations {
    public static void main(String[] args) {

        // Creating Index employees
        ElasticsearchUtils utils1 = new ElasticsearchUtils();
        String index1 = "employees";
        CreateIndexResponse response1 = utils1.createIndexSynchronously(
                utils1.getDefaultRestHighLevelClient(),
                utils1.getCreateIndexRequestWithDefaultSettingsAndMappings(index1)
        );
        if (response1.isAcknowledged() && response1.isShardsAcknowledged()) {
            System.out.println("Index - " + index1 + " created Successfully...!!!");
        }
        utils1.closeDefaultRestClient();

        // Creating Index departments
        String index2 = "departments";
        ElasticsearchUtils utils2 = new ElasticsearchUtils(index2, "127.0.0.1", 9200, "http");
        RestHighLevelClient client2 = utils2.getRestHighLevelClient();
        CreateIndexResponse response2 = utils2.createIndexSynchronously(
                client2,
                utils2.getCreateIndexRequestWithDefaultSettingsAndMappings()
        );
        if (response2.isAcknowledged() && response2.isShardsAcknowledged()) {
            System.out.println("Index - " + index2 + " created Successfully...!!!");
        }
        utils2.closeRestClient(client2);

        // Creating Index managers
        String index3 = "managers";
        ElasticsearchUtils utils3 = new ElasticsearchUtils();
        Builder settings = utils3.getDefaultSettings();
        XContentBuilder mappings = utils3.getDefaultMappings();
        RestHighLevelClient client3 = utils3.getRestHighLevelClient("127.0.0.1", 9200, "http");
        CreateIndexResponse response3 = utils3.createIndexSynchronously(
                client3,
                utils3.getCreateIndexRequestWithSettingsAndMappings(index3, settings, mappings)
        );
        if (response3.isAcknowledged() && response3.isShardsAcknowledged()) {
            System.out.println("Index - " + index3 + " created Successfully...!!!");
        }
        utils3.closeRestClient(client3);


        // List Indices

        // client.indices().get()

        // Preparing an Index Request

        // Creating the Index

        // Checking the Response
    }
}
