package com.task.request;

public class AddObjectRequest {
/* Example JSON Request:
    {
    "name": "Apple MacBook Pro 16",
    "data": {
        "year": 2019,
        "price": 1849.99,
        "cpu_model": "Intel Core i9",
        "hard_disk_size": "1 TB",
        "capacity": "2 cpu",
        "screen_size": "14 Inch",
        "color": "red"
    }
}*/
   private String name;
   private ObjectData data;
   public AddObjectRequest() {
         // Default constructor
    }
   public AddObjectRequest(String name, ObjectData data) {
         this.name = name;
         this.data = data;
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
}
