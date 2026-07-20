import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a single test case for a specific test.
 * <p>
 * This contains a generic field which contains the input for the test case, and a
 * generic field which contains the specific expected output for the test.
 * </p>
 *
 * @param <I> the data type of the input for the test case
 * @param <E> the data type of the expected output for the test case
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.11
 */
public class TestBuilder<I, E> {
    private final I INPUT;
    private final E EXPECTED;

    private static final Player p1 = new Player("P1", Color.RED);
    private static final Player p2 = new Player("P2", Color.BLUE);

    /**
     * Constructs a test case with the specified input and expected output of the test
     *
     * @param input the input of the test case
     * @param expected the expected output of the test case
     *
     * @since 1.11
     */
    public TestBuilder(I input, E expected) {
        this.INPUT = input;
        this.EXPECTED = expected;
    }

    /**
     * Converts the fields of this class to a string
     *
     * @return the string representation of this object
     *
     * @since 1.11
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        Object inputObj = INPUT;
        Object expectedObj = EXPECTED;

        if (INPUT == null) {
            string.append("null");
        }
        else if (inputObj instanceof Object[] array) {
            string.append(Arrays.toString(array));
        }
        else {
            string.append(INPUT);
        }

        string.append(",");

        if (EXPECTED == null) {
            string.append("null");
        }
        else if (expectedObj instanceof Object[] array) {
            string.append(Arrays.toString(array));
        }
        else {
            string.append(EXPECTED);
        }

        return string.toString();
    }

    /**
     * Provides a list that contains every animal piece of each respective player
     *
     * @return the list of all possible animal piece that can be created
     *
     * @since 1.26
     * @see AnimalPiece
     */
    public static List<AnimalPiece> provideAllPieces() {
        return List.of(
                new Mouse(p1), new Mouse(p2),
                new Cat(p1), new Cat(p2),
                new Wolf(p1), new Wolf(p2),
                new Dog(p1), new Dog(p2),
                new Leopard(p1), new Leopard(p2),
                new Tiger(p1), new Tiger(p2),
                new Lion(p1), new Lion(p2),
                new Elephant(p1), new Elephant(p2)
        );
    }

    /**
     * Provides a list of test cases for testing the method of isValidMove
     *
     * @param defaultExpected the default value of the expected output of each test case.
     * @param excluded a list of animal piece which is expected to have the opposite value of the default
     * @return the list of test cases to test the method
     *
     * @since 1.26
     * @see AnimalPiece
     * @see AnimalPiece#isMoveValid(BoardCell, BoardCell)
     */
    public static List<TestBuilder<AnimalPiece, Boolean>> provideIsMoveValidTests(Boolean defaultExpected, List<AnimalPiece> excluded) {
        List<TestBuilder<AnimalPiece, Boolean>> tests = new ArrayList<>();

        for (AnimalPiece piece : provideAllPieces()) {
            boolean isExcluded = excluded.contains(piece);
            boolean expectedOutcome = isExcluded != defaultExpected;

            tests.add(new TestBuilder<>(piece, expectedOutcome));
        }

        return tests;
    }

    /**
     * Provides a list of test cases for testing the method of getAllMoves
     *
     * @param defaultExpected the default value of the expected amount of moves per piece
     * @param excluded a list of animal pieces that have a different expected output from the default
     * @param excludedExpected the expected value for the pieces that were excluded
     * @return a list of test cases for the method
     *
     * @since 1.26
     * @see AnimalPiece
     * @see AnimalPiece#getAllMoves(BoardCell, BoardCell[][])
     */
    public static List<TestBuilder<BoardCell, Integer>> provideGetAllMovesTests(Integer defaultExpected, List<AnimalPiece> excluded, Integer excludedExpected) {
        List<TestBuilder<BoardCell, Integer>> tests = new ArrayList<>();
        BoardTile land = new BoardTile(Tiles.LAND);

        for (AnimalPiece piece : provideAllPieces()) {
            if (piece.getPlayer().equals(p1)) {
                boolean isExcluded = excluded.contains(piece);
                int expectedOutcome = (isExcluded) ? excludedExpected : defaultExpected;

                tests.add(new TestBuilder<>(new BoardCell(piece, land, 2, 2), expectedOutcome));
            }
        }

        return tests;
    }

    /**
     * Returns the set input of this test case
     *
     * @return input of the test
     *
     * @since 1.11
     */
    public I getInput() {
        return INPUT;
    }

    /**
     * Returns the set expected output of this test case
     *
     * @return the expected output of this test
     *
     * @since 1.11
     */
    public E getExpected() {
        return EXPECTED;
    }
}
