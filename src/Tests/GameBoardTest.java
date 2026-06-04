package Tests;

import Board.BoardCell;
import Board.GameBoard;
import Resources.BoardTiles;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardInitializationTest {

    GameBoard gameBoard = new GameBoard();

    @ParameterizedTest
    @ValueSource(strings = {
            BoardTiles.defaultPattern,
            "LLLLLLLLLLLLRRRLLLTLLRRRLLTDTLLLLLTdTLLRRRLLTLLLRRRLLLLLLLLLLLL610081000000120072002100000000004200000031000000520000000000000000000000000051000000320000004100000000002200710011000000820062",
    })
    public void initializeGameBoard(String pattern) {
        gameBoard.initialize(pattern);
        assertEquals(pattern, gameBoard.toPattern(true));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "LLLLLLLLLLLLRRRLLLTLLRRRLLTDTLLLLLTdTLLRRRLLTLLLRRRLLLLLLLLLLLL",
            "610081000000120072002100000000004200000031000000520000000000000000000000000051000000320000004100000000002200710011000000820062"
    })
    public void patternIsLessThanExpected(String pattern) {
        assertThrows(IllegalArgumentException.class, () -> {
           gameBoard.initialize(pattern);
        });
    }



}