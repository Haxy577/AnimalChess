/**
 * Represents the "Lion" piece in the game Animal Chess.
 * <p>
 * This piece has the rank of 7.
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
public class Lion extends AnimalPiece{

    /**
     * Creates an animal piece with the rank of 7, and the index
     * of the player that has control of this piece.
     *
     * @param playerIndex the index of the player controlling this animal piece
     *
     * @since 1.1
     * @see AnimalPiece
     */
    public Lion(int playerIndex) {
        super(7, playerIndex);
    }

    /**
     * Returns the specific board cell that the piece can move on to within the specified path.
     *
     * @param source the piece that is requesting to move
     * @param path the path of the moving piece based on the direction
     * @throws IllegalArgumentException if the provided source is null or does not contain this object
     * @return the BoardCell the piece can move to occupy. Returns {@code null} if there is no valid cell
     * the piece can move towards
     *
     * @since 1.8
     * @see BoardCell
     * @see Direction
     * @see #getDirectionalPath(BoardCell, BoardCell[][], Direction)
     * @see #isMoveValid(BoardCell, BoardCell)
     */
    @Override
    public BoardCell canMoveTo(BoardCell source, BoardCell[] path) throws IllegalArgumentException {
        if (source == null)
            throw new IllegalArgumentException("Invalid argument. The specified source cannot be null");
        if (!this.equals(source.getPiece()))
            throw new IllegalArgumentException("Invalid argument. The specified source does not contain this object");

        if (path == null || path.length == 0)
            return null;

        BoardCell target = path[0];

        if (target.getTile().getType() == Tiles.RIVER)
            target = canJumpTo(path);

        return (isMoveValid(source, target)) ? target : null;
    }

    /**
     * Returns the specific board cell that the piece can move on to. A piece can move to the closest
     * LAND tile in the specific direction, even if that means jumping over RIVER tiles. However, a piece
     * cannot perform the jump over RIVER tiles provided that there is no existing pieces on the RIVER tiles.
     *
     * @param path an array of board cells extending outwards from the cell adjacent to the source until
     *             the end of the array based on the direction given.
     * @return the BoardCell the piece can move to occupy. Returns {@code null} if there is no valid cell
     * the piece can move towards
     *
     * @since 1.8
     * @see BoardCell
     * @see Direction
     * @see #getDirectionalPath(BoardCell, BoardCell[][], Direction)
     * @see #isMoveValid(BoardCell, BoardCell)
     */
    private BoardCell canJumpTo(BoardCell[] path) {
        BoardCell target = null;
        for (int i = 0; i < path.length; i++) {
            target = path[i];

            if (target.getTile().getType() == Tiles.RIVER && target.getPiece() != null)
                return null;
            if (target.getTile().getType() != Tiles.RIVER)
                break;
            if (i == path.length - 1)
                return null;
        }

        return target;
    }

    /**
     * Determines whether the movement is valid given it meets the following conditions:
     * <ol>
     * <li>The parameters must be instantiated</li>
     * <li>The piece that is requesting to move must be instantiated</li>
     * <li>A piece cannot move to a river tile</li>
     * <li>A piece cannot move to its own den</li>
     * <li>A piece can always move to a cell that is empty/does not contain a piece</li>
     * <li>A piece cannot move to capture a piece that is controlled by the same player</li>
     * <li>A piece can always move to its own trap tile</li>
     * <li>A piece cannot move to capture an opponent's piece with a higher rank than its own rank</li>
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

        if (targetTile.getType() == Tiles.RIVER)
            return false;

        if (targetTile.getType() == Tiles.ANIMAL_DEN &&
                targetTile.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        if (targetPiece == null)
            return true;

        if (targetPiece.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        if (targetTile.getType() == Tiles.TRAP && targetTile.getPlayerIndex() == movingPiece.getPlayerIndex())
            return true;

        return targetPiece.getRank() <= movingPiece.getRank();
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
