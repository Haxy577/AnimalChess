package Display;

import Board.BoardCell;
import Board.BoardTile;
import AnimalPieces.AnimalPiece;
import Resources.BOARD_TILES;
import Resources.ANIMALS;

/**
 * Handles the terminal-based graphic rendering of the Animal Chess board.
 * Features dynamic, clash-free player colors for both pieces and Dens.
 *
 * @author Zachary Tan
 * @version 2.0 6/28/2026
 */
public class ConsoleDisplay {

    // ANSI Text Color Codes for structural elements
    private static final String RESET = "\u001B[0m";
    private static final String HEADER_TEXT = "\u001B[1;37m"; // Bold White

    // Fixed ANSI Background Color Codes (Terrain)
    private static final String BG_LAND = "\u001B[42m";    // Green
    private static final String BG_RIVER = "\u001B[44m";   // Blue
    private static final String BG_TRAP = "\u001B[100m";   // Dark Grey

    /**
     * Enum restricting player color choices to prevent clashing with 
     * the board's green land, blue river, and grey traps.
     */
    public enum PlayerColor {
        RED("\u001B[1;31m", "\u001B[41m"),
        YELLOW("\u001B[1;33m", "\u001B[43m"),
        MAGENTA("\u001B[1;35m", "\u001B[45m"),
        CYAN("\u001B[1;36m", "\u001B[46m"),
        WHITE("\u001B[1;37m", "\u001B[47m");

        public final String textCode;
        public final String bgCode;

        PlayerColor(String textCode, String bgCode) {
            this.textCode = textCode;
            this.bgCode = bgCode;
        }
    }

    // Default player colors
    private static PlayerColor p1Color = PlayerColor.RED;
    private static PlayerColor p2Color = PlayerColor.MAGENTA;

    /**
     * Allows the main game loop to assign custom colors to each player.
     */
    public static void setPlayerColors(PlayerColor p1, PlayerColor p2) {
        if (p1 == p2) {
            System.out.println("Warning: Both players selected " + p1.name() + "!");
        }
        p1Color = p1;
        p2Color = p2;
    }

    /**
     * Renders the complete game board into the console.
     */
    public static void printBoard(BoardCell[][] gameBoard) {
        if (gameBoard == null || gameBoard.length == 0 || gameBoard[0].length == 0) {
            System.out.println("Cannot render an empty or uninitialized game board.");
            return;
        }

        int rows = gameBoard.length;
        int cols = gameBoard[0].length;

        System.out.println("\n=== ANIMAL CHESS ===");
        
        // Print Column Headers
        System.out.print("   ");
        for (int c = 0; c < cols; c++) {
            System.out.print(HEADER_TEXT + "  " + c + "  " + RESET);
        }
        System.out.println("\n   " + "-----".repeat(cols));

        // Print Grid Rows
        for (int r = 0; r < rows; r++) {
            System.out.print(HEADER_TEXT + r + " |" + RESET);

            for (int c = 0; c < cols; c++) {
                System.out.print(getFormattedCell(gameBoard[r][c]));
            }
            
            System.out.println(RESET);
        }
        System.out.println("   " + "-----".repeat(cols) + "\n");
    }

    /**
     * Formats an individual cell block using the player's chosen colors 
     * and the specific piece/terrain data.
     */
    private static String getFormattedCell(BoardCell cell) {
        BoardTile tile = cell.getTile();
        AnimalPiece piece = cell.getPiece();
        
        String backgroundCode = BG_LAND;
        String contentText = " . "; 

        // 1. Identify Terrain and Apply Background Color
        if (tile != null) {
            BOARD_TILES tileType = tile.getTYPE();
            if (tileType == BOARD_TILES.RIVER) {
                backgroundCode = BG_RIVER;
                contentText = " ~ ";
            } else if (tileType == BOARD_TILES.TRAP) {
                backgroundCode = BG_TRAP;
                contentText = " x ";
            } else if (tileType == BOARD_TILES.ANIMAL_DEN) {
                // Den background matches the owner's chosen color
                backgroundCode = (tile.getPLAYER_INDEX() == 1) ? p1Color.bgCode : p2Color.bgCode;
                contentText = "Ω" + tile.getPLAYER_INDEX() + " ";
            }
        }

        // 2. Overlay Animal Piece (Using the classes you shared)
        if (piece != null) {
            String textStyle = (piece.getPlayerIndex() == 1) ? p1Color.textCode : p2Color.textCode;
            String symbol = getAnimalSymbol(piece.getAnimal());
            
            return backgroundCode + textStyle + " " + symbol + piece.getPlayerIndex() + " " + RESET;
        }

        return backgroundCode + contentText + RESET;
    }

    /**
     * Maps the ANIMALS enum to its single-character representation.
     */
    private static String getAnimalSymbol(ANIMALS animal) {
        if (animal == null) return "?";
        
        switch (animal) {
            case MOUSE:    return "M";
            case CAT:      return "C";
            case WOLF:     return "W";
            case DOG:      return "D";
            case LEOPARD:  return "L";
            case TIGER:    return "T";
            case LION:     return "I"; // 'I' avoids conflict with Leopard
            case ELEPHANT: return "E";
            default:       return "?";
        }
    }
}
