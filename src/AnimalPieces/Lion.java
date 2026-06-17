package AnimalPieces;

import Board.BoardCell;
import Board.BoardTile;
import Board.GameBoard;
import Resources.ANIMALS;
import Resources.BOARD_TILES;
import Resources.DIRECTION;

/**
 * Represents the "Lion" piece in the game Animal Chess.
 * <p>
 * This piece has the ANIMAL type of LION with the rank of 7.
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
public class Lion extends AnimalPiece{

    /**
     * Creates an animal piece with the animal type of LION with rank 7, and the index
     * of the player that has control of this piece.
     *
     * @param playerIndex the index of the player controlling this animal piece
     *
     * @since 1.1
     * @see ANIMALS
     * @see AnimalPiece
     */
    public Lion(int playerIndex) {
        super(ANIMALS.LION, playerIndex);
    }

    /**
     * Returns the specific board cell that the piece can move on to.
     *
     * @param source the piece that is requesting to move
     * @param gameBoard the array of board cells that represents the playing board
     * @param direction one of the cardinal direction that the piece is moving towards
     * @return the BoardCell the piece can move to occupy. Returns {@code null} if there is no valid cell
     * the piece can move towards
     *
     * @since 1.8
     * @see BoardCell
     * @see DIRECTION
     * @see #getDirectionalPath(BoardCell, BoardCell[][], DIRECTION)
     * @see #isMoveValid(BoardCell, BoardCell)
     */
    @Override
    protected BoardCell canMoveTo(BoardCell source, BoardCell[][] gameBoard, DIRECTION direction) {
        BoardCell[] path = getDirectionalPath(source, gameBoard, direction);

        BoardCell target = (path[0].getTile().getTYPE() == BOARD_TILES.RIVER) ? canJumpTo(path) : path[0];

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
     * @see DIRECTION
     * @see #getDirectionalPath(BoardCell, BoardCell[][], DIRECTION)
     * @see #isMoveValid(BoardCell, BoardCell)
     */
    private BoardCell canJumpTo(BoardCell[] path) {
        BoardCell target = null;
        for (BoardCell boardCell : path) {
            target = boardCell;

            if (target.getTile().getTYPE() == BOARD_TILES.RIVER && target.getPiece() != null)
                return null;
            if (target.getTile().getTYPE() != BOARD_TILES.RIVER)
                break;
        }

        return target;
    }

    /**
     * Determines whether the movement is valid given it meets the following conditions:
     * <ol>
     * <li>The boardCells must be instantiated</li>
     * <li>The piece that is requesting to move must be instantiated</li>
     * <li>A piece cannot move to a river tile</li>
     * <li>A piece cannot move to its own den</li>
     * <li>A piece can always move to a cell that is empty/does not contain a piece</li>
     * <li>A piece cannot move to capture a piece that is controlled by the
     * same player</li>
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
     * @see ANIMALS
     * @see BOARD_TILES
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

        if (targetTile.getTYPE() == BOARD_TILES.RIVER)
            return false;

        if (targetTile.getTYPE() == BOARD_TILES.ANIMAL_DEN &&
                targetTile.getPLAYER_INDEX() == movingPiece.getPlayerIndex())
            return false;

        if (targetPiece == null)
            return true;

        if (targetPiece.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        return targetPiece.getRank() <= movingPiece.getRank();
    }
}
