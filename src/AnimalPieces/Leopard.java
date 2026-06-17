package AnimalPieces;

import Resources.ANIMALS;

/**
 * Represents the "Leopard" piece in the game Animal Chess.
 * <p>
 * This piece has the ANIMAL type of LEOPARD with the rank of 5.
 * It also has an immutable playerIndex field which represents which player
 * has control over this piece.
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see AnimalPiece
 * @see ANIMALS
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.8 6/17/2026
 * @since 1.1
 */
public class Leopard extends AnimalPiece{

    /**
     * Creates an animal piece with the animal type of LEOPARD with rank 5, and the index
     * of the player that has control of this piece.
     *
     * @param playerIndex the index of the player controlling this animal piece
     *
     * @since 1.1
     * @see ANIMALS
     * @see AnimalPiece
     */
    public Leopard(int playerIndex) {
        super(ANIMALS.LEOPARD, playerIndex);
    }
}
