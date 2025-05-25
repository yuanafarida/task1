package restassured;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestEmployeeImpl {
    String token;
    String newIdAdded;

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

    @Test
    public void testListAllObject(){
       
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/objects");
        // Print the response
        System.out.println("Response List Object: " + response.asPrettyString());
        assert response.jsonPath().getString("[0]")!=null: "List All Object Empty";
    }

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

    @Test(dependsOnMethods = {"testAddObject"})
    public void testSingleObject(){       
        System.out.println("New Id Added: " + newIdAdded);
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/"+newIdAdded);

        // Print the response
        System.out.println("Response Single Object: " + response.asPrettyString());
        assert response.jsonPath().getString("name").equalsIgnoreCase("Asus Notebook Yuana"): "Name Different from Expected";
    }

    @Test
    public void testAddObject(){
        String requestBody = "{\n" + 
                "  \"name\": \"Asus Notebook Yuana\",\n" +
                "  \"data\": {\n" + 
                "    \"year\": \"2025\",\n" + 
                "    \"price\": 1000,\n" + 
                "    \"cpu_model\": \"Intel I3\",\n" + 
                "    \"hard_disk_size\": \"500 GB\",\n" + 
                "    \"capacity\": \"2 cpu\",\n" + 
                "    \"screen_size\": \"15 Inchi\",\n" + 
                "    \"color\": \"red\"\n" +
                "  }"+
                "}";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .log().all()
                .when()
                .post("/webhook/api/objects");

        // Print the response
        System.out.println("Response Add Object: " + response.asPrettyString());
        newIdAdded = response.jsonPath().getString("[0].id");
        assert response.jsonPath().getString("[0].id") != null: "Add Object Failed";
    }

    @Test(dependsOnMethods = {"testAddObject"})
    public void testUpdateObject(){
        System.out.println("New Id Added2: " + newIdAdded);
        String requestBody = "{\n" + 
                "  \"name\": \"HP Notebook Yuana\",\n" +
                "  \"data\": {\n" +                 
                "    \"year\": \"2025\",\n" + 
                "    \"price\": 4000,\n" + 
                "    \"cpu_model\": \"Intel I7\",\n" + 
                "    \"hard_disk_size\": \"500 GB\",\n" + 
                "    \"capacity\": \"2 cpu\",\n" + 
                "    \"screen_size\": \"15 Inchi\",\n" + 
                "    \"color\": \"hitam\"\n" +
                "  }"+
                "}";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .log().all()
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/"+newIdAdded);

        // Print the response
        System.out.println("Response Update Object: " + response.asPrettyString());
    }

    @Test(dependsOnMethods = {"testUpdateObject"})
    public void testPartiallyUpdateObject(){
        System.out.println("New Id Added3: " + newIdAdded);
        String requestBody = "{\n" + 
                "  \"name\": \"ACER Notebook Yuana\",\n" +
                "   \"year\": \"2024\"\n" +
                "}"; 

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .log().all()
                .when()
                .patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/"+newIdAdded);

        // Print the response
        System.out.println("Response Partially Update Object: " + response.asPrettyString());
    }

    @Test(dependsOnMethods = {"testPartiallyUpdateObject"})
    public void testDeleteObject(){
        System.out.println("New Id Added4: " + newIdAdded);
    
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/{id}", newIdAdded);

        // Print the response
        System.out.println("Response Delete Object: " + response.asPrettyString());
    }

    @Test
    @AfterClass
    public void testGetAllDepartment(){    
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/department");

        // Print the response
        System.out.println("Response All Dept: " + response.asPrettyString());
    }
}