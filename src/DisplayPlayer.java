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
    private final Player PLAYER;
    private final Dimension DIMENSION;
    private final AssetsManager ASSETS;

    /**
     * Constructs the object with the specified dimension and the player object to be displayed
     *
     * @param dimension the width and height of the player display
     * @param player the player object to be displayed
     * @throws IllegalArgumentException if the specified parameters are null or the specified
     * dimension contains negative integers
     *
     * @since 2.1
     * @see Player
     */
    public DisplayPlayer(AssetsManager assets, Dimension dimension, Player player) throws IllegalArgumentException {
        if (dimension == null || player == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        PLAYER = player;
        DIMENSION = dimension;
        ASSETS = assets;

        setPreferredSize(dimension);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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

        g2d.setColor(PLAYER.getColor());
        g2d.fillRoundRect(padding, padding, iconScale, iconScale, 10, 10);

        final int nameX = DIMENSION.height;
        final int nameY = (int) (DIMENSION.height / 2.5);
        final int nameScale = (int) (iconScale / 2.5);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, nameScale));
        g2d.drawString(PLAYER.getName(), nameX, nameY);
    }
}
