package com.bigtata.es.listener;

import org.elasticsearch.action.ActionListener;

public class IndexExistsListener implements ActionListener<Boolean> {
    @Override
    public void onResponse(Boolean aBoolean) {
        if(aBoolean) {
            System.out.println("Index exists...!!!");
        }
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("Exception occurred while creating the index - " + e);
    }
}
