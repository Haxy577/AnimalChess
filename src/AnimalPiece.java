import java.util.List;
import java.util.ArrayList;

/**
 * Represents an individual animal game piece on the board.
 * <p>
 * Each piece has an immutable {@link #RANK} which determines numerical rank of the piece and
 * an immutable {@link #PLAYER} which determines the Player instance that is controlling the piece,
 * </p>
 * <p>
 * By default, animal pieces conform to the following set of movement and capture rules, though
 * specific subclasses may override these behaviours:
 * <ol>
 * <li>Move exactly one space horizontally or vertically per turn</li>
 * <li>Cannot enter a river/water tile</li>
 * <li>Cannot enter their own player's animal den</li>
 * <li>Can freely move into an empty destination cell</li>
 * <li>Cannot capture a friendly piece that is controlled by the same player</li>
 * <li>Can freely occupy trap tiles that are owned by the same player</li>
 * <li>Cannot capture an opposing piece that possesses a higher numerical rank</li>
 * </ol>
 * </p>
 *
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 * @see Player
 * @see #isMoveValid(BoardCell, BoardCell)
 *
 * @author Richmond Jase Von M. Salvador
 * @version 2.2 7/20/2026
 * @since 1.0
 */
public abstract class AnimalPiece {

    /**
     * The rank of an animal piece. This field cannot be changed once set.
     *
     * @since 1.0
     */
    private final int RANK;

    /**
     * The player that has ownership/control of this piece. This field cannot
     * be set once set.
     *
     * @since 2.2
     */
    private final Player PLAYER;

    /**
     * Constructs an animal piece with a specified animal rank
     * and the player controlling this animal piece
     *
     * @param rank the rank of the animal piece
     * @param player the player object that has control of this piece
     * @throws IllegalArgumentException if there is no provided {@link #RANK},
     * or the {@link #PLAYER} is a null
     *
     * @since 2.2
     * @see Player
     */
    protected AnimalPiece(int rank, Player player) throws IllegalArgumentException {
        if (player == null)
            throw new IllegalArgumentException("The specified player argument cannot be null");

        RANK = rank;
        PLAYER = player;
    }

    /**
     * A method that returns the name of the piece
     *
     * @return the string representation of the piece
     *
     * @since 1.20
     */
    public abstract String pieceName();

    /**
     * Converts the fields of this class to a string
     *
     * @return the rank of this piece and the details of the player that has control of this piece
     *
     * @since 2.2
     * @see Player
     */
    @Override
    public String toString() {
        return this.pieceName() + "[rank=" + RANK + ",player=" + PLAYER + "]";
    }

    /**
     * Compares the specified object with the current object based on its numeric rank and the player object it contains
     *
     * @param obj   the reference object with which to compare.
     * @return true if the rank and player object are the same, false otherwise
     *
     * @since 2.2
     * @see Player#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof AnimalPiece piece))
            return false;

        return RANK == piece.getRank() && PLAYER.equals(piece.getPlayer());
    }

    /**
     * Gets all the possible moves of the piece. This checks whether the piece can move 1 space to the four
     * cardinal directions (UP, DOWN, LEFT, RIGHT).
     *
     * @param source The board cell that contains the piece
     * @param gameBoard the array of board cells that represents the playing board
     * @throws IllegalArgumentException if the provided source is outside or does not exist with the gameboard, or if the
     * source and/or gameboard is/are null
     * @return a list of all the possible moves the piece can do
     *
     * @since 1.8
     * @see BoardCell
     * @see GameBoard
     * @see Direction
     * @see #canMoveTo(BoardCell, BoardCell[])
     */
    public List<BoardCell> getAllMoves(BoardCell source, BoardCell[][] gameBoard) throws IllegalArgumentException {
        if (source == null || gameBoard == null)
            throw new IllegalArgumentException("Invalid source and/or gameboard. These parameters cannot be null");
        if (source.getRow() < 0 || source.getRow() >= gameBoard.length || source.getCol() < 0 || source.getCol() >= gameBoard[0].length)
            throw new IllegalArgumentException("Invalid source. The specified cell exists outside the bounds of the gameboard array");
        if (!source.equals(gameBoard[source.getRow()][source.getCol()]))
            throw new IllegalArgumentException("Invalid source. The specified cell does not exists within the board");

        List<BoardCell> allMoves = new ArrayList<>();
        BoardCell move;

        for (Direction direction : Direction.values()) {
            move = canMoveTo(source, getDirectionalPath(source, gameBoard, direction));
            if (move != null)
                allMoves.add(move);
        }

        return allMoves;
    }

    /**
     * Returns the specific board cell that the piece can move on to within the specified path.
     *
     * @param source the piece that is requesting to move
     * @param path the path of the moving piece based on the direction
     * @throws IllegalArgumentException if the provided source is null or does not contain this object
     * @return the BoardCell the piece can move to occupy. Returns {@code null} if there is no valid cell
     * the piece can move towards
     *
     * @since 1.8
     * @see BoardCell
     * @see Direction
     * @see #getDirectionalPath(BoardCell, BoardCell[][], Direction)
     * @see #isMoveValid(BoardCell, BoardCell)
     */
    public BoardCell canMoveTo(BoardCell source, BoardCell[] path) throws IllegalArgumentException {
        if (source == null)
            throw new IllegalArgumentException("Invalid argument. The specified source cannot be null");
        if (!this.equals(source.getPiece()))
            throw new IllegalArgumentException("Invalid argument. The specified source does not contain this object");

        if (path == null || path.length == 0)
            return null;

        return (isMoveValid(source, path[0])) ? path[0] : null;
    }

