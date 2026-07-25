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

    public DisplayPlayArea(Dimension dimension, Controller control) throws IllegalArgumentException {

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        setPreferredSize(dimension);
        setLayout(new BorderLayout());

        int scale = dimension.width / control.getColumn();
        int boardHeight = scale * control.getRow();
        int playerHeight = (dimension.height - boardHeight) / 2;
        AssetsManager assets = new AssetsManager();

        DisplayPlayer player1 = new DisplayPlayer(new Dimension(dimension.width, playerHeight), control, 1);
        DisplayPlayer player2 = new DisplayPlayer(new Dimension(dimension.width, playerHeight), control, 2);
        DisplayBoard gameboard = new DisplayBoard(new Dimension(dimension.width, boardHeight), control, assets);

        add(player1, BorderLayout.SOUTH);
        add(player2, BorderLayout.NORTH);
        add(gameboard, BorderLayout.CENTER);
    }
}
