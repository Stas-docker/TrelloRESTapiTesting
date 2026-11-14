package steps;

import core.BaseTestClass;
import dto.Board;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.ConfigReader;
import utils.ResponseParser;


public class BoardStepsPost extends BaseTestClass {
    private String buildBoardGettingUrl(final String boardName) {
        return BASE_URI + ConfigReader.getValue("createABoardEndPoint") + boardName;
    }

    public Response createABoard(final String boardName) {
        final String url = buildBoardGettingUrl(boardName);
        return sendAuthenticatedPostRequest(url, authorization, 200);
    }

    public void checkRequestNameEqualsResponseName(final Response responseOfCreatedBoard, final String expectedName) {
        final Board createdBoard = ResponseParser.convertResponseToBoardObject(responseOfCreatedBoard);
        final String actualName = createdBoard.getName();
        Assertions.assertThat(createdBoard.getId()).isNotBlank();
        Assertions.assertThat(actualName).isEqualTo(expectedName);
    }

    public Response createABoardWithInvalidName(final String boardName) {
        final String url = buildBoardGettingUrl(boardName);
        return sendAuthenticatedPostRequest(url, authorization, 400);
    }
}
