package Resources;

import AnimalPieces.AnimalPiece;

public class Player {
    private final String NAME;
    private final PlayerColor ANSI_COLOR;
    private int index;

    public Player(String name, PlayerColor ansiColor) {
        this.NAME = name;
        this.ANSI_COLOR = ansiColor;
        index = 0;
    }

    public Player(String name, PlayerColor ansiColor, int index) {
        this(name, ansiColor);
        this.index = index;
    }

    @Override
    public String toString() {
        return "Player[name=" + NAME + ",color=" + ANSI_COLOR + ",index=" + index + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Player player))
            return false;

        return NAME.equals(player.getName()) && ANSI_COLOR.equals(player.getAnsiColor()) && index == player.getIndex();
    }

    public String getName() {
        return NAME;
    }

    public PlayerColor getAnsiColor() {
        return ANSI_COLOR;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) throws IllegalArgumentException {
        if (index < 1 || index > 2)
            throw new IllegalArgumentException("The index can only be either 1 or 2");

        this.index = index;
    }
}
