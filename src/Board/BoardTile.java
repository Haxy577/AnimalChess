package Board;

import Resources.BoardTiles;

/**
 * @author Richmond Jase Von M. Salvador
 * @version 1.6 6/11/2026
 * @since 1.1
 */
public class BoardTile {

    /**
     * Represents the type of the board tile set. This field cannot be changed once set.
     *
     * @since 1.1
     * @see BoardTiles
     */
    private final BoardTiles type;

    /**
     * Represents what player has control over this tile. This field can only have 3 possible values:
     * 1 represents player 1, 2 represents player 2, -1 represents that no player has control over this
     * tile. This field cannot be changed once set
     *
     * @since 1.1
     */
    private final int playerIndex;

    /**
     * Initializes the designated type of this board tile. {@link BoardTiles} contains all
     * possible types. Also initializes the player index to be -1.
     *
     * @param type the type of BoardTiles this tile would be
     *
     * @since 1.2
     * @see BoardTiles
     */
    public BoardTile(BoardTiles type) {
        this.type = type;
        this.playerIndex = -1;
    }

    /**
     * Initializes the designated type of this board tile and the player index.
     * {@link BoardTiles} contains all possible types.
     *
     * @param type the type of BoardTiles this tile would be
     * @throws IllegalArgumentException if the player index does not equal to -1, 1, or 2
     *
     * @since 1.1
     * @see BoardTiles
     */
    public BoardTile(BoardTiles type, int playerIndex) throws IllegalArgumentException {
        this.type = type;

        if (playerIndex == -1 || playerIndex == 1 || playerIndex == 2)
            this.playerIndex = playerIndex;
        else
            throw new IllegalArgumentException("Invalid player index. Can only be: -1, 1, or 2");
    }

    /**
     * A getter for the type field
     *
     * @return the type of BoardTiles this tile is
     *
     * @since 1.1
     * @see BoardTiles
     */
    public BoardTiles getType() {
        return this.type;
    }

    /**
     * A getter for the playerIndex field
     *
     * @return the index of the player that has control of this tile
     *
     * @since 1.1
     */
    public int getPlayerIndex() {
        return this.playerIndex;
    }

    /**
     * Converts the field details of this class to a string
     *
     * @return the type and player index of the tile
     *
     * @since 1.5
     * @see BoardTiles
     */
    @Override
    public String toString() {
        return "Tile=" + this.type + "(" + this.playerIndex + ")";
    }
}
