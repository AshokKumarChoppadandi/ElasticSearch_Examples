package com.bigtata.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ListIndices {
    public static void main(String[] args) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")
                )
        );
        System.out.println("Rest Client Created Successfully");

        Request request = new Request("GET", "/_cat/indices");
        try {
            InputStream stream = client
                    .getLowLevelClient()
                    .performRequest(request)
                    .getEntity()
                    .getContent();
            List<String> indices = new BufferedReader(new InputStreamReader(stream))
                    .lines()
                    .map(x -> x.split(" ")[2])
                    .collect(Collectors.toList());
            indices.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
