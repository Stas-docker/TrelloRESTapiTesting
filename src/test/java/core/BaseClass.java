package core;

import dto.Board;
import utils.OAuthorization;
import io.restassured.http.Header;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.ResponseParser;

import static io.restassured.RestAssured.given;

public class BaseClass {
    protected static final String BASE_URI = ConfigReader.getValue("baseUri");
    protected final Header authorization = OAuthorization.getAuthorizationHeader();

    protected Board convertResponseToJavaObj(final Response response) {
        return ResponseParser.convertResponseToJavaObject(response, Board.class);
    }

    protected static Response getRequest(final String url, final Header header) {
        return
                given()
                        .header(header)
                        .get(url)
                        .then()
                        .extract()
                        .response();
    }

    protected static Response postRequest(final String url, final Header header) {
        return
                given()
                        .header(header)
                        .when()
                        .post(url)
                        .then()
                        .extract()
                        .response();
    }

    protected static Response putRequest(final String url, final Header header) {
        return
                given()
                        .header(header)
                        .when()
                        .put(url)
                        .then()
                        .extract()
                        .response();
    }

    protected static Response deleteRequest(final String url, final Header header) {
        return
                given()
                        .header(header)
                        .when()
                        .delete(url)
                        .then()
                        .extract()
                        .response();
    }
}
