package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import steps.BoardStepsDelete;
import steps.BoardStepsGet;
import steps.BoardStepsPost;
import steps.BoardStepsPut;
import utils.StringUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardTests {
    private static final BoardStepsPost BOARD_STEPS_POST = new BoardStepsPost();
    private static final BoardStepsGet BOARD_STEPS_GET = new BoardStepsGet();
    private static final BoardStepsPut BOARD_STEPS_PUT = new BoardStepsPut();
    private static final BoardStepsDelete BOARD_STEPS_DELETE = new BoardStepsDelete();
    private static Response createdBoard;
    private static Response gotBoard;
    private static Response updatedBoard;
    private static Response deletedBoard;

    //POST
    @Test
    @Order(1)
    void testBoardIsCreated() {
        createdBoard = BOARD_STEPS_POST.createABoard("newBoard");
        BOARD_STEPS_POST.checkPositiveStatusCode(createdBoard);
    }

    @Test
    @Order(8)
    void testBoardIsCreatedWithEmptyName() {
        final Response emptyName = BOARD_STEPS_POST.createABoard("");
        BOARD_STEPS_POST.checkBadRequestStatusCode(emptyName);
    }

    @Test
    @Order(9)
    void testBoardIsCreatedWithBlankName() {
        final Response blankName = BOARD_STEPS_POST.createABoard("  ");
        BOARD_STEPS_POST.checkBadRequestStatusCode(blankName);
    }

    @Test
    @Order(10)
    void testBoardIsCreatedWith1LetterName() {
        final Response minimumName = BOARD_STEPS_POST.createABoard("l");
        BOARD_STEPS_POST.checkPositiveStatusCode(minimumName);
        BOARD_STEPS_DELETE.deleteAddedBoardById(minimumName);
    }

    @Test
    @Order(11)
    void testBoardIsCreatedWithMaximumLettersName() {
        final String maxName = StringUtils.generateRandomStringWithLength(16384);
        final Response maximumNameResponse = BOARD_STEPS_POST.createABoard(maxName);
        BOARD_STEPS_POST.checkPositiveStatusCode(maximumNameResponse);
        BOARD_STEPS_DELETE.deleteAddedBoardById(maximumNameResponse);
    }

    @Test
    @Order(12)
    void testBoardIsCreatedWithMoreThanMaxLetters() {
        final String hugeName = StringUtils.generateRandomStringWithLength(16385);
        final Response moreThanMaximumNameResponse = BOARD_STEPS_POST.createABoard(hugeName);
        BOARD_STEPS_POST.checkBadRequestStatusCode(moreThanMaximumNameResponse);
    }

    //GET
    @Test
    @Order(2)
    void testGettingBoardWithId() {
        gotBoard = BOARD_STEPS_GET.getABoardByIDOfAddedBoard(createdBoard);
        BOARD_STEPS_GET.checkPositiveStatusCode(gotBoard);
    }

    @Test
    @Order(3)
    void testRequestIdIsEqualToResponse() {
        BOARD_STEPS_GET.checkResponseBoardIdAndName(createdBoard, gotBoard);
    }

    //PUT
    @Test
    @Order(4)
    void testExistingBoardIsChanged() {
        updatedBoard = BOARD_STEPS_PUT.putACreatedBoard(createdBoard, "neeeeeeeeeeewBoooooooard");
        BOARD_STEPS_PUT.checkPositiveStatusCode(updatedBoard);
    }

    @Test
    @Order(5)
    void testExistingBoardNameIsChanged() {
        BOARD_STEPS_PUT.checkNameOfAddedBoardIsChanged(createdBoard, updatedBoard);
    }

    //DELETE
    @Test
    @Order(6)
    void testExistingBoardIsDeleted() {
        deletedBoard = BOARD_STEPS_DELETE.deleteAddedBoardById(createdBoard);
        BOARD_STEPS_DELETE.checkStatusCode(deletedBoard);
    }

    @Test
    @Order(7)
    void testTheBoardDoesNotExitsAnyMore() {
        BOARD_STEPS_DELETE.checkBoardNotFoundAfterDeletion(createdBoard);
    }
}
