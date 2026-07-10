/**
 * Represents an individual player.
 * <p>
 * Each player has a mutable field of name which differentiates it from other player objects. It also has
 * a mutable field of color which represents the foreground and background the player has chosen to represent their pieces.
 * Lastly, it also has a mutable field of index which represents the order players would take turns within the game.
 * </p>
 *
 * @see PlayerColor
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public class Player {
    /**
     * The desired username of the player.
     *
     * @since 1.26
     */
    private String name;

    /**
     * The desired foreground and background colors of the player
     *
     * @since 1.26
     * @see PlayerColor
     */
    private PlayerColor color;

    /**
     * Represents the order players would take turns within the game
     *
     * @since 1.26
     */
    private int index;

    /**
     * Constructs a player object with the specified name, a default PlayerColor of red, and an index of 0
     *
     * @param name the desired username of the player
     *
     * @since 1.26
     */
    public Player(String name) {
        this(name, PlayerColor.RED);
    }

    /**
     * Constructs a player object with the specified name and PlayerColor, with an initial index of 0
     *
     * @param name the desired username of the player
     * @param ansiColor the desired PlayerColor of the player
     *
     * @since 1.26
     * @see PlayerColor
     */
    public Player(String name, PlayerColor ansiColor) {
        this(name, ansiColor, 0);
    }

    /**
     * Constructs a player object with the specified name, PlayerColor, and index
     *
     * @param name the desired username of the player
     * @param ansiColor the desired PlayerColor of the player
     * @param index the turn order of the player relative to other players
     *
     * @since 1.26
     * @see PlayerColor
     */
    public Player(String name, PlayerColor ansiColor, int index) {
        this.name = name;
        color = ansiColor;
        this.index = index;
    }

    /**
     * Swaps the current values contained within this object with the specified object
     *
     * @param player the other player object to swap with
     *
     * @since 1.26
     */
    public void swap(Player player) {
        String name = player.getName();
        PlayerColor color = player.getAnsiColor();
        int index = player.getIndex();

        player.setName(this.name);
        player.setAnsiColor(this.color);
        player.setIndex(this.index);

        this.name = name;
        this.color = color;
        this.index = index;
    }

    /**
     * Converts the fields of this class to a string representation
     *
     * @return the name, color, and index of the player
     *
     * @since 1.26
     * @see PlayerColor
     */
    @Override
    public String toString() {
        return "Player[name=" + name + ",color=" + color + ",index=" + index + "]";
    }

    /**
     * Compares this object with the specified object based on its name, color, and index
     *
     * @param obj   the reference object with which to compare.
     * @return {@code true} if the name, color, and index of the objects are the same, {@code false} otherwise
     *
     * @since 1.26
     * @see PlayerColor
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Player player))
            return false;

        return name.equals(player.getName()) && color.equals(player.getAnsiColor()) && index == player.getIndex();
    }

    /**
     * A getter method for the player's name
     *
     * @return the set name of the player
     *
     * @since 1.26
     */
    public String getName() {
        return name;
    }

    /**
     * A getter method for the player's color
     *
     * @return the PlayerColor the player desired
     *
     * @since 1.26
     * @see PlayerColor
     */
    public PlayerColor getAnsiColor() {
        return color;
    }

    /**
     * A getter method for the player's index
     *
     * @return the set index of the player
     *
     * @since 1.26
     */
    public int getIndex() {
        return index;
    }

    /**
     * A setter method for the index field of this class
     *
     * @param index the desired index to be set
     * @throws IllegalArgumentException if the specified index is neither 1 nor 2
     *
     * @since 1.26
     */
    public void setIndex(int index) throws IllegalArgumentException {
        if (index < 1 || index > 2)
            throw new IllegalArgumentException("The index can only be either 1 or 2");

        this.index = index;
    }

    /**
     * A setter method for the name field of this class
     *
     * @param name the desired name to be set
     *
     * @since 1.26
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A setter method for the color field of this class
     *
     * @param color the desired PlayerColor to be set
     *
     * @since 1.26
     * @see PlayerColor
     */
    public void setAnsiColor(PlayerColor color) {
        this.color = color;
    }
}
