import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that serves as a container for the displays of each player and the game board.
 *
 * @see DisplayBoard
 * @see DisplayPlayer
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.3 7/20/2026
 * @since 2.1
 */
public class DisplayPlayArea extends JPanel {

    /**
     * Constructs the object with the specified dimension, board, and player objects
     *
     * @param dimension the width and height for the entire play area
     * @param board the GameBoard that contains the array of BoardCells to be displayed
     * @param p1 the Player object that contains the details of the first player
     * @param p2 the Player object that contains the details of the second player
     * @throws IllegalArgumentException if any of the specified parameters are null
     *
     * @since 2.1
     * @see GameBoard
     * @see Player
     */
    public DisplayPlayArea(Dimension dimension, GameBoard board, Player p1, Player p2) throws IllegalArgumentException {
        if (dimension == null || board == null || p1 == null || p2 == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        setPreferredSize(dimension);
        setLayout(new BorderLayout());

        int scale = dimension.width / board.getColumns();
        int boardHeight = scale * board.getRows();
        int playerHeight = (dimension.height - boardHeight) / 2;
        AssetsManager assets = new AssetsManager();

        DisplayPlayer player1 = new DisplayPlayer(assets, new Dimension(dimension.width, playerHeight), p1);
        DisplayPlayer player2 = new DisplayPlayer(assets, new Dimension(dimension.width, playerHeight), p2);
        DisplayBoard gameboard = new DisplayBoard(assets, new Dimension(dimension.width, boardHeight), board);

        add(player1, BorderLayout.SOUTH);
        add(player2, BorderLayout.NORTH);
        add(gameboard, BorderLayout.CENTER);
    }
}
