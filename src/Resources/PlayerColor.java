package Resources;

/**
 * Upgraded to Bright (High-Intensity) text colors for maximum contrast.
 */
public enum PlayerColor {
    RED("\u001B[1;91m", "\u001B[41m"),
    YELLOW("\u001B[1;93m", "\u001B[43m"),
    MAGENTA("\u001B[1;95m", "\u001B[45m"),
    CYAN("\u001B[1;96m", "\u001B[46m"),
    WHITE("\u001B[1;97m", "\u001B[47m");

    public final String textCode;
    public final String bgCode;

    PlayerColor(String textCode, String bgCode) {
        this.textCode = textCode;
        this.bgCode = bgCode;
    }
}
