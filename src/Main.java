import java.awt.*;

/**
 * Contains the driver class for the whole project
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.11, 07/1/2026
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        GameBoard board = new GameBoard();
        //GameBoard board = new GameBoard();
        new Display(screenSize, board, p1, p2);
    }
}