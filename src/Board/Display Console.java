package Display;

import Board.BoardCell;
import Board.BoardTile;
import AnimalPieces.AnimalPiece;
import Resources.BOARD_TILES;
import Resources.ANIMALS;

/**
 * Handles the terminal-based graphic rendering of the Animal Chess board.
 * Uses ANSI escape codes to colorize various terrains and denote player ownership.
 * * @author Zachary Tan
 * @version 1.0 6/24/2026
 */
public class ConsoleDisplay {

    // ANSI Text Color Codes (Foreground)
    private static final String RESET = "\u001B[0m";
    private static final String P1_TEXT = "\u001B[1;31m"; // Bold Red for Player 1
    private static final String P2_TEXT = "\u001B[1;34m"; // Bold Blue for Player 2
    private static final String HEADER_TEXT = "\u001B[1;37m"; // Bold White for grid coordinates

    // ANSI Background Color Codes (Terrain)
    private static final String BG_LAND = "\u001B[42m";    // Green
    private static final String BG_RIVER = "\u001B[44m";   // Blue
    private static final String BG_TRAP = "\u001B[100m";   // Dark Grey
    private static final String BG_DEN = "\u001B[45m";    // Magenta

    /**
     * Renders the complete game board into the console with coordinate tracking.
     * * @param gameBoard the 2D array of board cells representing the current state
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
            // Print Row Header
            System.out.print(HEADER_TEXT + r + " |" + RESET);

            for (int c = 0; c < cols; c++) {
                BoardCell cell = gameBoard[r][c];
                System.out.print(getFormattedCell(cell));
            }
            
            // End of row reset safety line
            System.out.println(RESET);
        }
        System.out.println("   " + "-----".repeat(cols) + "\n");
    }

    /**
     * Determines the correct background color, foreground color, and text
     * shorthand representation for an individual cell block.
     */
    private static String getFormattedCell(BoardCell cell) {
        BoardTile tile = cell.getTile();
        AnimalPiece piece = cell.getPiece();
        
        String backgroundCode = BG_LAND;
        String contentText = " . "; // Default land symbol

        // 1. Identify Background Terrain Style
        if (tile != null) {
            BOARD_TILES tileType = tile.getTYPE();
            if (tileType == BOARD_TILES.RIVER) {
                backgroundCode = BG_RIVER;
                contentText = " ~ ";
            } else if (tileType == BOARD_TILES.TRAP) {
                backgroundCode = BG_TRAP;
                contentText = " x ";
            } else if (tileType == BOARD_TILES.ANIMAL_DEN) {
                backgroundCode = BG_DEN;
                contentText = "Ω" + tile.getPLAYER_INDEX() + " ";
            }
        }

        // 2. Overlay Animal Piece Details if Occupied
        if (piece != null) {
            String textStyle = (piece.getPlayerIndex() == 1) ? P1_TEXT : P2_TEXT;
            String symbol = getAnimalSymbol(piece.getAnimal());
            
            // Formats as " M1 " or " T2 " nested inside its background cell color block
            return backgroundCode + textStyle + " " + symbol + piece.getPlayerIndex() + " " + RESET;
        }

        // 3. Return stylized empty tile representation
        return backgroundCode + contentText + RESET;
    }

    /**
     * Maps an ANIMALS enum type to its single-character representation.
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
            case LION:     return "I"; // Using 'I' for Lion to distinguish clearly from Leopard
            case ELEPHANT: return "E";
            default:       return "?";
        }
    }
}
