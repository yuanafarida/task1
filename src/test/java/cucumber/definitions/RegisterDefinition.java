package cucumber.definitions;

import cucumber.context.EmployeeContext;
import cucumber.endpoint.EmployeeEndpoint;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class RegisterDefinition extends EmployeeEndpoint{  
    public static Response response;
    private final EmployeeContext context;

    public RegisterDefinition(EmployeeContext context) {
        this.context = context;
    }

    @When("Send employee to register with body:")
    public void doRegistration(String body) { 
       response = registerEmployee(body);
       context.setResponse(response);
    }

    @Then("The response status register must be {int}")
    public void verifyResponseStatusRegister(int statusCode) {
        assert context.getResponse().statusCode() == statusCode : "Expected status code 200 but got " + context.getResponse().statusCode();
    }

    @And("The response body register returns id")
    public void verifyResponseBodyReturnsId() {
        assert context.getResponse().getBody() != null : "Response body does not contain id";
    }
    
    @And("The response schema register should be match with schema {string}")
    public void verifyResponseBodyRegisterSchema(String schemaPath) {
        context.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));    
    }

    @When("Send employee to login with body:")
    public void doLogin(String body) { 
       response = loginEmployee(body);
       context.setResponse(response);
    }

    @Then("The response status login must be {int}")
    public void verifyResponseStatusLogin(int statusCode) {
        assert context.getResponse().statusCode() == statusCode : "Expected status code 200 but got " + context.getResponse().statusCode();
    }

    @And("The response body login returns token")
    public void verifyResponseBodyReturnsToken() {
        assert context.getResponse().jsonPath().getString("token")!=null: "Response body does not contain token";
    }
    
    @And("Save the token from the response to local storage")
    public void saveToken() {
       context.set("token", context.getResponse().jsonPath().getString("token"));
    }

    @Given("Make sure token in local storage not empty 1")
    public void prepareGetAllDepartment() { 
       assert context.get("token", String.class) != null : "Token in context null";    
    }

    @When("Using token from local storage to get all departments") 
    public void doGetAllDepartment() { 
       response = getAllDepartment(context.get("token", String.class));
       context.setResponse(response);
    }

    @Then("The response status get all departments must be {int}")
    public void verifyResponseStatusGetAllDepartment(int statusCode) {
        assert context.getResponse().statusCode() == statusCode : "Expected status code 200 but got " + context.getResponse().statusCode();
    }  

    @And("The response schema all departments should be match with schema {string}") 
    public void verifyResponseBodyGetAllDepartmentSchema(String schemaPath) {
        context.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));    
    }           

}
