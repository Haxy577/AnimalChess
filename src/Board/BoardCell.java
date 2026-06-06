package Board;

import AnimalPieces.AnimalPiece;

public class BoardCell {

    private AnimalPiece piece;
    private final BoardTile tile;
    private final int row;
    private final int col;

    public BoardCell(BoardTile tile, int row, int col) {
        this.tile = tile;
        this.row = row;
        this.col = col;
    }

    public BoardCell(AnimalPiece piece, BoardTile tile, int row, int col) {
        this(tile, row, col);
        this.piece = piece;
    }

    public AnimalPiece getPiece() {
        return piece;
    }

    public void setPiece(AnimalPiece piece) {
        this.piece = piece;
    }

    public BoardTile getTile() {
        return tile;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "(r=" + this.row + ",c=" + this.col + ")," + this.tile.toString() + "," + this.piece.toString();
    }
}
