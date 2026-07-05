package Board;

import AnimalPieces.AnimalPiece;
import Resources.PlayerColor;
import Resources.Tiles;

/**
 * Handles the terminal-based graphic rendering of the Animal Chess board.
 * Features dynamic player colors, token bases, rank numbers, and letter coordinates.
 *
 * @author Zachary Tan
 * @version 4 7/04/2026
 */
public class ConsoleDisplay {

    private static final String RESET = "\u001B[0m";
    private static final String HEADER_TEXT = "\u001B[1;37m"; // Bold White

    // Background Color Codes (Terrain)
    private static final String BG_LAND = "\u001B[42m";    // Green
    private static final String BG_RIVER = "\u001B[44m";   // Blue
    private static final String BG_TRAP = "\u001B[100m";   // Dark Grey
    private static final String BG_TOKEN = "\u001B[40m";   // Black (Piece Base)

    private final PlayerColor p1Color;
    private final PlayerColor p2Color;


    public ConsoleDisplay(PlayerColor p1, PlayerColor p2) {
        p1Color = p1;
        p2Color = p2;
    }

    public void printBoard(BoardCell[][] gameBoard) {
        if (gameBoard == null || gameBoard.length == 0 || gameBoard[0].length == 0) {
            System.out.println("Cannot render an empty or uninitialized game board.");
            return;
        }

        int rows = gameBoard.length;
        int cols = gameBoard[0].length;

        System.out.println("\n=== ANIMAL CHESS ===");
        
        // Print Column Headers (A, B, C...)
        System.out.print("   ");
        for (int c = 0; c < cols; c++) {
            char colLetter = (char) ('A' + c);
            System.out.print(HEADER_TEXT + "  " + colLetter + " " + RESET);
        }
        System.out.println("\n   " + "----".repeat(cols));

        // Print Grid Rows
        for (int r = 0; r < rows; r++) {
            System.out.print(HEADER_TEXT + r + " |" + RESET);

            for (int c = 0; c < cols; c++) {
                System.out.print(getFormattedCell(gameBoard[r][c]));
            }
            
            System.out.println(RESET);
        }
        System.out.println("   " + "----".repeat(cols) + "\n");
    }

    private String getFormattedCell(BoardCell cell) {
        BoardTile tile = cell.getTile();
        AnimalPiece piece = cell.getPiece();
        
        String backgroundCode = BG_LAND;
        String contentText = "  . "; 

        if (tile != null) {
            Tiles tileType = tile.getType();
            if (tileType == Tiles.RIVER) {
                backgroundCode = BG_RIVER;
                contentText = "  ~ ";
            } else if (tileType == Tiles.TRAP) {
                backgroundCode = BG_TRAP;
                contentText = "  x ";
            } else if (tileType == Tiles.ANIMAL_DEN) {
                backgroundCode = (tile.getPlayerIndex() == 1) ? p1Color.bgCode : p2Color.bgCode;
                // Replaced 'Ω' with 'D' to prevent '??' encoding errors in Windows CMD
                contentText = "  D "; 
            }
        }

        if (piece != null) {
            String textStyle = (piece.getPlayerIndex() == 1) ? p1Color.textCode : p2Color.textCode;
            String symbol = Integer.toString(piece.getRank());
            
            // Renders: [Terrain BG] + Space Space + [Black BG Token + Colored Rank] + [Terrain BG] + Space
            return backgroundCode + "  " + BG_TOKEN + textStyle + symbol + RESET + backgroundCode + " " + RESET;
        }

        return backgroundCode + contentText + RESET;
    }
}
