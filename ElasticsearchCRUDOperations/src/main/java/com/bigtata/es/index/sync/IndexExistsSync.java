package com.bigtata.es.index.sync;

import com.bigtata.es.utils.ElasticsearchUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;

public class IndexExistsSync {
    public static void main(String[] args) {
        String indexName = "employees";
        ElasticsearchUtils utils = new ElasticsearchUtils();

        RestHighLevelClient client = utils.getDefaultRestHighLevelClient();
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);

        try {
            boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if(exists) {
                System.out.println("Index - " + indexName + " exists...!!!");
            } else {
                System.out.println("Index - " + indexName + " doesn't exists...!!!");
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
