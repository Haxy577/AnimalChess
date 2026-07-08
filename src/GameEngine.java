import Board.BoardCell;
import Board.ConsoleDisplay;
import Board.GameBoard;
import Resources.Player;
import Resources.PlayerColor;
import Resources.Tiles;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The core controller class that manages the overall game flow, 
 * handles player turns, processes input via console, and evaluates victory conditions.
 */
public class GameEngine {
    private GameBoard board;
    private Player player1;
    private Player player2;

    /**
     * Starts and controls the main text-based game loop.
     * Manages turns, checks for legal moves, updates the board, and declares the winner.
     */
    
    public void console() {
        Scanner scanner = new Scanner(System.in);
        
// Setup and configure player 1 and player 2 profiles
        player1 = initializePlayer(scanner, player2);
        player2 = initializePlayer(scanner, player1);
        
// Randomly assign player indexes to decide turn orders
        setPlayerIndexes();
        
// Initialize the game arena and the UI
        board = new GameBoard();
        ConsoleDisplay display = new ConsoleDisplay(player1.getAnsiColor(), player2.getAnsiColor());
        int currentPlayerIndex = 1;

// Main gameplay loop
        while (true) {
            // Retrieve all valid moves for the active players
            Map<BoardCell, List<BoardCell>> moves = board.getAllPlayerMoves(currentPlayerIndex);
            
// Win Condition 1: If a player has no possible moves left, they lose
            if (moves.isEmpty()) {
                System.out.print("Winner: " + ((currentPlayerIndex == 1) ? player2.getName() : player1.getName()));
                scanner.close();
                break;
            }

            // Render current state of the board to the terminal
            display.printBoard(board.getBoard());

            // Get valid move selections from the user
            List<BoardCell> move = getMove(moves, scanner);
            BoardCell source = move.get(0);
            BoardCell target = move.get(1);

            // Execute the valid movement updates on the game board
            board.movePiece(source, target);

            // Win Condition 2: If a piece successfully infiltrates the ANIMAL_DEN tile
            if (target.getTile().getType() == Tiles.ANIMAL_DEN) {
                display.printBoard(board.getBoard());
                System.out.println("Winner: " + ((currentPlayerIndex == 1) ? player1.getName() : player2.getName()));
                scanner.close();
                break;
            }

            // Hand over control to the alternating player
            currentPlayerIndex = (currentPlayerIndex == 1) ? 2 : 1;
        }
    }
    
/**
     * Generates a random seed value to non-deterministically assign individual 
     * player indexes (1 or 2). Ensure uniqueness by using a comparative loop.
     */
    
    public void setPlayerIndexes() {
        Random random = new Random();
        int p1,p2;

        // Keep rolling values until distinct values between 1 and 8 are chosen
        do {
            p1 = random.nextInt(1,9);
            p2 = random.nextInt(1,9);
        } while (p1 == p2);

        // Assign player index based on whoever rolled higher
        player1.setIndex((p1 > p2) ? 1 : 2);
        player2.setIndex((p2 > p1) ? 1 : 2);
    }

    /**
     * Prompts a user to enter their profile details via the console terminal.
     * Prevents overlapping duplicate usernames or conflicting display color schemes.
     */
    public Player initializePlayer(Scanner scanner, Player otherPlayer) {
        while (true) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            PlayerColor color = chooseColor(scanner);
            Player player = new Player(username, color);

            // Ensure player profiles do not clash with each other
            if (!player.equals(otherPlayer))
                return player;

            System.out.println("Matching player data. Please choose another username and/or color");
        }
    }
    
/**
     * Renders an interactive choice menu of values matching the PlayerColor enum definition 
     * and processes validated numerical inputs to select a profile color.
     */
    
    public PlayerColor chooseColor(Scanner scanner) {
        PlayerColor[] colors = PlayerColor.values();
        
// Print available colors mapped to their numerical index position
        for (int i = 0; i < colors.length; i++) {
            System.out.println("[" + i + "] " + colors[i]);
        }

        do {
            System.out.print("Please enter the color for your pieces: ");
            String input = scanner.nextLine();

            // Validate that the input string is purely numerical digits
            if (!input.matches("^\\d+$"))
                continue;

            int index = Integer.parseInt(input);

            // Validate index falls nicely within standard array boundaries
            if (index >= 0 && index < colors.length) {
                return colors[index];
            }

            System.out.println("Invalid input. Please only enter a number between [0," + (colors.length - 1) + "]");
        } while (true);
    }
