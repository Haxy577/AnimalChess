package AnimalPieces;

import Board.BoardCell;
import Board.BoardTile;
import Resources.Animals;
import Resources.BoardTiles;

/**
 * @author Richmond Jase Von M. Salvador
 * @version 1.6 6/11/2026
 * @since 1.0
 */
public class Mouse extends AnimalPiece {

    public Mouse(int playerIndex) {
        super(Animals.MOUSE, playerIndex);
    }

    @Override
    public boolean canMove(BoardCell source, BoardCell... destination) {
        AnimalPiece movingPiece = source.getPiece();
        AnimalPiece targetPiece = destination[0].getPiece();
        BoardTile destinationTile = destination[0].getTile();

        if (destinationTile.getType() == BoardTiles.ANIMAL_DEN &&
                destinationTile.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;
        else if (targetPiece == null)
            return true;
        else if (targetPiece.getPlayerIndex() == movingPiece.getPlayerIndex())
            return false;
        else if (destinationTile.getType() == BoardTiles.TRAP)
            return true;
        else return targetPiece.getRank() <= movingPiece.getRank();
    }
}
