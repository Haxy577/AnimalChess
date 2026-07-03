import AnimalPieces.AnimalPiece;
import Board.BoardCell;
import Board.BoardTile;
import Board.GameBoard;
import Resources.Tiles;
import Resources.Direction;
import Resources.TestBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GetDirectionalPathTest {
    private static List<TestBuilder<Direction, Exception>> provideIllegalArguments() {
        return List.of(
                new TestBuilder<>(Direction.UP, new IllegalArgumentException()),
                new TestBuilder<>(Direction.DOWN, new IllegalArgumentException()),
                new TestBuilder<>(Direction.LEFT, new IllegalArgumentException()),
                new TestBuilder<>(Direction.RIGHT, new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIllegalArguments")
    public void sourceIsNull(TestBuilder<Direction, Exception> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E");
        AnimalPiece piece = gameBoard.getCell(2,2).getPiece();

        assertThrows(test.getExpected().getClass(), () -> piece.getDirectionalPath(null, gameBoard.getBoard(), test.getInput()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIllegalArguments")
    public void gameBoardIsNull(TestBuilder<Direction, Exception> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E");
        BoardCell cell = new BoardCell(new BoardTile(Tiles.LAND), 6,6);
        AnimalPiece piece = gameBoard.getCell(2,2).getPiece();

        assertThrows(test.getExpected().getClass(), () -> piece.getDirectionalPath(cell, gameBoard.getBoard(), test.getInput()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIllegalArguments")
    public void sourceOutsideBoard(TestBuilder<Direction, Exception> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E");
        BoardCell cell = new BoardCell(new BoardTile(Tiles.LAND), 6,6);
        AnimalPiece piece = gameBoard.getCell(2,2).getPiece();

        assertThrows(test.getExpected().getClass(), () -> piece.getDirectionalPath(cell, gameBoard.getBoard(), test.getInput()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIllegalArguments")
    public void sourceDoesNotExistsInBoard(TestBuilder<Direction, Exception> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E");
        BoardCell cell = new BoardCell(new BoardTile(Tiles.LAND), 2,2);
        AnimalPiece piece = gameBoard.getCell(2,2).getPiece();

        assertThrows(test.getExpected().getClass(), () -> piece.getDirectionalPath(cell, gameBoard.getBoard(), test.getInput()));
    }

    private static List<TestBuilder<Direction, BoardCell[]>> provideForGettingAllDirectionalPaths() {
        BoardTile land = new BoardTile(Tiles.LAND);
        return List.of(
                new TestBuilder<>(Direction.UP, new BoardCell[]{new BoardCell(land, 1, 2), new BoardCell(land, 0, 2)}),
                new TestBuilder<>(Direction.DOWN, new BoardCell[]{new BoardCell(land, 3, 2), new BoardCell(land, 4, 2)}),
                new TestBuilder<>(Direction.LEFT, new BoardCell[]{new BoardCell(land, 2, 1), new BoardCell(land, 2, 0)}),
                new TestBuilder<>(Direction.RIGHT, new BoardCell[]{new BoardCell(land, 2, 3), new BoardCell(land, 2, 4)})
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForGettingAllDirectionalPaths")
    public void gettingAllDirectionalPaths(TestBuilder<Direction, BoardCell[]> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E");
        BoardCell cell = gameBoard.getCell(2,2);
        AnimalPiece piece = cell.getPiece();
        BoardCell[] actual = piece.getDirectionalPath(cell, gameBoard.getBoard(), test.getInput());

        assertArrayEquals(test.getExpected(), actual);
    }
}
