import java.awt.*;

/**
 * Contains the driver class for the whole project
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.2, 7/20/2026
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Player p1 = new Player("Player 1", Color.RED);
        Player p2 = new Player("Player 2", Color.BLUE);
        GameBoard board = new GameBoard(p1, p2);
        new Display(screenSize, board, p1, p2);
    }
}