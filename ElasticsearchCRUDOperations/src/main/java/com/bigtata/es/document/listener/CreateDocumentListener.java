package com.bigtata.es.document.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;

public class CreateDocumentListener implements ActionListener<IndexResponse> {
    @Override
    public void onResponse(IndexResponse indexResponse) {
        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        DocWriteResponse.Result result = indexResponse.getResult();
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();

        System.out.println("Index Response :: \n" +
                "Index Name :: " + index + "\n" +
                "Document Id :: " + id + "\n" +
                "Result Status :: " + result + "\n" +
                "Shard Info :: " + shardInfo);
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("Exception Occurred while creating the documents..." + e);
    }
}
