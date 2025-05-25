package com.task.response;

import com.google.gson.annotations.SerializedName;
import com.task.request.ObjectData;

public class AddObjectResponse {
    // Example response data:
    /*
    [
        {
            "id": 105,
            "name": "Apple MacBook Pro 16 Yuana",
            "data": {
                "year": "2022",
                "price": 1849.99,
                "cpu_model": "Intel Core i9",
                "hard_disk_size": "1 TB",
                "color": "red",
                "capacity": "2 cpu",
                "screen_size": "14 Inch"
            }
        }
    ]
    */
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("data")
    private ObjectData data;
    @SerializedName("result")
    private String result;
    @SerializedName("message")
    private String message;
    
    public AddObjectResponse() {
        // Default constructor
    }
    public AddObjectResponse(Integer id, String name, ObjectData data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }
   public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ObjectData getData() {
        return data;
    }
    public void setData(ObjectData data) {
        this.data = data;
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
