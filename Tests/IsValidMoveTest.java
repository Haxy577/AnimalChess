import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Junit testing for the method of isValidMove
 *
 * @see AnimalPiece#isMoveValid(BoardCell, BoardCell)
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public class IsValidMoveTest {

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForNullTests() {
        return TestBuilder.provideIsMoveValidTests(false, List.of());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForNullTests")
    public void sourceIsNull(TestBuilder<AnimalPiece, Boolean> test) {
        BoardTile tile = new BoardTile(Tiles.LAND);
        AnimalPiece piece = test.getInput();
        BoardCell target = new BoardCell(tile, 0, 0);

        assertEquals(test.getExpected(), piece.isMoveValid(null, target));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForNullTests")
    public void movingToANullCell(TestBuilder<AnimalPiece, Boolean> test) {
        BoardTile tile = new BoardTile(Tiles.LAND);
        AnimalPiece piece = test.getInput();
        BoardCell source = new BoardCell(piece, tile, 0, 0);

        assertEquals(test.getExpected(), piece.isMoveValid(source, null));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToTheSameCell() {
        return TestBuilder.provideIsMoveValidTests(false, List.of());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToTheSameCell")
    public void movingToTheSameCell(TestBuilder<AnimalPiece, Boolean> test) {
        BoardTile land = new BoardTile(Tiles.LAND);
        AnimalPiece piece = test.getInput();
        BoardCell source = new BoardCell(piece, land, 0, 0);

        assertEquals(test.getExpected(), piece.isMoveValid(source, source));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingMoreThanOneCell() {
        return TestBuilder.provideIsMoveValidTests(false,
                List.of(new Tiger(1), new Tiger(2), new Lion(1), new Lion(2)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingMoreThanOneCell")
    public void movingMoreThanOneCell(TestBuilder<AnimalPiece, Boolean> test) {
        BoardTile land = new BoardTile(Tiles.LAND);
        AnimalPiece piece = test.getInput();
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(land, 0, 2);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToARiver() {
        return TestBuilder.provideIsMoveValidTests(false, List.of(new Mouse(1), new Mouse(2)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToARiver")
    public void movingToARiver(TestBuilder<AnimalPiece, Boolean> test) {
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile river = new BoardTile(Tiles.RIVER);
        AnimalPiece piece = test.getInput();
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(river, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToEnemyDen() {
        return TestBuilder.provideIsMoveValidTests(true, List.of());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToEnemyDen")
    public void movingToEnemyDen(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile den = new BoardTile(Tiles.ANIMAL_DEN, (piece.getPlayerIndex() == 1) ? 2 : 1);
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(den, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToOwnDen() {
        return TestBuilder.provideIsMoveValidTests(false, List.of());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToOwnDen")
    public void movingToOwnDen(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile den = new BoardTile(Tiles.ANIMAL_DEN, piece.getPlayerIndex());
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(den, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToEmptyTile() {
        return TestBuilder.provideIsMoveValidTests(true, List.of());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToEmptyTile")
    public void movingToEmptyTile(TestBuilder<AnimalPiece, Boolean> test) {
        BoardTile land = new BoardTile(Tiles.LAND);
        AnimalPiece piece = test.getInput();
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(land, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToOwnTrapTile() {
        return TestBuilder.provideIsMoveValidTests(true, List.of());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToOwnTrapTile")
    public void movingToOwnTrapTile(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile trap = new BoardTile(Tiles.TRAP, piece.getPlayerIndex());
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(trap, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToEnemyTrapTile() {
        return TestBuilder.provideIsMoveValidTests(true, List.of());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToEnemyTrapTile")
    public void movingToEnemyTrapTile(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile trap = new BoardTile(Tiles.TRAP, (piece.getPlayerIndex() == 1) ? 2 : 1);
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(trap, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToOwnTrapTileWithEnemyMouse() {
        return TestBuilder.provideIsMoveValidTests(true, List.of(new Elephant(1), new Elephant(2)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToOwnTrapTileWithEnemyMouse")
    public void movingToOwnTrapTileWithEnemyMouse(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        AnimalPiece enemy = new Mouse((piece.getPlayerIndex() == 1) ? 2 : 1);
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile trap = new BoardTile(Tiles.TRAP, piece.getPlayerIndex());
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(enemy, trap, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForMovingToEnemyTrapTileWithEnemyElephant() {
        return TestBuilder.provideIsMoveValidTests(false, List.of(new Mouse(1), new Mouse(2),
                new Elephant(1), new Elephant(2)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForMovingToEnemyTrapTileWithEnemyElephant")
    public void movingToEnemyTrapTileWithEnemyElephant(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        AnimalPiece enemy = new Elephant((piece.getPlayerIndex() == 1) ? 2 : 1);
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile trap = new BoardTile(Tiles.TRAP, (piece.getPlayerIndex() == 1) ? 2 : 1);
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(enemy, trap, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForCapturingPieceOnLandFromRiver() {
        return TestBuilder.provideIsMoveValidTests(true, List.of(new Mouse(1), new Mouse(2)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForCapturingPieceOnLandFromRiver")
    public void capturingPieceOnLandFromRiver(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        AnimalPiece enemy;
        if (piece instanceof Elephant)
            enemy = new Elephant((piece.getPlayerIndex() == 1) ? 2 : 1);
        else
            enemy = new Mouse((piece.getPlayerIndex() == 1) ? 2 : 1);

        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile river = new BoardTile(Tiles.RIVER);
        BoardCell source = new BoardCell(piece, river, 0, 0);
        BoardCell target = new BoardCell(enemy, land, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForCapturingPieceOnRiverFromLand() {
        return TestBuilder.provideIsMoveValidTests(false, List.of());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForCapturingPieceOnRiverFromLand")
    public void capturingPieceOnRiverFromLand(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        AnimalPiece enemy = new Mouse((piece.getPlayerIndex() == 1) ? 2 : 1);
        BoardTile land = new BoardTile(Tiles.LAND);
        BoardTile river = new BoardTile(Tiles.RIVER);
        BoardCell source = new BoardCell(piece, land, 0, 0);
        BoardCell target = new BoardCell(enemy, river, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }

    private static List<TestBuilder<AnimalPiece, Boolean>> provideForCapturingPieceOnSameTileType() {
        return TestBuilder.provideIsMoveValidTests(false, List.of(new Mouse(1), new Mouse(2)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideForCapturingPieceOnSameTileType")
    public void capturingPieceOnSameTileType(TestBuilder<AnimalPiece, Boolean> test) {
        AnimalPiece piece = test.getInput();
        AnimalPiece enemy = new Elephant((piece.getPlayerIndex() == 1) ? 2 : 1);
        BoardTile river = new BoardTile(Tiles.RIVER);
        BoardCell source = new BoardCell(piece, river, 0, 0);
        BoardCell target = new BoardCell(enemy, river, 0, 1);

        assertEquals(test.getExpected(), piece.isMoveValid(source, target));
    }
}