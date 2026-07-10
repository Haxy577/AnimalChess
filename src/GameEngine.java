import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The core controller class that manages the overall game flow,
 * handles player turns, processes input via console, and evaluates victory conditions.
 *
 * @see GameBoard
 * @see Player
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.26 7/11/2026
 * @since 1.26
 */
public class GameEngine {
    private GameBoard board;
    private Player player1;
    private Player player2;

    /**
     * Starts and controls the main text-based game loop.
     * Manages turns, checks for legal moves, updates the board, and declares the winner.
     *
     * @since 1.26
     * @see ConsoleDisplay
     * @see GameBoard
     * @see #initializeBoard(Scanner)
     * @see #setPlayerIndexes(Scanner)
     * @see #setPlayerColors(Scanner)
     */

    public void console() {
        Scanner scanner = new Scanner(System.in);

        initializeBoard(scanner);

        ConsoleDisplay.displayMenu();

        // Setup and configure player 1 and player 2 profiles
        player1 = initializePlayer(scanner, player2);
        player2 = initializePlayer(scanner, player1);

        System.out.println();

        // Randomly assign player indexes to decide turn orders
        setPlayerIndexes(scanner);

        setPlayerColors(scanner);

        // Initialize the game arena and the UI
        ConsoleDisplay display = new ConsoleDisplay(player1.getAnsiColor(), player2.getAnsiColor());
        int currentPlayerIndex = 1;

        // Main gameplay loop
        while (true) {
            // Retrieve all valid moves for the active players
            Map<BoardCell, List<BoardCell>> moves = board.getAllPlayerMoves(currentPlayerIndex);

            display.printBoard(board.getBoard(), currentPlayerIndex);

            // Win Condition 1: If a player has no possible moves left, they lose
            if (moves.isEmpty()) {
                System.out.print("Winner: " + (((currentPlayerIndex == 1) ? getPlayerByIndex(2).getName() : getPlayerByIndex(1).getName())));
                scanner.close();
                break;
            }

            // Win Condition 2: If every piece of the player has no available moves left, they lose
            int cannotMove = 0;
            for (BoardCell cell : moves.keySet()) {
                if (moves.get(cell).isEmpty())
                    cannotMove++;
            }

            if (cannotMove == moves.size()) {
                System.out.print("Winner: " + ((currentPlayerIndex == 1) ? getPlayerByIndex(2).getName() : getPlayerByIndex(1).getName()));
                scanner.close();
                break;
            }

            // Get valid move selections from the user
            List<BoardCell> move = getMove(moves, scanner);
            BoardCell source = move.get(0);
            BoardCell target = move.get(1);

            // Execute the valid movement updates on the game board
            board.movePiece(source, target);

            // Win Condition 3: If a piece successfully infiltrates the ANIMAL_DEN tile
            if (target.getTile().getType() == Tiles.ANIMAL_DEN) {
                display.printBoard(board.getBoard(), currentPlayerIndex);
                System.out.println("Winner: " + ((currentPlayerIndex == 1) ? getPlayerByIndex(1).getName() : getPlayerByIndex(2).getName()));
                scanner.close();
                break;
            }

            // Hand over control to the alternating player
            currentPlayerIndex = (currentPlayerIndex == 1) ? 2 : 1;
        }
    }

    /**
     * Asks the player whether to load the default configuration/layout of the board or to input their custom board pattern.
     * Initialized the board field based on the player's choice
     * 
     * @param scanner the scanner object to get the player's input
     *                
     * @since 1.26
     * @see GameBoard#initialize(String)
     */
    public void initializeBoard(Scanner scanner) {
        String input;

        System.out.println("[0/Enter] Start the game");
        System.out.println("[1] Enter custom board");

        do {
            System.out.print("Enter choice: ");
            input = scanner.nextLine().replaceAll("\\s", "");

            if (input.isBlank() || input.equals("0") || input.equals("\n")) {
                board = new GameBoard();
                return;
            }

        } while (!input.equals("1"));

        while (true) {
            try {
                int row = getIntInput(scanner, "Enter the # of rows: ", 1, Integer.MAX_VALUE);
                int col = getIntInput(scanner, "Enter the # of columns: ", 1, Integer.MAX_VALUE);
                System.out.print("Enter the pattern: ");
                input =  scanner.nextLine();
                board = new GameBoard(row, col, input);
                break;
            } catch (Exception e) {
                System.out.println(Ansi.RED + e.getMessage() + Ansi.RESET);
            }
        }
    }

