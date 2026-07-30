import javax.swing.*;
import java.awt.*;

/**
 * A JFrame that would contain all the visual representation necessary for the game Animal Piece
 *
 * @see DisplayPlayArea
 * @see DisplayBoard
 * @see DisplayPlayer
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.3 7/20/2026
 * @since 2.1
 */
public class GameDisplay {
    private final JFrame FRAME;
    private final Dimension RESOLUTION;

    /**
     * Constructs the JFrame based on the specified resolution, board, player 1, and player 2
     *
     * @param resolution the width and height of the screen, or the desired dimensions of the JFrame
     * @throws IllegalArgumentException if the specified arguments are null or the specified resolution contains
     * negative integers
     *
     * @since 2.1
     * @see GameBoard
     * @see Player
     */
    public GameDisplay(Dimension resolution, GameController control) throws IllegalArgumentException {

        if (resolution.width < 0 || resolution.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        RESOLUTION = resolution;
        Dimension playAreaDimension = getPlayAreaDimension(control);

        FRAME = new JFrame("Animal Chess");
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setResizable(false);
        FRAME.setLayout(new BoxLayout(FRAME.getContentPane(), BoxLayout.X_AXIS));

        FRAME.add(new DisplayPlayArea(playAreaDimension, control));

        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
        FRAME.setVisible(true);
    }

    private Dimension getPlayAreaDimension(GameController control) {
        int width = RESOLUTION.width;
        int height = RESOLUTION.height;

        int maxBoardWidth = (int) (2.0 / 3.0 * width);
        int maxBoardHeight = (int) (height * 7.0 / 8.0 * .95); // To leave some space for the menu bar

        int scale = Math.min(maxBoardHeight / control.getRow(), maxBoardWidth / control.getColumn());

        int fittedBoardWidth = control.getColumn() * scale;
        int fittedBoardHeight = control.getRow() * scale;
        int playerHeight = height / 16;
        int playAreaHeight = 2 * playerHeight + fittedBoardHeight;

        return new Dimension(fittedBoardWidth, playAreaHeight);
    }

    public void dispose() {
        FRAME.dispose();
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
