import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Graphical UI screen that randomly determines and displays turn order.
 * 
 * @author Zachary Tan
 * @version 1.0
 */
public class DisplayOrderSelection extends JFrame {

    private final String player1;
    private final String player2;
    private String firstPlayer;
    private String secondPlayer;

    private JLabel resultLabel;
    private JButton proceedButton;

    public DisplayOrderSelection(String p1Name, String p2Name) {
        this.player1 = p1Name;
        this.player2 = p2Name;

        // Window Configuration
        setTitle("Animal Chess - Turn Order Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 320);
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

        JLabel vsLabel = new JLabel(player1 + "  vs  " + player2);
        vsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        vsLabel.setForeground(Color.LIGHT_GRAY);
        vsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Result Label
        resultLabel = new JLabel("Click below to flip for first move!");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        resultLabel.setForeground(new Color(255, 215, 0)); // Gold text
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Randomize Button
        JButton flipButton = new JButton("🎲 Flip Coin");
        flipButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        flipButton.setBackground(new Color(130, 60, 220)); // Vibrant purple accent
        flipButton.setForeground(Color.WHITE);
        flipButton.setFocusPainted(false);
        flipButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        flipButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Launch Game Button (Disabled until order is rolled)
        proceedButton = new JButton("Enter Battleground");
        proceedButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        proceedButton.setBackground(new Color(40, 180, 90));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setFocusPainted(false);
        proceedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedButton.setEnabled(false);

        // Randomization Logic
        flipButton.addActionListener(e -> {
            determineFirstPlayer();
            flipButton.setEnabled(false); // Lock button after rolling
            proceedButton.setEnabled(true); // Unlock proceed button
        });

        // Hand-off to Main Game Board
        proceedButton.addActionListener(e -> {
            dispose(); // Close selection window
            
            // TODO: Pass firstPlayer and secondPlayer into your main Game Board GUI!
            JOptionPane.showMessageDialog(
                null, 
                firstPlayer + " gets to make the first move!\nLoading board...", 
                "Game Starting", 
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Assembly
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(vsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(resultLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(flipButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(proceedButton);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Randomly assigns who go first using Java's Random logic.
     */
    private void determineFirstPlayer() {
        Random random = new Random();
        boolean p1Starts = random.nextBoolean();

        if (p1Starts) {
            firstPlayer = player1;
            secondPlayer = player2;
        } else {
            firstPlayer = player2;
            secondPlayer = player1;
        }

        resultLabel.setText("🎉 " + firstPlayer.toUpperCase() + " GOES FIRST!");
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }
}