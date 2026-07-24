import javax.swing.*;
import java.awt.*;

/**
 * Graphical User Input screen to collect usernames for Player 1 and Player 2.
 * 
 * @author Zachary Tan
 * @version 1.0
 */
public class DisplayNameInput extends JFrame {

    private JTextField p1NameField;
    private JTextField p2NameField;

    public DisplayNameInput() {
        // Window Configuration
        setTitle("Animal Chess - Player Setup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Container Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Header Title
        JLabel titleLabel = new JLabel("Enter Player Usernames");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Player 1 Input Row
        JPanel p1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel p1Label = new JLabel("Player 1 Name: ");
        p1Label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p1NameField = new JTextField("Player 1", 15);
        p1Panel.add(p1Label);
        p1Panel.add(p1NameField);

        // Player 2 Input Row
        JPanel p2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel p2Label = new JLabel("Player 2 Name: ");
        p2Label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p2NameField = new JTextField("Player 2", 15);
        p2Panel.add(p2Label);
        p2Panel.add(p2NameField);

        // Proceed Button
        JButton proceedButton = new JButton("Confirm & Play");
        proceedButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedButton.setBackground(new Color(40, 180, 90));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setFocusPainted(false);
        proceedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        proceedButton.addActionListener(e -> validateAndStart());

        // Assembly
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(p1Panel);
        panel.add(p2Panel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(proceedButton);

        add(panel);
    }

    /**
     * Validates that neither username is empty before proceeding.
     */
    private void validateAndStart() {
        String p1Name = p1NameField.getText().trim();
        String p2Name = p2NameField.getText().trim();

        if (p1Name.isEmpty() || p2Name.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, 
                "Both players must enter a username!", 
                "Input Error", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Store names & proceed
        JOptionPane.showMessageDialog(
            this, 
            "Welcome " + p1Name + " vs " + p2Name + "!\nStarting game...", 
            "Match Ready", 
            JOptionPane.INFORMATION_MESSAGE
        );

        dispose(); // Close this window
        
        // TODO: Next step is launching the main Game Board GUI window!
    }
}