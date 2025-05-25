package com.task.response;

import com.google.gson.annotations.SerializedName;

public class DepartmentResponse {
/* Example JSON Response for Department
[
    {
        "id": 1,
        "department": "manager"
    },
    {
        "id": 2,
        "department": "marketing"
    },
    {
        "id": 3,
        "department": "sales"
    },
    {
        "id": 4,
        "department": "store manager"
    },
    {
        "id": 5,
        "department": "branch manager"
    },
    {
        "id": 6,
        "department": "branch manager"
    }
]*/
   @SerializedName("id")
   private int id;
   @SerializedName("department")
   private String department; 

   public DepartmentResponse() {
       // Default constructor
   }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}