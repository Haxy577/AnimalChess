import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that serves as a container for the displays of each player and the game board.
 *
 * @see DisplayBoard
 * @see DisplayPlayer
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.1 7/19/2026
 * @since 2.1
 */
public class DisplayPlayArea extends JPanel {

    public DisplayPlayArea(Dimension dimension, GameBoard board, Player p1, Player p2) {
        if (dimension == null)
            throw new IllegalArgumentException("The dimension cannot be null");

        setPreferredSize(dimension);
        setLayout(new BorderLayout());

        int playerHeight = dimension.height / 8;
        int boardHeight = dimension.height;

        DisplayPlayer player1 = new DisplayPlayer(new Dimension(dimension.width, playerHeight), p1);
        DisplayPlayer player2 = new DisplayPlayer(new Dimension(dimension.width, playerHeight), p2);
        DisplayBoard gameboard = new DisplayBoard(new Dimension(dimension.width, boardHeight), board);

        add(player1, BorderLayout.SOUTH);
        add(player2, BorderLayout.NORTH);
        add(gameboard, BorderLayout.CENTER);
    }
}
