package cucumber.definitions;

import org.testng.annotations.BeforeSuite;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EmployeeDefinition {
    public static String token;    
    public static String uniqueEmail;    
    public static Response response;

    @BeforeSuite
    public void setup() {
        // Setup code here, e.g., initializing RestAssured, setting base URI, etc.
        RestAssured.baseURI = "https://whitesmokehouse.com";
    }

    @Given("Employee having email that is not registeredd")
    public void setEmailForRegister() {
        // bisa setup dummy data atau mock
        EmployeeDefinition.uniqueEmail = "yuanafarida007@gmail.com";
    }

    @When("Send a http {string} request to {string} with body:")
    public void doRegistration(String method, String url, String body) { 
        // simulasi login berhasil
        response = RestAssured.given()
                .contentType("application/json")
                .body(body)
                .log().all()
                .when()
                .post(url);    
    }

    @Then("The response status must be {int}")
    public void verifyResponseStatus(int statusCode) {
        assert response.statusCode() == statusCode : "Expected status code 200 but got " + response.statusCode();
    }

    @And("The response body returns id")
    public void verifyResponseBodyReturnsId() {
        assert response.jsonPath().getString("id") != null : "Response body does not contain id";
    }
}
