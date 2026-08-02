import java.awt.*;

/**
 * Contains constants to be used for the display
 *
 * @author Richmond Jase Von M. Salvador
 * @version 3.0 8/2/2026
 * @since 3.0
 */
public class DisplayConstants {
    /**
     * Represents the background color of this component
     *
     * @since 2.7
     */
    public static final Color BACKGROUND = Color.DARK_GRAY;

    /**
     * Represents the color of the grid lines to be drawn
     *
     * @since 2.7
     */
    public static final Color GRID_COLOR = Color.DARK_GRAY;

    /**
     * Represents the color the square to be drawn would take which represents the current position of
     * the cursor relative to the grid
     *
     * @since 2.7
     */
    public static final Color HOVER_COLOR = Color.GRAY;

    /**
     * Represents the color the square to be drawn would take which represents the current position of
     * the selected cell
     *
     * @since 2.7
     */
    public static final Color HIGHLIGHT_COLOR = Color.LIGHT_GRAY;

    /**
     * Represents the thickness of the grid lines
     *
     * @since 2.7
     */
    public static final int GRID_LINE_THICKNESS = 2;

    /**
     * Represents the color to be displayed as a background for icons/text to be more legible
     *
     * @since 2.7
     */
    public static final Color NEUTRAL_BACKGROUND = new Color(200, 170, 143);

    /**
     * Represents the color to be displayed as a border/outline for the piece to be drawn
     *
     * @since 2.7
     */
    public static final Color PIECE_OUTLINE_COLOR = Color.DARK_GRAY;

    /**
     * Represents the thickness of the border/outline of the piece to be drawn
     *
     * @since 2.7
     */
    public static final int PIECE_OUTLINE_THICKNESS = 1;

    /**
     * Represents the scale of each piece relative to the scale of a cell
     *
     * @since 2.7
     */
    public static final double PIECE_SCALE_RATIO = .60;

    /**
     * Represents the scale of a circle inside the piece that would contain the piece's icon and rank
     *
     * @since 2.7
     */
    public static final double PIECE_BACKGROUND_RATIO = .75;

    /**
     * Represents the scale of the numerical rank to be displayed besides the icon relative to the {@link #PIECE_BACKGROUND_RATIO}
     *
     * @since 2.7
     */
    public static final double PIECE_RANK_RATIO = .25;

    /**
     * Represents the font the numerical rank to be drawn would take
     *
     * @since 2.7
     */
    public static final String PIECE_RANK_FONT = "Arial";

    /**
     * Represents the color the integer rank string would take
     *
     * @since 2.7
     */
    public static final Color PIECE_RANK_COLOR = Color.BLACK;

    /**
     * Represents the color to be displayed when drawing where pieces can move
     *
     * @since 2.7
     */
    public static final Color VALID_MOVE_COLOR = Color.GRAY;

    /**
     * Represents the scale of a filled circle that visually indicates a possible move of the player relative
     * to the scale of each cell
     *
     * @since 2.7
     */
    public static final double VALID_MOVE_DOT_RATIO = .25;

    /**
     * Represents the scale of a circle that would surround a piece if it can be captured, its scale is relative to
     * the scale of each cell
     *
     * @since 2.7
     */
    public static final double VALID_CAPTURE_RING_RATIO = .80;

    /**
     * Represents the thickness of the circle to be displayed when a piece can be captured
     *
     * @since 2.7
     */
    public static final int CAPTURE_RING_THICKNESS = 4;

    /**
     * Represents the scale of a filled square to be drawn inside a tile
     *
     * @since 2.7
     */
    public static final double TILE_BACKGROUND_RATIO = .80;

    /**
     * Represents the color of the text to be drawn
     *
     * @since 2.7
     */
    public static final Color TEXT_COLOR = Color.WHITE;

    /**
     * Represents the font the text would take
     *
     * @since 2.7
     */
    public static final String TEXT_FONT = "Arial";

    /**
     * Represents the scale the text would take relative to the size of the component
     *
     * @since 2.7
     */
    public static final double TEXT_SCALE_RATIO = 2.5;

    /**
     * Represents the padding of the icon to be drawn
     *
     * @since 2.7
     */
    public static final int ICON_PADDING = 7;

    /**
     * Represents the arc of the corners of the icon
     *
     * @since 2.7
     */
    public static final int ICON_ARC = 10;

    /**
     * Represents the scale of the results display relative to the size of the component
     *
     * @since 3.0
     */
    public static final double RESULTS_RATIO = 3.0 / 4.0;

    /**
     * Represents the scale of the text within the results display
     *
     * @since 3.0
     */
    public static final double RESULTS_TEXT_RATIO = 1.0 / 3.0;

    /**
     * Represents the scale of the player icons
     *
     * @since 3.0
     */
    public static final double ICON_RATIO = 0.60;

    /**
     * Represents the y position of the icon relative to the height of the component
     *
     * @since 3.0
     */
    public static final double ICON_HEIGHT_RATIO = 1.0 / 3.0;

    /**
     * Represents the color of the crown indicating which player won
     *
     * @since 3.0
     */
    public static final Color CROWN_COLOR = Color.YELLOW;

    /**
     * Represents the scale of the buttons display relative to the size of the component
     *
     * @since 3.0
     */
    public static final double BUTTONS_RATIO = 1.0 / 4.0;
    
}
