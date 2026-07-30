import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that would contain the visual representation of a player which would display its color and username
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.7 7/29/2026
 * @since 2.1
 */
public class DisplayPlayer extends JPanel {

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
     * Contains the controller which would provide the details to be displayed from the model
     *
     * @since 2.7
     * @see GameController
     */
    private final GameController CONTROL;

    /**
     * The width and height of this component
     *
     * @since 2.1
     */
    private final Dimension DIMENSION;

    /**
     * Represents which player to display. Player 1 is represented by 1 and Player 2 is represented by 2
     *
     * @since 2.1
     */
    private final int INDEX;

    /**
     * Constructs this component with the specified dimension, the controller to retrieve the details from the model, and
     * the index of which player to display
     *
     * @param dimension the width and height of this component
     * @param controller the source of the data to be displayed
     * @param index the index of the player to be displayed, this can only be 1 or 2
     * @throws NullPointerException if the specified object parameters are null
     * @throws IllegalArgumentException if the given dimension contain negative numbers or the index is neither 1 nor 2
     *
     * @since 2.7
     * @see GameController
     */
    public DisplayPlayer(Dimension dimension, GameController controller, int index) {
        if (dimension == null || controller == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        if (index < 1 || index > 2)
            throw new IllegalArgumentException("The specified index can only be either 1 or 2");

        CONTROL = controller;
        DIMENSION = dimension;
        INDEX = index;

        setPreferredSize(dimension);
        setBackground(BACKGROUND);
    }

    /**
     * Draws the icon color of the player and the username beside the drawn icon
     *
     * @param g the <code>Graphics</code> object to protect
     *
     * @since 2.7
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        final int iconScale = DIMENSION.height - ICON_PADDING * 2;

        g2d.setColor(CONTROL.getPlayerColor(INDEX));
        g2d.fillRoundRect(ICON_PADDING, ICON_PADDING, iconScale, iconScale, ICON_ARC, ICON_ARC);

        final int nameX = DIMENSION.height;
        final int nameY = (int) (DIMENSION.height / TEXT_SCALE_RATIO);
        final int nameScale = (int) (iconScale / TEXT_SCALE_RATIO);

        g2d.setColor(TEXT_COLOR);
        g2d.setFont(new Font(TEXT_FONT, Font.PLAIN, nameScale));
        g2d.drawString(CONTROL.getPlayerName(INDEX), nameX, nameY);
    }
}