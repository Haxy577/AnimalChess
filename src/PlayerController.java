import java.awt.*;

public class PlayerController implements ViewController{

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
        final int iconScale = AREA.height - DisplayConstants.ICON_PADDING * 2;

        renderer.fillBackground(DisplayConstants.BACKGROUND);
        renderer.roundRectangle(PLAYER.getColor(), DisplayConstants.ICON_PADDING, DisplayConstants.ICON_PADDING, iconScale, iconScale, DisplayConstants.ICON_ARC);

        final int nameX = AREA.height;
        final int nameY = (int) (AREA.height / DisplayConstants.TEXT_SCALE_RATIO);
        final int nameScale = (int) (iconScale / DisplayConstants.TEXT_SCALE_RATIO);

        Font font = new Font(DisplayConstants.TEXT_FONT, Font.PLAIN, nameScale);
        renderer.drawString(PLAYER.getName(), DisplayConstants.TEXT_COLOR, font, nameX, nameY);
    }
}
