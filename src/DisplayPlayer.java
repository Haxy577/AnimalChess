import javax.swing.*;
import java.awt.*;

public class DisplayPlayer extends JPanel {
    private Player player;

    DisplayPlayer(Player player, int boardWidth, int scale) {
        final int HEIGHT = 5; // 5 pixels
        this.player = player;

        setPreferredSize(new Dimension(boardWidth * scale, HEIGHT * scale));
        add(new JLabel(player.getName()));
    }

    DisplayPlayer(Dimension dimension) {
        if (dimension == null)
            throw new IllegalArgumentException("The dimension cannot be null");

        setPreferredSize(dimension);
        setBackground(Color.ORANGE);
    }
}
