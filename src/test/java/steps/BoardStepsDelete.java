package steps;

import core.BaseTestClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.ConfigReader;


public class BoardStepsDelete extends BaseTestClass {

    private String buildBoardGettingUrl(final String boardId) {
        return BASE_URI + ConfigReader.getValue("deleteABoardEndPoint").replace("{id}", boardId);
    }

    public Response deleteBoardById(final String boardId) {
        final String url = buildBoardGettingUrl(boardId);
        return sendAuthenticatedDeleteRequest(url, authorization, 200);
    }

    public Response checkBoardNotFoundAfterDeletion(final String boardId) {
        final String getABoardUrl = buildBoardGettingUrl(boardId);
        return sendAuthenticatedGetRequest(getABoardUrl, authorization, 404);
    }
}
