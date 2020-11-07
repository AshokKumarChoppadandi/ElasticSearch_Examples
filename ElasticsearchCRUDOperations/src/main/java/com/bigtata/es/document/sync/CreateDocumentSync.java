package com.bigtata.es.document.sync;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse.ShardInfo;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateDocumentSync {
    public static void main(String[] args) throws IOException {
        String indexName = "employees";

        // Creating the RestHighLevelClient
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        // Creating the Index / Create Document request
        // 1. Creating document with String
        IndexRequest indexRequest1 = new IndexRequest(indexName);
        indexRequest1.id("101");
        String document1 = "{\"eid\":101,\"ename\":\"Alice\",\"edept\":\"IT\",\"esalary\":10000}";
        indexRequest1.source(document1, XContentType.JSON);

        IndexResponse indexResponse1 = client.index(indexRequest1, RequestOptions.DEFAULT);
        String index1 = indexResponse1.getIndex();
        String id1 = indexResponse1.getId();
        Result result1 = indexResponse1.getResult();
        ShardInfo shardInfo1 = indexResponse1.getShardInfo();

        System.out.println("Index Response 1 :: \n" +
                "Index Name :: " + index1 + "\n" +
                "Document Id :: " + id1 + "\n" +
                "Result Status :: " + result1 + "\n" +
                "Shard Info :: " + shardInfo1);

        // 2. Creating document with Map
        IndexRequest indexRequest2 = new IndexRequest(indexName);
        indexRequest2.id("102");
        Map<String, Object> document2 = new HashMap<>();
        document2.put("eid", 102);
        document2.put("ename", "Bob");
        document2.put("edept", "FI");
        document2.put("esalary", 20000);
        indexRequest2.source(document2);

        IndexResponse indexResponse2 = client.index(indexRequest2, RequestOptions.DEFAULT);
        String index2 = indexResponse2.getIndex();
        String id2 = indexResponse2.getId();
        Result result2 = indexResponse2.getResult();
        ShardInfo shardInfo2 = indexResponse2.getShardInfo();

        System.out.println("Index Response 2 :: \n" +
                "Index Name :: " + index2 + "\n" +
                "Document Id :: " + id2 + "\n" +
                "Result Status :: " + result2 + "\n" +
                "Shard Info :: " + shardInfo2);

        // 3. Creating document with Elasticsearch Helpers
        IndexRequest indexRequest3 = new IndexRequest(indexName);
        indexRequest3.id("103");
        try {
            XContentBuilder document3 = XContentFactory.jsonBuilder();
            document3.startObject();
            {
                document3.field("eid", 103);
                document3.field("ename", "Charlie");
                document3.field("edept", "HR");
                document3.field("esalary", 15000);

            }
            document3.endObject();
            indexRequest3.source(document3);

        } catch (IOException e) {
            e.printStackTrace();
        }

        IndexResponse indexResponse3 = client.index(indexRequest3, RequestOptions.DEFAULT);
        String index3 = indexResponse3.getIndex();
        String id3 = indexResponse3.getId();
        Result result3 = indexResponse3.getResult();
        ShardInfo shardInfo3 = indexResponse3.getShardInfo();

        System.out.println("Index Response 3 :: \n" +
                "Index Name :: " + index3 + "\n" +
                "Document Id :: " + id3 + "\n" +
                "Result Status :: " + result3 + "\n" +
                "Shard Info :: " + shardInfo3);

        // 4. Creating document with Key Value pairs
        IndexRequest indexRequest4 = new IndexRequest(indexName);
        indexRequest4.id("104");
        indexRequest4.source("eid", 104, "ename", "Dave", "edept", "AC", "esalary", 20000);

        IndexResponse indexResponse4 = client.index(indexRequest4, RequestOptions.DEFAULT);
        String index4 = indexResponse4.getIndex();
        String id4 = indexResponse4.getId();
        Result result4 = indexResponse4.getResult();
        ShardInfo shardInfo4 = indexResponse4.getShardInfo();

        System.out.println("Index Response 4 :: \n" +
                "Index Name :: " + index4 + "\n" +
                "Document Id :: " + id4 + "\n" +
                "Result Status :: " + result4 + "\n" +
                "Shard Info :: " + shardInfo4);

        client.close();
        System.out.println("Client connection closed.");

    }
}
