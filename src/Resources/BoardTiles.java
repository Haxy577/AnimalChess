package Resources;

/**
 * Represents all the possible tile/square in the board in the game "Animal Chess"
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.6 6/11/2026
 * @since 1.0
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 */
public enum BoardTiles {

    LAND(0, 'L'),
    RIVER(1, 'R'),
    TRAP(2, 'T'),
    ANIMAL_DEN(3, 'A');

    public final int tileNumber;
    public final char patternChar;

    BoardTiles(int tileNumber, char patternChar) {
        this.tileNumber = tileNumber;
        this.patternChar = patternChar;
    }
}
