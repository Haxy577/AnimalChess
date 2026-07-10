import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit testing for the method of getAllPlayerPieces
 *
 * @see GameBoard#getAllPlayerPieces(int)
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public class GetAllPlayerPiecesTest {
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
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard().getAllPlayerPieces(test.getInput()));
        assertEquals("The player index can only be either 1 or 2", e.getMessage());
    }

    private static List<TestBuilder<String, Integer>> provideForHasDuplicatePieces() {
        return List.of(
                new TestBuilder<>("Aa2L|MMMM",4),
                new TestBuilder<>("Aa2L|WeWe",2)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForHasDuplicatePieces")
    public void hasDuplicatePieces(TestBuilder<String, Integer> test) {
        assertEquals(test.getExpected(), new GameBoard(2,2,test.getInput()).getAllPlayerPieces(1).size());
    }

    private static List<TestBuilder<Integer, Integer>> provideForNoRemainingPieces() {
        return List.of(
                new TestBuilder<>(1,0),
                new TestBuilder<>(2,0)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForNoRemainingPieces")
    public void noRemainingPieces(TestBuilder<Integer, Integer> test) {
        assertEquals(test.getExpected(), new GameBoard(3,3,"aA7L|").getAllPlayerPieces(test.getInput()).size());
    }
}
