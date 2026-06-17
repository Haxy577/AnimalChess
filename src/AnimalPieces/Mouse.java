package AnimalPieces;

import Board.BoardCell;
import Board.BoardTile;
import Board.GameBoard;
import Resources.ANIMALS;
import Resources.BOARD_TILES;

/**
 * Represents the "Mouse" piece in the game Animal Chess.
 * <p>
 * This piece has the ANIMAL type of MOUSE with the rank of 1.
 * It also has an immutable playerIndex field which represents which player
 * has control over this piece.
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see AnimalPiece
 * @see ANIMALS
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.6 6/11/2026
 * @since 1.0
 */
public class Mouse extends AnimalPiece {

    /**
     * Creates an animal piece with the animal type of MOUSE with rank 1, and the index
     * of the player that has control of this piece.
     *
     * @param playerIndex the index of the player controlling this animal piece
     *
     * @since 1.0
     * @see ANIMALS
     * @see AnimalPiece
     */
    public Mouse(int playerIndex) {
        super(ANIMALS.MOUSE, playerIndex);
    }

    /**
     * Determines whether the movement is valid given it meets the following conditions:
     * <ol>
     * <li>The boardCells must be instantiated</li>
     * <li>The piece that is requesting to move must be instantiated</li>
     * <li>A piece can only move 1 space either horizontally or vertically</li>
     * <li>A piece cannot move to its own den</li>
     * <li>A piece can always move to a cell that is empty/does not contain a piece</li>
     * <li>A piece cannot move to capture a piece that is controlled by the
     * same player</li>
     * <li>A piece cannot capture a piece that is on a different terrain that its own</li>
     * <li>The mouse animal piece can capture an enemy's elephant piece</li>
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
        int distance = Math.abs(source.getCol() - destination.getCol()) +
                Math.abs(source.getRow() - destination.getRow());

        if (distance != 1)
            return false;

        if (targetTile.getTYPE() == BOARD_TILES.ANIMAL_DEN &&
                targetTile.getPLAYER_INDEX() == movingPiece.getPlayerIndex())
            return false;

        if (targetPiece == null)
            return true;

        if (targetPiece.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        if (targetTile.getTYPE() != source.getTile().getTYPE())
            return false;

        return targetPiece.getAnimal() == ANIMALS.ELEPHANT;
    }
}
