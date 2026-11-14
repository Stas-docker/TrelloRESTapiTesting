package steps;

import core.BaseTestClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import dto.Board;
import utils.ConfigReader;
import utils.ResponseParser;

public class BoardStepsGet extends BaseTestClass {

    private String buildBoardGettingUrl(final String boardId) {
        return BASE_URI + ConfigReader.getValue("getABoardEndPoint").replace("{id}", boardId);
    }

    public Response getABoardByID(final String requestBoardId) {
        final String url = buildBoardGettingUrl(requestBoardId);
        return sendAuthenticatedGetRequest(url, authorization, 200);
    }

    public void checkResponseBoardIdAndName(final Response responseOfGettingBoard, final String expectedName, final String expectedId) {
        final Board gotBoard = ResponseParser.convertResponseToBoardObject(responseOfGettingBoard);
        Assertions.assertThat(gotBoard.getId()).isEqualTo(expectedId);
        Assertions.assertThat(gotBoard.getName()).isEqualTo(expectedName);
    }

    public Response getABoardWithANotExistingId(final String notExistingBoardId) {
        final String url = buildBoardGettingUrl(notExistingBoardId);
        return sendAuthenticatedGetRequest(url, authorization, 400);
    }
}
