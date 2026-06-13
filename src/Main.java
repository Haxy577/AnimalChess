import Board.GameBoard;
import Resources.BoardTiles;

/**
 * @author Richmond Jase Von M. Salvador
 * @version 1.7, 06/14/2026
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        String defaultPattern = "12L3R3LT2L3R2LTAT5LTaT2L3R2LT3L3R12L|G1E3m1n1C5d3W3p13P3W3D5c1N1M3e1g";
        gameBoard.initialize(defaultPattern);
        System.out.println(gameBoard.toPattern().equals(defaultPattern));
    }
}