package AnimalPieces;

import Resources.Animals;
import Resources.BoardTiles;

import java.awt.*;

/**
 * Represents an individual animal game piece on the board.
 * <p>
 * Each piece has an immutable {@link Animals} type which determines its animal type and rank,
 * an immutable {@link #playerIndex} which determines the index of the player controlling the piece,
 * a mutable {@link BoardTiles} position which describes the tile the piece is currently on, and
 * a mutable {@link Color} property that describes the color of the piece to be displayed.
 * </p>
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.0
 * @since 1.0
 * @see <a href = "https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see Animals
 * @see BoardTiles
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
     * The {@link BoardTiles} the animal piece is currently on
     *
     * @since 1.0
     * @see BoardTiles
     * @see #getTile()
     * @see #setTile(BoardTiles)
     */
    private BoardTiles tile;

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
     * Creates a new animal piece with a specified animal type, rank, starting tile,
     * and the player controlling this animal piece
     *
     * @param animal the type and rank of the animal piece
     * @param tile the starting {@link BoardTiles} type of the animal piece
     * @param playerIndex the index of the player controlling this animal piece
     * @throws IllegalArgumentException if there is no provided {@link #animal}, {@link #tile},
     * or the {@link #playerIndex} is a value less than 1.
     *
     * @since 1.0
     * @see Animals
     * @see BoardTiles
     * @see #animal
     * @see #tile
     * @see #playerIndex
     */
    AnimalPiece(Animals animal, BoardTiles tile, int playerIndex) throws IllegalArgumentException {
        if (animal == null)
            throw new IllegalArgumentException("The animal piece must have an animal type and rank");
        if (tile == null)
            throw new IllegalArgumentException("The animal piece must have a starting tile");
        if (playerIndex < 1)
            throw new IllegalArgumentException("The index of the player must be greater than or equal to 1");

        this.animal = animal;
        this.tile = tile;
        this.playerIndex = playerIndex;
    }

    /**
     * Creates a new animal piece with a specified animal type, rank, starting tile,
     * the player controlling this animal piece, and the color of the animal piece
     *
     * @param animal the type and rank of the animal piece
     * @param tile the starting {@link BoardTiles} type of the animal piece
     * @param playerIndex the index of the player controlling this animal piece
     * @param color determines the {@link Color} of the piece to be displayed
     * @throws IllegalArgumentException if there is no provided {@link #animal}, {@link #tile}, {@link #color},
     * or the {@link #playerIndex} is a value less than 1.
     *
     * @since 1.0
     * @see Animals
     * @see BoardTiles
     * @see Color
     * @see #animal
     * @see #tile
     * @see #playerIndex
     * @see #color
     */
    AnimalPiece(Animals animal, BoardTiles tile, int playerIndex, Color color) throws IllegalArgumentException {
        this(animal, tile, playerIndex);

        if (color == null)
            throw new IllegalArgumentException("The piece must have a color");

        this.color = color;
    }

    /**
     * Determines whether the piece is eligible to capture the desired
     * enemy piece. Its eligibility is determined by the following conditions:
     * <ul>
     * <li>The enemy piece's rank is lower or equal to the capturing piece's rank</li>
     * <li>Whenever an enemy piece is on a {@link BoardTiles#TRAP}</li>
     * </ul>
     *
     * @param enemy the enemy animal piece to be captured.
     * @return {@code true} if the piece can be captured, {@code false} otherwise.
     * @since 1.0
     * @see BoardTiles
     * @see #tile
     */
    public boolean canCapture(AnimalPiece enemy) {
        return enemy.getTile() == BoardTiles.TRAP || enemy.getRank() <= this.getRank();
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
     * Returns the current board tile the animal piece is currently on. See {@link BoardTiles} for all
     * possible board tiles.
     *
     * @return the current board tile the animal piece is currently on.
     * @since 1.0
     * @see BoardTiles
     * @see #setTile(BoardTiles)
     * @see #tile
     */
    public BoardTiles getTile() {
        return this.tile;
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
     * Changes the tile field of the object. See {@link BoardTiles} for all
     * possible board tiles.
     *
     * @param tile the new tile the animal piece would be on after it moved.
     * @since 1.0
     * @see BoardTiles
     * @see #getTile()
     * @see #tile
     */
    public void setTile(BoardTiles tile) {
        this.tile = tile;
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
