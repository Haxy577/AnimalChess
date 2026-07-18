import javax.swing.*;
import java.awt.*;

public class DisplayBoard extends JPanel {
    GameBoard board;

    DisplayBoard(Dimension dimension) {
        if (dimension == null)
            throw new IllegalArgumentException("The dimension cannot be null");

        setPreferredSize(dimension);
        setBackground(Color.GREEN);
    }
}
