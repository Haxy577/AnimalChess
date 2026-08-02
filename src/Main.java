

import java.awt.*;

/**
 * Contains the driver class for the whole project
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.3, 7/20/2026
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        new MainController(screenSize).showMenu();
    }
}