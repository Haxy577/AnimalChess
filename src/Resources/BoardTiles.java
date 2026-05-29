package Resources;

/**
 * Represents all the possible tile/square in the board in the game "Animal Chess"
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.0
 * @since 1.0
 * @see <a href = "https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 */
public enum BoardTiles {

    LAND(0),
    RIVER(1),
    TRAP(2),
    ANIMAL_DEN(3);

    public final int tileNumber;

    BoardTiles(int tileNumber) {
        this.tileNumber = tileNumber;
    }
}
