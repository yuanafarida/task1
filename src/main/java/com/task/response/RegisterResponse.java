package com.task.response;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    /*Example JSON Response Success Register
    {
        "id": "115",
        "email": "yuanafarida2@gmail.com",
        "full_name": "Yuana Farida",
        "department": "manager",
        "phone_number": "082264189134",
        "create_at": "2025-05-17T15:14:35.156Z",
        "update_at": "2025-05-17T15:14:35.156Z"
    } 
    Example JSON Response Failed Register
    {
    "result": "failed",
    "message": "Email = yuanafarida1@gmail.com already registered"
    }    
    */
    @SerializedName("id") 
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("department")
    private String department;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("create_at")
    private Date createAt;
    @SerializedName("update_at")
    private Date updateAt;
    @SerializedName("result")
    private String result;
    @SerializedName("message")
    private String message;

    public RegisterResponse() {
        // Default constructor
    }
    public RegisterResponse(int id, String email, String fullName, String department, String phoneNumber, Date createAt, Date updateAt) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
