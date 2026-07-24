import javax.swing.*;
import java.awt.*;

/**
 * Graphical Start Menu displaying the ASCII art welcome screen.
 * 
 * @author Zachary Tan
 * @version 1.0
 */
public class DisplayMenu extends JFrame {

    public DisplayMenu() {
        // Window Configuration
        setTitle("Animal Chess - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 380);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout(10, 10));

        // Outer Panel with Dark Background
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(25, 25, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ASCII Art Banner
        String asciiArt = 
            "_____________________________________________________\n\n" +
            "|    /\\ _/\\                               <:3 )~~   |\n" +
            "|   ( o o )                                         |\n" +
            "|    > ^ <                                          |\n" +
            "|               WELCOME TO ANIMAL CHESS             |\n" +
            "|                                                   |\n" +
            "_____________________________________________________";

        JTextArea asciiDisplay = new JTextArea(asciiArt);
        asciiDisplay.setFont(new Font("Monospaced", Font.BOLD, 13));
        asciiDisplay.setForeground(new Color(0, 255, 130)); // Bright terminal green
        asciiDisplay.setBackground(new Color(25, 25, 25));
        asciiDisplay.setEditable(false);

        // Start Button
        JButton startButton = new JButton("START GAME");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        startButton.setBackground(new Color(50, 140, 240));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Action: Close Menu & Open Player Name Input
        startButton.addActionListener(e -> {
            dispose(); // Close Main Menu
            new DisplayNameInput().setVisible(true); // Open Player Input
        });

        mainPanel.add(asciiDisplay, BorderLayout.CENTER);
        mainPanel.add(startButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}