package utils;

import io.restassured.http.Header;

public class OAuthorization {
    private static final Header AUTH_HEADER = new Header("Authorization",
            String.format("OAuth oauth_consumer_key=\"%s\", oauth_token=\"%s\"",
                    ConfigReader.getValue("trello.key"),
                    ConfigReader.getValue("trello.token")));

    public static Header getAuthorizationHeader() {
        return AUTH_HEADER;
    }
}
