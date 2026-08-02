import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles the mouse inputs within the board display component and passes these information
 * to the controller that controls the game state and the view
 *
 * @see BoardController
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.7 7/29/2026
 * @since 2.7
 */
public class BoardInputHandler extends MouseAdapter {

    /**
     * The object that would update the model based on the given mouse inputs
     *
     * @since 2.7
     * @see BoardController
     */
    private final BoardController CONTROL;

    /**
     * Constructs this object with the specified controller that has control over the
     * model and view
     *
     * @param controller the controller that is responsible for the board
     */
    BoardInputHandler(BoardController controller) {
        CONTROL = controller;
    }

    /**
     * Invoked when a mouse button has been pressed within the component.
     *
     * @param e the event to be processed
     *
     * @since 2.7
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        CONTROL.handlePressEvent(p.x, p.y);
    }

    /**
     * Invoked when a mouse that had been clicked/dragged has been released within the component.
     *
     * @param e the event to be processed
     *
     * @since 2.7
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Point p = e.getPoint();
        CONTROL.handleReleaseEvent(p.x, p.y);
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
        CONTROL.handleMouseExit();
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
        Point p = e.getPoint();
        CONTROL.handleDragEvent(p.x, p.y);
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
        Point p = e.getPoint();
        CONTROL.handleMouseMovement(p.x, p.y);
    }
}
