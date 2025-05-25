package com.task.request;

import com.google.gson.annotations.SerializedName;

public class ObjectData {
    /* Example JSON Request:
    {
        "year": 2019,
        "price": 1849.99,
        "cpu_model": "Intel Core i9",
        "hard_disk_size": "1 TB",
        "capacity": "2 cpu",
        "screen_size": "14 Inch",
        "color": "red"
    }
    */
    @SerializedName("year")
    private int year;
    @SerializedName("price")
    private double price;
    @SerializedName("cpu_model")
    private String cpuModel;
    @SerializedName("hard_disk_size")
    private String hardDiskSize;
    @SerializedName("capacity")
    private String capacity;
    @SerializedName("screen_size")
    private String screenSize;
    @SerializedName("color")
    private String color;

    // Getters and Setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public String getHardDiskSize() {
        return hardDiskSize;
    }

    public void setHardDiskSize(String hardDiskSize) {
        this.hardDiskSize = hardDiskSize;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
