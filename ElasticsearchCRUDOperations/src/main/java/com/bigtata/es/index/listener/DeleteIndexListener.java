package com.bigtata.es.index.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.master.AcknowledgedResponse;

public class DeleteIndexListener implements ActionListener<AcknowledgedResponse> {
    @Override
    public void onResponse(AcknowledgedResponse acknowledgedResponse) {
        if(acknowledgedResponse.isAcknowledged()) {
            System.out.println("Index deleted successfully");
        }
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("Exception occurred while deleting the index - " + e);
    }
}
