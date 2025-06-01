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

    public Response partiallyUpdateObject(String token, int idObject, String bodyRequest) {
        Response responsePartiallyUpdateObj = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(bodyRequest)
                .log().all()
                .when()
                .patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/{id}", idObject);

        return responsePartiallyUpdateObj;
    }

    public Response getSingleObject(String token, int idObject) {
        Response responseGetSingleObj = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", idObject)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/{id}");
        return responseGetSingleObj;
    }

    public Response deleteObject(String token, int idObject) {
        Response responseDeleteObj = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/{id}", idObject);
        return responseDeleteObj;
    }
}
