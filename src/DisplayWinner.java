import javax.swing.*;
import java.awt.*;

public class DisplayWinner extends JFrame {
    private static final Color BACKGROUND = Color.DARK_GRAY;
    private static final int PADDING = 20;

    private final Dimension DIMENSION;
    private final GameController CONTROL;

    public DisplayWinner (Dimension dimension, GameController controller) {
        DIMENSION = dimension;
        CONTROL = controller;

        setPreferredSize(DIMENSION);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(BACKGROUND);
        g2d.fillRect(0, 0, DIMENSION.width, DIMENSION.height);

        int scale = DIMENSION.width / 2;

        g2d.setColor(CONTROL.getPlayerColor(1));
        g2d.fillRect(PADDING, 100 + PADDING, scale - PADDING * 2, scale - PADDING * 2);

        g2d.setColor(CONTROL.getPlayerColor(2));
        g2d.fillRect(scale + PADDING, 100 + PADDING, scale - PADDING * 2, scale - PADDING * 2);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("vs", scale - 8, 166);

        // drawCrown(g, Color.YELLOW, 100, 100, 100);
    }

    public void drawCrown(Graphics g, Color color, int x, int y, int scale) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g2d.setColor(color);
        g2d.fillRect(x, y, scale, scale / 2);

        int diameter = scale / 2;
        g2d.setColor(BACKGROUND);
        g2d.fillOval(x, y - diameter / 2, diameter, diameter);
        g2d.fillOval(x + diameter, y - diameter / 2, diameter, diameter);
    }
}
