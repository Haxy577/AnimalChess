package Board;

import AnimalPieces.*;
import Resources.BOARD_TILES;

import java.util.*;

/**
 * Represents the current game board of two players that are playing the game "Animal Chess".
 * The gameboard consists of an array of BoardCell objects.
 *
 * @see BoardCell
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.11 7/2/2026
 * @since 1.1
 */
public class GameBoard {

    /**
     * The number of rows the gameboard has. This field cannot
     * be changed once set
     *
     * @since 1.1
     */
    private final int ROWS;

    /**
     * the number of columns the gameboard has. This field cannot be changed
     * once set
     *
     * @since 1.1
     */
    private final int COLUMNS;

    /**
     * Represents the playing field of the game. This is a 2-dimensional array of BoardCell.
     * This field cannot be changed once set
     *
     * @since 1.1
     * @see BoardCell
     */
    private final BoardCell[][] gameBoard;

    /**
     * Represents a gameboard pattern that has 9 rows, 7 columns, and the default layout of the game Animal Chess
     * in a vertical form
     *
     * @since 1.11
     * @see #initialize(String) 
     */
    private static final String DEFAULT_PATTERN = "2LTaT5LT11L2RL2R2L2RL2R2L2RL2R11LT5LTAT2L|n5g1d3c1m1p1w1e21E1W1P1M1C3D1G5N";

    /**
     * Tracks all the possible moves a player can make in a single turn. The key consists
     * of all the pieces the player controls, and the list consists of all the possible
     * moves these pieces can do
     *
     * @since 1.1
     * @see BoardCell
     * @see #getAllPlayerMoves(int) 
     */
    Map<BoardCell, List<BoardCell>> allPlayerMoves;

    /**
     * Constructs the gameboard array with the specified row, columns, and the layout ot each tile and piece within
     * the gameboard
     *
     * @param row the amount of rows the board will have
     * @param column the amount of columns every row would have
     * @param pattern the layout of tiles and pieces
     *
     * @since 1.11
     * @see BoardCell
     * @see #initialize(String)
     */
    public GameBoard(int row, int column, String pattern) {
        ROWS = row;
        COLUMNS = column;
        gameBoard = new BoardCell[row][column];
        initialize(pattern);
    }

    /**
     * Constructs a gameboard that has 9 rows and 7 columns with a default layout of the game Animal Chess represented
     * in a vertical orientation
     *
     * @since 1.1
     * @see BoardCell
     * @see #DEFAULT_PATTERN
     */
    public GameBoard() {
        this(9, 7, DEFAULT_PATTERN);
    }

    /**
     * Constructs a gameboard that has 9 rows and 7 columns with a custom board layout based on the pattern
     *
     * @param pattern the board tile and piece layout of the gameboard
     *
     * @since 1.7
     * @see #initialize(String)
     */
    public GameBoard(String pattern) {
        this(9, 7, pattern);
    }

    /**
     * Converts the gameBoard field of this class into a string
     *
     * @return the string representation of the gameBoard field
     *
     * @since 1.11
     * @see BoardCell
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("GameBoard{");

        for (int i = 0; i < ROWS; i++) {
            if (i > 0)
                string.append(',');

            string.append('{');

            for (int j = 0; j < COLUMNS; j++) {
                if (j > 0)
                    string.append(',');

                string.append(gameBoard[i][j].toString());
            }
            string.append('}');
        }
        string.append('}');

        return string.toString();
    }

    /**
     * Gets all the available pieces the player has and stores the position of each piece
     * in a list
     *
     * @param playerIndex the player index of the player that currently has the turn
     * @throws IllegalArgumentException if the specified player index is not 1 or 2
     * @return all the available pieces of a player
     *
     * @since 1.1
     * @see BoardCell
     */
    private List<BoardCell> getAllPlayerPieces(int playerIndex) throws IllegalArgumentException {
        if (playerIndex < 1 || playerIndex > 2) throw new IllegalArgumentException("The player index can only be either 1 or 2");

        List<BoardCell> allPieces = new ArrayList<>();

        for (BoardCell[] row : gameBoard) {
            for (BoardCell column : row) {
                if (column.getPiece() == null)
                    continue;

                if (column.getPiece().getPlayerIndex() == playerIndex)
                    allPieces.add(column);
            }
        }

        return allPieces;
    }

