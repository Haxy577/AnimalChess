import Board.ConsoleDisplay;
import Board.GameBoard;

/**
 * @author Richmond Jase Von M. Salvador
 * @version 1.11, 07/1/2026
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        final boolean isConsoleDisplay = true;
        GameBoard gameBoard = new GameBoard();
        ConsoleDisplay.printBoard(gameBoard.getBoard());
    }
}