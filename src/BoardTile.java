/**
 * Represents the type of board tile a specific board cell has
 * <p>
 * This has an immutable field of TYPE which represents the type of tile this object would be. Furthermore,
 * it also has an immutable field of PLAYER which represents the player object that has ownership of this tile.
 * </p>
 *
 * @see Tiles
 * @see BoardCell
 * @see Player
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.2 7/20/2026
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
     * Represents what player has control over this tile. This field can either be
     * null or the Player object that has ownership of the tile. THIs field cannot be
     * changed once set.
     *
     * @since 2.2
     * @see Player
     */
    private final Player PLAYER;

    /**
     * Constructs the board tile with the specified Tile type with the player field set to null. Furthermore,
     * only the following tile types can be set this way: LAND, RIVER
     *
     * @param type the type of BoardTiles this tile would be
     * @throws IllegalArgumentException if the specified type is either a trap or an animal den
     *
     * @since 2.2
     * @see Tiles
     * @see Player
     */
    public BoardTile(Tiles type) {
        this(type, null);
    }

    /**
     * Constructs the object with the specified tile type and player object.
     *
     * @param type the type of BoardTiles this tile would be
     * @param player the player object that has ownership of this tile
     * @throws IllegalArgumentException if the specified tile is either a trap or an animal den
     * and the specified player object is null
     *
     * @since 2.2
     * @see Tiles
     * @see Player
     */
    public BoardTile(Tiles type, Player player) throws IllegalArgumentException {
        if ((type == Tiles.TRAP || type == Tiles.ANIMAL_DEN) && player == null)
            throw new IllegalArgumentException("The player object cannot be null if the tile type to be created is either a trap or animal den");

        TYPE = type;
        PLAYER = player;
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
     * A getter to get which player owns this tile
     *
     * @return the player object that has control of this tile
     *
     * @since 2.2
     * @see Player
     */
    public Player getPlayer() {
        return PLAYER;
    }

    /**
     * Converts the field details of this class to a string
     *
     * @return the type and player object of the tile
     *
     * @since 1.5
     * @see Tiles
     * @see Player#toString()
     */
    @Override
    public String toString() {
        return "Tile[type=" + TYPE + ",player=" + PLAYER + "]";
    }

    /**
     * Compares the specified object with the current object based on its type and player object it has
     * @param obj   the reference object with which to compare.
     * @return true if the fields of the objects are the same, false otherwise
     *
     * @since 2.2
     * @see Tiles
     * @see Player#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof BoardTile target))
            return false;

        if (PLAYER == null)
            return target.getType() == TYPE && target.getPlayer() == null;

        return target.getType() == TYPE && PLAYER.equals(target.getPlayer());
    }
}
