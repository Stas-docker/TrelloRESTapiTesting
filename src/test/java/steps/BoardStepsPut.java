package steps;

import core.BaseTestClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import dto.Board;
import utils.ConfigReader;
import utils.ResponseParser;

public class BoardStepsPut extends BaseTestClass {
    private final BoardStepsGet boardStepsGet = new BoardStepsGet();

    private String buildBoardPuttingUrl(final String boardId, final String newBoardName) {
        return BASE_URI + ConfigReader.getValue("putABoardEndPoint")
                .replace("{id}", boardId)
                .replace("{name}", newBoardName);
    }

    public Response putABoard(final String boardId, final String newBoardName) {
        String url = buildBoardPuttingUrl(boardId, newBoardName);
        return sendAuthenticatedPutRequest(url, authorization, 200);
    }

    public void checkNameOfBoardIsChanged(final String expectedName, final String idOfTheBoard) {
        final Response actualGetResponse = boardStepsGet.getABoardByID(idOfTheBoard);
        final Board gotBoard = ResponseParser.convertResponseToBoardObject(actualGetResponse);
        final String actualName = gotBoard.getName();
        Assertions.assertThat(actualName).isEqualTo(expectedName);
    }
}
