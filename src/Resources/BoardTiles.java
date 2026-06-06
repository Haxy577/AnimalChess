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

    LAND(0, 'L'),
    RIVER(1, 'R'),
    TRAP(2, 'T'),
    ANIMAL_DEN(3, 'A');

    public final int tileNumber;
    public final char patternChar;

//    public static final String defaultBoardPattern = "LLLLLLLLLLLLRRRLLLTLLRRRLLTDTLLLLLTdTLLRRRLLTLLLRRRLLLLLLLLLLLL";
//    public static final String defaultPiecePattern = "610081000000120072002100000000004200000031000000520000000000000000000000000051000000320000004100000000002200710011000000820062";
//   public static final String defaultPattern = defaultBoardPattern + defaultPiecePattern;

    /*
    LLLLLLLLL -> 9L
    LLLRRRLLL -> 3L3R3L
    TLLRRRLLT -> T2L3R2LT
    DTLLLLLTd -> DT5LTd
    TLLRRRLLT -> T2L3R2LT
    LLLRRRLLL -> 3L3R3L
    LLLLLLLLL -> 9L

    LLLLLLLLL -> 12L3R3LT2L3R2LTDT5LTdT2L3R2LT3L3R12L
    LLLRRRLLL ->
    TLLRRRLLT ->
    DTLLLLLTd ->
    TLLRRRLLT ->
    LLLRRRLLL ->
    LLLLLLLLL ->

    G-E---m-n -> G1E3m1n1C5d3W3p13P3W3D5c1N1M3e1g
    -C-----d- ->
    --W---p-- ->
    --------- ->
    --P---w-- ->
    -D-----c- ->
    N-M---e-g ->

    Rules for board pattern:
    1. The prefix number before the letter indicate the amount of that tile.

    9L3L3R3LT2L3R2LTDT5LTdT2L3R2LT3L3R3L9L
    12L3R3LT2L3R2LTDT5LTdT2L3R2LT3L3R12L|G1E3m1n1C5d3W3p13P3W3D5c1N1M3e1g
    610081000000120072002100000000004200000031000000520000000000000000000000000051000000320000004100000000002200710011000000820062LLLLLLLLLLLRRRLLLTLLRRRLLTDTLLLLLTdTLLRRRLLTLLLRRRLLLLLLLLLLLL
     */

    public static final String defaultBoardPattern = "LLLLLLLLLLLLRRRLLLTLLRRRLLTDTLLLLLTdTLLRRRLLTLLLRRRLLLLLLLLLLLL";
    public static final String defaultPiecePattern = "610081000000120072002100000000004200000031000000520000000000000000000000000051000000320000004100000000002200710011000000820062";
    public static final String defaultPattern = defaultBoardPattern + defaultPiecePattern;

    BoardTiles(int tileNumber, char patternChar) {
        this.tileNumber = tileNumber;
        this.patternChar = patternChar;
    }
}
