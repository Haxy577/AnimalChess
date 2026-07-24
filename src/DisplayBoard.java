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
 * @version 2.4 7/24/2026
 * @since 2.1
 */
public class DisplayBoard extends JPanel {
    private final GameBoard BOARD;
    private final int SCALE;
    private final AssetsManager ASSETS;
    private BoardCell[] moves;

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
    public DisplayBoard(AssetsManager assets, Dimension dimension, GameBoard board) throws IllegalArgumentException{
        if (dimension == null || board == null)
            throw new IllegalArgumentException("The parameters cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The given dimension can only contain positive values");

        BOARD = board;
        SCALE = Math.min(dimension.height / board.getRows(), dimension.width / board.getColumns());
        ASSETS = assets;
        moves = null;

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
        int outerDiameter = (int) (SCALE * .60);
        int outerRadius = outerDiameter / 2;
        int middleDiameter = (int) (SCALE * .58);
        int middleRadius = middleDiameter / 2;
        int innerDiameter = (int) (SCALE * .50);
        int innerRadius = innerDiameter / 2;
        int border = 10;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // draw the details in the board
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                final BoardCell cell = board[y][x];
                final BoardTile tile = cell.getTile();
                final AnimalPiece piece = cell.getPiece();
                final Color neutralBackground = new Color(200, 170, 143);

                int tileX = x * SCALE;
                int tileY = y * SCALE;
                int tileIconScale = SCALE - border * 2;
                int iconX = tileX + border;
                int iconY = tileY + border;

                drawTile(g, cell, neutralBackground, SCALE, border);

                if (tile.getType() != Tiles.LAND)
                    g2d.drawImage(ASSETS.getTileIcon(tile.getType()), iconX, iconY, tileIconScale, tileIconScale, null);

                // draw the piece
                drawPiece(g, cell, neutralBackground, SCALE, (int) (SCALE * .60));
            }
        }

        g2d.setColor(Color.DARK_GRAY);

        if (moves != null) {
            for (BoardCell move : moves) {
                if (move == null)
                    continue;

                int x = move.getCol();
                int y = move.getRow();
                int moveDiameter = (int) (SCALE * .25);
                int outerCapture = (int) (SCALE * .70);
                int innerCapture = (int) (SCALE * .67);

                if (move.getPiece() != null) {
                    g2d.fillOval(x, y, moveDiameter, moveDiameter);
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

    private void drawTile(Graphics g, BoardCell cell, Color neutralBackground, int scale, int border) {
        Graphics2D g2d = (Graphics2D) g;
        int x = cell.getCol() * scale;
        int y = cell.getRow() * scale;
        final BoardTile tile = cell.getTile();

        if (tile.getPlayer() != null) {
            g2d.setColor(tile.getPlayer().getColor());
            g2d.fillRect(x, y, scale, scale);

            x += border;
            y += border;
            int innerScale = scale - border * 2;

            g2d.setColor(neutralBackground);
            g2d.fillRect(x, y, innerScale, innerScale);
        } else {
            g2d.setColor(tile.getType().COLOR);
            g2d.fillRect(x, y, scale, scale);
        }
    }

    /**
     *
     * @param g
     * @param cell
     * @param neutralBackground
     * @param cellScale the scale of the cell
     * @param pieceScale the diameter of the piece
     */
    private void drawPiece(Graphics g, BoardCell cell, Color neutralBackground, int cellScale, int pieceScale) {
        if (cell.getPiece() == null)
            return;

        Graphics2D g2d = (Graphics2D) g;
        AnimalPiece piece = cell.getPiece();

        int x = cell.getCol() * cellScale;
        int y = cell.getRow() * cellScale;
        int midX = x + cellScale / 2;
        int midY = y + cellScale / 2;

        int playerDiameter = (int) (pieceScale * .98);
        int backgroundDiameter = (int) (playerDiameter * .80);

        int posX = midX - pieceScale / 2;
        int posY = midY - pieceScale / 2;
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(posX, posY, pieceScale, pieceScale);

        posX = midX - playerDiameter / 2;
        posY = midY - playerDiameter / 2;
        g2d.setColor(cell.getPiece().getPlayer().getColor());
        g2d.fillOval(posX, posY, playerDiameter, playerDiameter);

        posX = midX - backgroundDiameter / 2;
        posY = midY - backgroundDiameter / 2;
        g2d.setColor(neutralBackground);
        g2d.fillOval(posX, posY, backgroundDiameter, backgroundDiameter);

        int imageScale = (int) (playerDiameter / 2.0 * Math.sqrt(2));
        int imageX = midX - imageScale / 2;
        int imageY = midY - imageScale / 2;
        g2d.drawImage(ASSETS.getAnimalIcon(piece), imageX, imageY, imageScale, imageScale, null);
    }

    public void setMoves(BoardCell[] moves) {
        this.moves = moves;
    }
}
