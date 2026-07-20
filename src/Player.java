import java.awt.*;

/**
 * Represents an individual player.
 * <p>
 * Each player has a mutable field of name which differentiates it from other player objects. It also has
 * a mutable field of color which represents the foreground and background the player has chosen to represent their pieces.
 * Lastly, it also has a mutable field of index which represents the order players would take turns within the game.
 * </p>
 *
 * @see Color
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.2 7/20/2026
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
     * The desired color of the player to represent its pieces
     *
     * @since 2.2
     * @see java.awt.Color
     */
    private Color color;

    /**
     * Constructs the player with the specified name and color
     *
     * @param name the username of the player
     * @param color the color
     * @throws IllegalArgumentException if the specified name and/or color is null
     */
    public Player(String name, Color color) throws IllegalArgumentException {
        if (name == null || color == null)
            throw new IllegalArgumentException("The name and/or color cannot be null");

        this.name = name;
        this.color = color;
    }

    /**
     * Converts the fields of this class to a string representation
     *
     * @return the name and color chosen by the player
     *
     * @since 1.26
     * @see Color
     */
    @Override
    public String toString() {
        return "Player[name=" + name + ",color=" + color + "]";
    }

    /**
     * Compares this object with the specified object based on its name, color, and index
     *
     * @param obj   the reference object with which to compare.
     * @return {@code true} if the name, color, and index of the objects are the same, {@code false} otherwise
     *
     * @since 1.26
     * @see Color
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Player player))
            return false;

        return name.equals(player.getName()) && color.equals(player.getColor());
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
     * @return the color the player desired to represent its pieces
     *
     * @since 1.26
     * @see Color
     */
    public Color getColor() {
        return color;
    }


    /**
     * A setter method for the name field of this class
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
     * @see Color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
