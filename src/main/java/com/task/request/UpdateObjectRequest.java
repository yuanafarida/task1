package com.task.request;

import com.google.gson.annotations.SerializedName;

public class UpdateObjectRequest {
    /* Example JSON Request:
   {
        "name": "Apple MacBook Pro 1611-albert12",
        "year": "2030"
    }
    */
    @SerializedName("name")
    private String name;
    @SerializedName("year")
    private String year;

    public UpdateObjectRequest() {
        // Default constructor
    }
    public UpdateObjectRequest(String name, String year) {
        this.name = name;
        this.year = year;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

}
