package scenario;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.request.AddObjectRequest;
import com.task.request.LoginRequest;
import com.task.request.ObjectData;
import com.task.response.AddObjectResponse;
import com.task.response.DeleteObjectResponse;
import com.task.response.LoginResponse;

import io.restassured.RestAssured;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

public class ScenarioTest2 {
    /*
     * Scenario : RestAssured E2E Test
     * Test Case - 002 Test Add and Delete Object 
     * 1. Hit the endpoint login untuk mendapatkan token
     * 2. Hit the endpoint add object dengan data yang valid dan token yang didapat dari langkah 1
     * 3. Hit the endpoint single object dengan id yang didapat dari langkah 2 untuk memastikan bahwa object berhasil ditambahkan
     * 4. Hit the endpoint delete object dengan id yang didapat dari langkah 2 untuk menghapus object
     * 5. Hit the endpoint single object dengan id yang didapat dari langkah 2 untuk memastikan bahwa object berhasil dihapus
     */

     String token;
     String uniqueEmail = "yuanafarida007@gmail.com";
     int idObject;

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
   public void testAddObject(){
        AddObjectRequest addObjectRequest = new AddObjectRequest();
        addObjectRequest.setName("Asus Notebook Yuana");
        ObjectData objectData = new ObjectData();
        objectData.setYear(2025);
        objectData.setPrice(1000);
        objectData.setCpuModel("Intel I3");
        objectData.setHardDiskSize("500 GB");
        objectData.setCapacity("2 cpu");
        objectData.setScreenSize("15 Inchi");
        objectData.setColor("red");
        addObjectRequest.setData(objectData);

        Gson gson = new Gson();
        String requestJson = gson.toJson(addObjectRequest);
        System.out.println("Request JSON Add Object: " + requestJson);

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestJson)
                .log().all()
                .when()
                .post("/webhook/api/objects");

        // Print the response
        System.out.println("Response Add Object: " + response.asPrettyString());
         try {
            response.then().assertThat().body(matchesJsonSchemaInClasspath("addobject-schema.json"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assert response.statusCode() == 200 : "Add Object failed with status code: " + response.statusCode();
        List<AddObjectResponse> addObjectResponseList = new Gson().fromJson(response.asString(),  new TypeToken<List<AddObjectResponse>>(){}.getType());
        for(AddObjectResponse addObjectResponse : addObjectResponseList) {
            System.out.println("Add Object Response: " + addObjectResponse.getName() + ", ID: " + addObjectResponse.getId());
            assert addObjectResponse.getData() != null : "Object data is null";
            assert addObjectResponse.getData().getYear() == 2025 : "Expected year 2025 but got " + addObjectResponse.getData().getYear();
            assert addObjectResponse.getData().getPrice() == 1000 : "Expected price 1000 but got " + addObjectResponse.getData().getPrice();
            assert addObjectResponse.getData().getCpuModel().equals("Intel I3") : "Expected CPU model Intel I3 but got " + addObjectResponse.getData().getCpuModel();
            assert addObjectResponse.getData().getHardDiskSize().equals("500 GB") : "Expected hard disk size 500 GB but got " + addObjectResponse.getData().getHardDiskSize();  
            assert addObjectResponse.getData().getCapacity().equals("2 cpu") : "Expected capacity 2 cpu but got " + addObjectResponse.getData().getCapacity();
            assert addObjectResponse.getData().getScreenSize().equals("15 Inchi") : "Expected screen size 15 Inchi but got " + addObjectResponse.getData().getScreenSize(); 
            assert addObjectResponse.getData().getColor().equals("red") : "Expected color red but got " + addObjectResponse.getData().getColor();       
            idObject = addObjectResponse.getId();
        }
    }

    @Test(priority = 2, dependsOnMethods = "testAddObject")
    public void testSingleObjectAfterAdd(){       
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", idObject)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/{id}");

        // Print the response
        System.out.println("Response Single Object: " + response.asPrettyString());
        assert response.statusCode() == 200 : "Get Single Object failed with status code: " + response.statusCode();
        try {
            response.then().assertThat().body(matchesJsonSchemaInClasspath("getsingleobject-schema.json"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }    
        assert response.statusCode() == 200 : "Get Single Object failed with status code: " + response.statusCode();
        AddObjectResponse addObjectResponse = new Gson().fromJson(response.asString(), AddObjectResponse.class);
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

    @Test(priority = 3)
    public void testDeleteObject(){

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/{id}", idObject);
        System.out.println("Response Delete Object: " + response.asPrettyString());
        assert response.statusCode() == 200 : "Delete Object failed with status code: " + response.statusCode();
        DeleteObjectResponse deleteObjectResponse = new Gson().fromJson(response.asString(), DeleteObjectResponse.class);
        assert deleteObjectResponse.getStatus().equals("deleted") : "Expected result deleted but got " + deleteObjectResponse.getStatus();
    }   
    
    @Test(priority = 4, dependsOnMethods = "testDeleteObject")
    public void testSingleObjectAfterDelete(){       
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", idObject)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/{id}");

        // Print the response
       System.out.println("Response Single Object After Delete: " + response.asPrettyString());
       assert response.statusCode() == 200 : "Get Single Object failed with status code: " + response.statusCode();
       AddObjectResponse addObjectResponse = new Gson().fromJson(response.asString(), AddObjectResponse.class);
       assert addObjectResponse.getId()==null : "Delete Object failed, expected null but got " + addObjectResponse;
    }
}
