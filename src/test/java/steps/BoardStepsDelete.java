package steps;

import core.BaseClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.ConfigReader;


public class BoardStepsDelete extends BaseClass {
    private final BoardStepsGet boardStepsGet = new BoardStepsGet();

    public Response deleteAddedBoardById(final Response responseOfCreatedBoard) {
        final String requestBoardId = convertResponseToJavaObj(responseOfCreatedBoard).getId();
        final String url = BASE_URI + ConfigReader.getValue("deleteABoardEndPoint").replace("{id}", requestBoardId);
        return deleteRequest(url, authorization);
    }

    public void checkStatusCode(final Response responseOfDeletedBoard) {
        Assertions.assertThat(responseOfDeletedBoard.getStatusCode()).isEqualTo(200);
    }

    public void checkBoardNotFoundAfterDeletion(final Response responseOfCreatedBoard) {
        final Response getBoardAfterDeletion = boardStepsGet.getABoardByIDOfAddedBoard(responseOfCreatedBoard);
        Assertions.assertThat(getBoardAfterDeletion.getStatusCode()).isEqualTo(404);
    }
}
