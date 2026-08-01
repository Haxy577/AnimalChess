import javax.swing.*;
import java.awt.*;

/**
 * Graphical User Input screen to collect usernames and colors for Player 1 and Player 2,
 * wrap them into Player objects, and pass them to the Order Selection screen.
 * 
 * @author Zachary Tan
 * @version 1.2
 */
public class DisplayNameInput extends JFrame {

    private JTextField p1NameField;
    private JTextField p2NameField;
    
    // Drop-down menus for color selection
    private JComboBox<String> p1ColorBox;
    private JComboBox<String> p2ColorBox;
    
    // Available colors for the game
    private final String[] availableColors = {"Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Cyan"};

    public DisplayNameInput() {
        // Window Configuration
        setTitle("Animal Chess - Player Setup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 280); // Made slightly wider to fit the color boxes
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Container Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Header Title
        JLabel titleLabel = new JLabel("Enter Player Usernames & Colors");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Player 1 Input Row
        JPanel p1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel p1Label = new JLabel("Player 1 Name: ");
        p1Label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p1NameField = new JTextField("Player 1", 12);
        
        JLabel p1ColorLabel = new JLabel(" Color: ");
        p1ColorBox = new JComboBox<>(availableColors);
        p1ColorBox.setSelectedIndex(0); // Default to Red
        
        p1Panel.add(p1Label);
        p1Panel.add(p1NameField);
        p1Panel.add(p1ColorLabel);
        p1Panel.add(p1ColorBox);

        // Player 2 Input Row
        JPanel p2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel p2Label = new JLabel("Player 2 Name: ");
        p2Label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p2NameField = new JTextField("Player 2", 12);
        
        JLabel p2ColorLabel = new JLabel(" Color: ");
        p2ColorBox = new JComboBox<>(availableColors);
        p2ColorBox.setSelectedIndex(1); // Default to Blue
        
        p2Panel.add(p2Label);
        p2Panel.add(p2NameField);
        p2Panel.add(p2ColorLabel);
        p2Panel.add(p2ColorBox);

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
        setVisible(true);
    }

    /**
     * Validates that neither username is empty, unique names and colors are chosen,
     * converts them into Player objects, and passes them to the Turn Order Selection window.
     */
    private void validateAndStart() {
        String p1Name = p1NameField.getText().trim();
        String p2Name = p2NameField.getText().trim();
        
        String p1Color = (String) p1ColorBox.getSelectedItem();
        String p2Color = (String) p2ColorBox.getSelectedItem();

        // Check for empty names
        if (p1Name.isEmpty() || p2Name.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, 
                "Both players must enter a username!", 
                "Input Error", 
                JOptionPane.WARNING_MESSAGE
            );
            return; // Stops the method so the game doesn't proceed
        } 
        
        // Check for duplicate names
        if (p1Name.equalsIgnoreCase(p2Name)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Both players must enter a unique username!",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        // Check for duplicate colors
        if (p1Color.equals(p2Color)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Players must choose different colors!",
                    "Color Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 1. Create the Player objects with Name AND Color
        // Player p1 = new Player(p1Name, p1Color);
        // Player p2 = new Player(p2Name, p2Color);

        dispose(); // Close this window
        
        // 2. Pass the Player objects into the order selection screen
        // new DisplayOrderSelection(p1, p2).setVisible(true);
    }
}