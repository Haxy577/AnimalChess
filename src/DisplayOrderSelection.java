import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Graphical UI screen that determines turn order.
 * 
 * @author Zachary Tan
 * @author Richmond Jase Von M. Salvador
 * @version 2.4
 */
public class DisplayOrderSelection extends JFrame {

    /**
     * Contains the controller for this view
     *
     * @since 2.4
     * @see StartController
     */
    private final StartController CONTROL;

    /**
     * The username chosen by the first player
     *
     * @since 2.4
     */
    private final String p1Name;

    /**
     * The username chosen by the second player
     *
     * @since 2.4
     */
    private final String p2Name;

    /**
     * The username of the player who won the draw
     *
     * @since 2.4
     */
    private String winningPlayerName;

    // Hardcoded colors just for UI visual feedback during the draw
    private final Color uiP1Color = new Color(130, 60, 220); // Purple
    private final Color uiP2Color = new Color(60, 150, 220); // Blue

    /**
     * Contains the instructions for the players
     *
     * @since 2.4
     */
    private JLabel statusLabel;

    /**
     * Contains the JButton to proceed to the main game
     *
     * @since 2.4
     */
    private JButton proceedButton;

    /**
     * Represents the buttons the players has to click in order to determine the turn order
     *
     * @since 2.4
     */
    private JButton[] boxButtons = new JButton[7];

    /**
     * Contains the hidden values of the buttons which would determine the turn order
     *
     * @since 2.4
     */
    private List<Integer> hiddenValues;

    /**
     * Represents which player is currently choosing a box to pick
     *
     * @since 2.4
     */
    private int currentPlayerPicking = 1;

    /**
     * Represents the chosen box of player 1
     *
     * @since 2.4
     */
    private int p1ChoiceIndex = -1;

    /**
     * Represents the chosen box of player 2
     */
    private int p2ChoiceIndex = -1;

    /**
     * Constructs the view with the specified controller, the name of player 1, and the name of player 2
     *
     * @param controller the controller for this view
     * @param p1Name the chosen username of player 1
     * @param p2Name the chosen username of player 2
     *
     * @since 2.4
     * @see StartController
     */
    public DisplayOrderSelection(StartController controller, String p1Name, String p2Name) {
        CONTROL = controller;
        this.p1Name = p1Name;
        this.p2Name = p2Name;

        setupHiddenValues();

        setTitle("Animal Chess - Turn Order Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(25, 25, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel("Turn Order Selection");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel vsLabel = new JLabel(p1Name + " vs " + p2Name);
        vsLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        vsLabel.setForeground(Color.LIGHT_GRAY);
        vsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel(p1Name + ", pick a box!");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusLabel.setForeground(uiP1Color); 
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel boxesPanel = new JPanel(new GridLayout(1, 7, 10, 0));
        boxesPanel.setBackground(new Color(25, 25, 25));
        boxesPanel.setMaximumSize(new Dimension(500, 60));

        for (int i = 0; i < 7; i++) {
            int boxIndex = i;
            JButton boxBtn = new JButton("?");
            boxBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
            boxBtn.setBackground(new Color(70, 70, 70));
            boxBtn.setForeground(Color.WHITE);
            boxBtn.setFocusPainted(false);
            boxBtn.addActionListener(e -> handleBoxPick(boxIndex, boxBtn));
            
            boxButtons[i] = boxBtn;
            boxesPanel.add(boxBtn);
        }

        proceedButton = new JButton("Proceed to Color Selection");
        proceedButton.setBackground(new Color(40, 180, 90));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setFocusPainted(false);
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedButton.setEnabled(false);

        proceedButton.addActionListener(e -> {
            dispose();
            String p1 = p1Name.equals(winningPlayerName) ? p1Name : p2Name;
            String p2 = p1.equals(p1Name) ? p2Name : p1Name;
            CONTROL.onTurnOrderDetermined(p1, p2);
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(vsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(boxesPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(proceedButton);

        add(mainPanel);
    }

    /**
     * A helper method that shuffles a list containing the values of 1 to 7
     *
     * @since 2.4
     */
    private void setupHiddenValues() {
        hiddenValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            hiddenValues.add(i);
        }
        Collections.shuffle(hiddenValues);
    }

    /**
     * A helper method that would handle the selection of the boxes
     *
     * @param boxIndex the index of the box that had been clicked
     * @param clickedBtn the button that had been clicked
     *
     * @since 2.4
     */
    private void handleBoxPick(int boxIndex, JButton clickedBtn) {
        if (currentPlayerPicking == 1) {
            p1ChoiceIndex = boxIndex;
            clickedBtn.setText("P1");
            clickedBtn.setBackground(uiP1Color); 
            clickedBtn.setEnabled(false);
            
            currentPlayerPicking = 2;
            statusLabel.setText(p2Name + ", pick a box!");
            statusLabel.setForeground(uiP2Color);
            
        } else if (currentPlayerPicking == 2) {
            p2ChoiceIndex = boxIndex;
            clickedBtn.setText("P2");
            clickedBtn.setBackground(uiP2Color); 
            
            for (JButton btn : boxButtons) {
                btn.setEnabled(false);
            }
            revealBoxesAndDetermineWinner();
        }
    }

    /**
     * A helper method that would reveal the hidden values on the boxes that the
     * players had clicked on
     *
     * @since 2.4
     */
    private void revealBoxesAndDetermineWinner() {
        int p1Score = hiddenValues.get(p1ChoiceIndex);
        int p2Score = hiddenValues.get(p2ChoiceIndex);

        boxButtons[p1ChoiceIndex].setText(String.valueOf(p1Score));
        boxButtons[p2ChoiceIndex].setText(String.valueOf(p2Score));

        if (p1Score > p2Score) {
            winningPlayerName = p1Name;
            statusLabel.setForeground(uiP1Color);
        } else {
            winningPlayerName = p2Name;
            statusLabel.setForeground(uiP2Color);
        }

        statusLabel.setText("🎉 " + winningPlayerName.toUpperCase() + " WINS THE DRAW!");
        proceedButton.setEnabled(true);
    }
}