import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GenericPanel extends JPanel implements Renderer, Displayer {
    private Graphics2D GRAPHICS;
    private final ViewController CONTROL;
    private final int WIDTH;
    private final int HEIGHT;

    public GenericPanel(ViewController controller, int width, int height) {
        CONTROL = controller;
        WIDTH = width;
        HEIGHT = height;

        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GRAPHICS = (Graphics2D) g;
        GRAPHICS.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GRAPHICS.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        CONTROL.render(this);
    }

    @Override
    public void solidRectangle(Color color, int x, int y, int width, int height) {
        GRAPHICS.setColor(color);
        GRAPHICS.fillRect(x, y, width, height);
    }

    @Override
    public void hollowRectangle(Color color, int x, int y, int width, int height, int thickness) {
        GRAPHICS.setColor(color);
        GRAPHICS.setStroke(new BasicStroke(thickness));
        GRAPHICS.drawRect(x, y, width, height);
    }

    @Override
    public void roundRectangle(Color color, int x, int y, int width, int height, int arc) {
        GRAPHICS.setColor(color);
        GRAPHICS.fillRoundRect(x, y, width, height, arc, arc);
    }

    @Override
    public void solidCircle(Color color, int x, int y, int diameter) {
        GRAPHICS.setColor(color);
        GRAPHICS.fillOval(x, y, diameter, diameter);
    }

    @Override
    public void hollowCircle(Color color, int x, int y, int diameter, int thickness) {
        GRAPHICS.setColor(color);
        GRAPHICS.setStroke(new BasicStroke(thickness));
        GRAPHICS.drawOval(x, y, diameter, diameter);
    }

    @Override
    public void drawLine(Color color, int x1, int y1, int x2, int y2, int thickness) {
        GRAPHICS.setColor(color);
        GRAPHICS.setStroke(new BasicStroke(thickness));
        GRAPHICS.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void drawString(String string, Color color, Font font, int x, int y) {
        GRAPHICS.setColor(color);
        GRAPHICS.setFont(font);
        GRAPHICS.drawString(string, x, y);
    }

    @Override
    public void drawImage(BufferedImage image, int x, int y, int size) {
        GRAPHICS.drawImage(image, x, y, size, size, null);
    }

    @Override
    public void fillBackground(Color color) {
        solidRectangle(color, 0, 0, WIDTH, HEIGHT);
    }

    @Override
    public void refresh() {
        repaint();
    }
}
