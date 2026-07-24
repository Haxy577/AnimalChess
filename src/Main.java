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
        Player p1 = new Player("Player 1", new Color(113, 126, 221));
        Player p2 = new Player("Player 2", new Color(214, 104, 104));
        // GameBoard board = new GameBoard(9,7, "2Ltat5Lt11L2RL2R2L2RL2R2L2RL2R11LT5LTAT2L|n5g1d3c1m1p1w1e21E1W1P1M1C3D1G5N", p1, p2);
        // GameBoard board = new GameBoard(2, 4, "LRTALRta|mmmmMMMM", p1, p2);
        GameBoard board = new GameBoard(p1, p2);
        new Display(screenSize, board, p1, p2);
        // new DisplayMenu();
        // new DisplayOrderSelection("Player 1", "Player 2");
    }
}