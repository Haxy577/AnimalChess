import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A JPanel that would contain the visual representation of the game board.
 *
 * @see GameBoard
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.3 7/20/2026
 * @since 2.1
 */
public class DisplayBoard extends JPanel {
    private final GameBoard BOARD;
    private final int SCALE;

    private final BufferedImage MOUSE_ICON;
    private final BufferedImage CAT_ICON;
    private final BufferedImage WOLF_ICON;
    private final BufferedImage DOG_ICON;
    private final BufferedImage LEOPARD_ICON;
    private final BufferedImage TIGER_ICON;
    private final BufferedImage LION_ICON;
    private final BufferedImage ELEPHANT_ICON;

    private final BufferedImage TRAP_TEXTURE;

    /**
     * Constructs the object with the given dimension and board
     *
     * @param dimension the width and height of the board display
     * @param board the board to be displayed
     * @throws IllegalArgumentException if the specified dimension or null are null, or the specified
     * dimension contains negative integers
     *
     * @since 2.1
     * @see GameBoard
     */
    public DisplayBoard(Dimension dimension, GameBoard board) throws IllegalArgumentException{
        if (dimension == null || board == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        try {
            TRAP_TEXTURE = ImageIO.read(new File("C:\\Users\\MY PC\\Downloads\\trap.png"));
            MOUSE_ICON = ImageIO.read(new File("src\\Mouse.png"));
            CAT_ICON = ImageIO.read(new File("src\\Cat.png"));
            WOLF_ICON = ImageIO.read(new File("src\\Wolf.png"));
            DOG_ICON = ImageIO.read(new File("src\\Dog.png"));
            LEOPARD_ICON = ImageIO.read(new File("src\\Leopard.png"));
            TIGER_ICON = ImageIO.read(new File("src\\Tiger.png"));
            LION_ICON = ImageIO.read(new File("src\\Lion.png"));
            ELEPHANT_ICON = ImageIO.read(new File("src\\Elephant.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.BOARD = board;
        SCALE = Math.min(dimension.height / board.getRows(), dimension.width / board.getColumns());

        setPreferredSize(dimension);
    }

    /**
     * Draws the specified game board
     * @param g the <code>Graphics</code> object to protect
     *
     * @since 2.1
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        BoardCell[][] board = BOARD.getBoard();
        int rows = BOARD.getRows();
        int cols = BOARD.getColumns();
        int diameter = (int) (SCALE * .60);
        int radius = diameter / 2;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // draw the details in the board
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                // set the color to the tile to be drawn
                if (board[y][x].getTile().getType() == Tiles.ANIMAL_DEN)
                    g2d.setColor(board[y][x].getTile().getPlayer().getColor());
                else
                    g2d.setColor(board[y][x].getTile().getType().COLOR);

                g2d.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);

                if (board[y][x].getTile().getType() == Tiles.TRAP)
                    g2d.drawImage(TRAP_TEXTURE, x * SCALE, y * SCALE, SCALE, SCALE, null);

                // draw the piece
                if (board[y][x].getPiece() != null) {
                    int xPos = x * SCALE + SCALE / 2 - radius;
                    int yPos = y * SCALE + SCALE / 2 - radius;
                    int imageScale = (int) (radius * Math.sqrt(2));
                    int imageX = x * SCALE + SCALE / 2 - imageScale / 2;
                    int imageY = y * SCALE + SCALE / 2 - imageScale / 2;

                    g2d.setColor(board[y][x].getPiece().getPlayer().getColor());
                    g2d.fillOval(xPos, yPos, diameter, diameter);
                    g2d.drawImage(getAnimalIcon(board[y][x].getPiece()), imageX, imageY, imageScale, imageScale, null);
                }
            }
        }

        // set the color for the grid lines
        g2d.setColor(Color.DARK_GRAY);

        // Change the thickness of the line
        g2d.setStroke(new BasicStroke(2));

        // draw the vertical grid lines
        for (int x = 0; x <= cols; x++) {
            int xPos = x * SCALE;
            g2d.drawLine(xPos, 0, xPos, rows * SCALE);
        }

        // draw the horizontal grid lines
        for (int y = 0; y <= rows; y++) {
            int yPos = y * SCALE;
            g2d.drawLine(0, yPos, cols * SCALE, yPos);
        }
    }

    /**
     * A helper method that returns the corresponding image for the specified image
     *
     * @param piece the piece to be converted
     * @return the image representation of the animal piece
     * @throws IllegalStateException if the specified piece is not one of the eight pieces in
     * the game Animal Chess
     *
     * @since 2.3
     * @see AnimalPiece
     */
    private BufferedImage getAnimalIcon(AnimalPiece piece) throws IllegalStateException{
        if (piece == null)
            return null;

        return switch (piece.pieceName().toLowerCase().trim()) {
            case "mouse" -> MOUSE_ICON;
            case "cat" -> CAT_ICON;
            case "wolf" -> WOLF_ICON;
            case "dog" -> DOG_ICON;
            case "leopard" -> LEOPARD_ICON;
            case "tiger" -> TIGER_ICON;
            case "lion" -> LION_ICON;
            case "elephant" -> ELEPHANT_ICON;
            default -> throw new IllegalStateException("Unexpected value: " + piece.pieceName().toLowerCase().trim());
        };
    }
}
