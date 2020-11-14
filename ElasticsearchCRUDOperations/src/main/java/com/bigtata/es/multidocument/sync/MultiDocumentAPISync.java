package com.bigtata.es.multidocument.sync;

/**
 * A BulkRequest can be used to execute multiple index, update and/or delete operations using a single request.
 * It requires at least one operation to be added to the Bulk request
 */

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

public class MultiDocumentAPISync {
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
        try {
            BulkResponse responses = client.bulk(request, RequestOptions.DEFAULT);
            for (BulkItemResponse response : responses) {
                // Processing based on the Bulk Responses
                DocWriteResponse itemResponse = response.getResponse();
                switch (response.getOpType()) {
                    case INDEX:
                    case CREATE:
                        IndexResponse indexResponse = (IndexResponse) itemResponse;
                        System.out.println(
                                "Request Type : " + indexResponse.getResult().name()
                                + "\nIndex : " + indexResponse.getIndex()
                                + "\nId : " + indexResponse.getId()
                        );
                        break;
                    case UPDATE:
                        UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                        System.out.println(
                                "Request Type : " + updateResponse.getResult().name()
                                + "\nIndex : " + updateResponse.getIndex()
                                + "\nId : " + updateResponse.getId()
                        );
                        break;
                    case DELETE:
                        DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                        System.out.println(
                                "Request Type : " + deleteResponse.getResult().name()
                                + "\nIndex : " + deleteResponse.getIndex()
                                + "\nId : " + deleteResponse.getId()
                        );
                        break;
                    default:
                        System.out.println("Unknown Response - ");
                        System.out.println(
                                "Request Type : " + itemResponse.getResult().name()
                                + "\nIndex : " + itemResponse.getIndex()
                                + "\nId : " + itemResponse.getId()
                        );
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while executing the Bulk API request - " + e);
        }

        // Closing the client connection
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Exception occurred while closing the client connection - " + e);
        }


    }
}