    /**
     * A helper method that prompts a user to enter an integer value within the set range
     *
     * @param scanner the scanner object to get the user's input
     * @param prompt a string that is displayed before asking the player for their input
     * @param origin the minimum point of the range, inclusive.
     * @param bound the maximum point of the range, exclusive.
     * @throws IllegalArgumentException if the origin is greater than the bound and/or
     * the provided scanner is null
     * @return the input of the user within the specified range
     *
     * @since 1.26
     */
    public int getIntInput(Scanner scanner, String prompt, int origin, int bound) {
        if (scanner == null)
            throw new IllegalArgumentException("The scanner cannot be null");

        if (origin > bound)
            throw new IllegalArgumentException("The specified origin cannot be greater than the bound");

        String input;
        int num;

        do {
            System.out.print(prompt);
            input = scanner.nextLine().replaceAll("\\s", "");

            if (!input.matches("^\\d+$")) {
                System.out.println("Invalid Input. Please enter a number from " + origin + " to " + (bound - 1));
                continue;
            }

            num = Integer.parseInt(input);

            if (num >= origin && num < bound)
                return num;

            System.out.println(Ansi.RED + "Invalid Input. Please enter a number from " + origin + " to " + (bound - 1) + Ansi.RESET);
        } while (true);
    }


    /**
     * Sets the individual player indexes of each player through a blind pick using a random set of Animal pieces.
     *
     * @param scanner the object to get the player's input
     *
     * @since 1.26
     * @see Player
     * @see #shuffle(List)
     */
    public void setPlayerIndexes(Scanner scanner) {
        List<AnimalPiece> pieces = new ArrayList<>(List.of(new Mouse(1), new Cat(1), new Wolf(1), new Dog(1),
                new Leopard(1), new Tiger(1), new Lion(1), new Elephant(1)));
        int p1,p2;

        shuffle(pieces);

        // Keep rolling values until distinct values between 1 and 8 are chosen
        do {
            p1 = getIntInput(scanner, (player1.getName() + ", Pick from a number from 0 to 7: "), 0, pieces.size());
            p2 = getIntInput(scanner, (player2.getName() + ", Pick from a number from 0 to 7: "), 0, pieces.size());
        } while (p1 == p2);

        System.out.println();
        System.out.println(player1.getName() + "  vs  " + player2.getName());
        System.out.println(pieces.get(p1) + "  vs  " + pieces.get(p2));
        System.out.println();

        // Assign player index based on whoever rolled higher
        if (pieces.get(p1).getRank() > pieces.get(p2).getRank()) {
            player1.setIndex(1);
            player2.setIndex(2);
        }
        else {
            player1.setIndex(2);
            player2.setIndex(1);
            player2.swap(player1);
        }

        System.out.println("1st move: " + getPlayerByIndex(1).getName());
    }

    /**
     * A helper method that helps to determine which player object has the specified index
     *
     * @param index the player index to search for
     * @return the player object that has the same index as the specified index
     *
     * @since 1.26
     * @see Player
     */
    public Player getPlayerByIndex(int index) {
        return (player1.getIndex() == index) ? player1 : player2;
    }

    /**
     * Implements the Fisher-Yates algorithm to shuffle the elements of the specified list
     *
     * @param list the list to be shuffled
     *
     * @since 1.18
     * @see AnimalPiece
     */
    private void shuffle(List<AnimalPiece> list) {
        if (list == null || list.isEmpty())
            return;

        Random random = new Random();
        int last = list.size() - 1;

        while (last > 0) {
            int rand = random.nextInt(0, last + 1);

            AnimalPiece temp = list.get(last);
            list.set(last, list.get(rand));
            list.set(rand, temp);

            last--;
        }
    }

