import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A JPanel that would contain the visual representation of the game "Animal Chess".
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.7 7/29/2026
 * @since 2.1
 */
public class DisplayBoard extends JPanel{
    /**
     * Represents the color of the grid lines to be drawn
     *
     * @since 2.7
     */
    private static final Color GRID_COLOR = Color.DARK_GRAY;

    /**
     * Represents the color the square to be drawn would take which represents the current position of
     * the cursor relative to the grid
     *
     * @since 2.7
     */
    private static final Color HOVER_COLOR = Color.GRAY;

    /**
     * Represents the color the square to be drawn would take which represents the current position of
     * the selected cell
     *
     * @since 2.7
     */
    private static final Color HIGHLIGHT_COLOR = Color.LIGHT_GRAY;

    /**
     * Represents the thickness of the grid lines
     *
     * @since 2.7
     */
    private static final int GRID_LINE_THICKNESS = 2;

    /**
     * Represents the color to be displayed as a background for icons/text to be more legible
     *
     * @since 2.7
     */
    private static final Color NEUTRAL_BACKGROUND = new Color(200, 170, 143);

    /**
     * Represents the color to be displayed as a border/outline for the piece to be drawn
     *
     * @since 2.7
     */
    private static final Color PIECE_OUTLINE_COLOR = Color.DARK_GRAY;

    /**
     * Represents the thickness of the border/outline of the piece to be drawn
     *
     * @since 2.7
     */
    private static final int PIECE_OUTLINE_THICKNESS = 1;

    /**
     * Represents the scale of each piece relative to the scale of a cell
     *
     * @since 2.7
     */
    private static final double PIECE_SCALE_RATIO = .60;

    /**
     * Represents the scale of a circle inside the piece that would contain the piece's icon and rank
     *
     * @since 2.7
     */
    private static final double PIECE_BACKGROUND_RATIO = .75;

    /**
     * Represents the scale of the numerical rank to be displayed besides the icon relative to the {@link #PIECE_BACKGROUND_RATIO}
     *
     * @since 2.7
     */
    private static final double PIECE_RANK_RATIO = .25;

    /**
     * Represents the font the numerical rank to be drawn would take
     *
     * @since 2.7
     */
    private static final String PIECE_RANK_FONT = "Arial";

    /**
     * Represents the color to be displayed when drawing where pieces can move
     *
     * @since 2.7
     */
    private static final Color VALID_MOVE_COLOR = Color.GRAY;

    /**
     * Represents the scale of a filled circle that visually indicates a possible move of the player relative
     * to the scale of each cell
     *
     * @since 2.7
     */
    private static final double VALID_MOVE_DOT_RATIO = .25;

    /**
     * Represents the scale of a circle that would surround a piece if it can be captured, its scale is relative to
     * the scale of each cell
     *
     * @since 2.7
     */
    private static final double VALID_CAPTURE_RING_RATIO = .80;

    /**
     * Represents the thickness of the circle to be displayed when a piece can be captured
     *
     * @since 2.7
     */
    private static final int CAPTURE_RING_THICKNESS = 4;

    /**
     * Represents the scale of a filled square to be drawn inside a tile
     *
     * @since 2.7
     */
    private static final double TILE_BACKGROUND_RATIO = .80;

    /**
     * Contains the controller which would provide the details to be displayed from the model
     *
     * @since 2.7
     * @see GameController
     */
    private final GameController CONTROL;

    /**
     * Contains the different icons for each tile and pieces
     *
     * @since 2.7
     * @see AssetsManager
     */
    private final AssetsManager ASSETS;

    /**
     * Represents the total amount of rows to be displayed
     *
     * @since 2.7
     */
    private final int ROWS;

    /**
     * Represents the total amount of columns to be displayed
     *
     * @since 2.7
     */
    private final int COLUMNS;

    /**
     * Represents the width and height of each cell
     *
     * @since 2.7
     */
    private final int SCALE;

    /**
     * Represents the moves to be displayed
     *
     * @since 2.7
     * @see BoardInputHandler
     */
    private Point[] moves;

