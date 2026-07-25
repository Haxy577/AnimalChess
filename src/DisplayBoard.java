import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * A JPanel that would contain the visual representation of the game "Animal Chess".
 *
 * @see GameBoard
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.5 7/24/2026
 * @since 2.1
 */
public class DisplayBoard extends JPanel implements MouseListener, MouseMotionListener {
    private final int ROW;
    private final int COLUMN;
    private final Controller CONTROL;
    private final int SCALE;

    private final Color NEUTRAL;
    private final Color LINE;

    private Point cursor;
    private Point selected;
    private final AssetsManager ASSETS;

    private boolean wasSelectedBefore;
    private boolean isDragged;

    DisplayBoard(Dimension dimension, Controller controller, AssetsManager assets) {
        CONTROL = controller;
        ROW = CONTROL.getRow();
        COLUMN = CONTROL.getColumn();
        SCALE = Math.min(dimension.height / ROW, dimension.width / COLUMN);
        NEUTRAL = new Color(200, 170, 143);
        LINE = Color.DARK_GRAY;
        ASSETS = assets;

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        final int tileBorder = 10;
        final int pieceScale = (int) (SCALE * .60);
        final int playerScale = (int) (pieceScale * .98);
        final int backgroundScale = (int) (playerScale * .75);

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                drawTile(g, row, col, SCALE, tileBorder);

                if (new Point(col, row).equals(snapToCell(selected)) && isDragged){
                    continue;
                }

                int posX = col * SCALE;
                int posY = row * SCALE;
                drawPiece(g, posX, posY, row, col, SCALE, pieceScale, playerScale, backgroundScale);
            }
        }

        drawGrid(g, ROW, COLUMN, SCALE);
        drawHighlight(g, Color.GRAY, cursor, SCALE);

        if (selected != null) {
            drawHighlight(g, Color.LIGHT_GRAY, selected, SCALE);
        }

        if (isDragged && selected != null) {
            int posX = cursor.x - pieceScale;
            int posY = cursor.y - pieceScale;
            int row = selected.y / SCALE;
            int column = selected.x / SCALE;
            drawPiece(g, posX, posY, row, column, SCALE, pieceScale, playerScale, backgroundScale);
        }
    }

    private void drawTile(Graphics g, int row, int column, int scale, int border) {
        Graphics2D g2d = (Graphics2D) g;
        int posX = column * scale;
        int posY = row * scale;

        Color color = CONTROL.getTileColorAt(row, column);

        if (CONTROL.isTilePlayerOwnedAt(row, column)) {
            g2d.setColor(color);
            g2d.fillRect(posX, posY, scale, scale);

            posX += border;
            posY += border;
            scale -= border * 2;

            g2d.setColor(NEUTRAL);
            g2d.fillRect(posX, posY, scale, scale);
        }
        else {
            g2d.setColor(color);
            g2d.fillRect(posX, posY, scale, scale);
        }

        int imageScale = scale - border * 2;
        g2d.drawImage(ASSETS.getTileIcon(CONTROL.getTileAt(row, column)), posX + border, posY + border, imageScale, imageScale, null);
    }

    private void drawPiece(Graphics g, int x, int y, int row, int column, int cellScale, int pieceScale, int playerScale, int backgroundScale) {
        if (pieceScale < playerScale || playerScale < backgroundScale)
            throw new IllegalArgumentException();

        if (!CONTROL.doesPieceExistAt(row, column))
            return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        int outlineX = x + cellScale / 2 - pieceScale / 2;
        int outlineY = y + cellScale / 2 - pieceScale / 2;
        g2d.setColor(LINE);
        g2d.fillOval(outlineX, outlineY, pieceScale, pieceScale);

        int playerX = x + cellScale / 2 - playerScale / 2;
        int playerY = y + cellScale / 2 - playerScale / 2;
        g2d.setColor(CONTROL.getPieceColorAt(row, column));
        g2d.fillOval(playerX, playerY, playerScale, playerScale);

        int backgroundX = x + cellScale / 2 - backgroundScale / 2;
        int backgroundY = y + cellScale / 2 - backgroundScale / 2;
        g2d.setColor(NEUTRAL);
        g2d.fillOval(backgroundX, backgroundY, backgroundScale, backgroundScale);

        int imageScale = (int) (backgroundScale / 2.0 * Math.sqrt(2.0));
        int imageX = x + cellScale / 2 - imageScale / 2;
        int imageY = y + cellScale / 2 - imageScale / 2;
        BufferedImage icon = ASSETS.getAnimalIcon(CONTROL.getPieceNameAt(row, column));
        g2d.drawImage(icon, imageX, imageY, imageScale, imageScale, null);

        int rankScale = (int) (imageScale * .25);
        int rankX = x + cellScale / 2 + (int) (imageScale * .75) / 2;
        int rankY = y + cellScale / 2 + (int) (imageScale * .75) / 2;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, rankScale));
        g2d.drawString(CONTROL.getPieceRankAt(row, column), rankX, rankY);
    }

    private void drawGrid(Graphics g, int rows, int columns, int scale) {
        Graphics2D g2d = (Graphics2D) g;
        int maxX = columns * scale;
        int maxY = rows * scale;

        g2d.setColor(LINE);
        g2d.setStroke(new BasicStroke(2));

        for (int i = 0; i < rows; i++) {
            g2d.drawLine(0, i * scale, maxX, i * scale);
        }

        for (int i = 0; i < columns; i++) {
            g2d.drawLine(i * scale, 0, i * scale, maxY);
        }
    }

    private void drawHighlight(Graphics g, Color color, Point pos, int scale) {
        if (pos == null)
            return;

        Graphics2D g2d = (Graphics2D) g;
        int posX = pos.x / scale * scale;
        int posY = pos.y / scale * scale;

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));

        g2d.drawRect(posX, posY, scale, scale);
    }

    private Point snapToGrid(Point p) {
        if (p == null) return null;
        int x = p.x / SCALE * SCALE;
        int y = p.y / SCALE * SCALE;
        return new Point(x, y);
    }

    private Point snapToCell(Point p) {
        if (p == null) return null;
        int x = p.x / SCALE;
        int y = p.y / SCALE;
        return new Point(x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point pressedPoint = snapToGrid(e.getPoint());
        wasSelectedBefore = pressedPoint.equals(selected);

        if (!wasSelectedBefore)
            selected = pressedPoint;

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point release = snapToGrid(e.getPoint());

        if (!isDragged) {
            if (wasSelectedBefore && release.equals(selected)) {
                selected = null;
            }
        }

        isDragged = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        cursor = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        isDragged = true;
        mouseMoved(e);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        cursor = e.getPoint();
        repaint();
    }
}
