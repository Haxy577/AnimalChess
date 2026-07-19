import javax.swing.*;
import java.awt.*;

public class DisplayPlayer extends JPanel {
    private Player player;

    public DisplayPlayer(Dimension dimension, Player player) {
        if (dimension == null || player == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        this.player = player;

        setPreferredSize(dimension);
        setBackground(Color.ORANGE);
    }
}
