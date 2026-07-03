import Board.ConsoleDisplay;
import Board.GameBoard;

import java.util.Arrays;

/**
 * @author Richmond Jase Von M. Salvador
 * @version 1.11, 07/1/2026
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(1, 8, "AaTTRRLL|dDdDdDdD");
        ConsoleDisplay.printBoard(gameBoard.getGameBoard());
    }
}