import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles the mouse inputs within the board display component and passes these information
 * to the view and controller
 *
 * @see DisplayBoard
 * @see GameController
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.7 7/29/2026
 * @since 2.7
 */
public class BoardInputHandler extends MouseAdapter {
    /**
     * The component where the mouse events would be tracked and displayed
     *
     * @since 2.7
     * @see DisplayBoard
     */
    private final DisplayBoard VIEW;

    /**
     * The object that would update the model based on the given mouse inputs
     *
     * @since 2.7
     * @see GameController
     */
    private final GameController CONTROL;

    /**
     * A helper field created to track whether the selected cell has already been selected before
     *
     * @since 2.7
     */
    private boolean wasSelectedBefore;

    /**
     * Constructs this input handler with the specified board display component with the controller
     * it also contains
     *
     * @param view the component where the mouse events would be gathered at
     *
     * @since 2.7
     * @see DisplayBoard
     * @see GameController
     */
    BoardInputHandler(DisplayBoard view) {
        VIEW = view;
        CONTROL = VIEW.getController();
    }

    /**
     * A helper method to convert the specified point to snap to a specific cell
     *
     * @param p the point to convert
     * @return the column and row position of the converted point
     *
     * @since 2.7
     */
    private Point snapToCell(Point p) {
        if (p == null) return null;
        int scale = VIEW.getScale();
        return (p.x < 0 || p.y < 0) ? new Point(-1, -1) : new Point(p.x / scale, p.y / scale);
    }

    /**
     * A helper method to check whether the specified point is outside the bounds of the component
     *
     * @param p the point position to be checked
     * @return true if the position is outside the bounds of the component, false otherwise
     *
     * @since 2.7
     */
    private boolean isOutsideBoard(Point p) {
        if (p == null) return true;
        int maxWidth = CONTROL.getColumn() * VIEW.getScale();
        int maxHeight = CONTROL.getRow() * VIEW.getScale();
        return p.x < 0 || p.x > maxWidth || p.y < 0 || p.y > maxHeight;
    }

    /**
     * A helper method to check whether the specified cell position exists in the pre-calculated
     * point of moves
     *
     * @param cell the cell position to be checked
     * @return true if the specified cell does exist in the moves, false otherwise
     *
     * @since 2.7
     */
    private boolean existInMoves(Point cell) {
        if (cell == null) return false;

        for (Point move : VIEW.getMoves()) {
            if (cell.equals(move))
                return true;
        }

        return false;
    }

    /**
     * A helper method to reset the selected point to null and the moves to an empty array
     *
     * @since 2.7
     */
    private void clearSelection() {
        VIEW.setSelected(null);
        VIEW.setMoves(new Point[0]);
    }

    /**
     * A helper method that checks with the controller if the queried update was successful, if it was
     * then it would clear the selection and moves
     *
     * @since 2.7
     * @see #clearSelection()
     */
    private void tryAdvanceTurn() {
        if (CONTROL.isNextTurn()) {
            clearSelection();
        }
    }

    /**
     * Invoked when a mouse button has been pressed within the component. It would either select the pressed cell
     * or call the update method of the controller
     *
     * @param e the event to be processed
     *
     * @since 2.7
     * @see GameController
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (isOutsideBoard(e.getPoint()))
            return;

        Point pressed = snapToCell(e.getPoint());
        Point selected = VIEW.getSelected();
        wasSelectedBefore = pressed.equals(selected);

        if (existInMoves(pressed) && selected != null) {
            CONTROL.update(selected.y, selected.x, pressed.y, pressed.x);
            tryAdvanceTurn();
        }
        else if (!wasSelectedBefore) {
            VIEW.setSelected(pressed);
            VIEW.setMoves(CONTROL.getMovesAt(pressed.y, pressed.x));
        }

        VIEW.repaint();
    }

    /**
     * Invoked when a mouse that had been clicked/dragged has been released within the component. It would either deselect
     * the cell or call the update method of the controller if it was being dragged.
     *
     * @param e the event to be processed
     *
     * @since 2.7
     * @see GameController
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (isOutsideBoard(e.getPoint()))
            return;

        Point release = snapToCell(e.getPoint());
        Point selected = VIEW.getSelected();

        if (VIEW.isDragged() && selected != null) {
            CONTROL.update(selected.y, selected.x, release.y, release.x);
            tryAdvanceTurn();
        }
        else if (selected != null){
            if (wasSelectedBefore && release.equals(selected)) {
                clearSelection();
            }
        }

        VIEW.setDragged(false);
        VIEW.repaint();
    }

    /**
     * Invoked whenever the mouse has exited the component.
     *
     * @param e the event to be processed
     *
     * @since 2.7
     */
    @Override
    public void mouseExited(MouseEvent e) {
        VIEW.setCursor((Point) null);
        VIEW.setDragged(false);
        VIEW.repaint();
    }

    /**
     * Invoked when a mouse has been pressed on a component and dragged.
     *
     * @param e the event to be processed
     *
     * @since 2.7
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isOutsideBoard(e.getPoint())) {
            mouseExited(e);
            return;
        }

        VIEW.setDragged(true);
        mouseMoved(e);
    }

    /**
     * Invoked when a mouse has been moved within the component without any other inputs
     *
     * @param e the event to be processed
     *
     * @since 2.7
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        VIEW.setCursor(e.getPoint());
        VIEW.repaint();
    }
}
