package Board;

import Resources.BoardTiles;

public class BoardTile {

    private BoardTiles type;
    private int playerIndex;

    public BoardTile(BoardTiles type, int playerIndex) {
        this.type = type;
        this.playerIndex = playerIndex;
    }

    public BoardTiles getType() {
        return type;
    }

    public void setType(BoardTiles type) {
        this.type = type;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