    /**
     * Gets all the available moves of a player within a single turn
     *
     * @param playerIndex the player index of the player that currently has the turn
     * @throws IllegalArgumentException if the specified player index is not 1 or 2
     * @return a map that contains each piece as the key and the available moves of each piece
     * as the values
     *
     * @since 1.1
     */
    public HashMap<BoardCell, List<BoardCell>> getAllPlayerMoves(int playerIndex) throws IllegalArgumentException {
        if (playerIndex < 1 || playerIndex > 2) throw new IllegalArgumentException("The player index can only be either 1 or 2");

        HashMap<BoardCell, List<BoardCell>> allMoves = new HashMap<>();
        List<BoardCell> allPieces = getAllPlayerPieces(playerIndex);

        for (BoardCell cell : allPieces) {
            allMoves.put(cell, cell.getPiece().getAllMoves(cell, gameBoard));
        }

        return allMoves;
    }

    /**
     * Initializes the gameBoard in accordance to the given pattern
     * <p>
     * A pattern consists of tokens, each token is made up of an optional number prefix and a letter suffix. Furthermore,
     * <strong>It consists of two sub-patterns</strong> separated by a divider character {@code '|'}:
     * <p>
     * <strong>Board pattern</strong>: the tiles of each board cell<br>
     * The number prefix specifies the amount of times this tile is repeated.<br>
     * The letter suffix represents the type of tile a cell would take, and its casing determines player ownership. (UPPERCASE = Player 1, lowercase = Player 2)
     * </p>
     * <p>
     * <strong>Piece pattern</strong>: the position of the animal pieces within the board<br>
     * The number prefix specifies the number of empty cells to skip before placing the piece.<br>
     * The letter suffix dictates the animal type, and its casing determines player ownership (UPPERCASE = Player 1, lowercase = Player 2).
     * </p>
     * </p>
     *
     * @param pattern a series of tokens that represents the layout of each tile and piece within the gameboard
     *
     * @since 1.7
     * @see #validateBoardPattern(String)
     * @see #validatePiecePattern(String)
     * @see #parseBoardPattern(String)
     * @see #parsePiecePattern(String)
     */
    public void initialize(String pattern) {
        try {
            if (pattern == null)
                throw new IllegalArgumentException("Invalid pattern. The pattern cannot be null");

            String cleanPattern = pattern.replaceAll("\\s", "");
            String parsedPattern = cleanPattern.substring(0, cleanPattern.indexOf('|'));
            String boardPattern = (parsedPattern.matches("(\\d*[LRTtAa])+")) ? parsedPattern : cleanPattern.substring(cleanPattern.indexOf('|') + 1);
            String piecePattern;

            if (boardPattern.length() == cleanPattern.length() + 1)
                piecePattern = "";
            else
                piecePattern = (parsedPattern.matches("(\\d*[MmCcWwDdPpNnGgEe])*")) ? parsedPattern : cleanPattern.substring(cleanPattern.indexOf('|') + 1);

            validateBoardPattern(boardPattern);
            validatePiecePattern(piecePattern);

            parseBoardPattern(boardPattern);
            parsePiecePattern(piecePattern);

        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("Invalid pattern. There must be a divider character '|' separating the two different patterns");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Checks whether the given pattern dedicated for board tiles is valid. Its validity is based on the following conditions:
     * <ol>
     * <li>The pattern is not null</li>
     * <li>Follows the regex tokens format: "(\d*[LRTtAa])+" -> An optional digit followed by a required character</li>
     * <li>The total size of the pattern must be the same as the dimensions of the gameBoard array</li>
     * <li>There must be at least 1 character that represents each player's den respectively</li>
     * </ol>
     *
     * @param pattern the pattern to be checked
     * @throws IllegalArgumentException if the given pattern has invalid characters, exceeds the size of the gameboard, or
     * an animal den tile is missing.
     *
     * @since 1.7
     * @see BoardTile
     * @see #parseTokens(String)
     */
    private void validateBoardPattern(String pattern) throws IllegalArgumentException {
        if (pattern == null)
            throw new IllegalArgumentException("Invalid board pattern. The pattern cannot be null");

        if (!pattern.matches("(\\d*[LRTtAa])+"))
            throw new IllegalArgumentException("Invalid board pattern structure. The pattern must only consists of digits and a character [LRTtAa]");

        List<String> tokens = parseTokens(pattern);
        int totalTiles = 0;
        int maxTiles = ROWS * COLUMNS;
        int player1DenCount = 0;
        int player2DenCount = 0;

        for (String token : tokens) {
            int number = (token.length() == 1) ? 1 : Integer.parseInt(token.substring(0, token.length() - 1));

            totalTiles += number;

            if (token.charAt(token.length() - 1) == 'A')
                player1DenCount += number;
            if (token.charAt(token.length() - 1) == 'a')
                player2DenCount += number;
        }

        if (totalTiles != maxTiles)
            throw new IllegalArgumentException("Invalid board pattern. The pattern size does not equal the board size. Expected: "
                    + maxTiles + ", Actual: " + totalTiles);

        if (player1DenCount < 1)
            throw new IllegalArgumentException("Invalid board pattern. There must be at least 1 animal den tile for player 1");

        if (player2DenCount < 1)
            throw new IllegalArgumentException("Invalid board pattern. There must be at least 1 animal den tile for player 2");
    }

    /**
     * Checks whether the given pattern dedicated for animal pieces is valid. Its validity is based on the following conditions:
     * <ol>
     * <li>The pattern is not null</li>
     * <li>Follows the regex tokens format: "(\d*[MmCcWwDdPpNnGgEe])*" -> An optional digit followed by a required character</li>
     * <li>The total size of the pattern must be at most the dimensions of the gameBoard array</li>
     * </ol>
     *
     * @param pattern the pattern to be checked
     * @throws IllegalArgumentException if the given pattern is invalid
     *
     * @since 1.7
     * @see AnimalPiece
     * @see #parseTokens(String)
     */
    private void validatePiecePattern(String pattern) throws IllegalArgumentException {
        if (pattern == null)
            throw new IllegalArgumentException("Invalid board pattern. The pattern cannot be null");

        if (!pattern.matches("(\\d*[MmCcWwDdPpNnGgEe])*"))
            throw new IllegalArgumentException("Invalid piece pattern structure. The pattern must only consists of digits and a character [MmCcWwDdPpNnGgEe]");

        List<String> tokens = parseTokens(pattern);
        int totalCell = 0;
        int maxCells = ROWS * COLUMNS;

        for (String token : tokens) {
            int number = (token.length() == 1) ? 1 : Integer.parseInt(token.substring(0, token.length() - 1));

            totalCell += number;
        }

        if (totalCell > maxCells)
            throw new IllegalArgumentException("Invalid piece pattern. The pattern size exceeds the board size. Expected: <"
                    + maxCells + ", Actual: " + totalCell);
    }

    /**
     * Parses the board string pattern and initializes the gameboard in accordance to the given pattern.
     * <p>
     * The pattern consists of tokens, each token is made up of an optional number prefix and a letter suffix <br>
     * The number prefix specifies the <strong>amount of times this tile is repeated</strong>. If the token lacks
     * the number prefix, it is assumed that the value is 1. <br>
     * The letter suffix represents the type of tile a cell would take, and its casing determines player ownership.
     * (UPPERCASE = Player 1, lowercase = Player 2)
     * </p>
     * <strong>Board tile mappings:</strong>
     * <ul>
     * <li>'L' = Land</li>
     * <li>'R' = River</li>
     * <li>'T|t' = Trap</li>
     * <li>'A|a' = Animal den</li>
     * </ul>
     * <strong>Example</strong>
     * <p>
     * "4LT" -> Sets the boardTile of indexes 0-3 into the LAND tile, sets the boardTile of index 4 into the TRAP tile
     * </p>
     *
     * @param pattern contains the pattern specifically for the board and its tiles
     *
     * @since 1.7
     * @see BoardTile
     * @see BOARD_TILES
     * @see #parseTokens(String)
     */
    private void parseBoardPattern(String pattern) {
        List<String> tokens = parseTokens(pattern);
        int boardIndex = 0;

        for (String token : tokens) {
            char tileChar = token.charAt(token.length() - 1);
            int number = (token.length() == 1) ? 1 : Integer.parseInt(token.substring(0, token.length() - 1));

            for (int i = 0; i < number; i++) {
                int row = boardIndex / COLUMNS;
                int col = boardIndex % COLUMNS;

                BoardTile tile = switch (tileChar) {
                    case 'L' -> new BoardTile(BOARD_TILES.LAND);
                    case 'R' -> new BoardTile(BOARD_TILES.RIVER);
                    case 'T' -> new BoardTile(BOARD_TILES.TRAP, 1);
                    case 't' -> new BoardTile(BOARD_TILES.TRAP, 2);
                    case 'A' -> new BoardTile(BOARD_TILES.ANIMAL_DEN, 1);
                    case 'a' -> new BoardTile(BOARD_TILES.ANIMAL_DEN, 2);
                    default -> throw new IllegalArgumentException("Invalid tile character. Expected: [LRTtAa], Actual: " + tileChar);
                };

                gameBoard[row][col] = new BoardCell(tile, row, col);
                boardIndex++;
            }
        }
    }

    /**
     * Parses the piece string pattern and sets the gameboard
     * <p>
     * The pattern consists of tokens, where each token consists of an optional number prefix and a mandatory letter suffix.<br>
     * The number prefix specifies the <strong>number of empty cells to skip</strong> before placing the piece. If the
     * token lacks the number prefix, it is assumed that its value is 0. <br>
     * The letter suffix dictates the animal type, and its casing determines player ownership
     * (UPPERCASE = Player 1, lowercase = Player 2).
     * </p>
     * <strong>Animal mappings:</strong>
     * <ul>
     * <li>'M|m' = Mouse (Rank 1)</li>
     * <li>'C|c' = Cat (Rank 2)</li>
     * <li>'W|w' = Wolf (Rank 3)</li>
     * <li>'D|d' = Dog (Rank 4)</li>
     * <li>'P|p' = Leopard (Rank 5)</li>
     * <li>'N|n' = Lion (Rank 6)</li>
     * <li>'G|g' = Tiger (Rank 7)</li>
     * <li>'E|e' = Elephant (Rank 8)</li>
     * </ul>
     * <strong>Example:</strong>
     * <p>
     * "M4c" -> Places a Player 1 Mouse at index 0, skips 4 empty squares,
     * and places a Player 2 Cat at index 5.
     * </p>
     *
     * @param pattern the layout pattern of pieces within the gameBoard
     * @throws IllegalArgumentException if an invalid piece character is found
     *
     * @since 1.7
     * @see AnimalPiece
     * @see #parseTokens(String)
     */
    private void parsePiecePattern(String pattern) throws IllegalArgumentException {
        List<String> tokens = parseTokens(pattern);
        int boardIndex = 0;

        for (String token : tokens) {
            char tileChar = token.charAt(token.length() - 1);
            int distance = (token.length() == 1) ? 0 : Integer.parseInt(token.substring(0, token.length() - 1));
            int playerIndex = (Character.isUpperCase(tileChar)) ? 1 : 2;

            boardIndex += distance;
            int row = boardIndex / COLUMNS;
            int col = boardIndex % COLUMNS;
            AnimalPiece piece = switch (tileChar) {
                case 'M','m' -> new Mouse(playerIndex);
                case 'C','c' -> new Cat(playerIndex);
                case 'W','w' -> new Wolf(playerIndex);
                case 'D','d' -> new Dog(playerIndex);
                case 'P','p' -> new Leopard(playerIndex);
                case 'N','n' -> new Lion(playerIndex);
                case 'G','g' -> new Tiger(playerIndex);
                case 'E','e' -> new Elephant(playerIndex);
                default -> throw new IllegalArgumentException("Invalid piece character. Expected: [MmCcWwDdPpNnGgEe], Actual: " + tileChar);
            };

            gameBoard[row][col].setPiece(piece);
            boardIndex++;
        }
    }

    /**
     * Splits a pattern into tokens where each token contains an optional number prefix and a mandatory letter suffix
     *
     * @param pattern the pattern to parse into tokens
     * @throws IllegalArgumentException if the pattern does not contain the mandatory letter suffix or its null
     * @return the list of parsed tokens
     *
     * @since 1.7
     */
    private List<String> parseTokens(String pattern) throws IllegalArgumentException {
        List<String> tokens = new ArrayList<>();
        int start = 0;
        int end = 0;

        if (pattern == null)
            throw new IllegalArgumentException("Invalid pattern. The specified pattern must not be null");

        if (pattern.isBlank())
            return tokens;

        if (Character.isDigit(pattern.charAt(pattern.length() - 1))) {
            throw new IllegalArgumentException("Invalid pattern. The end of the pattern must be a letter");
        }

        while(end < pattern.length()) {
            while (Character.isDigit(pattern.charAt(end))) {
                end++;
            }

            end++;
            tokens.add(pattern.substring(start, end));
            start = end;
        }

        return tokens;
    }

    /**
     * Converts the state of the gameboard into a string pattern.
     * <p>
     * A pattern consists of tokens, each token is made up of an optional number prefix and a letter suffix. Furthermore,
     * <strong>It consists of two sub-patterns</strong> separated by a divider character {@code '|'}:
     * <p>
     * <strong>Board pattern</strong>: the tiles of each board cell<br>
     * The number prefix specifies the amount of times this tile is repeated.<br>
     * The letter suffix represents the type of tile a cell would take, and its casing determines player ownership. (UPPERCASE = Player 1, lowercase = Player 2)
     * </p>
     * <p>
     * <strong>Piece pattern</strong>: the position of the animal pieces within the board<br>
     * The number prefix specifies the number of empty cells to skip before placing the piece.<br>
     * The letter suffix dictates the animal type, and its casing determines player ownership (UPPERCASE = Player 1, lowercase = Player 2).
     * </p>
     * </p>
     *
     * @return the board and piece pattern that represents the current gameboard
     *
     * @since 1.7
     * @see #toBoardPattern()
     * @see #toPiecePattern()
     */
    public String toPattern() {
        return toBoardPattern() + "|" + toPiecePattern();
    }

    /**
     * Converts the position of every active animal piece within the gameboard into a string pattern
     * <p>
     * The pattern consists of tokens, where each token consists of an optional number prefix and a mandatory letter suffix.<br>
     * The number prefix specifies the <strong>number of empty cells to skip</strong> before placing the piece. If the
     * token lacks the number prefix, it is assumed that its value is 0. <br>
     * The letter suffix dictates the animal type, and its casing determines player ownership
     * (UPPERCASE = Player 1, lowercase = Player 2).
     * </p>
     * <strong>Animal mappings:</strong>
     * <ul>
     * <li>'M|m' = Mouse (Rank 1)</li>
     * <li>'C|c' = Cat (Rank 2)</li>
     * <li>'W|w' = Wolf (Rank 3)</li>
     * <li>'D|d' = Dog (Rank 4)</li>
     * <li>'P|p' = Leopard (Rank 5)</li>
     * <li>'N|n' = Lion (Rank 6)</li>
     * <li>'G|g' = Tiger (Rank 7)</li>
     * <li>'E|e' = Elephant (Rank 8)</li>
     * </ul>
     *
     * @since 1.7
     * @see AnimalPiece
     */
    private String toPiecePattern() {
        int maxCapacity = ROWS * COLUMNS;
        StringBuilder pattern = new StringBuilder(maxCapacity);
        AnimalPiece piece;
        int distance = 0;
        char pieceChar;

        for (BoardCell[] row : gameBoard) {
            for (BoardCell col : row) {
                piece = col.getPiece();

                if (piece == null)
                    distance++;
                else {
                    pieceChar = switch (piece.getRank()) {
                        case 1 -> 'M';
                        case 2 -> 'C';
                        case 3 -> 'W';
                        case 4 -> 'D';
                        case 5 -> 'P';
                        case 6 -> 'G';
                        case 7 -> 'N';
                        case 8 -> 'E';
                        default -> '?';
                    };

                    pieceChar = (piece.getPlayerIndex() == 1) ? pieceChar : Character.toLowerCase(pieceChar);

                    if (distance > 0)
                        pattern.append(distance);

                    pattern.append(pieceChar);
                    distance = 0;
                }

            }
        }
        return pattern.toString();
    }

    /**
     * Converts the boardTile of every cell within the gameBoard into a string pattern
     * <p>
     * The pattern consists of tokens, each token is made up of an optional number prefix and a letter suffix <br>
     * The number prefix specifies the <strong>amount of times this tile is repeated</strong>. If the token lacks
     * the number prefix, it is assumed that the value is 1. <br>
     * The letter suffix represents the type of tile a cell would take, and its casing determines player ownership.
     * (UPPERCASE = Player 1, lowercase = Player 2)
     * </p>
     * <strong>Board tile mappings:</strong>
     * <ul>
     * <li>'L' = Land</li>
     * <li>'R' = River</li>
     * <li>'T|t' = Trap</li>
     * <li>'A|a' = Animal den</li>
     * </ul>
     *
     * @since 1.7
     * @see BoardTile
     * @see BOARD_TILES
     */
    private String toBoardPattern() {
        int maxCapacity = ROWS * COLUMNS;
        StringBuilder pattern = new StringBuilder(maxCapacity);
        char currentTile;
        char previousTile = getTileChar(gameBoard[0][0].getTile());
        int count = 0;

        for (BoardCell[] row : gameBoard) {
            for (BoardCell col : row) {
                currentTile = getTileChar(col.getTile());

                if (currentTile == previousTile)
                    count++;
                else {
                    if (count > 1) {
                        pattern.append(count);
                    }

                    pattern.append(previousTile);
                    previousTile = currentTile;
                    count = 1;
                }
            }
        }

        if (count > 1) {
            pattern.append(count);
        }

        pattern.append(previousTile);
        return pattern.toString().replaceAll("\\W", "");
    }

    /**
     * A helper method for determining the corresponding tile character for each board tile type
     * <p>
     * <strong>Board tile mappings:</strong>
     * <ul>
     * <li>'L' = Land</li>
     * <li>'R' = River</li>
     * <li>'T|t' = Trap</li>
     * <li>'A|a' = Animal den</li>
     * </ul>
     * </p>
     *
     * @param tile the board tile to be converted to a character
     * @return the character representation of the board tile
     *
     * @since 1.7
     * @see BOARD_TILES
     */
    private char getTileChar(BoardTile tile) {
        return switch (tile.getType()) {
            case LAND -> 'L';
            case RIVER -> 'R';
            case TRAP -> (tile.getPlayerIndex() == 1) ? 'T' : 't';
            case ANIMAL_DEN -> (tile.getPlayerIndex() == 1) ? 'A' : 'a';
        };
    }

    public int getColumns() {
        return COLUMNS;
    }

    public int getROWS() {
        return ROWS;
    }

    public BoardCell[][] getGameBoard() {
        return gameBoard;
    }

    public BoardCell getCell(int row, int column) {
        return gameBoard[row][column];
    }
}
