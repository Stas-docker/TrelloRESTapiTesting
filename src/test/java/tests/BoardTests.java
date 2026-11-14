package tests;

import dto.Board;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import steps.BoardStepsDelete;
import steps.BoardStepsGet;
import steps.BoardStepsPost;
import steps.BoardStepsPut;
import utils.ResponseParser;
import utils.StringUtils;

public class BoardTests {
    private static final BoardStepsPost BOARD_STEPS_POST = new BoardStepsPost();
    private static final BoardStepsGet BOARD_STEPS_GET = new BoardStepsGet();
    private static final BoardStepsPut BOARD_STEPS_PUT = new BoardStepsPut();
    private static final BoardStepsDelete BOARD_STEPS_DELETE = new BoardStepsDelete();
    private static final int MAX_BOARD_LENGTH = 16384;
    private String boardIdToCleanUp;

    private String createBoardAndGetId(final String boardName) {
        final Response response = BOARD_STEPS_POST.createABoard(boardName);
        final Board board = ResponseParser.convertResponseToBoardObject(response);
        return board.getId();
    }

    @AfterEach
    void cleanUpBoard() {
        if (boardIdToCleanUp != null) {
            BOARD_STEPS_DELETE.deleteBoardById(boardIdToCleanUp);
            boardIdToCleanUp = null;
        }
    }

    //POST
    @Test
    void testBoardIsCreated() {
        final Response createResponse = BOARD_STEPS_POST.createABoard("Vasea");
        boardIdToCleanUp = ResponseParser.convertResponseToBoardObject(createResponse).getId();
        BOARD_STEPS_POST.checkRequestNameEqualsResponseName(createResponse, "Vasea");
    }

    @Test
    void testBoardIsNotCreatedWithInvalidName() {
        final String emptyName = "";
        final String blankName = "  ";
        BOARD_STEPS_POST.createABoardWithInvalidName(emptyName);
        BOARD_STEPS_POST.createABoardWithInvalidName(blankName);
    }

    @Test
    void testBoardIsCreatedWith1LetterName() {
        final String minimumName = "l";
        boardIdToCleanUp = createBoardAndGetId(minimumName);
    }

    @Test
    void testBoardIsCreatedWithMaximumLettersName() {
        final String maxName = StringUtils.generateRandomStringWithLength(MAX_BOARD_LENGTH);
        boardIdToCleanUp = createBoardAndGetId(maxName);
    }

    @Test
    void testBoardIsCreatedWithMoreThanMaxLetters() {
        final String hugeName = StringUtils.generateRandomStringWithLength(16385);
        BOARD_STEPS_POST.createABoardWithInvalidName(hugeName);
    }

    //GET
    @Test
    void testGettingBoardWithId() {
        boardIdToCleanUp = createBoardAndGetId("Sirioja");
        final Response response = BOARD_STEPS_GET.getABoardByID(boardIdToCleanUp);
        BOARD_STEPS_GET.checkResponseBoardIdAndName(response, "Sirioja", boardIdToCleanUp);
    }

    @Test
    void testGettingBoardWithNonExistingId() {
        BOARD_STEPS_GET.getABoardWithANotExistingId("0000000001");

    }

    //PUT
    @Test
    void testBoardisChanged() {
        boardIdToCleanUp = createBoardAndGetId("Sirioja");
        BOARD_STEPS_PUT.putABoard(boardIdToCleanUp, "Vasea");
        BOARD_STEPS_PUT.checkNameOfBoardIsChanged("Vasea", boardIdToCleanUp);
    }

    //DELETE
    @Test
    void testExistingBoardIsDeleted() {
        final String boardId = createBoardAndGetId("Sirioja");
        BOARD_STEPS_DELETE.deleteBoardById(boardId);
        BOARD_STEPS_DELETE.checkBoardNotFoundAfterDeletion(boardId);
    }
}
