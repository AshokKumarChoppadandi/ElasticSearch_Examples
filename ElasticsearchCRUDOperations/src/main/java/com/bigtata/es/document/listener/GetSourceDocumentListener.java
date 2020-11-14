package com.bigtata.es.document.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.client.core.GetSourceResponse;

public class GetSourceDocumentListener implements ActionListener<GetSourceResponse> {
    @Override
    public void onResponse(GetSourceResponse getSourceResponse) {
        System.out.println(getSourceResponse.getSource().toString());
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("Exception occurred while reading the document");
    }
}
