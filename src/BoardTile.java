/**
 * Represents the type of board tile a specific board cell has and the index of the player
 * that has ownership of this specific tile.
 *
 * @see Tiles
 * @see BoardCell
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.11 7/1/2026
 * @since 1.1
 */
public class BoardTile {

    /**
     * Represents the type of the board tile set. This field cannot be changed once set.
     *
     * @since 1.1
     * @see Tiles
     */
    private final Tiles TYPE;

    /**
     * Represents what player has control over this tile. This field can only have 3 possible values:
     * 1 represents player 1, 2 represents player 2, -1 represents that no player has control over this
     * tile. This field cannot be changed once set
     *
     * @since 1.1
     */
    private final int PLAYER_INDEX;

    /**
     * Initializes the designated type of this board tile. {@link Tiles} contains all
     * possible types. Also initializes the player index to be -1.
     *
     * @param type the type of BoardTiles this tile would be
     * @throws IllegalArgumentException if the specified type is either a trap or an animal den
     *
     * @since 1.2
     * @see Tiles
     */
    public BoardTile(Tiles type) {
        if (type == Tiles.TRAP || type == Tiles.ANIMAL_DEN)
            throw new IllegalArgumentException("The specified board tile must have a player index");

        this.TYPE = type;
        this.PLAYER_INDEX = -1;
    }

    /**
     * Initializes the designated type of this board tile and the player index.
     * {@link Tiles} contains all possible types.
     *
     * @param type the type of BoardTiles this tile would be
     * @throws IllegalArgumentException if the player index does not equal to -1, 1, or 2
     *
     * @since 1.1
     * @see Tiles
     */
    public BoardTile(Tiles type, int playerIndex) throws IllegalArgumentException {
        TYPE = type;

        if (playerIndex == -1 || playerIndex == 1 || playerIndex == 2)
            PLAYER_INDEX = playerIndex;
        else
            throw new IllegalArgumentException("Invalid player index. Can only be: -1, 1, or 2");
    }

    /**
     * A getter for the type field
     *
     * @return the type of BoardTiles this tile is
     *
     * @since 1.1
     * @see Tiles
     */
    public Tiles getType() {
        return TYPE;
    }

    /**
     * A getter for the playerIndex field
     *
     * @return the index of the player that has control of this tile
     *
     * @since 1.1
     */
    public int getPlayerIndex() {
        return PLAYER_INDEX;
    }

    /**
     * Converts the field details of this class to a string
     *
     * @return the type and player index of the tile
     *
     * @since 1.5
     * @see Tiles
     */
    @Override
    public String toString() {
        return "Tile[type=" + TYPE + ",player=" + PLAYER_INDEX + "]";
    }

    /**
     * Compares the specified object with the current object based on its type and player index it has
     * @param obj   the reference object with which to compare.
     * @return true if the fields of the objects are the same, false otherwise
     *
     * @since 1.11
     * @see Tiles
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof BoardTile target))
            return false;

        return target.getType() == TYPE && target.getPlayerIndex() == PLAYER_INDEX;
    }
}
