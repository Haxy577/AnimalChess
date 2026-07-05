import Board.BoardCell;
import Board.ConsoleDisplay;
import Board.GameBoard;
import Resources.Player;
import Resources.PlayerColor;
import Resources.Tiles;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameEngine {
    private GameBoard board;
    private Player player1;
    private Player player2;

    public void console() {
        Scanner scanner = new Scanner(System.in);

        player1 = initializePlayer(scanner, player2);
        player2 = initializePlayer(scanner, player1);

        setPlayerIndexes();

        board = new GameBoard();
        ConsoleDisplay display = new ConsoleDisplay(player1.getAnsiColor(), player2.getAnsiColor());
        int currentPlayerIndex = 1;


        while (true) {
            Map<BoardCell, List<BoardCell>> moves = board.getAllPlayerMoves(currentPlayerIndex);

            if (moves.isEmpty()) {
                System.out.print("Winner: " + ((currentPlayerIndex == 1) ? player2.getName() : player1.getName()));
                scanner.close();
                break;
            }

            display.printBoard(board.getBoard());

            List<BoardCell> move = getMove(moves, scanner);
            BoardCell source = move.get(0);
            BoardCell target = move.get(1);

            board.movePiece(source, target);

            if (target.getTile().getType() == Tiles.ANIMAL_DEN) {
                display.printBoard(board.getBoard());
                System.out.println("Winner: " + ((currentPlayerIndex == 1) ? player1.getName() : player2.getName()));
                scanner.close();
                break;
            }

            currentPlayerIndex = (currentPlayerIndex == 1) ? 2 : 1;
        }
    }

    public void setPlayerIndexes() {
        Random random = new Random();
        int p1,p2;

        do {
            p1 = random.nextInt(1,9);
            p2 = random.nextInt(1,9);
        } while (p1 == p2);

        player1.setIndex((p1 > p2) ? 1 : 2);
        player2.setIndex((p2 > p1) ? 1 : 2);
    }

    public Player initializePlayer(Scanner scanner, Player otherPlayer) {
        while (true) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            PlayerColor color = chooseColor(scanner);
            Player player = new Player(username, color);

            if (!player.equals(otherPlayer))
                return player;

            System.out.println("Matching player data. Please choose another username and/or color");
        }
    }

    public PlayerColor chooseColor(Scanner scanner) {
        PlayerColor[] colors = PlayerColor.values();

        for (int i = 0; i < colors.length; i++) {
            System.out.println("[" + i + "] " + colors[i]);
        }

        do {
            System.out.print("Please enter the color for your pieces: ");
            String input = scanner.nextLine();

            if (!input.matches("^\\d+$"))
                continue;

            int index = Integer.parseInt(input);

            if (index >= 0 && index < colors.length) {
                return colors[index];
            }

            System.out.println("Invalid input. Please only enter a number between [0," + (colors.length - 1) + "]");
        } while (true);
    }

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

            while (true) {
                System.out.print("Enter the cell you want to move: ");
                Point pos = parseAlgebraicNotation(scanner.nextLine());
                if (pos != null && pos.y >= 0 && pos.y < board.getRows() && pos.x >= 0 && pos.x < board.getColumns())
                    movingCell = board.getCell(pos.y, pos.x);
                else
                    movingCell = null;

                if (moves.containsKey(movingCell))
                    break;

                System.out.println("Invalid input. Does not match any of the provided board cells");
            }

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

    public Point parseAlgebraicNotation(String notation) {
        if (notation == null || notation.isBlank())
            return null;

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

    public String toAlgebraicNotation(BoardCell cell) {
        return toAlgebraicNotation(new Point(cell.getCol(), cell.getRow()));
    }
}
