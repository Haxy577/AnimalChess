package Resources;

/**
 * Represents all the possible tile/square in the board in the game "Animal Chess"
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.11 7/4/2026
 * @since 1.0
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 */
public enum Tiles {

    LAND, RIVER, TRAP, ANIMAL_DEN;

    public boolean isLandBased() {
        return this == LAND || this == TRAP || this == ANIMAL_DEN;
    }

    public boolean isWaterBased() {
        return this == RIVER;
    }
}
