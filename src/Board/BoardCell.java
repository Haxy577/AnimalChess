package Board;

import AnimalPieces.AnimalPiece;

/**
 * Represents a single cell/position within the gameBoard array
 * <p>
 * Each specific cell within the gameBoard has an immutable row and column values which
 * represents its position relative to the gameBoard array. It also has an immutable BoardTile objects
 * which represents the type of tile this cell represents. It can also contain an AnimalPiece object
 * which represents the current animal piece this cell contains.
 * </p>
 *
 * @see GameBoard
 * @see BoardTile
 * @see AnimalPiece
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.6 6/11/2026
 * @since 1.1
 */
public class BoardCell {

    /**
     * Represents the animal piece in this specific cell/location in the game board
     *
     * @see AnimalPiece
     * @since 1.1
     */
    private AnimalPiece piece;

    /**
     * Represents the permanent board tile that this cell contains and its properties
     *
     * @see BoardTile
     * @since 1.1
     */
    private final BoardTile tile;

    /**
     * Represents the cell's row position relative to the gameBoard array
     *
     * @see GameBoard
     * @since 1.2
     */
    private final int row;

    /**
     * Represents the cell's column position relative to the gameBoard array
     *
     * @see GameBoard
     * @since 1.2
     */
    private final int col;

    /**
     * Initializes the immutable values/objects of a single cell in the game board.
     *
     * @param tile represents what kind of board tile this specific cell is
     * @param row represents its row position relative to the gameBoard array
     * @param col represents its column position relative to the gameBoard array
     * @throws IllegalArgumentException if there is no tile set, and/or the row or column position
     * is outside the gameBoard array's index range
     *
     * @since 1.2
     * @see GameBoard
     * @see BoardTile
     */
    public BoardCell(BoardTile tile, int row, int col) throws IllegalArgumentException {
        this.piece = null;

        if (tile == null) throw new IllegalArgumentException("The board tile cannot be null");
        if (row < 0) throw new IllegalArgumentException("Its row position must be within the gameBoard array index range");
        if (col < 0) throw new IllegalArgumentException("Its column position must be within the gameBoard array index range");

        this.tile = tile;
        this.row = row;
        this.col = col;
    }

    /**
     * Initializes the objects that represents a single cell/position in the gameBoard array
     *
     * @param piece represents the current animal piece this specific cell contains
     * @param tile represents what kind of board tile this specific cell is
     * @param row represents its row position relative to the gameBoard array
     * @param col represents its column position relative to the gameBoard array
     *
     * @since 1.2
     * @see AnimalPiece
     * @see GameBoard
     * @see BoardTile
     */
    public BoardCell(AnimalPiece piece, BoardTile tile, int row, int col) {
        this(tile, row, col);
        this.piece = piece;
    }

    /**
     * Returns the current animal piece this specific cell contains. It can either return
     * the animal piece or a null value which represents that there is no piece currently in
     * this cell.
     *
     * @return the animal piece that this cell contains
     *
     * @since 1.1
     * @see AnimalPiece
     */
    public AnimalPiece getPiece() {
        return piece;
    }

    /**
     * Changes the current animal piece this specific cell contains. It can contain either
     * the animal piece or a null value which represents that there is no piece in this cell
     * currently.
     *
     * @param piece represents the new animal piece that this cell would contain
     *
     * @since 1.1
     * @see AnimalPiece
     */
    public void setPiece(AnimalPiece piece) {
        this.piece = piece;
    }

    /**
     * Returns the set board tile of this specific cell
     *
     * @return the kind of board tile this cell contains
     *
     * @since 1.1
     * @see BoardTile
     */
    public BoardTile getTile() {
        return tile;
    }

    /**
     * Returns the row position of this specific cell relative to the gameBoard array
     *
     * @return its row position relative to the gameBoard array
     *
     * @since 1.2
     * @see GameBoard
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column position of this specific cell relative to the gameBoard aray
     *
     * @return its column position relative to the gameBoard array
     *
     * @since 1.2
     * @see GameBoard
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns a string that represents the values/objects that this specific cell contains
     *
     * @return its row & column position, the board tile it contains, and the animal piece it contains
     *
     * @since 1.5
     * @see AnimalPiece
     * @see BoardTile
     */
    @Override
    public String toString() {
        return "(r=" + this.row + ",c=" + this.col + ")," + this.tile.toString() + "," + this.piece.toString();
    }
}