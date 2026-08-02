import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * Controller class that bridges the Model (Player) and View (Display GUI classes).
 * Controls the flow of the pre-game setup and main game initialization.
 * 
 * @author Zachary Tan
 * @version 1.1
 */
public class StartController {

    private final MainController CONTROL;
    private String p1Name;
    private String p2Name;
    private Player player1;
    private Player player2;
    private String firstPlayer;

    public StartController(MainController control) {
        CONTROL = control;
    }

    public void displayMenu() {
        new DisplayMenu(this);
    }

    /**
     * Initializes and displays the Player Setup view.
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
     */
    public void onPlayerSetupSubmitted(String p1Name, String p2Name) {
        DisplayOrderSelection orderSelectionView = new DisplayOrderSelection(this, p1Name, p2Name);
        orderSelectionView.setVisible(true);
    }

    /**
     * Callback method triggered when the blind box mini-game concludes.
     * Sets the first player and transitions to the main game.
     */
    public void onTurnOrderDetermined(String firstPlayer, String secondPlayer) {
        p1Name = firstPlayer;
        p2Name = secondPlayer;

        DisplayColorSelection color = new DisplayColorSelection(this, p1Name, p2Name);
        color.setVisible(true);
    }

    public void onColorsSubmitted(Color p1, Color p2) {
        Player player1 = new Player(p1Name, p1);
        Player player2 = new Player(p2Name, p2);
        CONTROL.showMainGame(new GameBoard(player1, player2), player1, player2);
    }

    /**
     * Gets the Model object for Player 1.
     * 
     * @return The Player 1 object.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Gets the Model object for Player 2.
     * 
     * @return The Player 2 object.
     */
    public Player getPlayer2() {
        return player2;
    }
}