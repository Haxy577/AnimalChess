import AnimalPieces.AnimalPiece;
import AnimalPieces.Mouse;

/**
 * @author Richmond Jase Von M. Salvador
 * @version 1.0, 05/29/2026
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        AnimalPiece[] boardPieces = new AnimalPiece[2];
        boardPieces[0] = new Mouse(1);

        System.out.println(boardPieces[0].getRank());
    }
}