/**
 * Represents the possible colors a player can contain, each color has two representative ansi escape code which
 * contains the foreground and background code.
 *
 * @see Ansi
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public enum PlayerColor {
    RED(Ansi.BRIGHT_RED, Ansi.BG_RED),
    GREEN(Ansi.BRIGHT_GREEN, Ansi.BG_GREEN),
    YELLOW(Ansi.BRIGHT_YELLOW, Ansi.BG_YELLOW),
    BLUE(Ansi.BRIGHT_BLUE, Ansi.BG_BLUE),
    MAGENTA(Ansi.BRIGHT_MAGENTA, Ansi.BG_MAGENTA),
    CYAN(Ansi.BRIGHT_CYAN, Ansi.BG_CYAN),
    WHITE(Ansi.BRIGHT_WHITE, Ansi.BG_WHITE);

    /**
     * The ansi code representing the desired foreground/text color
     *
     * @since 1.26
     * @see Ansi
     */
    private final String textCode;

    /**
     * The ansi code representing the desired background color
     *
     * @since 1.26
     * @see Ansi
     */
    private final String bgCode;

    /**
     * Constructs each enum value with its respective ansi escape code for its foreground and background color
     *
     * @param textCode the ansi escape code for the foreground color
     * @param bgCode the ansi escape code for the background color
     *
     * @since 1.26
     * @see Ansi
     */
    PlayerColor(String textCode, String bgCode) {
        this.textCode = textCode;
        this.bgCode = bgCode;
    }

    /**
     * A getter method for the object's textCode field
     *
     * @return the ansi escape code for the foreground color
     *
     * @since 1.26
     * @see Ansi
     */
    public String getTextCode() {
        return textCode;
    }

    /**
     * A getter method for the object's bgCode field
     *
     * @return the ansi escape code for the background color
     *
     * @since 1.26
     * @see Ansi
     */
    public String getBgCode() {
        return bgCode;
    }
}
