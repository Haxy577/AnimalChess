import javax.swing.*;
import java.awt.*;

public class WinnerController implements ViewController {

    private final Dimension SIZE;
    private final Player WINNER;
    private final Player PLAYER1;
    private final Player PLAYER2;

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

    private void drawCrown(Renderer renderer, int scale, int x, int y) {
        int height = scale / 2;

        renderer.solidRectangle(DisplayConstants.CROWN_COLOR, x, y, scale, height);

        renderer.solidCircle(DisplayConstants.BACKGROUND, x, y - height / 2, height);
        renderer.solidCircle(DisplayConstants.BACKGROUND, x + height, y - height / 2, height);
    }
}
