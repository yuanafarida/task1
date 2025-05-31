package cucumber.context;

import java.util.HashMap;
import java.util.Map;   

import io.restassured.response.Response;   

public class EmployeeContext {
    private Response response;
    private static final Map<String, Object> contextData = new HashMap<>();
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    public void set(String key, Object value) {
        contextData.put(key, value);
    }
    public <T> T get(String key, Class<T> type) {
        // Implementation to retrieve the value by key and convert it to the specified type
       return type.cast(contextData.get(key));
    }
}
