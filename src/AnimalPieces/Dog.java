package AnimalPieces;

/**
 * Represents the "Dog" piece in the game Animal Chess.
 * <p>
 * This piece has the rank of 4.
 * It also has an immutable playerIndex field which represents which player
 * has control over this piece.
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see AnimalPiece
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.20 7/9/2026
 * @since 1.1
 */
public class Dog extends AnimalPiece{

    /**
     * Creates an animal piece with the rank of 4, and the index
     * of the player that has control of this piece.
     *
     * @param playerIndex the index of the player controlling this animal piece
     *
     * @since 1.1
     * @see AnimalPiece
     */
    public Dog(int playerIndex) {
        super(4, playerIndex);
    }

    /**
     * Returns the string representation of this piece
     *
     * @return a string with the value of "Dog"
     *
     * @since 1.20
     */
    @Override
    public String pieceName() {
        return "Dog";
    }
}
