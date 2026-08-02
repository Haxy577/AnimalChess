import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * Represents the controller that has control over all other controller classes
 *
 * @author Richmond Jase Von M. Salvador
 * @version 3.0 8/2/2026
 * @since 3.0
 */
public class MainController {

    /**
     * The current JFrame that is being displayed
     *
     * @since 3.0
     */
    private JFrame activeFrame;

    /**
     * The width and height of the screen
     *
     * @since 3.0
     */
    private final Dimension SCREEN;

    /**
     * Constructs the controller with the width and height of the screen
     *
     * @param screen width and height of the screen
     *
     * @since 3.0
     */
    public MainController(Dimension screen) {
        SCREEN = screen;
    }

    /**
     * Shows the display for the main menu, username selection, order selection, and color selection
     *
     * @since 3.0
     * @see StartController
     */
    public void showMenu() {
        new StartController(this).displayMenu();
    }

    /**
     * Shows the display for the main game loop of the game "Animal Chess"
     *
     * @param board the board to be played on
     * @param p1 the first player which has the first move advantage
     * @param p2 the second player
     *
     * @since 3.0
     * @see BoardController
     * @see PlayerController
     * @see GameBoard
     * @see Player
     */
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

    /**
     * Displays the result indicating which of the two players had won the game
     *
     * @param winner the winning player
     * @param p1 the first player
     * @param p2 the second player
     *
     * @since 3.0
     * @see WinnerController
     * @see Player
     */
    public void showResults(Player winner, Player p1, Player p2) {
        JFrame frame = new JFrame("Animal Chess - Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        Dimension dimension = getWinningDimension();

        WinnerController control = new WinnerController(dimension, winner, p1, p2);
        GenericPanel view = new GenericPanel(control, dimension.width, dimension.height);

        frame.add(view);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * A helper method to determine the width and height of the board display
     *
     * @param rows the amount of rows within the board
     * @param columns the amount of columns within the board
     * @return the width and height of the board display
     *
     * @since 3.0
     */
    private Dimension getBoardDimension(int rows, int columns) {
        int width = SCREEN.width;
        int height = SCREEN.height;

        int maxBoardWidth = (int) (2.0 / 3.0 * width);
        int maxBoardHeight = (int) (height * 7.0 / 8.0 * .95); // To leave some space for the menu bar

        int scale = Math.min(maxBoardHeight / rows, maxBoardWidth / columns);

        return new Dimension(columns * scale, rows * scale);
    }

    /**
     * A helper method to determine the width and height of the player display
     *
     * @param boardWidth the width of the calculated board display
     * @return the width and height of the player display
     *
     * @since 3.0
     */
    private Dimension getPlayerDimension(int boardWidth) {
        int playerHeight = SCREEN.height / 16;
        return new Dimension(boardWidth, playerHeight);
    }

    /**
     * A helper method to determine the width and height of the results display
     *
     * @return the width and height of the results display
     *
     * @since 3.0
     */
    private Dimension getWinningDimension() {
        return new Dimension(SCREEN.width / 4, SCREEN.height / 3);
    }

    /**
     * A helper method to dispose the current active frame
     *
     * @since 3.0
     */
    private void closeActiveFrame() {
        if (activeFrame != null)
            activeFrame.dispose();
    }
}
