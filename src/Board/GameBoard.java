package Board;

import AnimalPieces.AnimalPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GameBoard {

    final int boardRows;
    final int boardColumns;
    private BoardCell[][] gameBoard;
    HashMap<AnimalPiece, ArrayList<BoardCell>> allPlayerMoves;

    GameBoard() {
        this.boardRows = 7;
        this.boardColumns = 9;
        this.gameBoard = new BoardCell[boardRows][boardColumns];
    }

    private Stack<AnimalPiece> getAllPlayerPieces(int playerIndex) {
        Stack<AnimalPiece> allPieces = new Stack<>();

        for (BoardCell[] row : this.gameBoard) {
            for (BoardCell column : row) {
                if (column.getPiece().getPlayerIndex() == playerIndex)
                    allPieces.push(column.getPiece());
            }
        }

        return allPieces;
    }

    public HashMap<AnimalPiece, ArrayList<BoardCell>> getAllPlayerMoves() {
        HashMap<AnimalPiece, ArrayList<BoardCell>> allMoves = new HashMap<>();



        return allMoves;
    }
}
