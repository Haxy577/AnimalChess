import AnimalPieces.*;
import Board.GameBoard;
import Resources.TestBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InitializeBoardTest {
    private static List<TestBuilder<String, Exception>> provideForParameterIsNull() {
        return List.of(
                new TestBuilder<>(null, new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForParameterIsNull")
    public void parameterIsNull(TestBuilder<String, Exception> test) {
        GameBoard gameBoard = new GameBoard();

        Exception e = assertThrows(test.getExpected().getClass(), () -> gameBoard.initialize(test.getInput()));
        assertEquals("Invalid pattern. The pattern cannot be null", e.getMessage());
    }

    private static List<TestBuilder<String, Exception>> provideForPatternIsEmpty() {
        return List.of(
                new TestBuilder<>("", new IllegalArgumentException()),
                new TestBuilder<>("        ", new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForPatternIsEmpty")
    public void patternIsEmpty(TestBuilder<String, Exception> test) {
        GameBoard gameBoard = new GameBoard();

        Exception e = assertThrows(test.getExpected().getClass(), () -> gameBoard.initialize(test.getInput()));
        assertEquals("Invalid pattern. There must be a divider character '|' separating the two different patterns", e.getMessage());
    }

    private static List<TestBuilder<String, Exception>> provideForOnlyContainsSeparatorChar() {
        return List.of(
                new TestBuilder<>("|", new IllegalArgumentException()),
                new TestBuilder<>("     |   ", new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForOnlyContainsSeparatorChar")
    public void onlyContainsSeparatorChar(TestBuilder<String, Exception> test) {
        GameBoard gameBoard = new GameBoard();

        Exception e = assertThrows(test.getExpected().getClass(), () -> gameBoard.initialize(test.getInput()));
        assertEquals("Invalid board pattern structure. The pattern must only consists of digits and a character [LRTtAa]", e.getMessage());
    }

    private static List<TestBuilder<String, String>> provideForBoardPatternFirst() {
        return List.of(
                new TestBuilder<>("Aa2L|Mmmm", "Aa2L|Mmmm"),
                new TestBuilder<>("2a2A|ppe", "2a2A|ppe"),
                new TestBuilder<>("RaAt|wce", "RaAt|wce"),
                new TestBuilder<>("LRAa|wWcC", "LRAa|wWcC")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForBoardPatternFirst")
    public void boardPatternFirst(TestBuilder<String, String> test) {
        assertEquals(new GameBoard(2,2, test.getInput()).toPattern(), test.getExpected());
    }

    private static List<TestBuilder<String, String>> provideForPiecePatternFirst() {
        return List.of(
                new TestBuilder<>("Mmmm|Aa2L", "Aa2L|Mmmm"),
                new TestBuilder<>("ppe|2a2A", "2a2A|ppe"),
                new TestBuilder<>("|RaAt", "RaAt|wce"),
                new TestBuilder<>("wWcC|LRAa", "LRAa|wWcC")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForBoardPatternFirst")
    public void piecePatternFirst(TestBuilder<String, String> test) {
        assertEquals(new GameBoard(2,2, test.getInput()).toPattern(), test.getExpected());
    }

    private static List<TestBuilder<String, Exception>> provideForOnlyPiecePattern() {
        return List.of(
                new TestBuilder<>("|M", new IllegalArgumentException()),
                new TestBuilder<>("|BMGE", new IllegalArgumentException()),
                new TestBuilder<>("|wcee", new IllegalArgumentException()),
                new TestBuilder<>("|MmMm", new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForOnlyPiecePattern")
    public void onlyPiecePattern(TestBuilder<String, Exception> test) {
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard(2,2, test.getInput()));
        assertEquals("Invalid board pattern structure. The pattern must only consists of digits and a character [LRTtAa]", e.getMessage());
    }

    private static List<TestBuilder<String, String>> provideForOnlyBoardPattern() {
        return List.of(
                new TestBuilder<>("Aa2L|", "Aa2L|"),
                new TestBuilder<>("2a2A|", "2a2A|"),
                new TestBuilder<>("|RaAt", "RaAt|"),
                new TestBuilder<>("|LRAa", "LRAa|")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForOnlyBoardPattern")
    public void onlyBoardPattern(TestBuilder<String, String> test) {
        assertEquals(new GameBoard(2,2, test.getInput()).toPattern(), test.getExpected());
    }

    private static List<TestBuilder<String, Exception>> provideForInvalidBoardCharacters() {
        return List.of(
                new TestBuilder<>("Aa2l|", new IllegalArgumentException()),
                new TestBuilder<>("eAaq|", new IllegalArgumentException()),
                new TestBuilder<>("|RaAq", new IllegalArgumentException()),
                new TestBuilder<>("|rRAa", new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForInvalidBoardCharacters")
    public void invalidBoardCharacters(TestBuilder<String, Exception> test) {
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard(2,2, test.getInput()));
        assertEquals("Invalid board pattern structure. The pattern must only consists of digits and a character [LRTtAa]", e.getMessage());
    }

    private static List<TestBuilder<String, Exception>> provideForInvalidPieceCharacters() {
        return List.of(
                new TestBuilder<>("Aa2L|llaa", new IllegalArgumentException()),
                new TestBuilder<>("2a2A|rqpg", new IllegalArgumentException()),
                new TestBuilder<>("twce|RaAt", new IllegalArgumentException()),
                new TestBuilder<>("CeQd|LRAa", new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForInvalidPieceCharacters")
    public void invalidPieceCharacters(TestBuilder<String, Exception> test) {
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard(2,2, test.getInput()));
        assertEquals("Invalid piece pattern structure. The pattern must only consists of digits and a character [MmCcWwDdPpNnGgEe]", e.getMessage());
    }

    private static List<TestBuilder<String, Exception>> provideForBoardPatternSizeNotEqualToBoard() {
        return List.of(
                new TestBuilder<>("AaL|", new IllegalArgumentException()),
                new TestBuilder<>("aA|", new IllegalArgumentException()),
                new TestBuilder<>("|aAt", new IllegalArgumentException()),
                new TestBuilder<>("|LAa", new IllegalArgumentException()),
                new TestBuilder<>("25AaL|", new IllegalArgumentException()),
                new TestBuilder<>("a34A|", new IllegalArgumentException()),
                new TestBuilder<>("|aAaTtTt", new IllegalArgumentException()),
                new TestBuilder<>("|LA77a", new IllegalArgumentException())
                );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForBoardPatternSizeNotEqualToBoard")
    public void boardPatternSizeNotEqualToBoard(TestBuilder<String, Exception> test) {
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard(2,2, test.getInput()));
        assertTrue(e.getMessage().contains("Invalid board pattern. The pattern size does not equal the board size."));
    }

    private static List<TestBuilder<String, Exception>> provideForPiecePatternSizeNotEqualToBoard() {
        return List.of(
                new TestBuilder<>("AaLL|25e", new IllegalArgumentException()),
                new TestBuilder<>("AaLL|77Cw", new IllegalArgumentException()),
                new TestBuilder<>("ewwwww|AaLL", new IllegalArgumentException()),
                new TestBuilder<>("25peG|AaLL", new IllegalArgumentException()),
                new TestBuilder<>("AaLL|4m", new IllegalArgumentException()),
                new TestBuilder<>("AaLL|mE2e", new IllegalArgumentException()),
                new TestBuilder<>("pge1E|AaLL", new IllegalArgumentException()),
                new TestBuilder<>("C2D2e|AaLL", new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForPiecePatternSizeNotEqualToBoard")
    public void piecePatternSizeNotEqualToBoard(TestBuilder<String, Exception> test) {
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard(2,2, test.getInput()));
        assertTrue(e.getMessage().contains("Invalid piece pattern. The pattern size exceeds the board size."));
    }

    private static List<TestBuilder<String, Exception>> provideForOnlyOnePlayerDen() {
        return List.of(
                new TestBuilder<>("4A|", new IllegalArgumentException()),
                new TestBuilder<>("2aLR|", new IllegalArgumentException()),
                new TestBuilder<>("|2tAT", new IllegalArgumentException()),
                new TestBuilder<>("|4a", new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForOnlyOnePlayerDen")
    public void onlyOnePlayerDen(TestBuilder<String, Exception> test) {
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard(2,2, test.getInput()));
        assertTrue(e.getMessage().contains("Invalid board pattern. There must be at least 1 animal den tile for player"));
    }
}
