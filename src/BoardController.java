import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for telling the component object where and what to draw. It is also
 * responsible for updating the game state whenever a move has been made. Furthermore, it
 * is also responsible for calling the results display whenever a player has won.
 *
 * @author Richmond Jase Von M. Salvador
 * @version 3.0 8/2/2026
 * @since 3.0
 */
public class BoardController implements ViewController {

    /**
     * The main controller that has control over the display of other frames
     *
     * @since 3.0
     * @see MainController
     */
    private final MainController CONTROL;

    /**
     * The display that this controller has control over
     *
     * @since 3.0
     * @see GenericPanel
     */
    private Displayer VIEW;

    /**
     * Represents the current game state of the game
     *
     * @since 3.0
     * @see GameState
     */
    private final GameState STATE;

    /**
     * Represents the list of moves the selected cell could do
     *
     * @since 2.7
     */
    private List<BoardCell> selectedMoves;

    /**
     * Represents the position of the cursor within the displayer with the position starting from the top-left
     *
     * @since 2.7
     */
    private Point cursor;

    /**
     * Represents the position of the cell that is currently selected by the player
     *
     * @since 2.7
     */
    private Point selected;

    /**
     * A helper global field that checks whether the chosen cell has already
     * been selected before
     *
     * @since 3.0
     */
    private boolean wasSelectedBefore;

    /**
     * Contains the value if the user is currently dragging with the mouse or not
     *
     * @since 2.7
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

    /**
     * Constructs this object with the specified controller, area, and the state of the board.
     *
     * @param controller the main controller that contains all controllers for display
     * @param area the width and height of the
     * @param state the object representing the current state of the game
     *
     * @since 3.0
     * @see MainController
     * @see GameState
     */
    public BoardController(MainController controller, Dimension area, GameState state) {
        CONTROL = controller;
        STATE = state;

        ASSETS = new AssetsManager();

        int rows = STATE.getBoard().getRows();
        int columns = STATE.getBoard().getColumns();
        SCALE = Math.min(area.height / rows, area.width / columns);
    }

    /**
     * The method that contains the instructions the renderer shall display
     *
     * @param renderer the object responsible for drawing the instructions from
     *                 this controller
     *
     * @since 3.0
     * @see ViewController
     * @see Renderer
     */
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

    /**
     * A helper method that converts the specified x and y positions into
     * a position inside the game board
     *
     * @param x the x position to be converted
     * @param y the y position to be converted
     * @return the converted position relative to the game board
     *
     * @since 3.0
     */
    private Point snapToCell(int x, int y) {
        return new Point(x / SCALE, y / SCALE);
    }

    /**
     * A helper method that determines whether the specified cell is
     * within the current available moves
     *
     * @param cell the cell to be judged
     * @return true if the list of moves does contain the specified cell, false otherwise
     *
     * @since 3.0
     */
    private boolean existInMoves(BoardCell cell) {
        if (cell == null || selectedMoves == null) return false;

        for (BoardCell move : selectedMoves) {
            if (cell.equals(move))
                return true;
        }

        return false;
    }

    /**
     * A helper method that sets the selected field to null and its moves into
     * an empty list
     *
     * @since 3.0
     */
    private void clearSelection() {
        selected = null;
        selectedMoves = new ArrayList<>();
    }

    /**
     * A helper method that is called whenever the state has determined a winner. If it
     * has then it would call the main controller to display the results
     *
     * @since 3.0
     * @see GameState#getWinner()
     * @see MainController#showResults(Player, Player, Player)
     */
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

    /**
     * Invoked when a mouse button has been pressed within the component. It would either select the pressed cell
     * or call the update method of the controller
     *
     * @param x the x positon relative to the component where the mouse had been pressed
     * @param y the y positon relative to the component where the mouse had been pressed
     *
     * @since 2.7
     * @see BoardInputHandler
     */
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

    /**
     * Invoked when a mouse that had been clicked/dragged has been released within the component. It would either deselect
     * the cell or call the update method of the controller if it was being dragged.
     *
     * @param x the x positon relative to the component where the mouse had been released
     * @param y the y positon relative to the component where the mouse had been released
     *
     * @since 2.7
     * @see BoardInputHandler
     */
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

    /**
     * Invoked when a mouse has been pressed on a component and dragged.
     *
     * @param x the x positon relative to the component where the mouse is being dragged
     * @param y the y positon relative to the component where the mouse is being dragged
     *
     * @since 2.7
     * @see BoardInputHandler
     */
    public void handleDragEvent(int x, int y) {
        if (isOutsideBoard(x, y)) {
            handleMouseExit();
            return;
        }

        isDragged = true;
        handleMouseMovement(x, y);
    }

    /**
     * Invoked when a mouse has been moved within the component without any other inputs
     *
     * @param x the x positon relative to the component where the mouse had been moved to
     * @param y the y positon relative to the component where the mouse had been moved to
     *
     * @since 2.7
     * @see BoardInputHandler
     */
    public void handleMouseMovement(int x, int y) {
        if (isOutsideBoard(x, y)) {
            handleMouseExit();
            return;
        }

        cursor = new Point(x, y);
        VIEW.refresh();
    }

    /**
     * Invoked whenever the mouse has exited the component.
     *
     * @since 2.7
     * @see BoardInputHandler
     */
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
     * @param renderer the renderer that would display the instructions
     * @param row the row where the tile would be drawn
     * @param column the column where the move would be drawn
     *
     * @since 2.7
     * @see Renderer
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
     * @param renderer the renderer that would display the instructions
     * @param row the row where the move would be drawn
     * @param column the column where the move would be drawn
     *
     * @since 2.7
     * @see Renderer
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
     * @param renderer the renderer that would display the instructions
     * @param row the row where the piece would be drawn
     * @param column the column where the piece would be drawn
     *
     * @since 2.7
     * @see Renderer
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
     * @param renderer the renderer that would display the instructions
     * @param rows the amount of rows
     * @param columns the amount of columns
     *
     * @since 2.7
     * @see Renderer
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
     * @param renderer the renderer that would display the instructions
     * @param color the color the hollow square would take
     * @param x the position in the x-axis where the square would be drawn
     * @param y the position in the y-axis where the square would be drawn
     *
     * @since 2.7
     * @see Renderer
     */
    private void drawHighlight(Renderer renderer, Color color, int x, int y) {
        if (x < 0 || y < 0)
            return;

        renderer.hollowRectangle(color, x, y, SCALE, SCALE, DisplayConstants.GRID_LINE_THICKNESS);
    }

    /**
     * Sets the view which this controller has control of
     *
     * @param display the view component
     *
     * @since 3.0
     * @see Displayer
     */
    public void setDisplay(Displayer display) {
        VIEW = display;
    }
}
