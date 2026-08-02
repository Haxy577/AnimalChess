import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for initializing all the images to be used as display for
 * the GUI representation of a game board in the game "Animal Chess"
 *
 * @author Richmond Jase Vob M. Salvador
 * @version 3.0 8/2/2026
 * @since 2.7
 */
public class AssetsManager {

    private final BufferedImage RIVER_TEXTURE;
    private final BufferedImage TRAP_TEXTURE;
    private final BufferedImage DEN_TEXTURE;

    private final BufferedImage MOUSE_ICON;
    private final BufferedImage CAT_ICON;
    private final BufferedImage WOLF_ICON;
    private final BufferedImage DOG_ICON;
    private final BufferedImage LEOPARD_ICON;
    private final BufferedImage TIGER_ICON;
    private final BufferedImage LION_ICON;
    private final BufferedImage ELEPHANT_ICON;

    /**
     * Initializes all the image assets to be displayed
     *
     * @since 2.7
     */
    public AssetsManager() {
        try {
            RIVER_TEXTURE = ImageIO.read(new File("src\\River.png"));
            TRAP_TEXTURE = ImageIO.read(new File("src\\Trap.png"));
            DEN_TEXTURE = ImageIO.read(new File("src\\Den.png"));

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
    }

    /**
     * Returns the corresponding image asset for the specified tile type
     *
     * @param tile the type of tile to get the icon of
     * @return the image representation of the specified tile
     *
     * @since 2.7
     * @see Tiles
     */
    public BufferedImage getTileIcon(Tiles tile) {
        return switch (tile) {
            case LAND -> null;
            case RIVER -> RIVER_TEXTURE;
            case TRAP -> TRAP_TEXTURE;
            case DEN -> DEN_TEXTURE;
        };
    }

    /**
     * A helper method that returns the corresponding image for the specified animal piece
     *
     * @param piece the piece to be converted
     * @return the image representation of the animal piece
     * @throws IllegalStateException if the specified piece is not one of the eight pieces in
     * the game Animal Chess
     *
     * @since 2.3
     * @see AnimalPiece
     */
    public BufferedImage getAnimalIcon(String piece) throws IllegalStateException{
        if (piece == null)
            return null;

        return switch (piece.toLowerCase().trim()) {
            case "mouse" -> MOUSE_ICON;
            case "cat" -> CAT_ICON;
            case "wolf" -> WOLF_ICON;
            case "dog" -> DOG_ICON;
            case "leopard" -> LEOPARD_ICON;
            case "tiger" -> TIGER_ICON;
            case "lion" -> LION_ICON;
            case "elephant" -> ELEPHANT_ICON;
            default -> throw new IllegalStateException("Unexpected value: " + piece.toLowerCase().trim());
        };
    }
}
