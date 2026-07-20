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
    public DisplayPlayer(Dimension dimension, Player player) throws IllegalArgumentException {
        if (dimension == null || player == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        PLAYER = player;

        setPreferredSize(dimension);
        setBackground(Color.ORANGE);
    }
}
