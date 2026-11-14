package core;

import dto.Board;
import utils.OAuthorization;
import io.restassured.http.Header;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.ResponseParser;

import static io.restassured.RestAssured.given;

public class BaseTestClass {
    protected static final String BASE_URI = ConfigReader.getValue("baseUri");
    protected final Header authorization = OAuthorization.getAuthorizationHeader();

    protected static Response sendAuthenticatedGetRequest(final String url, final Header header, final int expectedStatusCode) {
        return
                given()
                        .header(header)
                        .when()
                        .get(url)
                        .then()
                        .statusCode(expectedStatusCode)
                        .extract()
                        .response();
    }

    protected static Response sendAuthenticatedPostRequest(final String url, final Header header, final int expectedStatusCode) {
        return
                given()
                        .header(header)
                        .when()
                        .post(url)
                        .then()
                        .statusCode(expectedStatusCode)
                        .extract()
                        .response();
    }

    protected static Response sendAuthenticatedPutRequest(final String url, final Header header, final int expectedStatusCode) {
        return
                given()
                        .header(header)
                        .when()
                        .put(url)
                        .then()
                        .statusCode(expectedStatusCode)
                        .extract()
                        .response();
    }

    protected static Response sendAuthenticatedDeleteRequest(final String url, final Header header, final int expectedStatusCode) {
        return
                given()
                        .header(header)
                        .when()
                        .delete(url)
                        .then()
                        .statusCode(expectedStatusCode)
                        .extract()
                        .response();
    }
}
