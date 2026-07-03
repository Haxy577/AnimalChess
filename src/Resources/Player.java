package Resources;

public class Player {
    private final String NAME;
    private final String ANSI_COLOR;
    private int playerIndex;

    public Player(String name, String ansiColor) {
        this.NAME = name;
        this.ANSI_COLOR = ansiColor;
        playerIndex = 0;
    }

    public Player(String name, String ansiColor, int playerIndex) {
        this(name, ansiColor);
        this.playerIndex = playerIndex;
    }

    public String getName() {
        return NAME;
    }

    public String getAnsiColor() {
        return ANSI_COLOR;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
