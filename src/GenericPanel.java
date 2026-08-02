import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a generic container that is only responsible for following the rendering instruction
 * it receives from a controller
 *
 * @see ViewController
 *
 * @author Richmond Jase Von M. Salvador
 * @version 3.0 8/2/2026
 * @since 3.0
 */
public class GenericPanel extends JPanel implements Renderer, Displayer {

    /**
     * Contains the object that is responsible for drawing/painting on this component
     *
     * @since 3.0
     * @see Graphics2D
     */
    private Graphics2D GRAPHICS;

    /**
     * Contains the controller that would provide the instructions to display
     *
     * @since 3.0
     * @see ViewController
     */
    private final ViewController CONTROL;

    /**
     * The width of this component
     *
     * @since 3.0
     */
    private final int WIDTH;

    /**
     * The height of this component
     */
    private final int HEIGHT;

    /**
     * Constructs this component with the specified width and height, and the controller that would provide the
     * instructions to display
     *
     * @param controller the controller that would provide the instructions
     * @param width the width of this component
     * @param height the height of this component
     *
     * @since 3.0
     * @see ViewController
     */
    public GenericPanel(ViewController controller, int width, int height) {
        CONTROL = controller;
        WIDTH = width;
        HEIGHT = height;

        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Responsible for translating the instructions from the controller and painting these
     * instructions within this component
     *
     * @param g the <code>Graphics</code> object to protect
     *
     * @since 3.0
     */
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
