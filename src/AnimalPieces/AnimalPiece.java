package AnimalPieces;

import Board.BoardCell;
import Board.BoardTile;
import Board.GameBoard;
import Resources.BOARD_TILES;
import Resources.DIRECTION;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an individual animal game piece on the board.
 * <p>
 * Each piece has an immutable {@link #RANK} which determines numerical rank of the piece,
 * an immutable {@link #playerIndex} which determines the index of the player controlling the piece,
 * and a mutable {@link Color} property that describes the color of the piece to be displayed.
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see Color
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.11 6/17/2026
 * @since 1.0
 */
public abstract class AnimalPiece {

    /**
     * The rank of an animal piece. This field cannot be changed once set.
     *
     * @since 1.0
     */
    private final int RANK;

    /**
     * The index of the player controlling this animal piece.
     * The index must be a value greater than or equal to 1.
     * This field cannot be changed once set.
     *
     * @since 1.0
     */
    private final int playerIndex;

    /**
     * Creates a new animal piece with a specified animal rank
     * and the player controlling this animal piece
     *
     * @param RANK the rank of the animal piece
     * @param playerIndex the index of the player controlling this animal piece
     * @throws IllegalArgumentException if there is no provided {@link #RANK},
     * or the {@link #playerIndex} is a value that is neither 1 nor 2
     *
     * @since 1.0
     */
    protected AnimalPiece(int RANK, int playerIndex) throws IllegalArgumentException {
        if (playerIndex < 1 || playerIndex > 2)
            throw new IllegalArgumentException("The player index must be either 1 or 2");

        this.RANK = RANK;
        this.playerIndex = playerIndex;
    }

    /**
     * Converts the fields of this class to a string
     *
     * @return the type of animal this piece is, its rank, and the player controlling this piece
     *
     * @since 1.5
     */
    @Override
    public String toString() {
        return "Piece[rank=" + RANK + ",player=" + playerIndex + "]";
    }

    /**
     * Compares the specified object with the current object based on its animal type and the player index it has
     *
     * @param obj   the reference object with which to compare.
     * @return true if the type and player index are the same, false otherwise
     *
     * @since 1.11
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AnimalPiece piece))
            return false;

        return RANK == piece.getRank() && playerIndex == piece.getPlayerIndex();
    }

    /**
     * Gets all the possible moves of the piece. This checks whether the piece can move 1 space to the four
     * cardinal directions (UP, DOWN, LEFT, RIGHT).
     *
     * @param source The board cell that contains the piece
     * @param gameBoard the array of board cells that represents the playing board
     * @throws IllegalArgumentException if the provided source is outside or does not exist with the gameboard, or if the
     * source and/or gameboard is/are null
     * @return a list of all the possible moves the piece can do
     *
     * @since 1.8
     * @see BoardCell
     * @see GameBoard
     * @see DIRECTION
     * @see #canMoveTo(BoardCell, BoardCell[])
     */
    public List<BoardCell> getAllMoves(BoardCell source, BoardCell[][] gameBoard) {
        if (source == null || gameBoard == null)
            throw new IllegalArgumentException("Invalid source and/or gameboard. These parameters cannot be null");
        if (source.getRow() < 0 || source.getRow() >= gameBoard.length || source.getCol() < 0 || source.getCol() >= gameBoard[0].length)
            throw new IllegalArgumentException("Invalid source. The specified cell exists outside the bounds of the gameboard array");
        if (!source.equals(gameBoard[source.getRow()][source.getCol()]))
            throw new IllegalArgumentException("Invalid source. The specified cell does not exists within the board");

        List<BoardCell> allMoves = new ArrayList<>();
        BoardCell move;

        for (DIRECTION direction : DIRECTION.values()) {
            move = canMoveTo(source, getDirectionalPath(source, gameBoard, direction));
            if (move != null)
                allMoves.add(move);
        }

        return allMoves;
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
     * @see DIRECTION
     * @see #getDirectionalPath(BoardCell, BoardCell[][], DIRECTION)
     * @see #isMoveValid(BoardCell, BoardCell)
     */
    public BoardCell canMoveTo(BoardCell source, BoardCell[] path) throws IllegalArgumentException {
        if (source == null)
            throw new IllegalArgumentException("Invalid argument. The specified source cannot be null");
        if (!this.equals(source.getPiece()))
            throw new IllegalArgumentException("Invalid argument. The specified source does not contain this object");

        if (path == null || path.length == 0)
            return null;

        return (isMoveValid(source, path[0])) ? path[0] : null;
    }

    /**
     * Converts a 2d array into a 1d array of board cells. It would take all entries starting from the
     * adjacent cell relative to the source until the edge of the array based on the direction given.
     *
     * @param source the array of board cells that represents the playing board
     * @param gameBoard the array of board cells that represents the playing board
     * @param direction one of the cardinal direction that the piece is moving towards
     * @throws IllegalArgumentException if the provided source is outside or does not exist with the gameboard, or if the
     * source and/or gameboard is/are null
     * @return an array that contains the path of the piece based on the given direction
     *
     * @since 1.8
     * @see BoardCell
     * @see DIRECTION
     */
    public BoardCell[] getDirectionalPath(BoardCell source, BoardCell[][] gameBoard, DIRECTION direction) throws IllegalArgumentException {
        if (source == null || gameBoard == null)
            throw new IllegalArgumentException("Invalid source and/or gameboard. These parameters cannot be null");

        BoardCell[] path;
        int row = source.getRow();
        int col = source.getCol();

        if (row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length)
            throw new IllegalArgumentException("Invalid source. The specified cell exists outside the bounds of the gameboard array");
        if (!source.equals(gameBoard[row][col]))
            throw new IllegalArgumentException("Invalid source. The specified cell does not exists within the board");

        int deltaRow = (direction == DIRECTION.UP) ? -1 : (direction == DIRECTION.DOWN) ? 1 : 0;
        int deltaCol = (direction == DIRECTION.LEFT) ? -1 : (direction == DIRECTION.RIGHT) ? 1 : 0;
        int limit;

        if (deltaRow != 0) {
            limit = (deltaRow < 0) ? row : gameBoard.length - row - 1;
        } else {
            limit = (deltaCol < 0) ? col : gameBoard[0].length - col - 1;
        }

        path = new BoardCell[limit];

        for (int i = 0; i < limit; i++) {
            int nextRow = row + (deltaRow * (i + 1));
            int nextCol = col + (deltaCol * (i + 1));
            path[i] = gameBoard[nextRow][nextCol];
        }

        return path;
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
     * @since 1.1
     * @see BOARD_TILES
     * @see GameBoard
     */
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

        if (targetTile.getType() == BOARD_TILES.RIVER)
            return false;

        if (targetTile.getType() == BOARD_TILES.ANIMAL_DEN &&
                targetTile.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        if (targetPiece == null)
            return true;

        if (targetPiece.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        if (targetTile.getType() == BOARD_TILES.TRAP && targetTile.getPlayerIndex() == movingPiece.getPlayerIndex())
            return true;

        return targetPiece.getRank() <= movingPiece.getRank();
    }

    /**
     * Returns the corresponding rank associated with the object's animal type.
     *
     * @return the rank of the object.
     *
     * @since 1.0
     */
    public int getRank() {
        return RANK;
    }

    /**
     * Returns the index of the player that has control of this animal piece
     *
     * @return the index of the controlling player
     *
     * @since 1.0
     */
    public int getPlayerIndex() {
        return playerIndex;
    }
}