/**
     * Handles console interactions to select a starting cell and a valid targeted destination tile.
     * Includes step-back capabilities allowing users to clear target selections and switch pieces.
     */
    public List<BoardCell> getMove(Map<BoardCell, List<BoardCell>> moves, Scanner scanner) {
        if (moves.isEmpty())
            return null;

        Set<BoardCell> pieces = moves.keySet();

        while (true) {
            BoardCell movingCell;
            BoardCell targetCell;

            System.out.print("Available moves: ");

            for (BoardCell piece : pieces) {
                System.out.print(toAlgebraicNotation(piece) + " ");
            }

            System.out.println();

            // Inner Loop Phase 1: Selecting a valid piece to move
            while (true) {
                System.out.print("Enter the cell you want to move: ");
                Point pos = parseAlgebraicNotation(scanner.nextLine());
                // Boundaries verification check against the total matrix limits
                if (pos != null && pos.y >= 0 && pos.y < board.getRows() && pos.x >= 0 && pos.x < board.getColumns())
                    movingCell = board.getCell(pos.y, pos.x);
                else
                    movingCell = null;

                // Break out once a coordinate mapped to actual movable units is picked
                if (moves.containsKey(movingCell))
                    break;

                System.out.println("Invalid input. Does not match any of the provided board cells");
            }

            // Inner Loop Phase 2: Selecting a target destination for the chosen piece
            while (true) {
                Collection<BoardCell> pieceMoves = moves.get(movingCell);

                System.out.println("Available moves for " + movingCell.getPiece().pieceName() + "(" + toAlgebraicNotation(movingCell) + ")");

                for (BoardCell target : pieceMoves) {
                    System.out.print(toAlgebraicNotation(target) + "(" + movingCell.getDirection(target) + ")  ");
                }

                System.out.print("\nEnter the cell you want to move to (or \"Back\" to go back): ");
                String input = scanner.nextLine();

                if (input.toLowerCase().trim().equals("back"))
                    break;

                Point pos = parseAlgebraicNotation(input);

                if (pos != null && pos.y >= 0 && pos.y < board.getRows() && pos.x >= 0 && pos.x < board.getColumns())
                    targetCell = board.getCell(pos.y, pos.x);
                else
                    targetCell = null;

                if (targetCell != null && pieceMoves.contains(targetCell))
                    return List.of(movingCell, targetCell);

                System.out.println("Invalid input. Does not match any of the provided board cells");
            }
        }
    }

    /**
     * Converts coordinate strings formatted in standard Algebraic notation (e.g., "a5", "b12")
     * into structural graphical representation points (X = column space, Y = row array index).
     */
    public Point parseAlgebraicNotation(String notation) {
        if (notation == null || notation.isBlank())
            return null;

        // Strip non-essential white space configurations and lowercase characters
        String clean = notation.replaceAll("\\s", "").toLowerCase();

        if (!clean.matches("^[a-z]+\\d+$"))
            return null;

        String rowStr = clean.replaceAll("\\D", "");
        String columnStr = clean.replaceAll("\\d", "");
        int row = Integer.parseInt(rowStr);
        int column = 0;

        for (int i = 0; i < columnStr.length(); i++) {
            column = column * 26 + (columnStr.charAt(i) - 'a' + 1);
        }

        return new Point(column - 1, row);
    }

    /**
     * Resolves a structural grid Point coordinate index reference and converts it 
     * back into an alphanumeric algebraic string format.
     */
    public String toAlgebraicNotation(Point pos) {
        if (pos.x < 0 || pos.y < 0)
            return null;

        StringBuilder str = new StringBuilder();
        int column = pos.x;

        while(column >= 0) {
            str.insert(0, (char) ('a' + column % 26));
            column = column / 26 - 1;
        }

        str.append(pos.y);

        return str.toString();
    }

    /**
     * Helper overload translating explicit structured BoardCell positions into String representation formatting.
     */
    public String toAlgebraicNotation(BoardCell cell) {
        return toAlgebraicNotation(new Point(cell.getCol(), cell.getRow()));
    }
}
