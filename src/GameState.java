import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Represents the current state of the game and is responsible for interpreting the moves of the players
 *
 * @see GameBoard
 * @see Player
 *
 * @author Richmond Jase Von M. Salvador
 * @version 3.0 8/2/2026
 * @since 3.0
 */
public class GameState {

    /**
     * Represents the board where the game is taking place
     *
     * @since 3.0
     * @see GameBoard
     */
    private final GameBoard BOARD;

    /**
     * Represents the first player who has the first move advantage
     *
     * @since 3.0
     * @see Player
     */
    private final Player PLAYER1;

    /**
     * Represents the second player
     *
     * @since 3.0
     * @see Player
     */
    private final Player PLAYER2;

    /**
     * Represents the player who currently has the turn to move
     *
     * @since 3.0
     * @see Player
     */
    private Player activePlayer;

    /**
     * Represents all the moves the active player can currently do
     *
     * @since 3.0
     * @see GameBoard#getAllPlayerMoves(Player)
     */
    private HashMap<BoardCell, List<BoardCell>> allMoves;

    /**
     * Represents the winner of the game
     *
     * @since 3.0
     * @see Player
     */
    private Player winner;

    /**
     * Constructs the initial game state with the specified game board, the first player, and the second player. Where
     * the first player defined would have the first move
     *
     * @param board the board where the game is taking place
     * @param p1 the first player
     * @param p2 the second player
     *
     * @since 3.0
     * @see GameBoard
     * @see Player
     */
    public GameState(GameBoard board, Player p1, Player p2) {
        BOARD = board;
        PLAYER1 = p1;
        PLAYER2 = p2;
        activePlayer = PLAYER1;
        allMoves = BOARD.getAllPlayerMoves(activePlayer);
    }

    /**
     * A helper method to switch the activePlayer field with the other player
     *
     * @since 3.0
     */
    private void switchTurn() {
        activePlayer = (activePlayer.equals(PLAYER1)) ? PLAYER2 : PLAYER1;
    }

    /**
     * Attempts to move the piece from the specified source position to the target position
     *
     * @param sourceRow the row position of the piece to be moved
     * @param sourceColumn the column position of the piece to be moved
     * @param targetRow the row position of the cell where the piece would be moved to
     * @param targetColumn the column position of the cell where the piece would be moved to
     * @return true if the specified move is successful, false otherwise
     *
     * @since 3.0
     */
    public boolean attemptMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        if (winner != null)
            return false;

        BoardCell source = BOARD.getCellAt(sourceRow, sourceColumn);
        BoardCell target = BOARD.getCellAt(targetRow, targetColumn);

        if (!allMoves.containsKey(source))
            return false;

        List<BoardCell> moves = allMoves.get(source);

        if (!moves.contains(target))
            return false;

        BOARD.movePiece(source, target);

        if (target.getTile().getType() == Tiles.DEN) {
            winner = activePlayer;
        }

        switchTurn();
        allMoves = BOARD.getAllPlayerMoves(activePlayer);

        if (allMoves.isEmpty() || !hasMoves()) {
            winner = (activePlayer.equals(PLAYER1)) ? PLAYER2 : PLAYER1;
        }

        return true;
    }

    /**
     * A helper method to check if the current player still has valid moves
     *
     * @return true if any of the pieces of the active player still have valid moves, false otherwise
     *
     * @since 3.0
     */
    private boolean hasMoves() {
        Set<BoardCell> moves = allMoves.keySet();

        for (BoardCell move : moves) {
            if (!allMoves.get(move).isEmpty())
                return true;
        }

        return false;
    }

    /**
     * A helper method to determine whether a piece exists on the specified row and column position
     *
     * @param row the row position of the cell to be checked
     * @param column the column position of the cell to be checked
     * @return true if a piece does exist at the specified position, false otherwise
     */
    public boolean doesPieceExistAt(int row, int column) {
        return getCellAt(row, column).getPiece() != null;
    }

    /**
     * A getter method to get the game board of this instance
     *
     * @return the game board used
     *
     * @since 3.0
     */
    public GameBoard getBoard() {
        return BOARD;
    }

    /**
     * A getter method to retrieve the information of a cell at the specified row and column position
     *
     * @param row the row position of the cell
     * @param column the column position of the cell
     * @return the BoardCell object at the specified position
     *
     * @since 3.0
     * @see BoardCell
     */
    public BoardCell getCellAt(int row, int column) {
        return BOARD.getCellAt(row, column);
    }

    /**
     * A getter method to retrieve all the possible moves the specified cell can perform
     *
     * @param key the cell to retrieve its possible moves
     * @return the possible moves of the specified cell
     *
     * @since 3.0
     * @see BoardCell
     */
    public List<BoardCell> getMovesAt(BoardCell key) {
        return allMoves.get(key);
    }

    /**
     * A getter method for the first player and has the first move advantage
     *
     * @return the first player
     *
     * @since 3.0
     * @see Player
     */
    public Player getPlayer1(){
        return PLAYER1;
    }

    /**
     * A getter method for the second player
     *
     * @return the second player
     *
     * @since 3.0
     * @see Player
     */
    public Player getPlayer2() {
        return PLAYER2;
    }

    /**
     * A getter method for the player who had won the game
     *
     * @return the winner of the game, or null if a winner had not been decided yet
     *
     * @since 3.0
     * @see Player
     */
    public Player getWinner() {
        return winner;
    }
}