    /**
     * Represents the position of the cursor within this panel with the position starting from the top-left
     *
     * @since 2.7
     * @see BoardInputHandler
     */
    private Point cursor;

    /**
     * Represents the position of the cell that is currently selected by the player
     *
     * @since 2.7
     * @see BoardInputHandler
     */
    private Point selected;

    /**
     * Contains the value if the user is currently dragging with the mouse or not
     *
     * @since 2.7
     * @see BoardInputHandler
     */
    private boolean isDragged;

    /**
     * Constructs the board display with the specified dimension, controller, and assets objects.
     *
     * @param dimension the width and height of the board display
     * @param controller the source of the data to be displayed
     * @param assets the source for the icons to be used for the tiles and pieces
     * @throws NullPointerException if the specified arguments are null
     * @throws IllegalArgumentException if the specified dimension contain negative value(s)
     *
     * @since 2.7
     * @see GameController
     * @see AssetsManager
     */
    DisplayBoard(Dimension dimension, GameController controller, AssetsManager assets) {
        if (dimension == null || controller == null || assets == null)
            throw new NullPointerException("The arguments cannot be null");

        if (dimension.width < 0 || dimension.height < 0)
            throw new IllegalArgumentException("The specified dimension cannot be negative");

        CONTROL = controller;
        ASSETS = assets;

        ROWS = CONTROL.getRow();
        COLUMNS = CONTROL.getColumn();
        SCALE = Math.min(dimension.height / ROWS, dimension.width / COLUMNS);
        moves = new Point[0];

        setPreferredSize(dimension);

        BoardInputHandler inputHandler = new BoardInputHandler(this);
        addMouseListener(inputHandler);
        addMouseMotionListener(inputHandler);
    }

    /**
     * Responsible for drawing all the details such as the tiles, pieces, and moves
     *
     * @param g the <code>Graphics</code> object to protect
     *
     * @since 2.7
     * @see #drawGrid(Graphics, int, int)
     * @see #drawMove(Graphics, int, int)
     * @see #drawHighlight(Graphics, Color, int, int)
     * @see #drawPiece(Graphics, int, int, int, int)
     * @see #drawTile(Graphics, int, int)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int selectedRow = (selected == null) ? -1 : selected.y;
        int selectedColumn = (selected == null) ? -1 : selected.x;
        int cursorX = (cursor == null) ? -1 : cursor.x / SCALE * SCALE;
        int cursorY = (cursor == null) ? -1 : cursor.y / SCALE * SCALE;

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                drawTile(g, row, column);

                if (row == selectedRow && column == selectedColumn && isDragged){
                    continue;
                }

                int posX = column * SCALE;
                int posY = row * SCALE;
                drawPiece(g, row, column, posX, posY);
            }
        }

        drawGrid(g, ROWS, COLUMNS);
        drawHighlight(g, HOVER_COLOR, cursorX, cursorY);

        if (selected != null) {
            drawHighlight(g, HIGHLIGHT_COLOR, selectedColumn * SCALE, selectedRow * SCALE);
            for (Point move : moves) {
                drawMove(g, move.y, move.x);
            }
        }

        if (isDragged && selected != null && cursor != null) {
            int posX = cursor.x - SCALE / 2;
            int posY = cursor.y - SCALE / 2;
            drawPiece(g, selectedRow, selectedColumn, posX, posY);
        }
    }

    /**
     * A helper method to get the position needed for the component to be drawn in the middle of the cell
     *
     * @param pos the x or y position where the component would be drawn
     * @param scale the width/height of the component
     * @return the position to draw the component in the center of the cell
     *
     * @since 2.7
     */
    private int getCentralPos(int pos, int scale) {
        return pos + SCALE / 2 - scale / 2;
    }

    /**
     * Draws the visual representation of what move can a piece can make, whether to move represented by a filled circle,
     * or to capture represented by a hollow circle. This assumes that the piece can actually move to the specified location
     *
     * @param g the graphics object that would draw the details
     * @param row the row where the move would be drawn
     * @param column the column where the move would be drawn
     *
     * @since 2.7
     */
    private void drawMove(Graphics g, int row, int column) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setColor(VALID_MOVE_COLOR);

