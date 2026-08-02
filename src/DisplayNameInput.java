
import javax.swing.*;
import java.awt.*;

/**
 * Graphical User Input screen to collect usernames.
 * 
 * @author Zachary Tan
 * @version 1.5
 */
public class DisplayNameInput extends JFrame {

    private final StartController controller;
    private JTextField p1NameField;
    private JTextField p2NameField;

    public DisplayNameInput(StartController controller) {
        this.controller = controller;

        setTitle("Animal Chess - Player Setup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 220); // Smaller window since colors are gone
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Enter Player Usernames");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel p1Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p1NameField = new JTextField("Player 1", 12);
        p1Panel.add(new JLabel("Player 1 Name: "));
        p1Panel.add(p1NameField);

        JPanel p2Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2NameField = new JTextField("Player 2", 12);
        p2Panel.add(new JLabel("Player 2 Name: "));
        p2Panel.add(p2NameField);

        JButton proceedButton = new JButton("Next: Draw for Turn Order");
        proceedButton.setBackground(new Color(40, 180, 90));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setFocusPainted(false);
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedButton.addActionListener(e -> validateAndSubmit());

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(p1Panel);
        panel.add(p2Panel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(proceedButton);

        add(panel);
    }

    private void validateAndSubmit() {
        String p1Name = p1NameField.getText().trim();
        String p2Name = p2NameField.getText().trim();

        if (p1Name.isEmpty() || p2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both players must enter a username!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        if (p1Name.equalsIgnoreCase(p2Name)) {
            JOptionPane.showMessageDialog(this, "Both players must enter a unique username!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dispose(); 
        controller.onPlayerSetupSubmitted(p1Name, p2Name);
    }
}