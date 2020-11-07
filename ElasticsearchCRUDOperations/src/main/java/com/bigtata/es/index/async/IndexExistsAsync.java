package com.bigtata.es.index.async;

import com.bigtata.es.index.listener.IndexExistsListener;
import com.bigtata.es.utils.ElasticsearchUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;

public class IndexExistsAsync {
    public static void main(String[] args) {
        String indexName = "employees";
        ElasticsearchUtils utils = new ElasticsearchUtils();

        RestHighLevelClient client = utils.getDefaultRestHighLevelClient();
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);

        try {
            client.indices().existsAsync(getIndexRequest, RequestOptions.DEFAULT, new IndexExistsListener());

            // Sleeping for 1 second, otherwise the client connection is closing because of Asynchronous Execution.
            // Otherwise the listener methods are not invoking
            Thread.sleep(1000L);
            client.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
