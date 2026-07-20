import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit testing for the method of getAllPlayerPieces
 *
 * @see GameBoard#getAllPlayerPieces(Player)
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public class GetAllPlayerPiecesTest {
    private static final Player p1 = new Player("P1", Color.RED);
    private static final Player p2 = new Player("P2", Color.BLUE);

    private static List<TestBuilder<Player, Exception>> provideForPlayerIsNull() {
        return List.of(
                new TestBuilder<>(null,new IllegalArgumentException())
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForPlayerIsNull")
    public void indexIsNotOneOrTwo(TestBuilder<Player, Exception> test) {
        Exception e = assertThrows(test.getExpected().getClass(), () -> new GameBoard(p1, p2).getAllPlayerPieces(test.getInput()));
        assertEquals("The specified player cannot be null", e.getMessage());
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
        assertEquals(test.getExpected(), new GameBoard(2,2,test.getInput(), p1, p2).getAllPlayerPieces(p1).size());
    }

    private static List<TestBuilder<Player, Integer>> provideForNoRemainingPieces() {
        return List.of(
                new TestBuilder<>(p1,0),
                new TestBuilder<>(p2,0)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForNoRemainingPieces")
    public void noRemainingPieces(TestBuilder<Player, Integer> test) {
        assertEquals(test.getExpected(), new GameBoard(3,3,"aA7L|", p1, p2).getAllPlayerPieces(test.getInput()).size());
    }
}
