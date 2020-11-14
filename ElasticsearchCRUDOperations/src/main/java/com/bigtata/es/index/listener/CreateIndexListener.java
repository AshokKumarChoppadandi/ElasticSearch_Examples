package com.bigtata.es.index.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.client.indices.CreateIndexResponse;

public class CreateIndexListener implements ActionListener<CreateIndexResponse> {
    public void onResponse(CreateIndexResponse createIndexResponse) {

        System.out.println("Inside the Listener Methods...!!!");

        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

        if(acknowledged && shardsAcknowledged) {
            System.out.println("Index - " + createIndexResponse.index() + " created successfully...!!!");
        } else {
            System.out.println("Acknowledgements for creating index - doesn't received.");
        }

        System.out.println("Index Created Successfully : " + createIndexResponse.toString());
    }

    public void onFailure(Exception e) {
        System.out.println("Unable to create index :: " + e);
    }
}
