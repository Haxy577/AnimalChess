package Board;

import AnimalPieces.*;
import Resources.BoardTiles;

import java.util.*;

/**
 *
 */
public class GameBoard {

    public final int boardRows;
    public final int boardColumns;
    public final BoardCell[][] gameBoard;
    Map<BoardCell, List<BoardCell>> allPlayerMoves;

    public GameBoard() {
        this.boardRows = 7;
        this.boardColumns = 9;
        this.gameBoard = new BoardCell[boardRows][boardColumns];
    }

    public void initialize(String pattern) throws IllegalArgumentException {
        validatePattern(pattern);

        initialize(getBoardPattern(pattern), getPiecePattern(pattern));
    }

    public void initialize(String boardPattern, String piecePattern) throws IllegalArgumentException {
        validatePattern(boardPattern, piecePattern);

        parseBoardPattern(boardPattern, this.gameBoard);
        parsePiecePattern(piecePattern, this.gameBoard);
    }

    public void validatePattern(String pattern) throws IllegalArgumentException {
        if (pattern == null) {
            throw new IllegalArgumentException("Invalid pattern! The pattern cannot be null");
        }

        validatePattern(getBoardPattern(pattern), getPiecePattern(pattern));
    }

    public void validatePattern(String boardPattern, String piecePattern) throws IllegalArgumentException {
        validateBoardPattern(boardPattern);
        validatePiecePattern(piecePattern);
    }

    public void validateBoardPattern(String pattern) throws IllegalArgumentException {
        final int boardPatternLength = boardColumns * boardRows;
        int denCount = 0;

        if (pattern == null) {
            throw new IllegalArgumentException("Invalid board pattern! The pattern cannot be null");
        }

        if (pattern.length() != boardPatternLength) {
            throw new IllegalArgumentException("Invalid board pattern length! Expected: " + boardPatternLength + ", Actual: " + pattern.length());
        }

        if (!pattern.replaceAll("[LRTDd]", "").isEmpty()) {
            throw new IllegalArgumentException("Invalid board pattern! Pattern can only contain the characters: L|R|T|D|d");
        }

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == 'D') {
                denCount++;
            }
            else if (pattern.charAt(i) == 'd') {
                denCount--;
            }
        }

        if (denCount != 0) {
            throw new IllegalArgumentException("Invalid board pattern! Pattern must only contain one instance of the animal den tile per player");
        }
    }

    public void validatePiecePattern(String pattern) throws IllegalArgumentException {
        final int piecePatternLength = boardColumns * boardRows * 2;
        int tokenIndex = 0;
        char pieceChar;
        char playerChar;

        if (pattern == null) {
            throw new IllegalArgumentException("Invalid piece pattern! The pattern cannot be null");
        }

        if (pattern.length() != piecePatternLength) {
            throw new IllegalArgumentException("Invalid piece pattern length! Expected: " + piecePatternLength + ", Actual: " + pattern.length());
        }

        if (!pattern.replaceAll("[0-8]", "").isEmpty()) {
            throw new IllegalArgumentException("Invalid piece pattern! Pattern can only contain the characters: 0 to 8");
        }

        do {
            pieceChar = pattern.charAt(tokenIndex);
            playerChar = pattern.charAt(tokenIndex + 1);

            if (pieceChar == '0' && playerChar != '0') {
                throw new IllegalArgumentException("Invalid playerIndex suffix at char " + (tokenIndex + 1) + "! Expected: 0, Actual: " + playerChar);
            }

            if (pieceChar != '0' && playerChar != '1' && playerChar != '2') {
                throw new IllegalArgumentException("Invalid playerIndex suffix at char " + (tokenIndex + 1) + "! Expected: 1|2, Actual: " + playerChar);
            }

            tokenIndex += 2;
        } while (tokenIndex < piecePatternLength);
    }

    public String getPiecePattern(String pattern) {
        return pattern.replaceAll("[^0-8]", "");
    }

    public String getBoardPattern(String pattern) {
        return pattern.replaceAll("[^LRTDd]", "");
    }

    private void parsePiecePattern(String pattern, BoardCell[][] gameBoard){
        String token;
        int rank;
        int playerIndex;
        int index = 0;

        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardColumns; col++) {
                token = pattern.substring(index, index + 2);

                index += 2;

                rank = Character.getNumericValue(token.charAt(0));
                playerIndex = Character.getNumericValue(token.charAt(1));

                switch (rank) {
                    case 0 -> gameBoard[row][col].setPiece(null);
                    case 1 -> gameBoard[row][col].setPiece(new Mouse(playerIndex));
                    case 2 -> gameBoard[row][col].setPiece(new Cat(playerIndex));
                    case 3 -> gameBoard[row][col].setPiece(new Wolf(playerIndex));
                    case 4 -> gameBoard[row][col].setPiece(new Dog(playerIndex));
                    case 5 -> gameBoard[row][col].setPiece(new Leopard(playerIndex));
                    case 6 -> gameBoard[row][col].setPiece(new Tiger(playerIndex));
                    case 7 -> gameBoard[row][col].setPiece(new Lion(playerIndex));
                    case 8 -> gameBoard[row][col].setPiece(new Elephant(playerIndex));
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
    private void parseBoardPattern(String pattern, BoardCell[][] gameBoard) {
        int index = 0;

        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardColumns; col++) {
                switch (pattern.charAt(index)) {
                    case 'L' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.LAND), row, col);
                    case 'T' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.TRAP), row, col);
                    case 'R' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.RIVER), row, col);
                    case 'D' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.ANIMAL_DEN, 1), row, col);
                    case 'd' -> gameBoard[row][col] = new BoardCell(new BoardTile(BoardTiles.ANIMAL_DEN, 2), row, col);
                }

                index++;
            }
        }
    }

    public String toPattern(boolean isBoardPatternFirst) {
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
                    case ANIMAL_DEN -> boardPattern[boardIndex++] = (col.getTile().getPlayerIndex() == 1) ? 'D' : 'd';
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

        if (isBoardPatternFirst) {
            return new String(boardPattern) + new String(piecePattern);
        }
        else {
            return Arrays.toString(piecePattern) + Arrays.toString(boardPattern);
        }
    }

    private List<BoardCell> getAllPlayerPieces(int playerIndex) {
        List<BoardCell> allPieces = new ArrayList<>();

        for (BoardCell[] row : this.gameBoard) {
            for (BoardCell column : row) {
                if (column.getPiece() == null)
                    continue;

                if (column.getPiece().getPlayerIndex() == playerIndex)
                    allPieces.add(column);
            }
        }

        return allPieces;
    }

    public HashMap<BoardCell, List<BoardCell>> getAllPlayerMoves(int playerIndex) {
        HashMap<BoardCell, List<BoardCell>> allMoves = new HashMap<>();
        List<BoardCell> allPieces = getAllPlayerPieces(playerIndex);

        for (BoardCell cell : allPieces) {
            allMoves.put(cell, cell.getPiece().getAllMoves(cell, this.gameBoard));
        }

        return allMoves;
    }

    public boolean equals(BoardCell[][] gameBoard) {
        return this.gameBoard == gameBoard;
    }

//    @Override
//    public String toString() {
//
//    }
}
