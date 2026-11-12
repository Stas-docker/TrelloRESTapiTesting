package steps;

import core.BaseClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import dto.Board;
import utils.ConfigReader;

public class BoardStepsGet extends BaseClass {

    public Response getABoardByIDOfAddedBoard(final Response responseCreatedBoard) {
        final String requestBoardId = convertResponseToJavaObj(responseCreatedBoard).getId();
        final String url = BASE_URI + ConfigReader.getValue("getABoardEndPoint").replace("{id}", requestBoardId);
        return getRequest(url, authorization);
    }

    public void checkPositiveStatusCode(final Response getBoardResponse) {
        Assertions.assertThat(getBoardResponse.getStatusCode()).isEqualTo(200);
    }

    public void checkResponseBoardIdAndName(final Response responseCreatedBoard, final Response getBoardResponse) {
        final Board createdBoard = convertResponseToJavaObj(responseCreatedBoard);
        final Board gotBoard = convertResponseToJavaObj(getBoardResponse);
        Assertions.assertThat(gotBoard.getId()).isEqualTo(createdBoard.getId());
        Assertions.assertThat(gotBoard.getName()).isEqualTo(createdBoard.getName());
    }
}
