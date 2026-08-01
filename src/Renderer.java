import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The {@link Renderer} interface should be implemented when attempting to draw inside a swing component
 *
 * @version 2.8 7/31/2026
 * @since 2.8
 */
public interface Renderer {

    /**
     * Draws a filled rectangle with the specified arguments in the specified location starting from the
     * top-left.
     *
     * @param color the color to be drawn
     * @param x the x position relative to the component
     * @param y the y position relative to the component
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     *
     * @since 2.8
     */
    void solidRectangle(Color color, int x, int y, int width, int height);

    /**
     * Draws an outline of a rectangle with the specified arguments in the specified location starting from the
     * top-left.
     *
     * @param color the color to be drawn
     * @param x the x position relative to the component
     * @param y the y position relative to the component
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param thickness the thickness of the line
     *
     * @since 2.8
     */
    void hollowRectangle(Color color, int x, int y, int width, int height, int thickness);

    /**
     * Draws a filled rectangle with rounded corners using the specified arguments in the specified location starting from the
     * top-left.
     *
     * @param color the color to be drawn
     * @param x the x position relative to the component
     * @param y the y position relative to the component
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param arc the diameter of the arc of the rounded corners
     *
     * @since 2.8
     */
    void roundRectangle(Color color, int x, int y, int width, int height, int arc);

    /**
     * Draws a filled circle with the specified arguments in the specified location starting from the
     * top-left.
     *
     * @param color the color to be drawn
     * @param x the x position relative to the component
     * @param y the y position relative to the component
     * @param diameter the diameter of the circle
     *
     * @since 2.8
     */
    void solidCircle(Color color, int x, int y, int diameter);

    /**
     * Draws an outline of a circle with the specified arguments in the specified location starting from the
     * top-left.
     *
     * @param color the color to be drawn
     * @param x the x position relative to the component
     * @param y the y position relative to the component
     * @param diameter the diameter of the circle
     * @param thickness the thickness of the line
     *
     * @since 2.8
     */
    void hollowCircle(Color color, int x, int y, int diameter, int thickness);

    /**
     * Draws a line starting from the first position until the last position
     *
     * @param color the color to be drawn
     * @param x1 the x position of the starting point of the line
     * @param y1 the y position of the starting point of the line
     * @param x2 the x position of the ending point of the line
     * @param y2 the y position of the ending point of the line
     * @param thickness the thickness of the line
     *
     * @since 2.8
     */
    void drawLine(Color color, int x1, int y1, int x2, int y2, int thickness);

    /**
     * Draws the specified string with the provided arguments on the specified location
     *
     * @param string the string to be drawn
     * @param color the color to be drawn
     * @param font the font of the string
     * @param x the x position relative to the component
     * @param y the y position relative to the component
     *
     * @since 2.8
     * @see Font
     */
    void drawString(String string, Color color, Font font, int x, int y);

    /**
     * Draws the specified image at the specified location and size
     *
     * @param image the image to be drawn
     * @param x the x position relative to the component
     * @param y the y position relative to the component
     * @param size the width and height of the image
     *
     * @since 2.8
     */
    void drawImage(BufferedImage image, int x, int y, int size);

    /**
     * Sets the background color of the component to the specified color
     *
     * @param color the color the background would take
     *
     * @since 2.8
     */
    void fillBackground(Color color);
}