    /**
     * Prompts a user to enter their profile details via the console terminal.
     * Prevents overlapping duplicate usernames or conflicting display color schemes.
     *
     * @param scanner the scanner object to get the player's input
     * @param otherPlayer the data of the other player within the game
     *
     * @since 1.26
     * @see Player
     */
    public Player initializePlayer(Scanner scanner, Player otherPlayer) {
        while (true) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            Player player = new Player(username);

            // Ensure player profiles do not clash with each other
            if (!player.equals(otherPlayer))
                return player;

            System.out.println(Ansi.RED + "Matching player data. Please choose another username" + Ansi.RESET);
        }
    }

    /**
     * Renders an interactive choice menu of values matching the PlayerColor enum definition
     * and processes validated numerical inputs to select a profile color.
     *
     * @param scanner the scanner object to get the player's input
     *
     * @since 1.26
     * @see PlayerColor
     * @see Player
     */
    public void setPlayerColors(Scanner scanner) {
        ArrayList<PlayerColor> colors = new ArrayList<>(List.of(PlayerColor.values()));

        // Print available colors mapped to their numerical index position
        for (int i = 0; i < colors.size(); i++) {
            System.out.println("[" + i + "] " + colors.get(i));
        }

        int input = getIntInput(scanner, ("Enter the color " + getPlayerByIndex(1).getName() + " want to use: "),  0, colors.size());
        player1.setAnsiColor(colors.get(input));
        colors.remove(input);
        System.out.println();

        for (int i = 0; i < colors.size(); i++) {
            System.out.println("[" + i + "] " + colors.get(i));
        }

        player2.setAnsiColor(colors.get(getIntInput(scanner, ("Enter the color " + getPlayerByIndex(2).getName() + " want to use: "),  0, colors.size())));
        System.out.println();
    }

    /**
     * Handles console interactions to select a starting cell and a valid targeted destination tile.
     * Includes step-back capabilities allowing users to clear target selections and switch pieces.
     *
     * @param moves is all the possible moves the current turn player can perform
     * @param scanner the scanner object to get the player's input
     * @return a list of BoardCell with a size of 2, where the first element represents the source cell, while the second element
     * represents the destination cell. Returns {@code null} if the map of moves is empty
     *
     * @since 1.26
     * @see BoardCell
     * @see GameBoard#getAllPlayerMoves(int)
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
                System.out.print(toAlgebraicNotation(piece) + "(" + piece.getPiece().pieceName() + ")  ");
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

                System.out.println(Ansi.RED + "Invalid input. Does not match any of the provided board cells" + Ansi.RESET);
            }

            // Inner Loop Phase 2: Selecting a target destination for the chosen piece
            while (true) {
                if (movingCell == null)
                    break;

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

                System.out.println(Ansi.RED + "Invalid input. Does not match any of the provided board cells" + Ansi.RESET);
            }
        }
    }

    /**
     * Converts coordinate strings formatted in standard Algebraic notation (e.g., "a5", "b12")
     * into structural graphical representation points (X = column space, Y = row array index).
     * 
     * @param notation the string to be parsed
     * @return the point position parsed from a valid notation. Returns {@code null} if the specified notation is invalid
     * 
     * @since 1.26
     * @see Point
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
     * 
     * @param pos the Point object to be converted to the algebraic notation
     * @return the algebraic notation based on the given Point. Returns {@code null} if the
     * specified Point is null or at least one of its value is a negative integer
     * 
     * @since 1.26
     * @see Point
     */
    public String toAlgebraicNotation(Point pos) {
        if (pos == null || pos.x < 0 || pos.y < 0)
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
     * 
     * @param cell the BoardCell object to be converted to the algebraic notation
     * @return the algebraic notation based on the given Point. Returns {@code null} if the
     * specified Point is null or at least one of its value is a negative integer
     * 
     * @since 1.26
     * @see BoardCell
     * @see #toAlgebraicNotation(Point) 
     */
    public String toAlgebraicNotation(BoardCell cell) {
        return toAlgebraicNotation(new Point(cell.getCol(), cell.getRow()));
    }
}
