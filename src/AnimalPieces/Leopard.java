package AnimalPieces;

/**
 * Represents the "Leopard" piece in the game Animal Chess.
 * <p>
 * This piece has the rank of 5.
 * It also has an immutable playerIndex field which represents which player
 * has control over this piece.
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see AnimalPiece
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.11 7/4/2026
 * @since 1.1
 */
public class Leopard extends AnimalPiece{

    /**
     * Creates an animal piece with the rank of 5, and the index
     * of the player that has control of this piece.
     *
     * @param playerIndex the index of the player controlling this animal piece
     *
     * @since 1.1
     * @see AnimalPiece
     */
    public Leopard(int playerIndex) {
        super(5, playerIndex);
    }

    @Override
    public String pieceName() {
        return "Leopard";
    }
}
