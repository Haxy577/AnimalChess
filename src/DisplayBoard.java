import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that serves would contain the visual representation of the game board.
 *
 * @see GameBoard
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.1 7/19/2026
 * @since 2.1
 */
public class DisplayBoard extends JPanel {
    private final GameBoard BOARD;
    private final int SCALE;

    public DisplayBoard(Dimension dimension, GameBoard board) {
        if (dimension == null || board == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        this.BOARD = board;
        SCALE = Math.min(dimension.height / board.getRows(), dimension.width / board.getColumns());

        setPreferredSize(dimension);
    }

    /**
     * Draws the specified game board
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        BoardCell[][] board = BOARD.getBoard();
        int rows = BOARD.getRows();
        int cols = BOARD.getColumns();

        // draw the details in the board
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                // set the color to the tile to be drawn
                g2d.setColor(board[y][x].getTile().getType().COLOR);
                g2d.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
            }
        }

        // set the color for the grid lines
        g2d.setColor(Color.DARK_GRAY);

        // Change the thickness of the line
        g2d.setStroke(new BasicStroke(2));

        // draw the vertical grid lines
        for (int x = 0; x <= cols; x++) {
            int xPos = x * SCALE;
            g2d.drawLine(xPos, 0, xPos, rows * SCALE);
        }

        // draw the horizontal grid lines
        for (int y = 0; y <= rows; y++) {
            int yPos = y * SCALE;
            g2d.drawLine(0, yPos, cols * SCALE, yPos);
        }
    }
}
