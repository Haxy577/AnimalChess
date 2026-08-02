/**
 * The {@link ViewController} should be implemented by a class that is responsible
 * for telling the renderer what to display
 *
 * @see Renderer
 *
 * @author Richmond Jase Von M. Salvador
 * @version 3.0 8/2/2026
 * @since 3.0
 */
public interface ViewController {

    /**
     * The method that contains the instructions the renderer shall display
     *
     * @param renderer the object responsible for drawing the instructions from
     *                 this controller
     *
     * @since 3.0
     * @see Renderer
     */
    void render(Renderer renderer);
}
