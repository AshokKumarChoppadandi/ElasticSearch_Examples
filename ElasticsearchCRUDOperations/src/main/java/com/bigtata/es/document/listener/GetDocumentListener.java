package com.bigtata.es.document.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;

public class GetDocumentListener implements ActionListener<GetResponse> {
    @Override
    public void onResponse(GetResponse documentFields) {
        System.out.println("Index :: " + documentFields.getIndex());
        System.out.println("ID :: " + documentFields.getId());
        if(documentFields.isExists()) {
            System.out.println("Version :: " + documentFields.getVersion());
            System.out.println("Document :: " + documentFields.getSourceAsString());
            System.out.println("Document :: " + documentFields.getSourceAsMap());
        }
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("Exception occurred while read the document");
    }
}
