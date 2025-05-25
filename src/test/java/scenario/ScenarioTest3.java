package scenario;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.task.request.LoginRequest;
import com.task.request.UpdateObjectRequest;
import com.task.response.AddObjectResponse;
import com.task.response.LoginResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ScenarioTest3 {
    /*
     * Scenario : RestAssured E2E Test
     * Test Case - 002 Test Update Object 
     * 1. Hit the endpoint login untuk mendapatkan token
     * 2. Hit the endpoint single object dengan id yang valid untuk mendapatkan object yang akan diupdate
     * 3. Hit the endpoint update object dengan data yang valid dan token yang didapat dari langkah 1
     * 4. Hit the endpoint single object dengan id yang didapat dari langkah 2 untuk memastikan bahwa object berhasil diupdate
     */

     String token;
     String uniqueEmail = "yuanafarida007@gmail.com";
     int idObject = 715; // Assuming this is the ID of the object to be updated

    @BeforeSuite
    public void setup() {
        // Setup code here, e.g., initializing RestAssured, setting base URI, etc.
        RestAssured.baseURI = "https://whitesmokehouse.com";
    }

    @BeforeClass
    public void testlogin(){
        // Create login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(uniqueEmail);
        loginRequest.setPassword("@dmin123");
        Gson gson = new Gson();
        String requestJson = gson.toJson(loginRequest);
        System.out.println("Request JSON Login: " + requestJson);

        // Send POST request to login endpoint
        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(requestJson)
                .log().all()
                .when()
                .post("/webhook/api/login");
    
        // System.out.println("Response: " + response.asPrettyString()); 
        assert response.statusCode() == 200 : "Login failed with status code: " + response.statusCode();
        LoginResponse loginResponse = gson.fromJson(response.asString(), LoginResponse.class);
        assert loginResponse.getToken() != null : "Login failed with message: " + loginResponse.getMessage();
    
        token = loginResponse.getToken();
        //System.out.println("Token: " + token);
    }
    @Test(priority = 1)
    public void testSingleObjectBeforeUpdate(){       
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", idObject)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/{id}");

        // Print the response
       System.out.println("Response Single Object After Update: " + response.asPrettyString());
       assert response.statusCode() == 200 : "Get Single Object failed with status code: " + response.statusCode();
       AddObjectResponse addObjectResponse = new Gson().fromJson(response.asString(), AddObjectResponse.class);
       assert addObjectResponse.getId() == idObject : "Expected ID " + idObject + " but got " + addObjectResponse.getId();
       assert addObjectResponse != null : "Add Object Response is null";
       assert addObjectResponse.getId() == idObject : "Expected ID " + idObject + " but got " + addObjectResponse.getId();
       assert addObjectResponse.getName().equalsIgnoreCase("Asus Notebook Yuana") : "Expected name Asus Notebook Yuana but got " + addObjectResponse.getName();    
       assert addObjectResponse.getData().getYear() == 2025 : "Expected year 2025 but got " + addObjectResponse.getData().getYear();   
       assert addObjectResponse.getData().getPrice() == 1000 : "Expected price 1000 but got " + addObjectResponse.getData().getPrice();
       assert addObjectResponse.getData().getCpuModel().equals("Intel I3") : "Expected CPU model Intel I3 but got " + addObjectResponse.getData().getCpuModel();
       assert addObjectResponse.getData().getHardDiskSize().equals("500 GB") : "Expected hard disk size 500 GB but got " + addObjectResponse.getData().getHardDiskSize();
       assert addObjectResponse.getData().getCapacity().equals("2") : "Expected capacity 2 cpu but got " + addObjectResponse.getData().getCapacity();
       assert addObjectResponse.getData().getScreenSize().equals("15") : "Expected screen size 15 Inchi but got " + addObjectResponse.getData().getScreenSize();
       assert addObjectResponse.getData().getColor().equals("red") : "Expected color red but got " + addObjectResponse.getData().getColor();
    }

    @Test(priority = 2)
        public void testPartiallyUpdateObject(){
        UpdateObjectRequest updateObjectRequest = new UpdateObjectRequest();
        updateObjectRequest.setName("Notebook Macbook Pro 16 Yuana Updated");
        updateObjectRequest.setYear("2000");
        Gson gson = new Gson();
        String requestJson = gson.toJson(updateObjectRequest);
        System.out.println("Request JSON Update: " + requestJson);
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestJson)
                .log().all()
                .when()
                .patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/{id}", idObject);
        System.out.println("Response Update: " + response.asPrettyString());
        // Print the response
        AddObjectResponse updateResponse = gson.fromJson(response.asString(), AddObjectResponse.class);
        assert response.statusCode() == 200 : "Update Partially failed with status code: " + response.statusCode();
        assert updateResponse.getName().equals("Notebook Macbook Pro 16 Yuana Updated") : "Expected name Notebook Macbook Pro 16 Updated but got " + updateResponse.getName();
        System.out.println("Cek Response: " + String.valueOf(updateResponse.getData().getYear()).equals("2000"));
        assert String.valueOf(updateResponse.getData().getYear()).equals("2000") : "Expected year 2000 but got " + updateResponse.getData().getYear();
    }

   @Test(priority = 3, dependsOnMethods = "testPartiallyUpdateObject")
    public void testSingleObjectAfterUpdate(){       
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", idObject)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/{id}");

        // Print the response
       System.out.println("Response Single Object After Update: " + response.asPrettyString());
       assert response.statusCode() == 200 : "Get Single Object failed with status code: " + response.statusCode();
       AddObjectResponse addObjectResponse = new Gson().fromJson(response.asString(), AddObjectResponse.class);
       assert addObjectResponse.getId() == idObject : "Expected ID " + idObject + " but got " + addObjectResponse.getId();
       assert addObjectResponse.getName().equalsIgnoreCase("Notebook Macbook Pro 16 Yuana Updated") : "Expected name Notebook Macbook Pro 16 Yuana Updated but got " + addObjectResponse.getName();    
       assert addObjectResponse.getData().getYear() == 2000 : "Expected year 2000 but got " + addObjectResponse.getData().getYear();   
    }
}
