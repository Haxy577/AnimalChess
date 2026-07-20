/**
 * Represents the "Elephant" piece in the game Animal Chess.
 * <p>
 * This piece has the rank of 8.
 * It also has an immutable player field which represents which player
 * has control over this piece.
 * </p>
 * <p>
 * This piece has the following special behaviours:
 * <ol>
 * <li>Cannot capture an enemy mouse piece</li>
 * </ol>
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
public class Elephant extends AnimalPiece{

    /**
     * Creates an animal piece with the rank of 8, and the player object
     * of the player that has control of this piece.
     *
     * @param player the player object that has control/ownership of this piece
     *
     * @since 1.1
     * @see AnimalPiece
     * @see Player
     */
    public Elephant(Player player) {
        super(8, player);
    }

    /**
     * Determines whether the movement is valid given it meets the following conditions:
     * <ol>
     * <li>The parameters must be instantiated</li>
     * <li>The piece that is requesting to move must be instantiated</li>
     * <li>A piece can only move 1 space either horizontally or vertically</li>
     * <li>A piece cannot move to a river tile</li>
     * <li>A piece cannot move to its own den</li>
     * <li>A piece can always move to a cell that is empty/does not contain a piece</li>
     * <li>A piece cannot move to capture a piece that is controlled by the
     * same player</li>
     * <li>A piece can always move to its own trap tile as long as it does not contain an enemy's mouse animal piece</li>
     * <li>A piece cannot move to capture an opponent's piece with a higher rank than its own rank</li>
     * <li>The elephant animal piece cannot capture an enemy's mouse animal piece</li>
     * </ol>
     *
     * @param source the cell containing the piece requesting to move
     * @param destination an array of cells that contain the path the piece is trying to move to. The
     *                    last element of the array is where the piece is attempting to move.
     * @return {@code true} if the piece is allowed to move to the destination, {@code false} if the piece does
     * not exist nor can move to that destination.
     *
     * @since 1.8
     * @see Tiles
     * @see GameBoard
     */
    @Override
    public boolean isMoveValid(BoardCell source, BoardCell destination) {
        if (source == null || destination == null)
            return false;

        if (source.getPiece() == null)
            return false;

        AnimalPiece movingPiece = source.getPiece();
        AnimalPiece targetPiece = destination.getPiece();
        BoardTile targetTile = destination.getTile();
        Player movingPlayer = movingPiece.getPlayer();
        int distance = Math.abs(source.getCol() - destination.getCol()) + Math.abs(source.getRow() - destination.getRow());

        if (distance != 1)
            return false;

        if (targetTile.getType() == Tiles.RIVER)
            return false;

        if (targetTile.getType() == Tiles.ANIMAL_DEN && movingPlayer.equals(targetTile.getPlayer()))
            return false;

        if (targetPiece == null)
            return true;

        if (targetPiece.getPlayer().equals(movingPlayer))
            return false;

        if (targetTile.getType() == Tiles.TRAP && targetTile.getPlayer().equals(movingPiece.getPlayer()))
            return !(targetPiece instanceof Mouse);

        return !(targetPiece instanceof Mouse) && targetPiece.getRank() <= movingPiece.getRank();
    }

    /**
     * Returns the string representation of this piece
     *
     * @return a string with the value of "Elephant"
     *
     * @since 1.20
     */
    @Override
    public String pieceName() {
        return "Elephant";
    }
}
