package cucumber.definitions;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.request.AddObjectRequest;
import com.task.request.ObjectData;
import com.task.response.AddObjectResponse;

import cucumber.context.EmployeeContext;
import cucumber.endpoint.EmployeeEndpoint;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UpdateDefinition extends EmployeeEndpoint{  
    public static Response response;
    private final EmployeeContext context;

    public UpdateDefinition(EmployeeContext context) {
        this.context = context;
    }

    @Given("Make sure token in local storage not empty 2")
    public void prepareAddObject() { 
       assert context.get("token", String.class) != null : "Token in context null";    
    }

    @When("Send a request to add object")
    public void doAddObject() { 
        AddObjectRequest addObjectRequest = new AddObjectRequest();
        addObjectRequest.setName("Asus Notebook Yuana");
        ObjectData objectData = new ObjectData();
        objectData.setYear(2025);
        objectData.setPrice(1000);
        objectData.setCpuModel("Intel Core I3");
        objectData.setHardDiskSize("500 GB");
        objectData.setCapacity("2 cpu");
        objectData.setScreenSize("15 Inchi");
        objectData.setColor("Red");
        addObjectRequest.setData(objectData);
        Gson gson = new Gson();
        String body = gson.toJson(addObjectRequest);
        response = addObject(context.get("token", String.class), body);
        context.setResponse(response);
    }

    @Then("The response status add object must be {int}")
    public void verifyResponseStatusAddObj(int statusCode) {
        assert context.getResponse().statusCode() == statusCode : "Expected status code 200 but got " + context.getResponse().statusCode();
    }
    
    @And("The response schema add object should be match with schema {string}")
    public void verifyResponseBodyAddObjSchema(String schemaPath) {
        context.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));    
        List<AddObjectResponse> addObjectResponseList = new Gson().fromJson(context.getResponse().asString(),  new TypeToken<List<AddObjectResponse>>(){}.getType());
        context.set("addObj", addObjectResponseList);
    }           

    @And("Year in the response add object must be {int}")
    public void verifyResponseBodyAddObjectReturnsYear(int year) {
        @SuppressWarnings("unchecked")
        List<AddObjectResponse> addObjectResponseList = context.get("addObj", List.class);
        assert addObjectResponseList.get(0).getData().getYear()==year : "Year in the response add object not match with expected "+year+", but got " + addObjectResponseList.get(0).getData().getYear();
    }

    @And("Price in the response add object must be {int}")
    public void verifyResponseBodyAddObjectReturnPrice(int price) {
        @SuppressWarnings("unchecked")
        List<AddObjectResponse> addObjectResponseList = context.get("addObj", List.class);
        assert addObjectResponseList.get(0).getData().getPrice()==price : "Price in the response add object not match with expected "+price+", but got " + addObjectResponseList.get(0).getData().getPrice();
    }

    @And("CPU Model in the response add object must be {string}")
    public void verifyResponseBodyAddObjectReturnsCpuModel(String cpuModel) {
        @SuppressWarnings("unchecked")
        List<AddObjectResponse> addObjectResponseList = context.get("addObj", List.class);
        assert addObjectResponseList.get(0).getData().getCpuModel().equals(cpuModel) : "Cpu Model in the response add object not match with expected "+cpuModel+", but got " + addObjectResponseList.get(0).getData().getCpuModel();
    }

    @And("Hard Disk Size in the response add object must be {string}")
    public void verifyResponseBodyAddObjectReturnsHardDiskSize(String hardDiskSize) {
        @SuppressWarnings("unchecked")
        List<AddObjectResponse> addObjectResponseList = context.get("addObj", List.class);
        assert addObjectResponseList.get(0).getData().getHardDiskSize().equals(hardDiskSize): "Hard Disk Size in the response add object not match with expected "+hardDiskSize+", but got " + addObjectResponseList.get(0).getData().getHardDiskSize();
    }

    @And("Capacity in the response add object must be {string}")
    public void verifyResponseBodyAddObjectReturnsCapacity(String capacity) {
        @SuppressWarnings("unchecked")
        List<AddObjectResponse> addObjectResponseList = context.get("addObj", List.class);
        assert addObjectResponseList.get(0).getData().getCapacity().equals(capacity): "Capacity in the response add object not match with expected "+capacity+", but got " + addObjectResponseList.get(0).getData().getCapacity();
    }

    @And("Screen Size in the response add object must be {string}")
    public void verifyResponseBodyAddObjectReturnsScreenSize(String screenSize) {
        @SuppressWarnings("unchecked")
        List<AddObjectResponse> addObjectResponseList = context.get("addObj", List.class);
        assert addObjectResponseList.get(0).getData().getScreenSize().equals(screenSize): "Screen Size in the response add object not match with expected "+ screenSize +", but got " + addObjectResponseList.get(0).getData().getScreenSize();
    }

    @And("Color in the response add object must be {string}")
    public void verifyResponseBodyAddObjectReturnsColor(String color) {
        @SuppressWarnings("unchecked")
        List<AddObjectResponse> addObjectResponseList = context.get("addObj", List.class);
        assert addObjectResponseList.get(0).getData().getColor().equals(color): "Color in the response add object not match with expected "+color+", but got " + addObjectResponseList.get(0).getData().getColor();
    }
}
