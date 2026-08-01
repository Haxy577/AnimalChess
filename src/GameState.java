import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GameState {
    private final GameBoard BOARD;
    private final Player PLAYER1;
    private final Player PLAYER2;
    private Player activePlayer;
    private HashMap<BoardCell, List<BoardCell>> allMoves;
    private Player winner;

    public GameState(GameBoard board, Player p1, Player p2) {
        BOARD = board;
        PLAYER1 = p1;
        PLAYER2 = p2;
        activePlayer = PLAYER1;
        allMoves = BOARD.getAllPlayerMoves(activePlayer);
    }

    private void switchTurn() {
        activePlayer = (activePlayer.equals(PLAYER1)) ? PLAYER2 : PLAYER1;
    }

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

    private boolean hasMoves() {
        Set<BoardCell> moves = allMoves.keySet();

        for (BoardCell move : moves) {
            if (allMoves.get(move).isEmpty())
                return false;
        }

        return true;
    }

    public boolean doesPieceExistAt(int row, int column) {
        return getCellAt(row, column).getPiece() != null;
    }

    public GameBoard getBoard() {
        return BOARD;
    }

    public BoardCell getCellAt(int row, int column) {
        return BOARD.getCellAt(row, column);
    }

    public List<BoardCell> getMovesAt(BoardCell key) {
        return allMoves.get(key);
    }

    public Player getPlayer1(){
        return PLAYER1;
    }

    public Player getPlayer2() {
        return PLAYER2;
    }

    public Player getWinner() {
        return winner;
    }
}
