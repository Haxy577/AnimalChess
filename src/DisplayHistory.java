import javax.swing.*;
import java.awt.*;

public class DisplayHistory extends JPanel {
    public DisplayHistory(Dimension dimension) {
        if (dimension == null)
            throw new IllegalArgumentException("The dimension cannot be null");

        setPreferredSize(dimension);
        setBackground(Color.BLUE);
    }
}
