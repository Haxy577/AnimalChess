/**
 * Upgraded to Bright (High-Intensity) text colors for maximum contrast.
 */
public enum PlayerColor {
    RED(Ansi.BRIGHT_RED, Ansi.BG_RED),
    GREEN(Ansi.BRIGHT_GREEN, Ansi.BG_GREEN),
    YELLOW(Ansi.BRIGHT_YELLOW, Ansi.BG_YELLOW),
    BLUE(Ansi.BRIGHT_BLUE, Ansi.BG_BLUE),
    MAGENTA(Ansi.BRIGHT_MAGENTA, Ansi.MAGENTA),
    CYAN(Ansi.BRIGHT_CYAN, Ansi.BG_CYAN),
    WHITE(Ansi.BRIGHT_WHITE, Ansi.BG_WHITE);

    private final String textCode;
    private final String bgCode;

    PlayerColor(String textCode, String bgCode) {
        this.textCode = textCode;
        this.bgCode = bgCode;
    }

    public String getTextCode() {
        return textCode;
    }

    public String getBgCode() {
        return bgCode;
    }
}
