package AnimalPieces;

import Board.BoardCell;
import Board.BoardTile;
import Resources.Animals;
import Resources.BoardTiles;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an individual animal game piece on the board.
 * <p>
 * Each piece has an immutable {@link Animals} type which determines its animal type and rank,
 * an immutable {@link #playerIndex} which determines the index of the player controlling the piece,
 * and a mutable {@link Color} property that describes the color of the piece to be displayed.
 * </p>
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.1
 * @since 1.0
 * @see <a href = "https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see Animals
 * @see Color
 */
public abstract class AnimalPiece {

    /**
     * The type and corresponding rank of an animal piece. See {@link Animals} for all
     * possible values. This field cannot be changed once set.
     *
     * @since 1.0
     * @see Animals
     * @see #getAnimal()
     * @see #getRank()
     */
    private final Animals animal;

    /**
     * The color of the piece to be displayed. See {@link Color} to see all possible ways
     * to represent colors
     *
     * @since 1.0
     * @see Color
     * @see #getColor()
     * @see #setColor(Color)
     */
    private Color color;

    /**
     * The index of the player controlling this animal piece.
     * The index must be a value greater than or equal to 1.
     * This field cannot be changed once set.
     *
     * @since 1.0
     * @see #getPlayerIndex()
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
     * @see Animals
     * @see #animal
     * @see #playerIndex
     */
    AnimalPiece(Animals animal, int playerIndex) throws IllegalArgumentException {
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
     * @see Animals
     * @see Color
     * @see #animal
     * @see #playerIndex
     * @see #color
     */
    AnimalPiece(Animals animal, int playerIndex, Color color) throws IllegalArgumentException {
        this(animal, playerIndex);

        if (color == null)
            throw new IllegalArgumentException("The piece must have a color");

        this.color = color;
    }

    /**
     * z
     *
     * @param source
     * @param gameBoard
     * @return
     */
    public List<BoardCell> getAllMoves(BoardCell source, BoardCell[][] gameBoard) {
        ArrayList<BoardCell> allMoves = new ArrayList<>();
        int row = source.getRow();
        int col = source.getCol();

        if (col > 0 && canMove(source, gameBoard[row][col - 1]))
            allMoves.add(gameBoard[row][col - 1]);

        if (col < gameBoard[row].length - 1 && canMove(source, gameBoard[row][col + 1]))
            allMoves.add(gameBoard[row][col + 1]);

        if (row > 0 && canMove(source, gameBoard[row - 1][col]))
            allMoves.add(gameBoard[row - 1][col]);

        if (row < gameBoard.length - 1 && canMove(source, gameBoard[row + 1][col]))
            allMoves.add(gameBoard[row + 1][col]);

        return allMoves;
    }

    /**
     * Determines whether the movement is valid given it meets the following conditions:
     * <ol>
     * <li>A piece cannot move to a river tile</li>
     * <li>A piece cannot move to its own den</li>
     * <li>A piece can always move to a cell that is empty/does not contain a piece</li>
     * <li>A piece cannot move to capture a piece that is controlled by the
     * same player</li>
     * <li>A piece can always move to a trap tile</li>
     * <li>A piece cannot move to capture an opponent's piece with a higher rank than its own rank</li>
     * </ol>
     * @param source the cell containing the piece requesting to move
     * @param destination an array of cells that contain the path the piece is trying to move to. The
     *                    last element of the array is where the piece is attempting to move.
     * @return {@code true} if the piece is allowed to move to the destination, {@code false} if the piece does
     * not exist nor can move to that destination.
     *
     * @since 1.1
     * @see Animals
     * @see BoardTiles
     * @see Board.GameBoard
     *
     */
    public boolean canMove(BoardCell source, BoardCell... destination) {
        if (source.getPiece() == null)
            return false;

        AnimalPiece movingPiece = source.getPiece();
        AnimalPiece targetPiece = destination[destination.length - 1].getPiece();
        BoardTile destinationTile = destination[destination.length - 1].getTile();

        if (destinationTile.getType() == BoardTiles.RIVER)
            return false;

        if (destinationTile.getType() == BoardTiles.DEN &&
                destinationTile.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        if (targetPiece == null)
            return true;

        if (targetPiece.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;

        if (destinationTile.getType() == BoardTiles.TRAP)
            return true;

        return targetPiece.getRank() <= movingPiece.getRank();
    }

    /**
     * Returns the object's animal type. See {@link Animals} for all possible animal types.
     *
     * @return the animal type of the piece
     * @since 1.0
     * @see Animals
     * @see #animal
     */
    public Animals getAnimal() {
        return this.animal;
    }

    /**
     * Returns the corresponding rank associated with the object's animal type. See {@link Animals}
     * for all possible animal types and its corresponding rank.
     *
     * @return the rank of the object.
     * @since 1.0
     * @see Animals
     * @see #animal
     */
    public int getRank() {
        return this.animal.rankNumber;
    }

    /**
     * Returns the current color set to the animal piece. See {@link Color} for all the possible
     * color representations
     *
     * @return the color set to the animal piece
     * @since 1.0
     * @see Color
     * @see #setColor(Color)
     * @see #color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the index of the player that has control of this animal piece
     *
     * @return the index of the controlling player
     * @since 1.0
     * @see #playerIndex
     */
    public int getPlayerIndex() {
        return this.playerIndex;
    }

    /**
     * Changes the color field of the object. See {@link Color} for all the possible
     * color representations
     *
     * @param color the new color that the piece would take
     * @since 1.0
     * @see Color
     * @see #getColor()
     * @see #color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
