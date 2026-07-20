/**
 * Represents the "Leopard" piece in the game Animal Chess.
 * <p>
 * This piece has the rank of 5.
 * It also has an immutable player field which represents which player
 * has control over this piece.
 * </p>
 * <p>
 * This piece has no special behaviors.
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see AnimalPiece
 * @see Player
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.2 7/20/2026
 * @since 1.1
 */
public class Leopard extends AnimalPiece{

    /**
     * Creates an animal piece with the rank of 5, and the player object
     * of the player that has control of this piece.
     *
     * @param player the player object that has control/ownership of this piece
     *
     * @since 1.1
     * @see AnimalPiece
     * @see Player
     */
    public Leopard(Player player) {
        super(5, player);
    }

    /**
     * Returns the string representation of this piece
     *
     * @return a string with the value of "Leopard"
     *
     * @since 1.20
     */
    @Override
    public String pieceName() {
        return "Leopard";
    }
}
