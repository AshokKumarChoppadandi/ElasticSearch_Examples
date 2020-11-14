package com.bigtata.es.multidocument.async;

/**
 * A BulkRequest can be used to execute multiple index, update and/or delete operations using a single request.
 * It requires at least one operation to be added to the Bulk request
 */

import com.bigtata.es.multidocument.listener.MultiDocumentListener;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class MultiDocumentAPIAsync {
    public static void main(String[] args) {
        String indexName = "employees";

        // Creating the Client
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        // Creating a Bulk Request
        BulkRequest request = new BulkRequest();

        // Adding an Index Request
        request.add(
                new IndexRequest(indexName)
                        .id("105")
                        .source(XContentType.JSON, "ename", "Eve")
        );
        // Adding another Index Request
        request.add(
                new IndexRequest(indexName)
                        .id("106")
                        .source(XContentType.JSON, "ename", "Frank")
        );

        // Adding an Update Request
        request.add(
                new UpdateRequest(indexName, "101")
                        .doc(XContentType.JSON, "esalary", "11111")
        );
        // Adding a Delete Request
        request.add(
                new DeleteRequest(indexName, "102")
        );

        // Executing a Bulk Request
        client.bulkAsync(request, RequestOptions.DEFAULT, new MultiDocumentListener());

        // Closing the client connection
        try {
            Thread.sleep(1000L);
            client.close();
        } catch (IOException | InterruptedException e) {
            System.out.println("Exception occurred while closing the client connection - " + e);
        }


    }
}
