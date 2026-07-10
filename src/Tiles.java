/**
 * Represents all the possible tile/square in the board in the game "Animal Chess"
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.0
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 */
public enum Tiles {

    LAND, RIVER, TRAP, ANIMAL_DEN;

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
