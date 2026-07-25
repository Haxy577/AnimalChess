import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that would contain the visual representation of the player object
 *
 * @see Player
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.3
 * @since 2.1
 */
public class DisplayPlayer extends JPanel {
    private final Controller CONTROL;
    private final Dimension DIMENSION;
    private final int INDEX;


    public DisplayPlayer(Dimension dimension, Controller controller, int index) throws IllegalArgumentException {
        if (dimension == null || controller == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        CONTROL = controller;
        DIMENSION = dimension;
        INDEX = index;

        setPreferredSize(dimension);
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        final int padding = 7;
        final int iconScale = DIMENSION.height - padding * 2;

        g2d.setColor(CONTROL.getPlayerColor(INDEX));
        g2d.fillRoundRect(padding, padding, iconScale, iconScale, 10, 10);

        final int nameX = DIMENSION.height;
        final int nameY = (int) (DIMENSION.height / 2.5);
        final int nameScale = (int) (iconScale / 2.5);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, nameScale));
        g2d.drawString(CONTROL.getPlayerName(INDEX), nameX, nameY);
    }
}