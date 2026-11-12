package steps;

import core.BaseClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.ConfigReader;

public class BoardStepsPost extends BaseClass {

    public Response createABoard(final String boardName) {
        final String url = BASE_URI + ConfigReader.getValue("createABoardEndPoint") + boardName;
        return postRequest(url, authorization);
    }

    public void checkPositiveStatusCode(final Response response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
    }

    public void checkBadRequestStatusCode(final Response response) {
        Assertions.assertThat(response.getStatusCode()).isEqualTo(400);
    }
}