        final int captureScale = (int) (SCALE * VALID_CAPTURE_RING_RATIO);
        final int moveScale = (int) (SCALE * VALID_MOVE_DOT_RATIO);
        int x = column * SCALE;
        int y = row * SCALE;

        if (CONTROL.doesPieceExistAt(row, column)) {
            x = getCentralPos(x, captureScale);
            y = getCentralPos(y, captureScale);

            g2d.setStroke(new BasicStroke(CAPTURE_RING_THICKNESS));
            g2d.drawOval(x, y, captureScale, captureScale);
        }
        else {
            x = getCentralPos(x, moveScale);
            y = getCentralPos(y, moveScale);

            g2d.fillOval(x, y, moveScale, moveScale);
        }
    }

    /**
     * Draws the visual representation of a single tile which is a filled square with an inner square and an icon if applicable
     *
     * @param g the graphics object that would draw the details
     * @param row the row where the tile would be drawn
     * @param column the column where the move would be drawn
     *
     * @since 2.7
     */
    private void drawTile(Graphics g, int row, int column) {
        Graphics2D g2d = (Graphics2D) g;

        Color color = CONTROL.getTileColorAt(row, column);
        int scale = SCALE;
        int posX = column * scale;
        int posY = row * scale;

        if (CONTROL.isTilePlayerOwnedAt(row, column)) {
            g2d.setColor(color);
            g2d.fillRect(posX, posY, scale, scale);

            scale = (int) (scale * TILE_BACKGROUND_RATIO);
            posX = getCentralPos(posX, scale);
            posY = getCentralPos(posY, scale);

            g2d.setColor(NEUTRAL_BACKGROUND);
            g2d.fillRect(posX, posY, scale, scale);
        }
        else {
            g2d.setColor(color);
            g2d.fillRect(posX, posY, scale, scale);
        }

        scale = (int) (scale * TILE_BACKGROUND_RATIO);
        posX = getCentralPos(column * SCALE, scale);
        posY = getCentralPos(row * SCALE, scale);
        g2d.drawImage(ASSETS.getTileIcon(CONTROL.getTileAt(row, column)), posX, posY, scale, scale, null);
    }

    /**
     * Draws the visual representation of a single piece which is a filled circle with an inner circle and an outline.
     * The outer circle would represent which player controls it, while the inner circle would contain the piece icon and rank.
     *
     * @param g the graphics object that would draw the details
     * @param row the row where the piece would be drawn
     * @param column the column where the piece would be drawn
     *
     * @since 2.7
     */
    private void drawPiece(Graphics g, int row, int column, int posX, int posY) {
        if (!CONTROL.doesPieceExistAt(row, column))
            return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        final int pieceScale = (int) (SCALE * PIECE_SCALE_RATIO);
        final int backgroundScale = (int) (pieceScale * PIECE_BACKGROUND_RATIO);
        final int imageScale = (int) (backgroundScale / 2.0 * Math.sqrt(2.0)); // find the maximal square in a circle
        final int rankScale = (int) (imageScale * PIECE_RANK_RATIO);

        int x = getCentralPos(posX, pieceScale);
        int y = getCentralPos(posY, pieceScale);

        g2d.setColor(CONTROL.getPieceColorAt(row, column));
        g2d.fillOval(x, y, pieceScale, pieceScale);

        g2d.setColor(PIECE_OUTLINE_COLOR);
        g2d.setStroke(new BasicStroke(PIECE_OUTLINE_THICKNESS));
        g2d.drawOval(x, y, pieceScale, pieceScale);

        x = getCentralPos(posX, backgroundScale);
        y = getCentralPos(posY, backgroundScale);

        g2d.setColor(NEUTRAL_BACKGROUND);
        g2d.fillOval(x, y, backgroundScale, backgroundScale);

        x = getCentralPos(posX, imageScale);
        y = getCentralPos(posY, imageScale);

        BufferedImage icon = ASSETS.getAnimalIcon(CONTROL.getPieceNameAt(row, column));
        g2d.drawImage(icon, x, y, imageScale, imageScale, null);

        x = posX + SCALE / 2 + (int) (imageScale * (1 - PIECE_RANK_RATIO) / 2.0);
        y = posY + SCALE / 2 + (int) (imageScale * (1 - PIECE_RANK_RATIO) / 2.0);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(PIECE_RANK_FONT, Font.BOLD, rankScale));
        g2d.drawString(CONTROL.getPieceRankAt(row, column), x, y);
    }

    /**
     * Draws the visual separator to differentiate different cells from one another. This would draw
     * horizontal and vertical lines depending on the number of rows and columns respectively.
     *
     * @param g the graphics object that would draw the details
     * @param rows the amount of rows
     * @param columns the amount of columns
     *
     * @since 2.7
     */
    private void drawGrid(Graphics g, int rows, int columns) {
        Graphics2D g2d = (Graphics2D) g;
        int maxX = columns * SCALE;
        int maxY = rows * SCALE;

        g2d.setColor(GRID_COLOR);
        g2d.setStroke(new BasicStroke(GRID_LINE_THICKNESS));

        for (int i = 0; i < rows; i++) {
            g2d.drawLine(0, i * SCALE, maxX, i * SCALE);
        }

        for (int i = 0; i < columns; i++) {
            g2d.drawLine(i * SCALE, 0, i * SCALE, maxY);
        }
    }

    /**
     * Draws a visual representation of a highlight which is a hollow square
     *
     * @param g the graphics object that would draw the details
     * @param color the color the hollow square would take
     * @param x the position in the x-axis where the square would be drawn
     * @param y the position in the y-axis where the square would be drawn
     *
     * @since 2.7
     */
    private void drawHighlight(Graphics g, Color color, int x, int y) {
        if (x < 0 || y < 0)
            return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(GRID_LINE_THICKNESS));

        g2d.drawRect(x, y, SCALE, SCALE);
    }

    /**
     * A getter method for the moves that are currently being displayed
     *
     * @return an array of Points that represents all the current moves
     *
     * @since 2.7
     */
    public Point[] getMoves() {
        return moves;
    }

    /**
     * A getter method for the scale of each cell
     *
     * @return the width and height of each cell
     *
     * @since 2.7
     */
    public int getScale() {
        return SCALE;
    }

    /**
     * A getter method for the position of the cell that is currently selected
     *
     * @return the column and row position of the cell currently selected
     */
    public Point getSelected() {
        return selected;
    }

    /**
     * A getter method for the controller that serves as a translator between the view and model
     *
     * @return the controller of this instance
     *
     * @since 2.7
     */
    public GameController getController() {
        return CONTROL;
    }

    /**
     * A getter method for whether the user is dragging their mouse
     *
     * @return true if the user is currently dragging the mouse, false otherwise
     *
     * @since 2.7
     */
    public boolean isDragged() {
        return isDragged;
    }

    /**
     * A setter method to change the state of the field indicating whether the user is dragging their mouse or not
     *
     * @param state the new state of this field
     *
     * @since 2.7
     */
    public void setDragged(boolean state) {
        isDragged = state;
    }

    /**
     * A setter method to change the previous moves with the specified array of moves
     *
     * @param moves the new moves to replace the old moves
     *
     * @since 2.7
     */
    public void setMoves(Point[] moves) {
        this.moves = moves;
    }

    /**
     * A setter method to change the previous selected cell to the specified cell
     *
     * @param position the column and row position of the new selected cell
     *
     * @since 2.7
     */
    public void setSelected(Point position) {
        selected = position;
    }

    /**
     * A setter method to change the cursor position to reflect the current position of the cursor
     *
     * @param position the x and y position of the cursor
     *
     * @since 2.7
     */
    public void setCursor(Point position) {
        cursor = position;
    }
}
