import javax.swing.*;
import java.awt.*;

/**
 * A JFrame that would contain all the visual representation necessary for the game Animal Piece
 *
 * @see DisplayPlayArea
 * @see DisplayHistory
 * @see DisplayBoard
 * @see DisplayPlayer
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.3 7/20/2026
 * @since 2.1
 */
public class Display {
    private final JFrame FRAME;
    private final Dimension RESOLUTION;

    /**
     * Constructs the JFrame based on the specified resolution, board, player 1, and player 2
     *
     * @param resolution the width and height of the screen, or the desired dimensions of the JFrame
     * @param board the board to be displayed
     * @param p1 the player object of player 1 to be displayed
     * @param p2 the player object of player 2 to be displayed
     * @throws IllegalArgumentException if the specified arguments are null or the specified resolution contains
     * negative integers
     *
     * @since 2.1
     * @see GameBoard
     * @see Player
     */
    public Display(Dimension resolution, GameBoard board, Player p1, Player p2) throws IllegalArgumentException {
        if (resolution == null || board == null || p1 == null || p2 == null)
            throw new IllegalArgumentException("The resolution cannot be null");

        if (resolution.width < 0 || resolution.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        RESOLUTION = resolution;
        int width = RESOLUTION.width;
        int height = RESOLUTION.height;

        int maxBoardWidth = (int) (2.0 / 3.0 * width);
        int maxBoardHeight = (int) (height * 3.0 / 4.0 * .93); // To leave some space for the menu bar

        int scale = Math.min(maxBoardHeight / board.getRows(), maxBoardWidth / board.getColumns());

        int fittedBoardWidth = board.getColumns() * scale;
        int fittedBoardHeight = board.getRows() * scale;
        int playerHeight = height / 8;
        int playAreaHeight = 2 * playerHeight + fittedBoardHeight;

        Dimension playAreaDimension = new Dimension(fittedBoardWidth, playAreaHeight);
        Dimension historyDimension = new Dimension(width / 4, playAreaHeight);

        FRAME = new JFrame("Animal Chess");
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setResizable(false);
        FRAME.setLayout(new BoxLayout(FRAME.getContentPane(), BoxLayout.X_AXIS));

        FRAME.add(new DisplayPlayArea(playAreaDimension, board, p1, p2));
        FRAME.add(new DisplayHistory(historyDimension));

        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
        FRAME.setVisible(true);
    }

    /**
     * A getter method to return the set resolution of the frame
     *
     * @return the width and height of the frame
     *
     * @since 2.1
     */
    public Dimension getResolution() {
        return RESOLUTION;
    }
}
