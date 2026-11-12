package steps;

import core.BaseClass;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import dto.Board;
import utils.ConfigReader;

public class BoardStepsPut extends BaseClass {
    private final BoardStepsGet boardStepsGet = new BoardStepsGet();

    public Response putACreatedBoard(final Response responseOfCreatedBoard, final String newBoardName) {
        final Board createdBoard = convertResponseToJavaObj(responseOfCreatedBoard);
        final String requestBoardId = createdBoard.getId();
        final String url = BASE_URI + ConfigReader.getValue("putABoardEndPoint")
                .replace("{id}", requestBoardId)
                .replace("{name}", newBoardName);
        return putRequest(url, authorization);
    }

    public void checkPositiveStatusCode(final Response responseOfChangedBoard) {
        Assertions.assertThat(responseOfChangedBoard.getStatusCode()).isEqualTo(200);
    }

    public void checkNameOfAddedBoardIsChanged(final Response responseOfCreatedBoard, final Response responseOfChangedBoard) {
        final Response actualGetResponse = boardStepsGet.getABoardByIDOfAddedBoard(responseOfCreatedBoard);
        final String actualName = convertResponseToJavaObj(actualGetResponse).getName();
        final String expectedName = convertResponseToJavaObj(responseOfChangedBoard).getName();
        Assertions.assertThat(actualName).isEqualTo(expectedName);
    }
}
