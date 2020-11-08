package com.bigtata.es.document.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.rest.RestStatus;

public class DeleteDocumentListener implements ActionListener<DeleteResponse> {
    @Override
    public void onResponse(DeleteResponse deleteResponse) {
        RestStatus status = deleteResponse.status();
        if(status == RestStatus.OK) {
            System.out.println("Document deleted successfully...!!!");
        } else if (status == RestStatus.NOT_FOUND) {
            System.out.println("Document doesn't exists to delete...!!!");
        } else {
            System.out.println("Something happened with the status - " + status);
        }
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("Error occurred while searching for document");
    }
}
