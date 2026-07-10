/**
 * Contains various ansi escape codes represented as static final string fields
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public class Ansi {
    /**
     * Contains the ansi escape code for resetting all styles back to the default
     * @since 1.26
     */
    public static final String RESET = "\u001B[0m";

    /**
     * Contains the ansi escape code for adding the bold text style to characters
     * @since 1.26
     */
    public static final String BOLD = "\u001B[1m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to the color black
     * @since 1.26
     */
    public static final String BLACK = "\u001B[30m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to the color red
     * @since 1.26
     */
    public static final String RED = "\u001B[31m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to the color green
     * @since 1.26
     */
    public static final String GREEN = "\u001B[32m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to the color yellow
     * @since 1.26
     */
    public static final String YELLOW = "\u001B[33m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to the color blue
     * @since 1.26
     */
    public static final String BLUE = "\u001B[34m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to the color magenta
     * @since 1.26
     */
    public static final String MAGENTA = "\u001B[35m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to the color cyan
     * @since 1.26
     */
    public static final String CYAN = "\u001B[36m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to the color white
     * @since 1.26
     */
    public static final String WHITE = "\u001B[37m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text back to the default color
     * @since 1.26
     */
    public static final String DEFAULT = "\u001B[39m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the color black
     * @since 1.26
     */
    public static final String BG_BLACK = "\u001B[40m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the color red
     * @since 1.26
     */
    public static final String BG_RED = "\u001B[41m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the color green
     * @since 1.26
     */
    public static final String BG_GREEN = "\u001B[42m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the color yellow
     * @since 1.26
     */
    public static final String BG_YELLOW = "\u001B[43m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the color blue
     * @since 1.26
     */
    public static final String BG_BLUE = "\u001B[44m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the color magenta
     * @since 1.26
     */
    public static final String BG_MAGENTA = "\u001B[45m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the color cyan
     * @since 1.26
     */
    public static final String BG_CYAN = "\u001B[46m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the color white
     * @since 1.26
     */
    public static final String BG_WHITE = "\u001B[47m";

    /**
     * Contains the ansi escape code for changing the background color of the text to the default color
     * @since 1.26
     */
    public static final String BG_DEFAULT = "\u001B[49m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to a brighter color of black
     * @since 1.26
     */
    public static final String BRIGHT_BLACK = "\u001B[90m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to a brighter color of red
     * @since 1.26
     */
    public static final String BRIGHT_RED = "\u001B[91m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to a brighter color of green
     * @since 1.26
     */
    public static final String BRIGHT_GREEN = "\u001B[92m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to a brighter color of yellow
     * @since 1.26
     */
    public static final String BRIGHT_YELLOW = "\u001B[93m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to a brighter color of blue
     * @since 1.26
     */
    public static final String BRIGHT_BLUE = "\u001B[94m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to a brighter color of magenta
     * @since 1.26
     */
    public static final String BRIGHT_MAGENTA = "\u001B[95m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to a brighter color of cyan
     * @since 1.26
     */
    public static final String BRIGHT_CYAN = "\u001B[96m";

    /**
     * Contains the ansi escape code for changing the foreground color of the text to a brighter color of white
     * @since 1.26
     */
    public static final String BRIGHT_WHITE = "\u001B[97m";

    /**
     * Contains the ansi escape code for changing the background color of the text to a brighter color of black
     * @since 1.26
     */
    public static final String BRIGHT_BG_BLACK = "\u001B[100m";

    /**
     * Contains the ansi escape code for changing the background color of the text to a brighter color of red
     * @since 1.26
     */
    public static final String BRIGHT_BG_RED = "\u001B[101m";

    /**
     * Contains the ansi escape code for changing the background color of the text to a brighter color of green
     * @since 1.26
     */
    public static final String BRIGHT_BG_GREEN = "\u001B[102m";

    /**
     * Contains the ansi escape code for changing the background color of the text to a brighter color of yellow
     * @since 1.26
     */
    public static final String BRIGHT_BG_YELLOW = "\u001B[103m";

    /**
     * Contains the ansi escape code for changing the background color of the text to a brighter color of blue
     * @since 1.26
     */
    public static final String BRIGHT_BG_BLUE = "\u001B[104m";

    /**
     * Contains the ansi escape code for changing the background color of the text to a brighter color of magenta
     * @since 1.26
     */
    public static final String BRIGHT_BG_MAGENTA = "\u001B[105m";

    /**
     * Contains the ansi escape code for changing the background color of the text to a brighter color of cyan
     * @since 1.26
     */
    public static final String BRIGHT_BG_CYAN = "\u001B[106m";

    /**
     * Contains the ansi escape code for changing the background color of the text to a brighter color of white
     * @since 1.26
     */
    public static final String BRIGHT_BG_WHITE = "\u001B[107m";
}
