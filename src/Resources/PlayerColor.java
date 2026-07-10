package Resources;

/**
 * Upgraded to Bright (High-Intensity) text colors for maximum contrast.
 */
public enum PlayerColor {
    RED(Ansi.RED, Ansi.BG_RED),
    GREEN(Ansi.GREEN, Ansi.BG_GREEN),
    YELLOW(Ansi.YELLOW, Ansi.BG_YELLOW),
    BLUE(Ansi.BLUE, Ansi.BG_BLUE),
    MAGENTA(Ansi.MAGENTA, Ansi.MAGENTA),
    CYAN(Ansi.CYAN, Ansi.BG_CYAN),
    WHITE(Ansi.WHITE, Ansi.BG_WHITE);

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
