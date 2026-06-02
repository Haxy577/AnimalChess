import AnimalPieces.AnimalPiece;
import AnimalPieces.Mouse;
import Board.BoardTile;
import Board.GameBoard;
import Resources.BoardTiles;

/**
 * @author Richmond Jase Von M. Salvador
 * @version 1.2, 06/02/2026
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();

        gameBoard.initialize(BoardTiles.defaultPattern);
        String pattern = gameBoard.toPattern();

        System.out.println(pattern.equals(BoardTiles.defaultPattern));
        System.out.println(pattern);
        System.out.println(BoardTiles.defaultPattern);


    }
}