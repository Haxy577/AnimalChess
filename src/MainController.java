import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class MainController {
    private JFrame activeFrame;
    private final Dimension SCREEN;

    public MainController(Dimension screen) {
        SCREEN = screen;
    }

    public void showMainGame(GameBoard board, Player p1, Player p2) {
        closeActiveFrame();

        activeFrame = new JFrame("Animal Chess");
        activeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        activeFrame.setLayout(new BorderLayout(0, 0));

        Dimension boardArea = getBoardDimension(board.getRows(), board.getColumns());
        Dimension playerArea = getPlayerDimension(boardArea.width);

        GameState state = new GameState(board, p1, p2);
        BoardController boardController = new BoardController(this, boardArea, state);
        GenericPanel boardDisplay = new GenericPanel(boardController, boardArea.width, boardArea.height);
        boardController.setDisplay(boardDisplay);

        MouseAdapter listener = new BoardInputHandler(boardController);
        boardDisplay.addMouseListener(listener);
        boardDisplay.addMouseMotionListener(listener);

        PlayerController player1Control = new PlayerController(playerArea, p1);
        GenericPanel player1Display = new GenericPanel(player1Control, playerArea.width, playerArea.height);

        PlayerController player2Control = new PlayerController(playerArea, p2);
        GenericPanel player2Display = new GenericPanel(player2Control, playerArea.width, playerArea.height);

        activeFrame.add(boardDisplay, BorderLayout.CENTER);
        activeFrame.add(player1Display, BorderLayout.SOUTH);
        activeFrame.add(player2Display, BorderLayout.NORTH);

        activeFrame.pack();
        activeFrame.setLocationRelativeTo(null);
        activeFrame.setResizable(false);
        activeFrame.setVisible(true);
    }

    public void showResults(Player winner, Player p1, Player p2) {
        JFrame frame = new JFrame("Animal Chess - Results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private Dimension getBoardDimension(int rows, int columns) {
        int width = SCREEN.width;
        int height = SCREEN.height;

        int maxBoardWidth = (int) (2.0 / 3.0 * width);
        int maxBoardHeight = (int) (height * 7.0 / 8.0 * .95); // To leave some space for the menu bar

        int scale = Math.min(maxBoardHeight / rows, maxBoardWidth / columns);

        return new Dimension(columns * scale, rows * scale);
    }

    private Dimension getPlayerDimension(int boardWidth) {
        int playerHeight = SCREEN.height / 16;
        return new Dimension(boardWidth, playerHeight);
    }

    private void closeActiveFrame() {
        if (activeFrame != null)
            activeFrame.dispose();
    }
}
