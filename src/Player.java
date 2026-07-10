public class Player {
    private String name;
    private PlayerColor color;
    private int index;

    public Player(String name) {
        this(name, PlayerColor.RED);
    }

    public Player(String name, PlayerColor ansiColor) {
        this(name, ansiColor, 0);
    }

    public Player(String name, PlayerColor ansiColor, int index) {
        this.name = name;
        color = ansiColor;
        this.index = index;
    }

    public void swap(Player player) {
        String name = player.getName();
        PlayerColor color = player.getAnsiColor();
        int index = player.getIndex();

        player.setName(this.name);
        player.setAnsiColor(this.color);
        player.setIndex(this.index);

        this.name = name;
        this.color = color;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Player[name=" + name + ",color=" + color + ",index=" + index + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Player player))
            return false;

        return name.equals(player.getName()) && color.equals(player.getAnsiColor()) && index == player.getIndex();
    }

    public String getName() {
        return name;
    }

    public PlayerColor getAnsiColor() {
        return color;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) throws IllegalArgumentException {
        if (index < 1 || index > 2)
            throw new IllegalArgumentException("The index can only be either 1 or 2");

        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnsiColor(PlayerColor color) {
        this.color = color;
    }
}
