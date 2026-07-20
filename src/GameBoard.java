import java.util.*;

/**
 * Represents the current game board of two players that are playing the game "Animal Chess".
 * The gameboard consists of an array of BoardCell objects.
 *
 * @see BoardCell
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.1 7/19/2026
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
    private final BoardCell[][] BOARD;

    private final Player PLAYER1;
    private final Player PLAYER2;

    /**
     * Represents a gameboard pattern that has 7 rows, 9 columns, and the default layout of the game Animal Chess
     * in a horizontal orientation where the first player is at the left while the second player is at the right
     *
     * @since 1.11
     * @see #initialize(String) 
     */
    public static final String DEFAULT_PATTERN = "12L3R3LT2L3R2LtAT5LtaT2L3R2Lt3L3R12L|G1E3m1n1C5d3W3p13P3w3D5c1N1M3e1g";

    /**
     * Constructs the gameboard array with the specified row, columns, and the layout ot each tile and piece within
     * the gameboard
     *
     * @param row the amount of rows the board will have
     * @param column the amount of columns every row would have
     * @param pattern the layout of tiles and pieces
     * @throws IllegalArgumentException if the row and/or column is less than 1 and/or the specified player object(s) is/are null
     *
     * @since 1.11
     * @see BoardCell
     * @see #initialize(String)
     */
    public GameBoard(int row, int column, String pattern, Player p1, Player p2) {
        if (row < 1 || column < 1)
            throw new IllegalArgumentException("The specified row and/or column cannot be less than 1");
        if (p1 == null || p2 == null)
            throw new IllegalArgumentException("The specified player(s) cannot be null");

        ROWS = row;
        COLUMNS = column;
        BOARD = new BoardCell[row][column];
        PLAYER1 = p1;
        PLAYER2 = p2;
        initialize(pattern);
    }

    /**
     * Constructs a gameboard that has 7 rows and 9 columns with a default layout of the game Animal Chess represented
     * in a horizontal orientation
     *
     * @since 1.1
     * @see BoardCell
     * @see #DEFAULT_PATTERN
     */
    public GameBoard(Player p1, Player p2) {
        this(7, 9, DEFAULT_PATTERN, p1, p2);
    }

    /**
     * Constructs a gameboard that has 7 rows and 9 columns with a custom board layout based on the pattern
     *
     * @param pattern the board tile and piece layout of the gameboard
     *
     * @since 1.7
     * @see #initialize(String)
     */
    public GameBoard(String pattern, Player p1, Player p2) {
        this(7, 9, pattern, p1, p2);
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

                string.append(BOARD[i][j].toString());
            }
            string.append('}');
        }
        string.append('}');

        return string.toString();
    }

    /**
     * Compares the current object with the specified object
     *
     * @param obj   the reference object with which to compare.
     * @return {@code true} if both objects have identical field values, {@code false} otherwise
     *
     * @since 1.11
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameBoard board) {
            if (board.getRows() != ROWS || board.getColumns() != COLUMNS)
                return false;

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if (!BOARD[i][j].equals(board.getCell(i, j)))
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Gets all the available pieces the player has and stores the position of each piece
     * in a list
     *
     * @param player the player object to search for
     * @throws IllegalArgumentException if the specified player is null
     * @return all the available pieces of the specified player
     *
     * @since 1.1
     * @see BoardCell
     */
    public List<BoardCell> getAllPlayerPieces(Player player) throws IllegalArgumentException {
        if (player == null) throw new IllegalArgumentException("The specified player cannot be null");

        List<BoardCell> allPieces = new ArrayList<>();

        for (BoardCell[] row : BOARD) {
            for (BoardCell column : row) {
                if (column.getPiece() == null)
                    continue;

                if (player.equals(column.getPiece().getPlayer()))
                    allPieces.add(column);
            }
        }

        return allPieces;
    }

    /**
     * Gets all the available moves of a player within a single turn
     *
     * @param player the player index of the player that currently has the turn
     * @throws IllegalArgumentException if the specified player is null
     * @return a map that contains each piece as the key and the available moves of each piece
     * as the values
     *
     * @since 1.1
     */
    public HashMap<BoardCell, List<BoardCell>> getAllPlayerMoves(Player player) throws IllegalArgumentException {
        if (player == null) throw new IllegalArgumentException("The specified player cannot be null");

        HashMap<BoardCell, List<BoardCell>> allMoves = new HashMap<>();
        List<BoardCell> allPieces = getAllPlayerPieces(player);

        for (BoardCell cell : allPieces) {
            allMoves.put(cell, cell.getPiece().getAllMoves(cell, BOARD));
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
     * @throws IllegalArgumentException if the pattern does not contain the '|' character, or the provided patterns for the board and pieces
     * are invalid
     *
     * @since 1.7
     * @see #validateBoardPattern(String)
     * @see #validatePiecePattern(String)
     * @see #parseBoardPattern(String)
     * @see #parsePiecePattern(String)
     */
    public void initialize(String pattern) throws IllegalArgumentException {

        if (pattern == null)
            throw new IllegalArgumentException("Invalid pattern. The pattern cannot be null");

        String cleanPattern = pattern.replaceAll("\\s", "");

        if (!cleanPattern.contains("|"))
            throw new IllegalArgumentException("Invalid pattern. There must be a divider character '|' separating the two different patterns");

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
            int number = (token.length() == 1) ? 1 : Integer.parseInt(token.substring(0, token.length() - 1)) + 1;

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
     * @see Tiles
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
                    case 'L' -> new BoardTile(Tiles.LAND);
                    case 'R' -> new BoardTile(Tiles.RIVER);
                    case 'T' -> new BoardTile(Tiles.TRAP, PLAYER1);
                    case 't' -> new BoardTile(Tiles.TRAP, PLAYER2);
                    case 'A' -> new BoardTile(Tiles.ANIMAL_DEN, PLAYER1);
                    case 'a' -> new BoardTile(Tiles.ANIMAL_DEN, PLAYER2);
                    default -> throw new IllegalArgumentException("Invalid tile character. Expected: [LRTtAa], Actual: " + tileChar);
                };

                BOARD[row][col] = new BoardCell(tile, row, col);
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
            Player player = (Character.isUpperCase(tileChar)) ? PLAYER1 : PLAYER2;

            boardIndex += distance;
            int row = boardIndex / COLUMNS;
            int col = boardIndex % COLUMNS;
            AnimalPiece piece = switch (tileChar) {
                case 'M','m' -> new Mouse(player);
                case 'C','c' -> new Cat(player);
                case 'W','w' -> new Wolf(player);
                case 'D','d' -> new Dog(player);
                case 'P','p' -> new Leopard(player);
                case 'N','n' -> new Lion(player);
                case 'G','g' -> new Tiger(player);
                case 'E','e' -> new Elephant(player);
                default -> throw new IllegalArgumentException("Invalid piece character. Expected: [MmCcWwDdPpNnGgEe], Actual: " + tileChar);
            };

            BOARD[row][col].setPiece(piece);
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

        for (BoardCell[] row : BOARD) {
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

                    pieceChar = (PLAYER1.equals(piece.getPlayer())) ? pieceChar : Character.toLowerCase(pieceChar);

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
     * @see Tiles
     */
    private String toBoardPattern() {
        int maxCapacity = ROWS * COLUMNS;
        StringBuilder pattern = new StringBuilder(maxCapacity);
        char currentTile;
        char previousTile = getTileChar(BOARD[0][0].getTile());
        int count = 0;

        for (BoardCell[] row : BOARD) {
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
     * @see Tiles
     */
    private char getTileChar(BoardTile tile) {
        return switch (tile.getType()) {
            case LAND -> 'L';
            case RIVER -> 'R';
            case TRAP -> (PLAYER1.equals(tile.getPlayer())) ? 'T' : 't';
            case ANIMAL_DEN -> (PLAYER1.equals(tile.getPlayer())) ? 'A' : 'a';
        };
    }

    /**
     * Takes the piece within the specified source and sets the specified target with the taken piece, sets the piece within
     * the specified source to null afterward
     *
     * @param source the cell where the piece would be taken from
     * @param target the cell where the taken piece would be set at
     * @throws IllegalArgumentException if the specified current and/or target cell is not instantiated
     *
     * @since 1.26
     * @see BoardCell
     */
    public void movePiece(BoardCell source, BoardCell target) throws IllegalArgumentException {
        if (source == null || target == null)
            throw new IllegalArgumentException("The specified parameters must be instantiated");

        target.setPiece(source.getPiece());
        source.setPiece(null);
    }

    /**
     * A getter for the column field which represents the length of each row within the board
     *
     * @return the length of each row array
     *
     * @since 1.26
     */
    public int getColumns() {
        return COLUMNS;
    }

    /**
     * A getter for the row field which represents the amount of row the board field contains
     *
     * @return the amount of rows within the 2d array
     *
     * @since 1.26
     */
    public int getRows() {
        return ROWS;
    }

    public Player getPlayer1() {
        return PLAYER1;
    }

    public Player getPlayer2() {
        return PLAYER2;
    }

    /**
     * A getter for the board field which represents the current playing field of the game
     *
     * @return a 2d array of BoardCells that represents the board
     *
     * @since 1.26
     * @see BoardCell
     */
    public BoardCell[][] getBoard() {
        return BOARD;
    }

    /**
     * A getter method that returns a specific BoardCell from the 2d array of BoardCells
     *
     * @param row the index of the desired cell within the 2d array
     * @param column the index of the desired cell within the row
     * @throws IllegalArgumentException if the specified position is outside the bounds of the initialized 2d array
     * @return the BoardCell at the specified location within the board
     *
     * @since 1.26
     * @see BoardCell
     */
    public BoardCell getCell(int row, int column) throws IllegalArgumentException {
        if (row < 0 || row >= ROWS || column < 0 || column >= COLUMNS)
            throw new IllegalArgumentException("The specified position exists outside the initialized board");

        return BOARD[row][column];
    }
}
