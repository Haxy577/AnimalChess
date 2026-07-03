package Resources;

import AnimalPieces.*;
import Board.BoardCell;
import Board.BoardTile;

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
 * @version 1.11 7/2/2026
 * @since 1.11
 */
public class TestBuilder<I, E> {
    private final I input;
    private final E expected;

    public TestBuilder(I input, E expected) {
        this.input = input;
        this.expected = expected;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        Object inputObj =  input;
        Object expectedObj = expected;

        if (input == null) {
            string.append("null");
        }
        else if (inputObj instanceof Object[] array) {
            string.append(Arrays.toString(array));
        }
        else {
            string.append(input);
        }

        string.append(",");

        if (expected == null) {
            string.append("null");
        }
        else if (expectedObj instanceof Object[] array) {
            string.append(Arrays.toString(array));
        }
        else {
            string.append(expected);
        }

        return string.toString();
    }

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

    public static List<TestBuilder<AnimalPiece, Boolean>> provideIsMoveValidTests(Boolean defaultExpected, List<AnimalPiece> excluded) {
        List<TestBuilder<AnimalPiece, Boolean>> tests = new ArrayList<>();

        for (AnimalPiece piece : provideAllPieces()) {
            boolean isExcluded = excluded.contains(piece);
            boolean expectedOutcome = isExcluded != defaultExpected;

            tests.add(new TestBuilder<>(piece, expectedOutcome));
        }

        return tests;
    }

    public static List<TestBuilder<BoardCell, Integer>> provideGetAllMovesTests(Integer defaultExpected, List<AnimalPiece> excluded, Integer excludedExpected) {
        List<TestBuilder<BoardCell, Integer>> tests = new ArrayList<>();
        BoardTile land = new BoardTile(BOARD_TILES.LAND);

        for (AnimalPiece piece : provideAllPieces()) {
            if (piece.getPlayerIndex() == 1) {
                boolean isExcluded = excluded.contains(piece);
                int expectedOutcome = (isExcluded) ? excludedExpected : defaultExpected;

                tests.add(new TestBuilder<>(new BoardCell(piece, land, 2, 2), expectedOutcome));
            }
        }

        return tests;
    }

    public I getInput() {
        return input;
    }

    public E getExpected() {
        return expected;
    }
}
