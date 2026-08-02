
import javax.swing.*;
import java.awt.*;

/**
 * Graphical User Input screen to collect player colors after turn order is decided.
 * 
 * @author Zachary Tan
 * @version 1.0
 */
public class DisplayColorSelection extends JFrame {

    private final StartController controller;
    private JComboBox<String> firstPlayerColorBox;
    private JComboBox<String> secondPlayerColorBox;

    private final String[] availableColors = {"Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Cyan"};

    public DisplayColorSelection(StartController controller, String firstPlayerName, String secondPlayerName) {
        this.controller = controller;

        setTitle("Animal Chess - Color Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel("Choose Your Colors");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitleLabel = new JLabel(firstPlayerName + " won the draw and goes first!");
        subTitleLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        subTitleLabel.setForeground(new Color(100, 100, 100));
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // First Player Row
        JPanel p1Panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        firstPlayerColorBox = new JComboBox<>(availableColors);
        firstPlayerColorBox.setSelectedIndex(0); // Default Red
        p1Panel.add(new JLabel("1st Turn (" + firstPlayerName + "): "));
        p1Panel.add(firstPlayerColorBox);

        // Second Player Row
        JPanel p2Panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        secondPlayerColorBox = new JComboBox<>(availableColors);
        secondPlayerColorBox.setSelectedIndex(1); // Default Blue
        p2Panel.add(new JLabel("2nd Turn (" + secondPlayerName + "): "));
        p2Panel.add(secondPlayerColorBox);

        JButton proceedButton = new JButton("Enter Battleground");
        proceedButton.setBackground(new Color(40, 180, 90));
        proceedButton.setForeground(Color.WHITE);
        proceedButton.setFocusPainted(false);
        proceedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedButton.addActionListener(e -> validateAndSubmit());

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(subTitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(p1Panel);
        panel.add(p2Panel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(proceedButton);

        add(panel);
    }

    private void validateAndSubmit() {
        String color1Str = (String) firstPlayerColorBox.getSelectedItem();
        String color2Str = (String) secondPlayerColorBox.getSelectedItem();

        if (color1Str.equals(color2Str)) {
            JOptionPane.showMessageDialog(this, "Players must choose different colors!", "Color Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Color color1 = mapStringToColor(color1Str);
        Color color2 = mapStringToColor(color2Str);

        dispose(); 
        controller.onColorsSubmitted(color1, color2);
    }

    private Color mapStringToColor(String colorName) {
        switch (colorName) {
            case "Red": return Color.RED;
            case "Blue": return new Color(50, 150, 255); 
            case "Green": return new Color(40, 180, 90);
            case "Yellow": return Color.ORANGE; 
            case "Purple": return new Color(130, 60, 220);
            case "Orange": return new Color(255, 140, 0);
            case "Cyan": return Color.CYAN;
            default: return Color.WHITE;
        }
    }
}