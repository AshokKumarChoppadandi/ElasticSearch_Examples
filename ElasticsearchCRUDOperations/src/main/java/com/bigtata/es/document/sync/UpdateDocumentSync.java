package com.bigtata.es.document.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class UpdateDocumentSync {
    public static void main(String[] args) {

        String indexName = "employees";

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        /**
         *
         * Updates can done in two ways:
         *
         * 1. With a Script - Inline or Stored Scripts
         * 2. With a Partial Document
         *
         * Upserts:
         *
         * If the document does not already exist, it is possible to define some content that will be inserted as a new document using the upsert method.
         */

        // 1. Update with a Script - Inline

        System.out.println("Executing the request : 1");
        // Creating an Update Request
        UpdateRequest updateRequest1 = new UpdateRequest(indexName, "101");

        // Creating Parameters - Script parameters provided as a Map of Objects
        Map<String, Object> parameters = Collections.singletonMap("esalary", 12500);

        // Creating a Inline Script using the "painless" language and the parameters.
        Script inlineScript = new Script(ScriptType.INLINE, "painless", "ctx._source.esalary = params.esalary", parameters);

        // Sets the Script to the Update Request
        updateRequest1.script(inlineScript);

        // Inline Script - Executing the Update Request
        try {
            UpdateResponse updateResponse1 = client.update(updateRequest1, RequestOptions.DEFAULT);

            // Capturing the result
            DocWriteResponse.Result result1 = updateResponse1.getResult();

            // Printing the Result
            printOutput(result1);
        } catch (IOException e) {
            System.out.println("Error occurred while executing the update request...!!!" + e);
        }

        System.out.println("Executing the request : 2");

        // 2. Update with a partial document
        UpdateRequest updateRequest2 = new UpdateRequest(indexName, "102");

        // Creating a partial document - Using String Source or by Map or by using the build in Elasticsearch helpers.
        // For this example, using the built in Elasticsearch built in helpers
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("esalary", 12500);
            }
            builder.endObject();
            updateRequest2.doc(builder);

            // Partial Document - Executing the Update request
            UpdateResponse updateResponse2 = client.update(updateRequest2, RequestOptions.DEFAULT);

            // Capturing the result
            DocWriteResponse.Result result2 = updateResponse2.getResult();

            // Printing the Result
            printOutput(result2);
        } catch (IOException e) {
            System.out.println("Error in building the Partial document for Update...!!!" + e);
        }

        System.out.println("Executing the request : 3");

        // 3. Upserts
        UpdateRequest updateRequest3 = new UpdateRequest(indexName, "103");

        // Setting the update value as a String
        // String jsonString = "{\"eage\":30,\"dob\":\"04-09-1990\"}";
        String jsonString = "{\"city\":\"Hyderabad\"}";

        // Setting the Upsert request
        updateRequest3.doc(jsonString, XContentType.JSON);
        updateRequest3.upsert(jsonString, XContentType.JSON);

        // Upsert - Executing the Update request
        try {
            UpdateResponse updateResponse3 = client.update(updateRequest3, RequestOptions.DEFAULT);

            // Capturing the result
            DocWriteResponse.Result result3 = updateResponse3.getResult();

            // Printing the Result
            printOutput(result3);
        } catch (IOException e) {
            System.out.println("Exception occurred while Update / Upsert...!!!" + e);
        }

        // Closing the client
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Exception occurred while closing the client connection - " + e);
        }
    }

    public static void printOutput(DocWriteResponse.Result result) {
        switch (result) {
            case CREATED:
                System.out.println("Upsert - Document has been created successfully...!!!");
                break;
            case UPDATED:
                System.out.println("Updated - Document has been updated successfully...!!!");
                break;
            case DELETED:
                System.out.println("Deleted - Document has been deleted successfully...!!!");
                break;
            case NOT_FOUND:
                System.out.println("Not Found - Document not found for the given _id");
                break;
            case NOOP:
                System.out.println("No Operation - No Operation has been performed for the given Update request.");
                break;
        }
        System.out.println("--------------------------------------------------------------------------------------------");
    }
}
