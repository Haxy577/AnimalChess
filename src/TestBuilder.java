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
                new Mouse(1), new Mouse(2),
                new Cat(1), new Cat(2),
                new Wolf(1), new Wolf(2),
                new Dog(1), new Dog(2),
                new Leopard(1), new Leopard(2),
                new Tiger(1), new Tiger(2),
                new Lion(1), new Lion(2),
                new Elephant(1), new Elephant(2)
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
            if (piece.getPlayerIndex() == 1) {
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
