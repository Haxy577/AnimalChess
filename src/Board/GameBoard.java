package Board;

import AnimalPieces.*;

import java.util.*;

/**
 * Represents the current game board of two players that are playing the game "Animal Chess".
 * The gameboard consists of an array of BoardCell objects.
 *
 * @see BoardCell
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.6 6/11/2026
 * @since 1.1
 */
public class GameBoard {

    /**
     * The number of rows the gameboard has. This field cannot
     * be changed once set
     *
     * @since 1.1
     */
    public final int boardRows;

    /**
     * the number of columns the gameboard has. This field cannot be changed
     * once set
     *
     * @since 1.1
     */
    public final int boardColumns;

    /**
     * Represents the playing field of the game. This is a 2-dimensional array of BoardCell.
     * This field cannot be changed once set
     *
     * @since 1.1
     * @see BoardCell
     */
    public final BoardCell[][] gameBoard;

    /**
     * Tracks all the possible moves a player can make in a single turn. The key consists
     * of all the pieces the player controls, and the list consists of all the possible
     * moves these pieces can do
     *
     * @since 1.1
     * @see BoardCell
     * @see #getAllPlayerMoves(int)
     */
    Map<BoardCell, List<BoardCell>> allPlayerMoves;

    /**
     * Sets the dimensions of the board and initializes the gameBoard
     *
     * @since 1.1
     * @see BoardCell
     */
    public GameBoard() {
        this.boardRows = 7;
        this.boardColumns = 9;
        this.gameBoard = new BoardCell[boardRows][boardColumns];
    }

    /**
     * Gets all the available pieces the player has and stores the position of each piece
     * in a list
     *
     * @param playerIndex the player index of the player that currently has the turn
     * @return all the available pieces of a player
     *
     * @since 1.1
     */
    private List<BoardCell> getAllPlayerPieces(int playerIndex) {
        List<BoardCell> allPieces = new ArrayList<>();

        for (BoardCell[] row : this.gameBoard) {
            for (BoardCell column : row) {
                if (column.getPiece() == null)
                    continue;

                if (column.getPiece().getPlayerIndex() == playerIndex)
                    allPieces.add(column);
            }
        }

        return allPieces;
    }

    /**
     * Gets all the available moves of a player within a single turn
     *
     * @param playerIndex the player index of the player that currently has the turn
     * @return a map that contains each piece as the key and the available moves of each piece
     * as the values
     *
     * @since 1.1
     */
    public HashMap<BoardCell, List<BoardCell>> getAllPlayerMoves(int playerIndex) {
        HashMap<BoardCell, List<BoardCell>> allMoves = new HashMap<>();
        List<BoardCell> allPieces = getAllPlayerPieces(playerIndex);

        for (BoardCell cell : allPieces) {
            allMoves.put(cell, cell.getPiece().getAllMoves(cell, this.gameBoard));
        }

        return allMoves;
    }
}
