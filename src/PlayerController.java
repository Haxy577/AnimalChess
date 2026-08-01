import java.awt.*;

public class PlayerController implements ViewController{
    /**
     * Represents the background color of this component
     *
     * @since 2.7
     */
    private static final Color BACKGROUND = Color.DARK_GRAY;

    /**
     * Represents the color of the text to be drawn
     *
     * @since 2.7
     */
    private static final Color TEXT_COLOR = Color.WHITE;

    /**
     * Represents the font the text would take
     *
     * @since 2.7
     */
    private static final String TEXT_FONT = "Arial";

    /**
     * Represents the scale the text would take relative to the size of the component
     *
     * @since 2.7
     */
    private static final double TEXT_SCALE_RATIO = 2.5;

    /**
     * Represents the padding of the icon to be drawn
     *
     * @since 2.7
     */
    private static final int ICON_PADDING = 7;

    /**
     * Represents the arc of the corners of the icon
     *
     * @since 2.7
     */
    private static final int ICON_ARC = 10;

    /**
     * The width and height of this component
     *
     * @since 2.1
     */
    private final Dimension AREA;

    private final Player PLAYER;

    /**
     * Constructs this component with the specified dimension, the controller to retrieve the details from the model, and
     * the index of which player to display
     *
     * @param dimension the width and height of this component
     *
     * @since 2.7
     */
    public PlayerController(Dimension dimension, Player player) {
        if (dimension == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        AREA = dimension;
        PLAYER = player;
    }

    @Override
    public void render(Renderer renderer) {
        final int iconScale = AREA.height - ICON_PADDING * 2;

        renderer.fillBackground(BACKGROUND);
        renderer.roundRectangle(PLAYER.getColor(), ICON_PADDING, ICON_PADDING, iconScale, iconScale, ICON_ARC);

        final int nameX = AREA.height;
        final int nameY = (int) (AREA.height / TEXT_SCALE_RATIO);
        final int nameScale = (int) (iconScale / TEXT_SCALE_RATIO);

        Font font = new Font(TEXT_FONT, Font.PLAIN, nameScale);
        renderer.drawString(PLAYER.getName(), TEXT_COLOR, font, nameX, nameY);
    }
}
