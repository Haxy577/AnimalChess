package Board;

import Resources.BoardTiles;

public class BoardTile {

    private BoardTiles type;
    private int playerIndex;

    public BoardTile(BoardTiles type) {
        this.type = type;
    }

    public BoardTile(BoardTiles type, int playerIndex) {
        this(type);
        this.playerIndex = playerIndex;
    }

    public BoardTiles getType() {
        return this.type;
    }

    public void setType(BoardTiles type) {
        this.type = type;
    }

    public int getPlayerIndex() {
        return this.playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    @Override
    public String toString() {
        return "Tile=" + this.type + "(" + this.playerIndex + ")";
    }
}
