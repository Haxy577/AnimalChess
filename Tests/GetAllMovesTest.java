import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit testing for the method of getAllMoves
 *
 * @see AnimalPiece#getAllMoves(BoardCell, BoardCell[][])
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public class GetAllMovesTest {
    private static final Player p1 = new Player("P1", Color.RED);
    private static final Player p2 = new Player("P2", Color.BLUE);

    private static List<TestBuilder<BoardCell, Exception>> provideIllegalArguments() {
        BoardTile tile = new BoardTile(Tiles.LAND);
        return List.of(
                new TestBuilder<>(new BoardCell(new Mouse(p1), tile, 2, 2), new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIllegalArguments")
    public void sourceIsNull(TestBuilder<BoardCell, Exception> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E", p1, p2);
        AnimalPiece piece = gameBoard.getCell(2,2).getPiece();

        assertThrows(test.getExpected().getClass(), () -> piece.getAllMoves(null, gameBoard.getBoard()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIllegalArguments")
    public void gameBoardIsNull(TestBuilder<BoardCell, Exception> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E", p1, p2);
        BoardCell cell = new BoardCell(new BoardTile(Tiles.LAND), 6,6);
        AnimalPiece piece = gameBoard.getCell(2,2).getPiece();

        assertThrows(test.getExpected().getClass(), () -> piece.getAllMoves(cell, gameBoard.getBoard()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIllegalArguments")
    public void sourceOutsideBoard(TestBuilder<BoardCell, Exception> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E", p1, p2);
        BoardCell cell = new BoardCell(new BoardTile(Tiles.LAND), 6,6);
        AnimalPiece piece = gameBoard.getCell(2,2).getPiece();

        assertThrows(test.getExpected().getClass(), () -> piece.getAllMoves(cell, gameBoard.getBoard()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideIllegalArguments")
    public void sourceDoesNotExistsInBoard(TestBuilder<BoardCell, Exception> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|12E", p1, p2);
        BoardCell cell = new BoardCell(new BoardTile(Tiles.LAND), 2,2);
        AnimalPiece piece = gameBoard.getCell(2,2).getPiece();

        assertThrows(test.getExpected().getClass(), () -> piece.getAllMoves(cell, gameBoard.getBoard()));
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByEmptyLandCell() {
        return TestBuilder.provideGetAllMovesTests(4, List.of(), 0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByEmptyLandCell")
    public void surroundedByEmptyLandCell(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByEmptyRiverCell() {
        return TestBuilder.provideGetAllMovesTests(0, List.of(new Mouse(p1), new Tiger(p1), new Lion(p1)), 4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByEmptyRiverCell")
    public void surroundedByEmptyRiverCell(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa5LR3LRLR3LR7L|", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByEnemyMouseOnLand() {
        return TestBuilder.provideGetAllMovesTests(4, List.of(new Elephant(p1)), 0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByEnemyMouseOnLand")
    public void surroundedByEnemyMouseOnLand(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|7m3m1m3m", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByEnemyMouseOnRiver() {
        return TestBuilder.provideGetAllMovesTests(0, List.of(), 4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByEnemyMouseOnRiver")
    public void surroundedByEnemyMouseOnRiver(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa5LR3LRLR3LR7L|7m3m1m3m", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByEnemyElephantOnLand() {
        return TestBuilder.provideGetAllMovesTests(0, List.of(new Mouse(p1), new Elephant(p1)), 4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByEnemyElephantOnLand")
    public void surroundedByEnemyElephantOnLand(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa23L|7e3e1e3e", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByEnemyElephantOnOwnTrap() {
        return TestBuilder.provideGetAllMovesTests(4, List.of(), 0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByEnemyElephantOnOwnTrap")
    public void surroundedByEnemyElephantOnOwnTrap(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa5LT3LTLT3LT7L|7e3e1e3e", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByEnemyElephantOnEnemyTrap() {
        return TestBuilder.provideGetAllMovesTests(0, List.of(new Mouse(p1), new Elephant(p1)), 4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByEnemyElephantOnEnemyTrap")
    public void surroundedByEnemyElephantOnEnemyTrap(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa5Lt3LtLt3Lt7L|7e3e1e3e", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByOwnMouseOnEnemyTrap() {
        return TestBuilder.provideGetAllMovesTests(0, List.of(), 4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByOwnMouseOnEnemyTrap")
    public void surroundedByOwnMouseOnEnemyTrap(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa5Lt3LtLt3Lt7L|7M3M1M3M", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByRiverTileWithEnemyMouseAtTheEnd() {
        return TestBuilder.provideGetAllMovesTests(0, List.of(new Mouse(p1)), 4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByRiverTileWithEnemyMouseAtTheEnd")
    public void surroundedByRiverTileWithEnemyElephantAtTheEnd(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "Aa5LR3LRLR3LR7L|2e7e3e7e", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByOwnDen() {
        return TestBuilder.provideGetAllMovesTests(0, List.of(), 4);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByOwnDen")
    public void surroundedByOwnDen(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "a6LA3LALA3LA7L|", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }

    private static List<TestBuilder<BoardCell, Integer>> provideForSurroundedByEnemyDen() {
        return TestBuilder.provideGetAllMovesTests(4, List.of(), 0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSurroundedByEnemyDen")
    public void surroundedByEnemyDen(TestBuilder<BoardCell, Integer> test) {
        GameBoard gameBoard = new GameBoard(5, 5, "A6La3LaLa3La7L|", p1, p2);
        BoardCell source = test.getInput();
        gameBoard.getCell(2,2).setPiece(source.getPiece());

        assertEquals(test.getExpected(), source.getPiece().getAllMoves(source, gameBoard.getBoard()).size());
    }
}
