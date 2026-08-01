import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BoardController implements ViewController {

    private final MainController CONTROL;
    private Displayer VIEW;
    private final GameState STATE;

    /**
     * Represents the list of moves the selected cell could do
     *
     * @since 2.7
     * @see BoardInputHandler
     */
    private List<BoardCell> selectedMoves;

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

    private boolean wasSelectedBefore;

    /**
     * Contains the value if the user is currently dragging with the mouse or not
     *
     * @since 2.7
     * @see BoardInputHandler
     */
    private boolean isDragged;

    /**
     * Contains the different icons for each tile and pieces
     *
     * @since 2.7
     * @see AssetsManager
     */
    private final AssetsManager ASSETS;

    /**
     * Represents the width and height of each cell
     *
     * @since 2.7
     */
    private final int SCALE;

    public BoardController(MainController controller, Dimension area, GameState state) {
        CONTROL = controller;
        STATE = state;

        ASSETS = new AssetsManager();

        int rows = STATE.getBoard().getRows();
        int columns = STATE.getBoard().getColumns();
        SCALE = Math.min(area.height / rows, area.width / columns);
    }

    @Override
    public void render(Renderer renderer) {
        renderer.fillBackground(DisplayConstants.BACKGROUND);

        int rows = STATE.getBoard().getRows();
        int columns = STATE.getBoard().getColumns();

        int selectedRow = (selected == null) ? -1 : selected.y;
        int selectedColumn = (selected == null) ? -1 : selected.x;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                drawTile(renderer, row, col);

                if (isDragged && selectedRow == row && selectedColumn == col)
                    continue;

                int x = col * SCALE;
                int y = row * SCALE;
                drawPiece(renderer, row, col, x, y);
            }
        }

        drawGrid(renderer, rows, columns);

        if (cursor != null) {
            int x = cursor.x / SCALE * SCALE;
            int y = cursor.y / SCALE * SCALE;
            drawHighlight(renderer, DisplayConstants.HOVER_COLOR, x, y);
        }

        if (selected != null) {
            int row = selected.y;
            int column = selected.x;

            drawHighlight(renderer, DisplayConstants.HIGHLIGHT_COLOR, column * SCALE, row * SCALE);
        }

        if (selectedMoves != null && !selectedMoves.isEmpty()) {
            for (BoardCell move : selectedMoves) {
                int row = move.getRow();
                int col = move.getCol();

                drawMove(renderer, row, col);
            }
        }

        if (isDragged && selected != null && cursor != null) {
            int posX = cursor.x - SCALE / 2;
            int posY = cursor.y - SCALE / 2;
            drawPiece(renderer, selectedRow, selectedColumn, posX, posY);
        }
    }

    private Point snapToCell(int x, int y) {
        return new Point(x / SCALE, y / SCALE);
    }

    private boolean existInMoves(BoardCell cell) {
        if (cell == null || selectedMoves == null) return false;

        for (BoardCell move : selectedMoves) {
            if (cell.equals(move))
                return true;
        }

        return false;
    }

    private void clearSelection() {
        selected = null;
        selectedMoves = new ArrayList<>();
    }

    private void displayWinner() {
        Player winner = STATE.getWinner();
        if (winner != null) {
            CONTROL.showResults(winner, STATE.getPlayer1(), STATE.getPlayer2());
        }
    }

    /**
     * A helper method to check whether the specified point is outside the bounds of the component
     *
     * @return true if the position is outside the bounds of the component, false otherwise
     *
     * @since 2.7
     */
    private boolean isOutsideBoard(int x, int y) {
        int maxX = STATE.getBoard().getColumns() * SCALE;
        int maxY = STATE.getBoard().getRows() * SCALE;
        return x < 0 || x > maxX || y < 0 || y > maxY;
    }

    public void handlePressEvent(int x, int y) {
        if (isOutsideBoard(x, y)) {
            handleMouseExit();
            return;
        }

        Point pressed = snapToCell(x, y);
        BoardCell target = STATE.getCellAt(pressed.y, pressed.x);
        wasSelectedBefore = pressed.equals(selected);

        if (existInMoves(target) && selected != null) {
            BoardCell source = STATE.getCellAt(selected.y, selected.x);

            if (STATE.attemptMove(source.getRow(), source.getCol(), target.getRow(), target.getCol())) {
                clearSelection();
                displayWinner();
            }
        }
        else if (!wasSelectedBefore) {
            selected = pressed;
            selectedMoves = STATE.getMovesAt(target);
        }

        VIEW.refresh();
    }

    public void handleReleaseEvent(int x, int y) {
        if (isOutsideBoard(x, y))
            return;

        Point release = snapToCell(x, y);

        if (isDragged && selected != null) {
            if (STATE.attemptMove(selected.y, selected.x, release.y, release.x)) {
                clearSelection();
                displayWinner();
            }
        }
        else if (selected != null){
            if (wasSelectedBefore && release.equals(selected)) {
                clearSelection();
            }
        }

        isDragged = false;
        VIEW.refresh();
    }

    public void handleDragEvent(int x, int y) {
        if (isOutsideBoard(x, y)) {
            handleMouseExit();
            return;
        }

        isDragged = true;
        handleMouseMovement(x, y);
    }

    public void handleMouseMovement(int x, int y) {
        if (isOutsideBoard(x, y)) {
            handleMouseExit();
            return;
        }

        cursor = new Point(x, y);
        VIEW.refresh();
    }

    public void handleMouseExit() {
        cursor = null;
        isDragged = false;
        VIEW.refresh();
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
     * Draws the visual representation of a single tile which is a filled square with an inner square and an icon if applicable
     *
     * @param row the row where the tile would be drawn
     * @param column the column where the move would be drawn
     *
     * @since 2.7
     */
    private void drawTile(Renderer renderer, int row, int column) {
        BoardTile tile = STATE.getCellAt(row, column).getTile();

        int scale = SCALE;
        int posX = column * scale;
        int posY = row * scale;

        if (tile.getPlayer() != null) {
            renderer.solidRectangle(tile.getPlayer().getColor(), posX, posY, scale, scale);

            scale = (int) (scale * DisplayConstants.TILE_BACKGROUND_RATIO);
            posX = getCentralPos(posX, scale);
            posY = getCentralPos(posY, scale);

            renderer.solidRectangle(DisplayConstants.NEUTRAL_BACKGROUND, posX, posY, scale, scale);
        }
        else {
            renderer.solidRectangle(tile.getType().COLOR, posX, posY, scale, scale);
        }

        scale = (int) (scale * DisplayConstants.TILE_BACKGROUND_RATIO);
        posX = getCentralPos(column * SCALE, scale);
        posY = getCentralPos(row * SCALE, scale);

        renderer.drawImage(ASSETS.getTileIcon(tile.getType()), posX, posY, scale);
    }

    /**
     * Draws the visual representation of what move can a piece can make, whether to move represented by a filled circle,
     * or to capture represented by a hollow circle. This assumes that the piece can actually move to the specified location
     *
     * @param row the row where the move would be drawn
     * @param column the column where the move would be drawn
     *
     * @since 2.7
     */
    private void drawMove(Renderer renderer, int row, int column) {
        final int captureScale = (int) (SCALE * DisplayConstants.VALID_CAPTURE_RING_RATIO);
        final int moveScale = (int) (SCALE * DisplayConstants.VALID_MOVE_DOT_RATIO);
        int x = column * SCALE;
        int y = row * SCALE;

        if (STATE.doesPieceExistAt(row, column)) {
            x = getCentralPos(x, captureScale);
            y = getCentralPos(y, captureScale);

            renderer.hollowCircle(DisplayConstants.VALID_MOVE_COLOR, x, y, captureScale, DisplayConstants.CAPTURE_RING_THICKNESS);
        }
        else {
            x = getCentralPos(x, moveScale);
            y = getCentralPos(y, moveScale);

            renderer.solidCircle(DisplayConstants.VALID_MOVE_COLOR, x, y, moveScale);
        }
    }

    /**
     * Draws the visual representation of a single piece which is a filled circle with an inner circle and an outline.
     * The outer circle would represent which player controls it, while the inner circle would contain the piece icon and rank.
     *
     * @param row the row where the piece would be drawn
     * @param column the column where the piece would be drawn
     *
     * @since 2.7
     */
    private void drawPiece(Renderer renderer, int row, int column, int posX, int posY) {
        AnimalPiece piece = STATE.getCellAt(row, column).getPiece();

        if (piece == null)
            return;

        final int pieceScale = (int) (SCALE * DisplayConstants.PIECE_SCALE_RATIO);
        final int backgroundScale = (int) (pieceScale * DisplayConstants.PIECE_BACKGROUND_RATIO);
        final int imageScale = (int) (backgroundScale / 2.0 * Math.sqrt(2.0)); // find the maximal square in a circle
        final int rankScale = (int) (imageScale * DisplayConstants.PIECE_RANK_RATIO);

        int x = getCentralPos(posX, pieceScale);
        int y = getCentralPos(posY, pieceScale);

        Color color = piece.getPlayer().getColor();
        renderer.solidCircle(color, x, y, pieceScale);
        renderer.hollowCircle(DisplayConstants.PIECE_OUTLINE_COLOR, x, y, pieceScale, DisplayConstants.PIECE_OUTLINE_THICKNESS);

        x = getCentralPos(posX, backgroundScale);
        y = getCentralPos(posY, backgroundScale);

        renderer.solidCircle(DisplayConstants.NEUTRAL_BACKGROUND, x, y, backgroundScale);

        x = getCentralPos(posX, imageScale);
        y = getCentralPos(posY, imageScale);

        BufferedImage icon = ASSETS.getAnimalIcon(piece.pieceName());
        renderer.drawImage(icon, x, y, imageScale);

        x = posX + SCALE / 2 + (int) (imageScale * (1 - DisplayConstants.PIECE_RANK_RATIO) / 2.0);
        y = posY + SCALE / 2 + (int) (imageScale * (1 - DisplayConstants.PIECE_RANK_RATIO) / 2.0);

        Font font = new Font(DisplayConstants.PIECE_RANK_FONT, Font.BOLD, rankScale);
        renderer.drawString(Integer.toString(piece.getRank()), DisplayConstants.PIECE_RANK_COLOR, font, x, y);
    }

    /**
     * Draws the visual separator to differentiate different cells from one another. This would draw
     * horizontal and vertical lines depending on the number of rows and columns respectively.
     *
     * @param rows the amount of rows
     * @param columns the amount of columns
     *
     * @since 2.7
     */
    private void drawGrid(Renderer renderer, int rows, int columns) {
        int maxX = columns * SCALE;
        int maxY = rows * SCALE;

        for (int i = 0; i < rows; i++) {
            renderer.drawLine(DisplayConstants.GRID_COLOR, 0, i * SCALE, maxX, i * SCALE, DisplayConstants.GRID_LINE_THICKNESS);
        }

        for (int i = 0; i < columns; i++) {
            renderer.drawLine(DisplayConstants.GRID_COLOR, i * SCALE, 0, i * SCALE, maxY, DisplayConstants.GRID_LINE_THICKNESS);
        }
    }

    /**
     * Draws a visual representation of a highlight which is a hollow square
     *
     * @param color the color the hollow square would take
     * @param x the position in the x-axis where the square would be drawn
     * @param y the position in the y-axis where the square would be drawn
     *
     * @since 2.7
     */
    private void drawHighlight(Renderer renderer, Color color, int x, int y) {
        if (x < 0 || y < 0)
            return;

        renderer.hollowRectangle(color, x, y, SCALE, SCALE, DisplayConstants.GRID_LINE_THICKNESS);
    }

    public void setDisplay(Displayer display) {
        VIEW = display;
    }
}
