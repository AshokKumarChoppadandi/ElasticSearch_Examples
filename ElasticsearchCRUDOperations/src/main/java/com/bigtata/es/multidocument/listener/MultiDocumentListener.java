package com.bigtata.es.multidocument.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

public class MultiDocumentListener implements ActionListener<BulkResponse> {
    @Override
    public void onResponse(BulkResponse bulkItemResponses) {
        for (BulkItemResponse response : bulkItemResponses) {
            // Processing based on the Bulk Responses
            DocWriteResponse itemResponse = response.getResponse();
            switch (response.getOpType()) {
                case INDEX:
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    System.out.println(
                            "Request Type : " + indexResponse.getResult().name()
                            + "\nIndex : " + indexResponse.getIndex()
                            + "\nId : " + indexResponse.getId()
                    );
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    System.out.println(
                            "Request Type : " + updateResponse.getResult().name()
                            + "\nIndex : " + updateResponse.getIndex()
                            + "\nId : " + updateResponse.getId()
                    );
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    System.out.println(
                            "Request Type : " + deleteResponse.getResult().name()
                            + "\nIndex : " + deleteResponse.getIndex()
                            + "\nId : " + deleteResponse.getId()
                    );
                    break;
                default:
                    System.out.println("Unknown Response - ");
                    System.out.println(
                            "Request Type : " + itemResponse.getResult().name()
                            + "\nIndex : " + itemResponse.getIndex()
                            + "\nId : " + itemResponse.getId()
                    );
                    break;
            }
        }
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("Exception Occurred while executing the BULK API Request -" + e);
    }
}
