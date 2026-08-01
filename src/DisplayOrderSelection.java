import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Graphical UI screen that determines turn order through a 
 * 7-box blind selection mini-game.
 * 
 * @author Zachary Tan
 * @version 2.0
 */
public class DisplayOrderSelection extends JFrame {

    private final Player player1;
    private final Player player2;
    private Player firstPlayer;
    private Player secondPlayer;

    private JLabel statusLabel;
    private JButton proceedButton;
    
    // Mini-game variables
    private JButton[] boxButtons = new JButton[7];
    private List<Integer> hiddenValues;
    private int currentPlayerPicking = 1; // 1 for P1, 2 for P2
    private int p1ChoiceIndex = -1;
    private int p2ChoiceIndex = -1;

    public DisplayOrderSelection(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;

        // Shuffle the blind box values (1 to 7)
        setupHiddenValues();

        // Window Configuration
        setTitle("Animal Chess - Turn Order Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 350); // Slightly wider to fit 7 boxes
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Dark Container Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(25, 25, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Title & Matchup Info
        JLabel titleLabel = new JLabel("Turn Order Selection");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel vsLabel = new JLabel(player1.getName() + "  vs  " + player2.getName());
        vsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        vsLabel.setForeground(Color.LIGHT_GRAY);
        vsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Dynamic Status Label
        statusLabel = new JLabel(player1.getName() + ", pick a box!");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusLabel.setForeground(new Color(255, 215, 0)); // Gold text
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Boxes Panel (7 columns)
        JPanel boxesPanel = new JPanel(new GridLayout(1, 7, 10, 0));
        boxesPanel.setBackground(new Color(25, 25, 25));
        boxesPanel.setMaximumSize(new Dimension(500, 60));

        // Create the 7 clickable boxes
        for (int i = 0; i < 7; i++) {
            int boxIndex = i;
            JButton boxBtn = new JButton("?");
            boxBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
            boxBtn.setBackground(new Color(70, 70, 70));
            boxBtn.setForeground(Color.WHITE);
            boxBtn.setFocusPainted(false);
            boxBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            boxBtn.addActionListener(e -> handleBoxPick(boxIndex, boxBtn));
            
            boxButtons[i] = boxBtn;
            boxesPanel.add(boxBtn);
        }

        // Launch Game Button (Disabled until winner is declared)
        proceedButton = new JButton("Enter Battleground");
        proceedButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        proceedButton.setBackground(new Color(40, 180, 90));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setFocusPainted(false);
        proceedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedButton.setEnabled(false);

        proceedButton.addActionListener(e -> {
            dispose(); // Close selection window
            JOptionPane.showMessageDialog(
                null, 
                firstPlayer.getName() + " gets to make the first move!\nLoading board...", 
                "Game Starting", 
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Assembly
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

        setVisible(true);
    }

    /**
     * Initializes a list with numbers 1-7 and randomizes their order.
     */
    private void setupHiddenValues() {
        hiddenValues = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            hiddenValues.add(i);
        }
        Collections.shuffle(hiddenValues);
    }

    /**
     * Handles the logic when a player clicks a box.
     */
    private void handleBoxPick(int boxIndex, JButton clickedBtn) {
        if (currentPlayerPicking == 1) {
            p1ChoiceIndex = boxIndex;
            clickedBtn.setText("P1");
            clickedBtn.setBackground(new Color(130, 60, 220)); // P1 Color (Purple)
            clickedBtn.setEnabled(false); // Lock this box
            
            currentPlayerPicking = 2;
            statusLabel.setText(player2.getName() + ", pick a box!");
            
        } else if (currentPlayerPicking == 2) {
            p2ChoiceIndex = boxIndex;
            clickedBtn.setText("P2");
            clickedBtn.setBackground(new Color(60, 150, 220)); // P2 Color (Blue)
            
            // Lock all buttons since both have picked
            for (JButton btn : boxButtons) {
                btn.setEnabled(false);
            }
            
            revealBoxesAndDetermineWinner();
        }
    }

    /**
     * Reveals the numbers behind the chosen boxes and calculates the winner.
     */
    private void revealBoxesAndDetermineWinner() {
        int p1Score = hiddenValues.get(p1ChoiceIndex);
        int p2Score = hiddenValues.get(p2ChoiceIndex);

        // Update the button text to show what they rolled
        boxButtons[p1ChoiceIndex].setText(String.valueOf(p1Score));
        boxButtons[p2ChoiceIndex].setText(String.valueOf(p2Score));

        // Determine winner (No ties possible since numbers 1-7 are unique)
        if (p1Score > p2Score) {
            firstPlayer = player1;
            secondPlayer = player2;
        } else {
            firstPlayer = player2;
            secondPlayer = player1;
        }

        // Update UI to show the result
        statusLabel.setText("🎉 " + firstPlayer.getName().toUpperCase() + " WINS THE DRAW!");
        proceedButton.setEnabled(true);
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }
}