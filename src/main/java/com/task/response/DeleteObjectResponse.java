package com.task.response;

import com.google.gson.annotations.SerializedName;

public class DeleteObjectResponse {
    /* Example JSON Response for Delete Object
    {
    "status": "deleted",
    "message": "Object with id = 106, has been deleted."
    }*/
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public DeleteObjectResponse() {
        // Default constructor
    }

    public DeleteObjectResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
