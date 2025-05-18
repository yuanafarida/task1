package restassured;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestEmployeeImpl {
    String token;

    // @BeforeClass
    // public void setup() {
    //     // Base URI of your API (change it to your actual API URL)
    //     RestAssured.baseURI = "https://whitesmokehouse.com";
    // }

    // @Test
    // public void testRegistration() {
    //    String requestBody = "{\"email\": \"yuanafaridatest4@gmail.com\",\n" + 
    //                     "  \"full_name\": \"Yuana Test4\",\n" + //
    //                     "  \"password\": \"@dmin123\",\n" + //
    //                     "  \"department\": \"Executive\",\n" + //
    //                      "  \"phone_number\": \"0816168888\"\n" + //
    //                     "}";

    //     Response response = RestAssured.given()
    //             .contentType("application/json")
    //             .body(requestBody)
    //             .log().all()
    //             .when()
    //             .post("/webhook/api/register");
        
    //     Assert.assertNull(response.jsonPath().getString("result"), "Registration Failed");                
    // }

    @BeforeSuite
    @Test
    public void testlogin(){
        RestAssured.baseURI = "https://whitesmokehouse.com";
        // Create login request
        String requestBody = "{\n" + //
                        "  \"email\": \"yuanafaridatest@gmail.com\",\n" + //
                        "  \"password\": \"@dmin123\"\n" + //
                        "}";
        // Send POST request to login endpoint
        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/webhook/api/login");
    
        // System.out.println("Response: " + response.asPrettyString()); 
        assert response.statusCode() == 200 : "Login failed with status code: " + response.statusCode();
        assert response.jsonPath().getString("token") != null: "Login failed with message: " + response.jsonPath().getString("message");
        token = response.jsonPath().getString("token");  
        System.out.println("Token: " + token);
    }

    // @Test
    // public void testListAllObject(){
       
    //     Response response = RestAssured.given()
    //             .header("Content-Type", "application/json")
    //             .header("Authorization", "Bearer " + token)
    //             .log().all()
    //             .when()
    //             .get("/webhook/api/objects");
    //     // Print the response
    //     System.out.println("Response List Object: " + response.asPrettyString());
    //     assert response.jsonPath().getString("[0]")!=null: "List All Object Empty";
    // }

     @Test
    public void testListOfObjectsByIds(){       
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("id",56)
                .log().all()
                .when()
                .get("/webhook/api/objects");

        // Print the response
        System.out.println("Response List of Object Id: " + response.asPrettyString());
        assert response.jsonPath().getString("[0].id").equals("56"): "Object Id Not Match";
        assert response.jsonPath().getString("[0].name").equalsIgnoreCase("test delete"): "Name Not Match";
        assert response.jsonPath().getString("[0].data.year").equals("2022"): "Year Not Match";
    }
}