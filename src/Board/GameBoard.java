package Board;

import AnimalPieces.*;
import Resources.BoardTiles;

import java.util.*;

/**
 *
 */
public class GameBoard {

    final int boardRows;
    final int boardColumns;
    private BoardCell[][] gameBoard;
    Map<BoardCell, List<BoardCell>> allPlayerMoves;

    public GameBoard() {
        boardRows = 7;
        boardColumns = 9;
        gameBoard = new BoardCell[boardRows][boardColumns];
    }

    public boolean equals(BoardCell[][] gameBoard) {
        return this.gameBoard == gameBoard;
    }

    public void initialize(String boardPattern, String piecePattern) {
        final int boardPatternLength = boardColumns * boardRows;
        final int piecePatternLength = boardColumns * boardRows * 2;
        String cleanBoard = boardPattern.replaceAll("[^LRTDd]", "");
        String cleanPiece = piecePattern.replaceAll("[^0-8]", "");

        if (cleanBoard.length() != boardPatternLength) {
            throw new IllegalArgumentException("Invalid pattern length! Expected: " + boardPatternLength + ", Actual: " + cleanBoard.length());
        }

        if (cleanPiece.length() != piecePatternLength) {
            throw new IllegalArgumentException("Invalid pattern length! Expected: " + piecePatternLength + ", Actual: " + cleanPiece.length());
        }

        parseBoardPattern(cleanBoard, this.gameBoard);
        parsePiecePattern(cleanPiece, this.gameBoard);
    }

    public void initialize(String pattern) {
        final int boardPatternLength = boardColumns * boardRows;
        final int piecePatternLength = boardColumns * boardRows * 2;
        final int expectedLength = boardPatternLength + piecePatternLength;
        String cleanPattern = pattern.replaceAll("\\s+", "");

        if (cleanPattern.length() != expectedLength) {
            throw new IllegalArgumentException("Invalid pattern length! Expected: " + expectedLength + ", Actual: " + cleanPattern.length());
        }

        String boardPattern;
        String piecePattern;

        if (Character.isLetter(cleanPattern.charAt(0))) {
            boardPattern = cleanPattern.substring(0, boardPatternLength);
            piecePattern = cleanPattern.substring(boardPatternLength);
        }
        else {
            piecePattern = cleanPattern.substring(0, piecePatternLength);
            boardPattern = cleanPattern.substring(piecePatternLength);
        }

        initialize(boardPattern, piecePattern);
    }

    private void parsePiecePattern(String pattern, BoardCell[][] gameBoard) throws IllegalArgumentException {
        String token;
        char rankChar;
        int playerIndex;
        int index = 0;

        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardColumns; col++) {
                token = pattern.substring(index, index + 2);

                index += 2;

                if (token.equals("00")) {
                    gameBoard[row][col].setPiece(null);
                    continue;
                }

                rankChar = token.charAt(0);
                playerIndex = Character.getNumericValue(token.charAt(1));

                if (playerIndex != 1 && playerIndex != 2) {
                    throw new IllegalArgumentException("Invalid playerIndex suffix at char " + (index + 1) + "! Expected: 1|2, Actual" + playerIndex);
                }

                switch (rankChar) {
                    case '1' -> gameBoard[row][col].setPiece(new Mouse(playerIndex));
                    case '2' -> gameBoard[row][col].setPiece(new Cat(playerIndex));
                    case '3' -> gameBoard[row][col].setPiece(new Wolf(playerIndex));
                    case '4' -> gameBoard[row][col].setPiece(new Dog(playerIndex));
                    case '5' -> gameBoard[row][col].setPiece(new Leopard(playerIndex));
                    case '6' -> gameBoard[row][col].setPiece(new Tiger(playerIndex));
                    case '7' -> gameBoard[row][col].setPiece(new Lion(playerIndex));
                    case '8' -> gameBoard[row][col].setPiece(new Elephant(playerIndex));
                    default -> {
                        throw new IllegalArgumentException("Invalid rank token! Expected: [1,8], Actual: " + rankChar);
                    }
                }
            }
        }
    }

    /**
     * L = Land
     * R = River
     * T = Trap
     * D = Den of player 1
     * d = Den of player 2
     * @param pattern
     * @param gameBoard
     * @throws IllegalArgumentException
     */
    private void parseBoardPattern(String pattern, BoardCell[][] gameBoard) throws IllegalArgumentException {
        int index = 0;

        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardColumns; col++) {
                switch (pattern.charAt(index)) {
                    case 'L' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.LAND), row, col);
                    case 'T' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.TRAP), row, col);
                    case 'R' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.RIVER), row, col);
                    case 'D' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.DEN, 1), row, col);
                    case 'd' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.DEN, 2), row, col);
                }

                index++;
            }
        }
    }

    public String toPattern() {
        char[] boardPattern = new char[boardRows * boardColumns];
        char[] piecePattern = new char[boardRows * boardColumns * 2];
        int boardIndex = 0;
        int pieceIndex = 0;

        for (BoardCell[] row : this.gameBoard) {
            for (BoardCell col : row) {
                switch (col.getTile().getType()) {
                    case LAND -> boardPattern[boardIndex++] = 'L';
                    case RIVER -> boardPattern[boardIndex++] = 'R';
                    case TRAP -> boardPattern[boardIndex++] = 'T';
                    case DEN -> boardPattern[boardIndex++] = (col.getTile().getPlayerIndex() == 1) ? 'D' : 'd';
                }

                if (col.getPiece() == null) {
                    piecePattern[pieceIndex++] = '0';
                    piecePattern[pieceIndex++] = '0';
                    continue;
                }

                piecePattern[pieceIndex++] = (char) ('0' + col.getPiece().getRank());
                piecePattern[pieceIndex++] = (char) ('0' + col.getPiece().getPlayerIndex());
            }
        }

        return Arrays.toString(boardPattern).replaceAll("[^LRTDd]", "") + Arrays.toString(piecePattern).replaceAll("[^0-8]", "");
    }

    private Stack<BoardCell> getAllPlayerPieces(int playerIndex) {
        Stack<BoardCell> allPieces = new Stack<>();

        for (BoardCell[] row : this.gameBoard) {
            for (BoardCell column : row) {
                if (column.getPiece().getPlayerIndex() == playerIndex)
                    allPieces.push(column);
            }
        }

        return allPieces;
    }

    public HashMap<BoardCell, List<BoardCell>> getAllPlayerMoves(int playerIndex) {
        HashMap<BoardCell, List<BoardCell>> allMoves = new HashMap<>();
        Stack<BoardCell> allPieces = getAllPlayerPieces(playerIndex);
        BoardCell cell;

        while (!allPieces.empty()) {
            cell = allPieces.pop();
            allMoves.put(cell, cell.getPiece().getAllMoves(cell, this.gameBoard));
        }

        return allMoves;
    }
}
