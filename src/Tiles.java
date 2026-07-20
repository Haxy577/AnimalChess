import java.awt.*;

/**
 * Represents all the possible tile/square in the board in the game "Animal Chess" and its corresponding
 * color to be displayed
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.3 7/20/2026
 * @since 1.0
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 */
public enum Tiles {

    LAND(new Color(0, 153, 69)),
    RIVER(new Color(64, 107, 166)),
    TRAP(new Color(159, 67, 67)),
    ANIMAL_DEN(null);

    /**
     * The assigned color to the specific type of tile
     *
     * @since 2.1
     * @see Color
     */
    public final Color COLOR;

    /**
     * Constructs each enum value with its color value
     * @param tileColor the color of the tile
     *
     * @since 2.1
     * @see Color
     */
    Tiles(Color tileColor) {
        COLOR = tileColor;
    }

    /**
     * Checks if the current object has the Tiles value of either LAND, TRAP, or an ANIMAL_DEN
     *
     * @return {@code true} if this object is considered as a land based tile, {@code false} otherwise
     *
     * @since 1.26
     */
    public boolean isLandBased() {
        return this == LAND || this == TRAP || this == ANIMAL_DEN;
    }

    /**
     * Checks if the current object has the Tiles value of RIVER
     *
     * @return {@code true} if this object is considered as water based tile, {@code false} otherwise
     *
     * @since 1.26
     */
    public boolean isWaterBased() {
        return this == RIVER;
    }
}
