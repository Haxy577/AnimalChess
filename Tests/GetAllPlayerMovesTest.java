import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit testing for the method of getAllPlayerMoves
 *
 * @see GameBoard#getAllPlayerMoves(int)
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public class GetAllPlayerMovesTest {
    private static List<TestBuilder<Integer, Exception>> provideForIndexIsNotOneOrTwo() {
        return List.of(
                new TestBuilder<>(Integer.MAX_VALUE,new IllegalArgumentException()),
                new TestBuilder<>(Integer.MIN_VALUE,new IllegalArgumentException()),
                new TestBuilder<>(-1,new IllegalArgumentException()),
                new TestBuilder<>(0,new IllegalArgumentException()),
                new TestBuilder<>(3,new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForIndexIsNotOneOrTwo")
    public void indexIsNotOneOrTwo(TestBuilder<Integer, Exception> test) {
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard().getAllPlayerMoves(test.getInput()));
        assertEquals("The player index can only be either 1 or 2", e.getMessage());
    }

    private static List<TestBuilder<String, Map<String, Integer>>> provideForSinglePieceWithFourMoves() {
        return List.of(
                new TestBuilder<>("ALa6L|4M", Map.of("Key", 1, "Value", 4)),
                new TestBuilder<>("ALa6L|4E", Map.of("Key", 1, "Value", 4)),
                new TestBuilder<>("ALa6L|4N", Map.of("Key", 1, "Value", 4)),
                new TestBuilder<>("ALa6L|4N", Map.of("Key", 1, "Value", 4))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForSinglePieceWithFourMoves")
    public void singlePieceWithFourMoves(TestBuilder<String, Map<String, Integer>> test) {
        GameBoard gameboard = new GameBoard(3,3, test.getInput());
        Map<BoardCell, List<BoardCell>> moves = gameboard.getAllPlayerMoves(1);

        assertEquals(test.getExpected().get("Key"), moves.size());

        List<BoardCell> pieceMoves = moves.get(gameboard.getCell(1,1));

        assertEquals(test.getExpected().get("Value"), pieceMoves.size());
    }

    private static List<TestBuilder<String, Map<String, Integer>>> provideForMultiplePiecesWithNoMoves() {
        return List.of(
                new TestBuilder<>("ALa6L|MCWDPGNED", Map.of("Key", 9, "Value", 0))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMultiplePiecesWithNoMoves")
    public void multiplePiecesWithNoMoves(TestBuilder<String, Map<String, Integer>> test) {
        GameBoard gameboard = new GameBoard(3,3, test.getInput());
        Map<BoardCell, List<BoardCell>> moves = gameboard.getAllPlayerMoves(1);

        assertEquals(test.getExpected().get("Key"), moves.size());

        for (BoardCell key : moves.keySet()) {
            List<BoardCell> pieceMoves = moves.get(key);
            assertEquals(test.getExpected().get("Value"), pieceMoves.size());
        }
    }

    private static List<TestBuilder<String, Map<String, Integer>>> provideForNoPiecesRemaining() {
        return List.of(
                new TestBuilder<>("ALa6L|", Map.of("Key", 0, "Value", 0))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForNoPiecesRemaining")
    public void noPiecesRemaining(TestBuilder<String, Map<String, Integer>> test) {
        GameBoard gameboard = new GameBoard(3,3, test.getInput());
        Map<BoardCell, List<BoardCell>> moves = gameboard.getAllPlayerMoves(1);

        assertEquals(test.getExpected().get("Key"), moves.size());

        for (BoardCell key : moves.keySet()) {
            List<BoardCell> pieceMoves = moves.get(key);
            assertEquals(test.getExpected().get("Value"), pieceMoves.size());
        }
    }
}