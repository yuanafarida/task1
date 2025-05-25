package scenario;

import java.util.List;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.request.LoginRequest;
import com.task.request.RegisterRequest;
import com.task.response.DepartmentResponse;
import com.task.response.LoginResponse;
import com.task.response.RegisterResponse;

import io.restassured.RestAssured;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

public class ScenarioTest1 {
    /*
     * Scenario : RestAssured E2E Test
     * Test Case - 001: Register Employee
     * 1. Hit the endpoint register dengan data valid
     * 2. Hit the endpoint login dengan data yang sudah diregister di langkah 1 untuk memastikan bahwa data sudah terdafta
     * 3. Hit the endpoint get all departement dengan token yang didapat dari langkah 2
     */

     String token;
     int idRegister;
     String uniqueEmail = "yuanafarida007@gmail.com";

    @BeforeSuite
    public void setup() {
        // Setup code here, e.g., initializing RestAssured, setting base URI, etc.
        RestAssured.baseURI = "https://whitesmokehouse.com";
    }

    @Test(priority = 1)
    public void testRegistration() {
       RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.setEmail(uniqueEmail);
        registerRequest.setFullName("Yuana Farida");
        registerRequest.setPassword("@dmin123");
        registerRequest.setDepartment("Technology");
        registerRequest.setPhoneNumber("08123456789");

        Gson gson = new Gson();
        String requestJson = gson.toJson(registerRequest);

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestJson)
                .log().all()
                .when()
                .post("/webhook/api/register");
                 System.out.println("Response: " + response.asPrettyString());
        RegisterResponse registerResponse = gson.fromJson(response.asString(), RegisterResponse.class);
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
        try {
            response.then().assertThat().body(matchesJsonSchemaInClasspath("register-schema.json"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assert registerResponse.getEmail().equals(uniqueEmail) : "Expected email " + uniqueEmail + " but got " + registerResponse.getEmail();
        assert registerResponse.getFullName().equals("Yuana Farida") : "Expected name Yuana Farida but got " + registerResponse.getFullName();
        assert registerResponse.getDepartment().equals("Technology") : "Expected department Technology but got " + registerResponse.getDepartment();
        assert registerResponse.getPhoneNumber().equals("08123456789") : "Expected phone number 08123456789 but got " + registerResponse.getPhoneNumber();
        
        idRegister = registerResponse.getId();             
    }
    
    @Test(priority = 2, dependsOnMethods = "testRegistration")
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

   @Test(priority = 3, dependsOnMethods = "testlogin")
    public void testGetAllDepartment() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/department");

        // Print the response
        System.out.println("Response All Dept: " + response.asPrettyString());
        assert response.statusCode() == 200 : "Gell all department failed with status code: " + response.statusCode();
       try {
            response.then().assertThat().body(matchesJsonSchemaInClasspath("department-schema.json"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<DepartmentResponse> departmentResponseList = new Gson().fromJson(response.asString(),  new TypeToken<List<DepartmentResponse>>() {}.getType());
        assert departmentResponseList != null : "Department response is null";
        for(DepartmentResponse department : departmentResponseList) {
            System.out.println("Department ID: " + department.getId() + ", Name: " + department.getDepartment());
        }
    }
}
