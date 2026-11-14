package utils;

import dto.Board;
import io.restassured.response.Response;

public class ResponseParser {
    public static <T> T convertResponseToJavaObject(Response response, Class<T> responseClass) {
        return response.as(responseClass);
    }

    public static Board convertResponseToBoardObject(final Response response) {
        return convertResponseToJavaObject(response, Board.class);
    }
}
