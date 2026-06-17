package Board;

import Resources.BOARD_TILES;

/**
 * Represents the type of board tile a specific board cell has and the index of the player
 * that has ownership of this specific tile.
 *
 * @see BOARD_TILES
 * @see BoardCell
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.8 6/17/2026
 * @since 1.1
 */
public class BoardTile {

    /**
     * Represents the type of the board tile set. This field cannot be changed once set.
     *
     * @since 1.1
     * @see BOARD_TILES
     */
    private final BOARD_TILES TYPE;

    /**
     * Represents what player has control over this tile. This field can only have 3 possible values:
     * 1 represents player 1, 2 represents player 2, -1 represents that no player has control over this
     * tile. This field cannot be changed once set
     *
     * @since 1.1
     */
    private final int PLAYER_INDEX;

    /**
     * Initializes the designated type of this board tile. {@link BOARD_TILES} contains all
     * possible types. Also initializes the player index to be -1.
     *
     * @param type the type of BoardTiles this tile would be
     *
     * @since 1.2
     * @see BOARD_TILES
     */
    public BoardTile(BOARD_TILES type) {
        this.TYPE = type;
        this.PLAYER_INDEX = -1;
    }

    /**
     * Initializes the designated type of this board tile and the player index.
     * {@link BOARD_TILES} contains all possible types.
     *
     * @param type the type of BoardTiles this tile would be
     * @throws IllegalArgumentException if the player index does not equal to -1, 1, or 2
     *
     * @since 1.1
     * @see BOARD_TILES
     */
    public BoardTile(BOARD_TILES type, int playerIndex) throws IllegalArgumentException {
        this.TYPE = type;

        if (playerIndex == -1 || playerIndex == 1 || playerIndex == 2)
            this.PLAYER_INDEX = playerIndex;
        else
            throw new IllegalArgumentException("Invalid player index. Can only be: -1, 1, or 2");
    }

    /**
     * A getter for the type field
     *
     * @return the type of BoardTiles this tile is
     *
     * @since 1.1
     * @see BOARD_TILES
     */
    public BOARD_TILES getTYPE() {
        return this.TYPE;
    }

    /**
     * A getter for the playerIndex field
     *
     * @return the index of the player that has control of this tile
     *
     * @since 1.1
     */
    public int getPLAYER_INDEX() {
        return this.PLAYER_INDEX;
    }

    /**
     * Converts the field details of this class to a string
     *
     * @return the type and player index of the tile
     *
     * @since 1.5
     * @see BOARD_TILES
     */
    @Override
    public String toString() {
        return "Tile=" + this.TYPE + "(" + this.PLAYER_INDEX + ")";
    }
}
