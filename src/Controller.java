import java.awt.*;

public class Controller {
    private GameBoard board;
    private final int ROW;
    private final int COLUMN;
    private Player activePlayer;
    private Player player1;
    private Player player2;

    public Controller() {
        player1 = new Player("Player 1", new Color(113, 126, 221));
        player2 = new Player("Player 2", new Color(214, 104, 104));
        board = new GameBoard(player1, player2);
        activePlayer = player1;
        ROW = 7;
        COLUMN = 9;
    }

    public String getPieceNameAt(int row, int column) {
        AnimalPiece piece = board.getCellAt(row, column).getPiece();
        return (piece == null) ? null : piece.pieceName().toLowerCase();
    }

    public String getPieceRankAt(int row, int column) {
        AnimalPiece piece = board.getCellAt(row, column).getPiece();
        return (piece == null) ? null : Integer.toString(piece.getRank());
    }

    public String getTileAt(int row, int column) {
        return board.getCellAt(row, column).getTile().getType().toString();
    }

    public int getRow() {
        return ROW;
    }

    public int getColumn() {
        return COLUMN;
    }

    public Color getPieceColorAt(int row, int column) {
        return board.getCellAt(row, column).getPiece().getPlayer().getColor();
    }

    public Color getTileColorAt(int row, int column) {
        BoardTile tile = board.getCellAt(row, column).getTile();
        Player player = tile.getPlayer();
        return (player == null) ? tile.getType().COLOR : player.getColor();
    }

    public boolean isTilePlayerOwnedAt(int row, int column) {
        return board.getCellAt(row, column).getTile().getPlayer() != null;
    }

    public boolean doesPieceExistAt(int row, int column) {
        return board.getCellAt(row, column).getPiece() != null;
    }

    public String getPlayerName(int num) {
        return (num == 1) ? player1.getName() : (num == 2) ? player2.getName() : null;
    }

    public Color getPlayerColor(int num) {
        return (num == 1) ? player1.getColor() : (num == 2) ? player2.getColor() : null;
    }
}