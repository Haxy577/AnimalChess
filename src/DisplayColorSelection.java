import javax.swing.*;
import java.awt.*;

/**
 * Graphical User Input screen to collect player colors after turn order is decided.
 * 
 * @author Zachary Tan
 * @author Richmond Jase Von M. Salvador
 * @version 1.0
 */
public class DisplayColorSelection extends JFrame {

    /**
     * Contains the controller for this view
     *
     * @since 1.0
     * @see StartController
     */
    private final StartController CONTROL;

    /**
     * Represents a drop-down menu that contains all the colors player 1 can choose from
     *
     * @since 1.0
     */
    private JComboBox<String> firstPlayerColorBox;

    /**
     * Represents a drop-down menu that contains all the colors player 2 can choose from
     *
     * @since 1.0
     */
    private JComboBox<String> secondPlayerColorBox;

    /**
     * Contains all the possible colors a player can choose
     *
     * @since 1.0
     */
    private final String[] COLORS = {"Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Cyan"};

    /**
     * Constructs this class with the specified controller, the first player's name, and the second player's name
     *
     * @param controller the controller that contains the details to be displayed
     * @param firstPlayerName the chosen name for the first player
     * @param secondPlayerName the chosen name for the second player
     *
     * @since 1.0
     * @see StartController
     */
    public DisplayColorSelection(StartController controller, String firstPlayerName, String secondPlayerName) {
        this.CONTROL = controller;

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
        firstPlayerColorBox = new JComboBox<>(COLORS);
        firstPlayerColorBox.setSelectedIndex(0); // Default Red
        p1Panel.add(new JLabel("1st Turn (" + firstPlayerName + "): "));
        p1Panel.add(firstPlayerColorBox);

        // Second Player Row
        JPanel p2Panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        secondPlayerColorBox = new JComboBox<>(COLORS);
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

    /**
     * A helper method that determines whether the player's choices are valid and unique. If it is then
     * it would pass this information to the controller
     *
     * @since 1.0
     * @see StartController
     */
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
        CONTROL.onColorsSubmitted(color1, color2);
    }

    /**
     * A helper method that converts the specified string with its corresponding color object
     *
     * @param colorName the string to be converted
     * @return the color object representation of the string
     *
     * @since 1.0
     */
    private Color mapStringToColor(String colorName) {
        return switch (colorName) {
            case "Red" -> Color.RED;
            case "Blue" -> new Color(50, 150, 255);
            case "Green" -> new Color(40, 180, 90);
            case "Yellow" -> Color.ORANGE;
            case "Purple" -> new Color(130, 60, 220);
            case "Orange" -> new Color(255, 140, 0);
            case "Cyan" -> Color.CYAN;
            default -> Color.WHITE;
        };
    }
}