import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame frame;
    private final Dimension RESOLUTION;

    public Display(Dimension resolution, GameBoard board, Player p1, Player p2) {
        if (resolution == null)
            throw new IllegalArgumentException("The resolution cannot be null");

        RESOLUTION = resolution;
        int width = RESOLUTION.width;
        int height = RESOLUTION.height;

        int maxBoardWidth = (int) (2.0 / 3.0 * width);
        int maxBoardHeight = (int) (height * 3.0 / 4.0);

        int scale = Math.min(maxBoardHeight / board.getRows(), maxBoardWidth / board.getColumns());

        int fittedBoardWidth = board.getColumns() * scale;
        int fittedBoardHeight = board.getRows() * scale;
        int playerHeight = height / 8;
        int playAreaHeight = 2 * playerHeight + fittedBoardHeight;

        Dimension playAreaDimension = new Dimension(fittedBoardWidth, playAreaHeight);
        Dimension historyDimension = new Dimension(width / 4, playAreaHeight);

        frame = new JFrame("Animal Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        frame.add(new DisplayPlayArea(playAreaDimension, board, p1, p2));
        frame.add(new DisplayHistory(historyDimension));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Dimension getResolution() {
        return RESOLUTION;
    }
}
