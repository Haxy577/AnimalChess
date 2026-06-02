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
    DEN(3);

    public final int tileNumber;

    public static final String defaultBoardPattern = "LLLLLLLLLLLLRRRLLLTLLRRRLLTDTLLLLLTdTLLRRRLLTLLLRRRLLLLLLLLLLLL";
    public static final String defaultPiecePattern = "610081000000120072002100000000004200000031000000520000000000000000000000000051000000320000004100000000002200710011000000820062";
    public static final String defaultPattern = defaultBoardPattern + defaultPiecePattern;

    BoardTiles(int tileNumber) {
        this.tileNumber = tileNumber;
    }
}
