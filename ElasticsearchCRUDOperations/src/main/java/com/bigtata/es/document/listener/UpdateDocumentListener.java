package com.bigtata.es.document.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateResponse;

public class UpdateDocumentListener implements ActionListener<UpdateResponse> {
    @Override
    public void onResponse(UpdateResponse updateResponse) {
        DocWriteResponse.Result result = updateResponse.getResult();

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

    @Override
    public void onFailure(Exception e) {
        System.out.println("Exception Occurred while creating the documents..." + e);
    }
}
