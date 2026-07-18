import javax.swing.*;
import java.awt.*;

public class DisplayPlayArea extends JPanel {

    DisplayPlayArea(Dimension dimension) {
        if (dimension == null)
            throw new IllegalArgumentException("The dimension cannot be null");

        setPreferredSize(dimension);
        setLayout(new BorderLayout());

        int playerHeight = dimension.height / 8;
        int boardHeight = dimension.height * 3 / 4;

        add(new DisplayPlayer(new Dimension(dimension.width, playerHeight)), BorderLayout.NORTH); // 1/8 of the size vertically
        add(new DisplayPlayer(new Dimension(dimension.width, playerHeight)), BorderLayout.SOUTH); // 1/8 of the size vertically
        add(new DisplayBoard(new Dimension(dimension.width, boardHeight)), BorderLayout.CENTER); // 3/4 of the size vertically
    }
}
