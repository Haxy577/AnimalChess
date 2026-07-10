import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.List;

public class ParseAlgebraicNotationTest {
    private static List<TestBuilder<String, Point>> provideForInvalidAlgebraicNotations() {
        return List.of(
                new TestBuilder<>(null, null),
                new TestBuilder<>("", null),
                new TestBuilder<>("1B", null),
                new TestBuilder<>("$2", null),
                new TestBuilder<>("Azero", null)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForInvalidAlgebraicNotations")
    public void invalidAlgebraicNotations(TestBuilder<String, Point> test) {
        assertEquals(test.getExpected(), new GameEngine().parseAlgebraicNotation(test.getInput()));
    }

    private static List<TestBuilder<String, Point>> provideForCheckAlgebraicNotationConversion() {
        return List.of(
                new TestBuilder<>("a0", new Point(0,0)),
                new TestBuilder<>("a01", new Point(0,1)),
                new TestBuilder<>("z26", new Point(25,26)),
                new TestBuilder<>("aA2", new Point(26,2)),
                new TestBuilder<>("bcF3235", new Point(1435,3235))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForCheckAlgebraicNotationConversion")
    public void checkAlgebraicNotationConversion(TestBuilder<String, Point> test) {
        assertEquals(test.getExpected(), new GameEngine().parseAlgebraicNotation(test.getInput()));
    }

    private static List<TestBuilder<String, Point>> provideForWithUnnecessarySpaces() {
        return List.of(
                new TestBuilder<>(" a  0  ", new Point(0,0)),
                new TestBuilder<>("a 0    1   ", new Point(0,1)),
                new TestBuilder<>("z  26  ", new Point(25,26)),
                new TestBuilder<>("   a  a2  ", new Point(26,2)),
                new TestBuilder<>("  b c F3  235  ", new Point(1435,3235))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForWithUnnecessarySpaces")
    public void withUnnecessarySpaces(TestBuilder<String, Point> test) {
        assertEquals(test.getExpected(), new GameEngine().parseAlgebraicNotation(test.getInput()));
    }
}
