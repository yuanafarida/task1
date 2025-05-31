package cucumber.endpoint;

import cucumber.helper.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EmployeeEndpoint {
    public EmployeeEndpoint() {
        String baseUrl = ConfigManager.getBaseUrl();
        RestAssured.baseURI = baseUrl;
    }

    public Response registerEmployee(String bodyRequest){
        Response responseRegisterEmployee = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bodyRequest)
                .log().all()
                .when()
                .post("/webhook/api/register");
        return responseRegisterEmployee;
    }

    public Response loginEmployee(String bodyRequest){
        Response responseLoginEmployee = RestAssured.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(bodyRequest)
                .log().all()
                .when()
                .post("/webhook/api/login");
        return responseLoginEmployee;
    }

    public Response getAllDepartment(String token) {
        Response responseGetAllDept = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/department");
        return responseGetAllDept;
    }

    public Response addObject(String token, String bodyRequest) {
        Response responseAddObj = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(bodyRequest)
                .log().all()
                .when()
                .post("/webhook/api/objects");
        return responseAddObj;
    }
}
