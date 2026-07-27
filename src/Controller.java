import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class Controller {
    private final int ROW;
    private final int COLUMN;
    private final GameBoard BOARD;
    private final Player PLAYER1;
    private final Player PLAYER2;

    private Player activePlayer;
    private HashMap<BoardCell, List<BoardCell>> moves;
    private boolean isNextTurn;

    private Player winner;

    public Controller(GameBoard board, Player p1, Player p2) {
        if (board == null || p1 == null || p2 == null)
            throw new NullPointerException("The parameters cannot be null");

        PLAYER1 = p1;
        PLAYER2 = p2;
        activePlayer = PLAYER1;

        BOARD = board;
        ROW = BOARD.getRows();
        COLUMN = BOARD.getColumns();

        moves = BOARD.getAllPlayerMoves(activePlayer);
    }

    private boolean isOutsideBoard(int row, int column) {
        return row < 0 || row >= ROW || column < 0 || column >= COLUMN;
    }

    public void update(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
        if (isOutsideBoard(sourceRow, sourceColumn) || isOutsideBoard(targetRow, targetColumn))
            return;

        isNextTurn = false;

        BoardCell source = BOARD.getCellAt(sourceRow, sourceColumn);
        BoardCell target = BOARD.getCellAt(targetRow, targetColumn);

        if (moves.containsKey(source) && moves.get(source).contains(target)) {
            BOARD.movePiece(source, target);

            if (target.getTile().getType() == Tiles.DEN) {
                winner = activePlayer;
                System.out.println(winner);
            }

            activePlayer = (activePlayer.equals(PLAYER1)) ? PLAYER2 : PLAYER1;
            moves = BOARD.getAllPlayerMoves(activePlayer);
            isNextTurn = true;

            boolean doesPiecesHaveMoves = true;
            List<BoardCell> pieces = BOARD.getAllPlayerPieces(activePlayer);

            for (BoardCell piece : pieces) {
                if (moves.get(piece).isEmpty()) {
                    doesPiecesHaveMoves = false;
                    break;
                }
            }

            if (pieces.isEmpty() || !doesPiecesHaveMoves) {
                winner = (activePlayer.equals(PLAYER1)) ? PLAYER2 : PLAYER1;
                System.out.println(winner);
            }
        }
    }

    public boolean isNextTurn() {
        return isNextTurn;
    }

    public Point[] getMovesAt(int row, int column) {
        BoardCell cell = BOARD.getCellAt(row, column);
        AnimalPiece piece = cell.getPiece();

        if (piece == null || !piece.getPlayer().equals(activePlayer))
            return new Point[0];

        List<BoardCell> moves = this.moves.get(cell);

        if (moves == null)
            return new Point[0];

        Point[] points = new Point[moves.size()];

        for (int i = 0; i < moves.size(); i++) {
            int x = moves.get(i).getCol();
            int y = moves.get(i).getRow();
            points[i] = new Point(x, y);
        }

        return points;
    }

    public Controller(Player p1, Player p2) {
        this(new GameBoard(p1, p2), p1, p2);
    }

    public String getPieceNameAt(int row, int column) {
        AnimalPiece piece = BOARD.getCellAt(row, column).getPiece();
        return (piece == null) ? null : piece.pieceName().toLowerCase();
    }

    public String getPieceRankAt(int row, int column) {
        AnimalPiece piece = BOARD.getCellAt(row, column).getPiece();
        return (piece == null) ? null : Integer.toString(piece.getRank());
    }

    public String getTileAt(int row, int column) {
        return BOARD.getCellAt(row, column).getTile().getType().toString();
    }

    public int getRow() {
        return ROW;
    }

    public int getColumn() {
        return COLUMN;
    }

    public Color getPieceColorAt(int row, int column) {
        return BOARD.getCellAt(row, column).getPiece().getPlayer().getColor();
    }

    public Color getTileColorAt(int row, int column) {
        BoardTile tile = BOARD.getCellAt(row, column).getTile();
        Player player = tile.getPlayer();
        return (player == null) ? tile.getType().COLOR : player.getColor();
    }

    public boolean isTilePlayerOwnedAt(int row, int column) {
        return BOARD.getCellAt(row, column).getTile().getPlayer() != null;
    }

    public boolean doesPieceExistAt(int row, int column) {
        return BOARD.getCellAt(row, column).getPiece() != null;
    }

    public String getPlayerName(int num) {
        return (num == 1) ? PLAYER1.getName() : (num == 2) ? PLAYER2.getName() : null;
    }

    public Color getPlayerColor(int num) {
        return (num == 1) ? PLAYER1.getColor() : (num == 2) ? PLAYER2.getColor() : null;
    }
}