import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CanMoveToTest {
    private static List<TestBuilder<BoardCell, Exception>> provideForIllegalArgumentTests() {
        List<TestBuilder<BoardCell, Exception>> tests = new ArrayList<>();

        for (AnimalPiece piece : TestBuilder.provideAllPieces()) {
            if (piece.getPlayerIndex() == 1) {
                tests.add(new TestBuilder<>(new BoardCell(piece, new BoardTile(Tiles.LAND), 0, 0), new IllegalArgumentException()));
            }
        }

        return tests;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForIllegalArgumentTests")
    public void sourceIsNull(TestBuilder<BoardCell, Exception> test) {
        assertThrows(test.getExpected().getClass(), () -> test.getInput().getPiece().canMoveTo(null, null));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForIllegalArgumentTests")
    public void sourceDoesNotContainPiece(TestBuilder<BoardCell, Exception> test) {
        BoardCell cell = new BoardCell(test.getInput().getTile(), 0 ,0);
        assertThrows(test.getExpected().getClass(), () -> test.getInput().getPiece().canMoveTo(cell, null));
    }

    private static List<TestBuilder<BoardCell, BoardCell>> provideForPathIsEmpty() {
        List<TestBuilder<BoardCell, BoardCell>> tests = new ArrayList<>();
        BoardTile land = new BoardTile(Tiles.LAND);

        for (AnimalPiece piece : TestBuilder.provideAllPieces()) {
            if (piece.getPlayerIndex() == 1) {
                tests.add(new TestBuilder<>(new BoardCell(piece, land, 0, 0), null));
            }
        }

        return tests;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForPathIsEmpty")
    public void pathIsEmpty(TestBuilder<BoardCell, BoardCell> test) {
        BoardCell[] path = {};
        BoardCell source = test.getInput();

        assertEquals(test.getExpected(), source.getPiece().canMoveTo(source, path));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForPathIsEmpty")
    public void pathIsNull(TestBuilder<BoardCell, BoardCell> test) {
        BoardCell source = test.getInput();

        assertEquals(test.getExpected(), source.getPiece().canMoveTo(source, null));
    }

    private static List<TestBuilder<BoardCell, BoardCell>> provideForAdjacentToLandTile() {
        List<TestBuilder<BoardCell, BoardCell>> tests = new ArrayList<>();
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardCell expected = new BoardCell(land, 0, 1);

        for (AnimalPiece piece : TestBuilder.provideAllPieces()) {
            if (piece.getPlayerIndex() == 1)
                tests.add(new TestBuilder<>(new BoardCell(piece, land, 0, 0), expected));
        }

        return tests;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForAdjacentToLandTile")
    public void adjacentToLandTile(TestBuilder<BoardCell, BoardCell> test) {
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile river = new BoardTile(Tiles.RIVER);
        BoardCell[] path = {new BoardCell(land, 0, 1), new BoardCell(river, 0, 2), new BoardCell(land, 0, 3)};
        BoardCell source = test.getInput();

        assertEquals(test.getExpected(), source.getPiece().canMoveTo(source, path));
    }

    private static List<TestBuilder<BoardCell, BoardCell>> provideForAdjacentToRiverTile() {
        List<TestBuilder<BoardCell, BoardCell>> tests = new ArrayList<>();
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile river = new BoardTile(Tiles.RIVER);

        for (AnimalPiece piece : TestBuilder.provideAllPieces()) {
            if (piece.getPlayerIndex() != 1) {
                continue;
            }

            BoardCell expected;

            if (piece instanceof Mouse)
                expected = new BoardCell(river, 0, 1);
            else if (piece instanceof Tiger || piece instanceof Lion)
                expected = new BoardCell(land, 0, 3);
            else
                expected = null;

            tests.add(new TestBuilder<>(new BoardCell(piece, land, 0, 0), expected));
        }

        return tests;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForAdjacentToRiverTile")
    public void adjacentToRiverTile(TestBuilder<BoardCell, BoardCell> test) {
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile river = new BoardTile(Tiles.RIVER);
        BoardCell[] path = {new BoardCell(river, 0, 1), new BoardCell(river, 0, 2), new BoardCell(land, 0, 3)};
        BoardCell source = test.getInput();

        assertEquals(test.getExpected(), source.getPiece().canMoveTo(source, path));
    }

    private static List<TestBuilder<BoardCell, BoardCell>> provideForAdjacentToRiverTileWithAPiece() {
        List<TestBuilder<BoardCell, BoardCell>> tests = new ArrayList<>();
        BoardTile land = new BoardTile(Tiles.LAND);

        for (AnimalPiece piece : TestBuilder.provideAllPieces()) {
            if (piece.getPlayerIndex() == 1) {
                tests.add(new TestBuilder<>(new BoardCell(piece, land, 0, 0), null));
            }
        }

        return tests;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForAdjacentToRiverTileWithAPiece")
    public void adjacentToRiverTileWithAPiece(TestBuilder<BoardCell, BoardCell> test) {
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile river = new BoardTile(Tiles.RIVER);
        BoardCell[] path = {new BoardCell(new Mouse(2), river, 0, 1), new BoardCell(river, 0, 2), new BoardCell(land, 0, 3)};
        BoardCell source = test.getInput();

        assertEquals(test.getExpected(), source.getPiece().canMoveTo(source, path));
    }

    private static List<TestBuilder<BoardCell, BoardCell>> provideForJumpingOutsideTheBoard() {
        List<TestBuilder<BoardCell, BoardCell>> tests = new ArrayList<>();
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile river = new BoardTile(Tiles.RIVER);

        for (AnimalPiece piece : TestBuilder.provideAllPieces()) {
            if (piece.getPlayerIndex() == 1) {
                BoardCell expected = (piece instanceof Mouse) ? new BoardCell(river, 0, 1) : null;

                tests.add(new TestBuilder<>(new BoardCell(piece, land, 0, 0), expected));
            }
        }

        return tests;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForJumpingOutsideTheBoard")
    public void jumpingOutsideTheBoard(TestBuilder<BoardCell, BoardCell> test) {
        BoardTile river = new BoardTile(Tiles.RIVER);
        BoardCell[] path = {new BoardCell(river, 0, 1), new BoardCell(river, 0, 2), new BoardCell(river, 0, 3)};
        BoardCell source = test.getInput();

        assertEquals(test.getExpected(), source.getPiece().canMoveTo(source, path));
    }
}