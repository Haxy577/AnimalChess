import javax.swing.*;
import java.awt.*;

/**
 * Responsible for telling the renderer on how to display the results page
 *
 * @author Richmond Jase Von M. Salvador
 * @version 3.0 8/2/2026
 * @since 3.0
 */
public class WinnerController implements ViewController {

    /**
     * The width and height of the component
     *
     * @since 3.0
     */
    private final Dimension SIZE;

    /**
     * The player who had won the game
     *
     * @since 3.0
     * @see Player
     */
    private final Player WINNER;

    /**
     * The first player that had the first move advantage
     *
     * @since 3.0
     * @see Player
     */
    private final Player PLAYER1;

    /**
     * The second player
     *
     * @since 3.0
     * @see Player
     */
    private final Player PLAYER2;

    /**
     * Constructs this controller with the specified size of the component, the player who had won,
     * the first player, and the second player
     *
     * @param size the width and height of the component
     * @param winner the winner of the game
     * @param p1 the first player
     * @param p2 the second player
     *
     * @since 3.0
     * @see Player
     * @see GameState
     */
    public WinnerController(Dimension size, Player winner, Player p1, Player p2) {
        SIZE = size;

        WINNER = winner;
        PLAYER1 = p1;
        PLAYER2 = p2;
    }

    @Override
    public void render(Renderer renderer) {
        renderer.fillBackground(DisplayConstants.BACKGROUND);

        drawResults(renderer);
    }

    private void drawResults(Renderer renderer) {
        int width = SIZE.width / 2;
        int height = (int) (SIZE.height * DisplayConstants.RESULTS_RATIO);

        int iconScale = (int) (width * DisplayConstants.ICON_RATIO);
        int iconHeight = (int) (height * DisplayConstants.ICON_HEIGHT_RATIO);
        int padding = (width - iconScale) / 2;

        renderer.roundRectangle(PLAYER1.getColor(), padding, iconHeight, iconScale, iconScale, DisplayConstants.ICON_ARC);
        renderer.roundRectangle(PLAYER2.getColor(), padding + width, iconHeight, iconScale, iconScale, DisplayConstants.ICON_ARC);

        int textScale =  (int) (padding * 2 * DisplayConstants.RESULTS_TEXT_RATIO);
        int textX = width - textScale / 2;
        int textY = height / 2 + textScale / 2;

        Font font = new Font(DisplayConstants.TEXT_FONT, Font.BOLD, textScale);
        renderer.drawString("vs", DisplayConstants.TEXT_COLOR, font, textX, textY);

        textScale =  (int) (iconScale / 2.0 * DisplayConstants.RESULTS_TEXT_RATIO);
        textX = width / 2 - textScale - padding / 2;
        textY = iconHeight + iconScale + padding / 2;

        font = new Font(DisplayConstants.TEXT_FONT, Font.BOLD, textScale);
        renderer.drawString(PLAYER1.getName(), DisplayConstants.TEXT_COLOR, font, textX, textY);
        renderer.drawString(PLAYER2.getName(), DisplayConstants.TEXT_COLOR, font, textX + width, textY);

        int scale = iconScale - padding;
        int y = iconHeight - padding - (padding / 4);
        int x = width / 2 - scale + padding;

        if (PLAYER2.equals(WINNER)) {
            x += width;
        }

        drawCrown(renderer, scale, x, y);
    }

    /**
     * A helper method that would draw a crown at the specified x and y position. With the
     * width being the specified scale and the height being half of the scale
     *
     * @param renderer the renderer that would display the instructions
     * @param scale the width of the crown
     * @param x the x position where the crown would be drawn
     * @param y the y position where the crown would be drawn
     *
     * @since 3.0
     * @see Renderer
     */
    private void drawCrown(Renderer renderer, int scale, int x, int y) {
        int height = scale / 2;

        renderer.solidRectangle(DisplayConstants.CROWN_COLOR, x, y, scale, height);

        renderer.solidCircle(DisplayConstants.BACKGROUND, x, y - height / 2, height);
        renderer.solidCircle(DisplayConstants.BACKGROUND, x + height, y - height / 2, height);
    }
}
