package com.bigtata.es.document.listener;

import org.elasticsearch.action.ActionListener;

public class DocumentExistsListener implements ActionListener<Boolean> {
    @Override
    public void onResponse(Boolean aBoolean) {
        if(aBoolean)
            System.out.println("Document exists...!!!");
        else
            System.out.println("Document doesn't exists...!!!");

    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("Error occurred while searching for document");
    }
}
