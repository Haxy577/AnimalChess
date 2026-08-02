import java.awt.Color;

/**
 * Controller class that bridges the Model (Player) and View (Display GUI classes).
 * Controls the flow of the pre-game setup and main game initialization.
 * 
 * @author Zachary Tan
 * @author Richmond Jase Von M. Salvador
 * @version 1.1
 */
public class StartController {

    /**
     * The controller that contains all the controller classes
     *
     * @since 1.1
     * @see MainController
     */
    private final MainController CONTROL;

    /**
     * The chosen username of the first player
     *
     * @since 1.1
     */
    private String p1Name;

    /**
     * The chosen username of the second player
     *
     * @since 1.1
     */
    private String p2Name;

    /**
     * Constructs the controller with the controller that owns all controllers
     *
     * @param control the controller of all controllers
     *
     * @since 1.1
     * @see MainController
     */
    public StartController(MainController control) {
        CONTROL = control;
    }

    /**
     * Displays the main menu
     *
     * @since 1.1
     * @see DisplayMenu
     */
    public void displayMenu() {
        new DisplayMenu(this);
    }

    /**
     * Initializes and displays the Player Setup view.
     *
     * @since 1.1
     * @see DisplayNameInput
     */
    public void startPlayerSetup() {
        DisplayNameInput nameInputView = new DisplayNameInput(this);
        nameInputView.setVisible(true);
    }

    /**
     * Callback method triggered when the user submits their names and colors.
     * Converts raw View data into Model objects and advances the screen.
     * 
     * @param p1Name  The username entered by Player 1.
     * @param p2Name  The username entered by Player 2.
     *
     * @since 1.1
     * @see DisplayOrderSelection
     */
    public void onPlayerSetupSubmitted(String p1Name, String p2Name) {
        DisplayOrderSelection orderSelectionView = new DisplayOrderSelection(this, p1Name, p2Name);
        orderSelectionView.setVisible(true);
    }

    /**
     * Callback method triggered when the blind box mini-game concludes.
     * Sets the first player and transitions to the color setup.
     *
     * @param firstPlayer the username of the first player
     * @param secondPlayer the username of the second player
     *
     * @since 1.1
     * @see DisplayColorSelection
     */
    public void onTurnOrderDetermined(String firstPlayer, String secondPlayer) {
        p1Name = firstPlayer;
        p2Name = secondPlayer;

        DisplayColorSelection color = new DisplayColorSelection(this, p1Name, p2Name);
        color.setVisible(true);
    }

    /**
     * Callback method triggered when the players have submitted their chosen colors. This
     * would also set up the display for the main game with the default board layout and the players.
     *
     * @param p1 the color player 1 chose
     * @param p2 the color player 2 chose
     *
     * @since 1.1
     * @see MainController#showMainGame(GameBoard, Player, Player)
     */
    public void onColorsSubmitted(Color p1, Color p2) {
        Player player1 = new Player(p1Name, p1);
        Player player2 = new Player(p2Name, p2);
        CONTROL.showMainGame(new GameBoard(player1, player2), player1, player2);
    }
}