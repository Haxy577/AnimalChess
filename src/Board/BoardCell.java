package Board;

import AnimalPieces.AnimalPiece;

public class BoardCell {

    private AnimalPiece piece;
    private final BoardTile tile;

    public BoardCell(BoardTile tile) {
        this.tile = tile;
    }

    public BoardCell(AnimalPiece piece, BoardTile tile) {
        this(tile);
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
}
