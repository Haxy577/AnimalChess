package AnimalPieces;

import Board.BoardCell;
import Board.BoardTile;
import Board.GameBoard;
import Resources.ANIMALS;
import Resources.BOARD_TILES;
import Resources.DIRECTION;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an individual animal game piece on the board.
 * <p>
 * Each piece has an immutable {@link ANIMALS} type which determines its animal type and rank,
 * an immutable {@link #playerIndex} which determines the index of the player controlling the piece,
 * and a mutable {@link Color} property that describes the color of the piece to be displayed.
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see ANIMALS
 * @see Color
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.8 6/17/2026
 * @since 1.0
 */
public abstract class AnimalPiece {

    /**
     * The type and corresponding rank of an animal piece. See {@link ANIMALS} for all
     * possible values. This field cannot be changed once set.
     *
     * @since 1.0
     * @see ANIMALS
     */
    private final ANIMALS animal;

    /**
     * The color of the piece to be displayed. See {@link Color} to see all possible ways
     * to represent colors
     *
     * @since 1.0
     * @see Color
     */
    private Color color;

    /**
     * The index of the player controlling this animal piece.
     * The index must be a value greater than or equal to 1.
     * This field cannot be changed once set.
     *
     * @since 1.0
     */
    private final int playerIndex;

    /**
     * Creates a new animal piece with a specified animal type, rank,
     * and the player controlling this animal piece
     *
     * @param animal the type and rank of the animal piece
     * @param playerIndex the index of the player controlling this animal piece
     * @throws IllegalArgumentException if there is no provided {@link #animal},
     * or the {@link #playerIndex} is a value less than 1.
     *
     * @since 1.0
     * @see ANIMALS
     */
    AnimalPiece(ANIMALS animal, int playerIndex) throws IllegalArgumentException {
        if (animal == null)
            throw new IllegalArgumentException("The animal piece must have an animal type and rank");
        if (playerIndex < 1)
            throw new IllegalArgumentException("The index of the player must be greater than or equal to 1");

        this.animal = animal;
        this.playerIndex = playerIndex;
    }

    /**
     * Creates a new animal piece with a specified animal type, rank,
     * the player controlling this animal piece, and the color of the animal piece
     *
     * @param animal the type and rank of the animal piece
     * @param playerIndex the index of the player controlling this animal piece
     * @param color determines the {@link Color} of the piece to be displayed
     * @throws IllegalArgumentException if there is no provided {@link #animal}, {@link #color},
     * or the {@link #playerIndex} is a value less than 1.
     *
     * @since 1.0
     * @see ANIMALS
     * @see Color
     */
    AnimalPiece(ANIMALS animal, int playerIndex, Color color) throws IllegalArgumentException {
        this(animal, playerIndex);

        if (color == null)
            throw new IllegalArgumentException("The piece must have a color");

        this.color = color;
    }

    /**
     * Converts the fields of this class to a string
     *
     * @return the type of animal this piece is, its rank, and the player controlling this piece
     *
     * @since 1.5
     * @see ANIMALS
     */
    @Override
    public String toString() {
        return "Piece=" + getAnimal() + "(" + getRank() + "),Player=" + getPlayerIndex();
    }

    /**
     * Gets all the possible moves of the piece. This checks whether the piece can move 1 space to the four
     * cardinal directions (UP, DOWN, LEFT, RIGHT).
     *
     * @param source The board cell that contains the piece
     * @param gameBoard the array of board cells that represents the playing board
     * @return a list of all the possible moves the piece can do
     *
     * @since 1.8
     * @see BoardCell
     * @see GameBoard
     * @see DIRECTION
     * @see #canMoveTo(BoardCell, BoardCell[][], DIRECTION)
     */
    public List<BoardCell> getAllMoves(BoardCell source, BoardCell[][] gameBoard) {
        List<BoardCell> allMoves = new ArrayList<>();
        BoardCell move;

        for (DIRECTION direction : DIRECTION.values()) {
            move = canMoveTo(source, gameBoard, direction);
            if (move != null)
                allMoves.add(move);
        }

        return allMoves;
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
    protected BoardCell canMoveTo(BoardCell source, BoardCell[][] gameBoard, DIRECTION direction) {
        BoardCell[] path = getDirectionalPath(source, gameBoard, direction);

        BoardCell target = path[0];

        return (isMoveValid(source, target)) ? target : null;
    }

    /**
     * Converts a 2d array into a 1d array of board cells. It would take all entries starting from the
     * adjacent cell relative to the source until the edge of the array based on the direction given.
     *
     * @param source the array of board cells that represents the playing board
     * @param gameBoard the array of board cells that represents the playing board
     * @param direction one of the cardinal direction that the piece is moving towards
     * @return an array that contains the path of the piece based on the given direction
     *
     * @since 1.8
     * @see BoardCell
     * @see DIRECTION
     */
    protected BoardCell[] getDirectionalPath(BoardCell source, BoardCell[][] gameBoard, DIRECTION direction) {
        BoardCell[] path;
        int row = source.getRow();
        int col = source.getCol();
        int deltaRow = (direction == DIRECTION.UP) ? -1 : (direction == DIRECTION.DOWN) ? 1 : 0;
        int deltaCol = (direction == DIRECTION.LEFT) ? -1 : (direction == DIRECTION.RIGHT) ? 1 : 0;
        int limit;

        if (deltaRow != 0) {
            limit = (deltaRow < 0) ? row : gameBoard.length - row;
        } else {
            limit = (deltaCol < 0) ? col : gameBoard[0].length - col;
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
     * <li>The boardCells must be instantiated</li>
     * <li>The piece that is requesting to move must be instantiated</li>
     * <li>A piece can only move 1 space either horizontally or vertically</li>
     * <li>A piece cannot move to a river tile</li>
     * <li>A piece cannot move to its own den</li>
     * <li>A piece can always move to a cell that is empty/does not contain a piece</li>
     * <li>A piece cannot move to capture a piece that is controlled by the
     * same player</li>
     * <li>A piece can always move to a trap tile</li>
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
     * @see ANIMALS
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

        if (targetTile.getTYPE() == BOARD_TILES.RIVER)
            return false;

        if (targetTile.getTYPE() == BOARD_TILES.ANIMAL_DEN &&
                targetTile.getPLAYER_INDEX() == movingPiece.getPlayerIndex())
            return false;

        if (targetPiece == null)
            return true;

        if (targetPiece.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        if (targetTile.getTYPE() == BOARD_TILES.TRAP)
            return true;

        return targetPiece.getRank() <= movingPiece.getRank();
    }

    /**
     * Returns the object's animal type. See {@link ANIMALS} for all possible animal types.
     *
     * @return the animal type of the piece
     *
     * @since 1.0
     * @see ANIMALS
     */
    public ANIMALS getAnimal() {
        return this.animal;
    }

    /**
     * Returns the corresponding rank associated with the object's animal type. See {@link ANIMALS}
     * for all possible animal types and its corresponding rank.
     *
     * @return the rank of the object.
     *
     * @since 1.0
     * @see ANIMALS
     */
    public int getRank() {
        return this.animal.rankNumber;
    }

    /**
     * Returns the current color set to the animal piece. See {@link Color} for all the possible
     * color representations
     *
     * @return the color set to the animal piece
     *
     * @since 1.0
     * @see Color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the index of the player that has control of this animal piece
     *
     * @return the index of the controlling player
     *
     * @since 1.0
     */
    public int getPlayerIndex() {
        return this.playerIndex;
    }

    /**
     * Changes the color field of the object. See {@link Color} for all the possible
     * color representations
     *
     * @param color the new color that the piece would take
     *
     * @since 1.0
     * @see Color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
