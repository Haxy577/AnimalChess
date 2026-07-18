import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame frame;
    private final Dimension RESOLUTION;

    Display(Dimension resolution) {
        if (resolution == null)
            throw new IllegalArgumentException("The resolution cannot be null");

        RESOLUTION = resolution;
        int width = RESOLUTION.width;
        int height = RESOLUTION.height;

        frame = new JFrame("Animal Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        frame.add(new DisplayPlayArea(new Dimension((int)(2.0 / 3.0 * width), height)));
        frame.add(new DisplayHistory(new Dimension(width / 3, height)));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Dimension getResolution() {
        return RESOLUTION;
    }
}