    /**
     * Converts a 2d array into a 1d array of board cells. It would take all entries starting from the
     * adjacent cell relative to the source until the edge of the array based on the direction given.
     *
     * @param source the array of board cells that represents the playing board
     * @param gameBoard the array of board cells that represents the playing board
     * @param direction one of the cardinal direction that the piece is moving towards
     * @throws IllegalArgumentException if the provided source is outside or does not exist with the gameboard, or if the
     * source and/or gameboard is/are null
     * @return an array that contains the path of the piece based on the given direction
     *
     * @since 1.8
     * @see BoardCell
     * @see Direction
     */
    public BoardCell[] getDirectionalPath(BoardCell source, BoardCell[][] gameBoard, Direction direction) throws IllegalArgumentException {
        if (source == null || gameBoard == null)
            throw new IllegalArgumentException("Invalid source and/or gameboard. These parameters cannot be null");

        BoardCell[] path;
        int row = source.getRow();
        int col = source.getCol();

        if (row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length)
            throw new IllegalArgumentException("Invalid source. The specified cell exists outside the bounds of the gameboard array");
        if (!source.equals(gameBoard[row][col]))
            throw new IllegalArgumentException("Invalid source. The specified cell does not exists within the board");

        int deltaRow = (direction == Direction.UP) ? -1 : (direction == Direction.DOWN) ? 1 : 0;
        int deltaCol = (direction == Direction.LEFT) ? -1 : (direction == Direction.RIGHT) ? 1 : 0;
        int limit;

        if (deltaRow != 0) {
            limit = (deltaRow < 0) ? row : gameBoard.length - row - 1;
        } else {
            limit = (deltaCol < 0) ? col : gameBoard[0].length - col - 1;
        }

        path = new BoardCell[limit];

        for (int i = 0; i < limit; i++) {
            int nextRow = row + (deltaRow * (i + 1));
            int nextCol = col + (deltaCol * (i + 1));
            path[i] = gameBoard[nextRow][nextCol];
        }

        return path;
    }

    /**
     * Determines whether the movement is valid given it meets the following conditions:
     * <ol>
     * <li>The parameters must be instantiated</li>
     * <li>The piece that is requesting to move must be instantiated</li>
     * <li>A piece can only move 1 space either horizontally or vertically</li>
     * <li>A piece cannot move to a river tile</li>
     * <li>A piece cannot move to its own den</li>
     * <li>A piece can always move to a cell that is empty/does not contain a piece</li>
     * <li>A piece cannot move to capture a piece that is controlled by the same player</li>
     * <li>A piece can always move to its own trap tile</li>
     * <li>A piece cannot move to capture an opponent's piece with a higher rank than its own rank</li>
     * </ol>
     *
     * @param source the cell containing the piece requesting to move
     * @param destination an array of cells that contain the path the piece is trying to move to. The
     *                    last element of the array is where the piece is attempting to move.
     * @return {@code true} if the piece is allowed to move to the destination, {@code false} if the piece does
     * not exist nor can move to that destination.
     *
     * @since 1.1
     * @see Tiles
     * @see GameBoard
     */
    public boolean isMoveValid(BoardCell source, BoardCell destination) {
        if (source == null || destination == null)
            return false;

        if (source.getPiece() == null)
            return false;

        AnimalPiece movingPiece = source.getPiece();
        AnimalPiece targetPiece = destination.getPiece();
        BoardTile targetTile = destination.getTile();
        Player movingPlayer = movingPiece.getPlayer();
        int distance = Math.abs(source.getCol() - destination.getCol()) + Math.abs(source.getRow() - destination.getRow());

        if (distance != 1)
            return false;

        if (targetTile.getType().isWaterBased())
            return false;

        if (targetTile.getType() == Tiles.ANIMAL_DEN && movingPlayer.equals(targetTile.getPlayer()))
            return false;

        if (targetPiece == null)
            return true;

        if (targetPiece.getPlayer().equals(movingPlayer))
            return false;

        if (targetTile.getType() == Tiles.TRAP && targetTile.getPlayer().equals(movingPiece.getPlayer()))
            return true;

        return targetPiece.getRank() <= movingPiece.getRank();
    }

    /**
     * Returns the corresponding rank associated with the object's animal type.
     *
     * @return the rank of the object.
     *
     * @since 1.0
     */
    public int getRank() {
        return RANK;
    }

    /**
     * Returns the Player object that has control of this animal piece
     *
     * @return the player instance that has control of this piece
     *
     * @since 2.2
     * @see Player
     */
    public Player getPlayer() {
        return PLAYER;
    }
}
