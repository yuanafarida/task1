package com.task.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    /* Example JSON Response Success Login
    {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYyMSIsImlhdCI6MTc0ODA5Njg4NX0.DALl54y5DQcjwMrH-TcseG1Rfl1rsgKms_9kHrII33k"
    {
    Example JSON Response Failed Login
    {
        "status": "success",
        "message": "This email= yuanafarida111@gmail.com has not been registered yet."
    }
    */
    @SerializedName("token")
    private String token;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    
    public LoginResponse() {
        // Default constructor
    }
    public LoginResponse(String token, String status, String message) {
        this.token = token;
        this.status = status;
        this.message = message;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
