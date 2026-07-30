/**
 * Represents the "Lion" piece in the game Animal Chess.
 * <p>
 * This piece has the rank of 7.
 * It also has an immutable player field which represents which player
 * has control over this piece.
 * </p>
 * <p>
 * This piece has the following special behaviours:
 * <ol>
 * <li>Can move more than one space when attempting a jump</li>
 * <li>Can jump over continuous river tiles given there is no piece obstructing the path</li>
 * <li>Cannot capture a piece on a different based tile than its own</li>
 * </ol>
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see AnimalPiece
 * @see LeapingAnimal
 * @see Player
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.2 7/20/2026
 * @since 1.1
 */
public class Lion extends LeapingAnimal{

    /**
     * Creates an animal piece with the rank of 7, and the player object
     * of the player that has control of this piece.
     *
     * @param player the player object that has control/ownership of this piece
     *
     * @since 1.1
     * @see AnimalPiece
     * @see Player
     */
    public Lion(Player player) {
        super(7, player);
    }

    /**
     * Returns the string representation of this piece
     *
     * @return a string with the value of "Lion"
     *
     * @since 1.20
     */
    @Override
    public String pieceName() {
        return "Lion";
    }
}
