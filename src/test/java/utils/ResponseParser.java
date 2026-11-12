package utils;

import io.restassured.response.Response;

public class ResponseParser {
    public static <T> T convertResponseToJavaObject(Response response, Class<T> responseClass) {
        return response.as(responseClass);
    }
}
