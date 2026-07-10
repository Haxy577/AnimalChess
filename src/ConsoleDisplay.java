import java.util.Scanner;

/**
 * Handles the terminal-based graphic rendering of the Animal Chess board.
 * Features dynamic player colors, token bases, rank numbers, and letter coordinates.
 *
 * @author Zachary Tan
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 */
public class ConsoleDisplay {
    /**
     * The player color of the player with the index of 1. This field cannot be changed once set.
     *
     * @since 1.26
     * @see PlayerColor
     */
    private final PlayerColor p1Color;

    /**
     * The player color of the player with the index of 2. This field cannot be changed once set.
     *
     * @since 1.26
     * @see PlayerColor
     */
    private final PlayerColor p2Color;

    /**
     * Constructs the object with the specified colors for each player
     *
     * @param p1 the color for the player with the index of 1
     * @param p2 the color for the player with the index of 2
     *
     * @since 1.26
     * @see PlayerColor
     */
    public ConsoleDisplay(PlayerColor p1, PlayerColor p2) {
        p1Color = p1;
        p2Color = p2;
    }

    /**
     * Displays the current state of the board within the terminal based on the specified gameBoard and which
     * player index currently has the turn
     *
     * @param gameBoard the 2d array of BoardCells that represents the playing board
     * @param currentTurn the player index of the player that currently is having their turn
     *
     * @since 1.26
     * @see GameEngine
     * @see BoardCell
     */
    public void printBoard(BoardCell[][] gameBoard, int currentTurn) {
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
            System.out.print(Ansi.WHITE + Ansi.BOLD + "  " + colLetter + " " + Ansi.RESET);
        }
        System.out.println("\n   " + "----".repeat(cols));

        // Print Grid Rows
        for (int r = 0; r < rows; r++) {
            System.out.print(Ansi.WHITE + Ansi.BOLD + r + " |" + Ansi.RESET);

            for (int c = 0; c < cols; c++) {
                System.out.print(getFormattedCell(gameBoard[r][c], currentTurn));
            }
            
            System.out.println(Ansi.RESET);
        }
        System.out.println("   " + "----".repeat(cols) + "\n");
    }

    /**
     * Returns a formatted string that is representative of the specified cell.
     *
     * @param cell the cell to be formatted
     * @param currentTurn the index of the current player that has their turn currently
     * @return the formatted cell based on the data within the cell
     *
     * @since 1.26
     * @see BoardCell
     * @see Ansi
     */
    private String getFormattedCell(BoardCell cell, int currentTurn) {
        if (cell == null || cell.getTile() == null)
            return null;

        BoardTile tile = cell.getTile();
        Tiles tileType = tile.getType();
        AnimalPiece piece = cell.getPiece();

        String contentText;
        String backgroundCode;
        String foregroundCode = Ansi.DEFAULT;

        if (tileType == Tiles.LAND) {
            backgroundCode = Ansi.BG_GREEN;
            contentText = "  . ";
        }
        else if (tileType == Tiles.RIVER) {
            backgroundCode = Ansi.BG_BLUE;
            contentText = "  ~ ";
        } else if (tileType == Tiles.TRAP) {
            backgroundCode = Ansi.BRIGHT_BG_BLACK;
            foregroundCode = (tile.getPlayerIndex() == 1) ? p1Color.getTextCode() : p2Color.getTextCode();
            contentText = "  x ";
        } else {
            backgroundCode = (tile.getPlayerIndex() == 1) ? p1Color.getBgCode() : p2Color.getBgCode();
            contentText = "  D ";
        }

        if (piece != null) {
            String textStyle = (piece.getPlayerIndex() == 1) ? p1Color.getTextCode() : p2Color.getTextCode();
            String symbol = Integer.toString(piece.getRank());

            if (currentTurn == piece.getPlayerIndex())
                return backgroundCode + "  " + Ansi.BG_BLACK + textStyle + Ansi.BOLD + symbol + Ansi.RESET + backgroundCode + " " + Ansi.RESET;

            // Renders: [Terrain BG] + Space Space + [Black BG Token + Colored Rank] + [Terrain BG] + Space
            return backgroundCode + "  " + Ansi.BG_BLACK + textStyle + symbol + Ansi.RESET + backgroundCode + " " + Ansi.RESET;
        }

        return backgroundCode + foregroundCode + contentText + Ansi.RESET;
    }

    /**
     * Displays the ASCII art menu and loops until the user enters "Start".
     *
     * @since 1.26
     */
    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.println("+-------------------------------------------------------------+"); 
        System.out.println("|                                                             |");
        System.out.println("|     /\\_/\\                                     <:3 )~~       |");
        System.out.println("|    ( o o )                                                  |");
        System.out.println("|     > ^ <                                                   |");
        System.out.println("|                  WELCOME TO ANIMAL CHESS                    |");
        System.out.println("|                                                             |");
        System.out.println("|             ENTER: \"Start\" to begin the game                |");
        System.out.println("|                                                             |");
        System.out.println("+-------------------------------------------------------------+"); 

        // Loop until the player types "Start" (case-insensitive)
        while (!input.equalsIgnoreCase("Start")) {
            System.out.print("\n> ");
            input = scanner.nextLine().trim();

            if (!input.equalsIgnoreCase("Start")) {
                System.out.println("Invalid command. Please type \"Start\" to play!");
            }
        }

        System.out.println("\nInitializing board... Good luck!\n");
    }
}
