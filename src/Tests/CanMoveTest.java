package Tests;

import AnimalPieces.*;
import Board.BoardCell;
import Board.BoardTile;
import Resources.BoardTiles;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CanMoveTest {

    private static Stream<Object> allAnimalPieces() {
        return Stream.of(
                Arguments.of(new Mouse(1), true),
                Arguments.of(new Cat (1), false),
                Arguments.of(new Wolf (1), false),
                Arguments.of(new Dog (1), false),
                Arguments.of(new Leopard (1), false),
                Arguments.of(new Tiger (1), false),
                Arguments.of(new Lion (1), false),
                Arguments.of(new Elephant (1), false)
        );
    }

    @ParameterizedTest
    @MethodSource("allAnimalPieces")
    public void movingToARiverTile(AnimalPiece piece, boolean expected) {
        BoardCell moving = new BoardCell(piece, new BoardTile(BoardTiles.LAND, 0));
        BoardCell target = new BoardCell(new BoardTile(BoardTiles.RIVER, 0));

        assertEquals(expected, moving.getPiece().canMove(moving, target));

    }
}