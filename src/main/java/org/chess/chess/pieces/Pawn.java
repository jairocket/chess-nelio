package org.chess.chess.pieces;

import org.chess.boardgame.Board;
import org.chess.boardgame.Position;
import org.chess.chess.ChessPiece;
import org.chess.chess.enums.Color;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pawnPosition = new Position(0,0);
        Position twoBlocksForwardPosition = new Position(0, 0);

        if(getColor() == Color.WHITE) {
            pawnPosition.setValues(position.getRow() - 1, position.getColumn());
            boolean canMoveABlockForward = getBoard().positionExists(pawnPosition) &&
                    !getBoard().isThereAPiece(pawnPosition);
            if(canMoveABlockForward) {
                mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }

            twoBlocksForwardPosition.setValues(position.getRow() - 2, position.getColumn());
            boolean canMoveTwoBlocksForward =
                    getBoard().positionExists(twoBlocksForwardPosition) &&
                    !getBoard().isThereAPiece(twoBlocksForwardPosition) &&
                    getMoveCount() == 0 &&
                    canMoveABlockForward;
            if(canMoveTwoBlocksForward) {
                mat[twoBlocksForwardPosition.getRow()][twoBlocksForwardPosition.getColumn()] = true;
            }

            pawnPosition.setValues(position.getRow() - 1, position.getColumn() -1);
            boolean canMoveForwardAndLeft =
                    getBoard().positionExists(pawnPosition) &&
                    isThereOpponentPiece(pawnPosition);
            if(canMoveForwardAndLeft) {
                mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }

            pawnPosition.setValues(position.getRow() - 1, position.getColumn() +1);
            boolean canMoveForwardAndRight=
                    getBoard().positionExists(pawnPosition) &&
                            isThereOpponentPiece(pawnPosition);
            if(canMoveForwardAndRight) {
                mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }

        } else {
            pawnPosition.setValues(position.getRow() + 1, position.getColumn());
            boolean canMoveABlockForward = getBoard().positionExists(pawnPosition) &&
                    !getBoard().isThereAPiece(pawnPosition);
            if(canMoveABlockForward) {
                mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }

            twoBlocksForwardPosition.setValues(position.getRow() + 2, position.getColumn());
            boolean canMoveTwoBlocksForward =
                    getBoard().positionExists(twoBlocksForwardPosition) &&
                            !getBoard().isThereAPiece(twoBlocksForwardPosition) &&
                            getMoveCount() == 0 &&
                            canMoveABlockForward;
            if(canMoveTwoBlocksForward) {
                mat[twoBlocksForwardPosition.getRow()][twoBlocksForwardPosition.getColumn()] = true;
            }

            pawnPosition.setValues(position.getRow() + 1, position.getColumn() -1);
            boolean canMoveForwardAndLeft =
                    getBoard().positionExists(pawnPosition) &&
                            isThereOpponentPiece(pawnPosition);
            if(canMoveForwardAndLeft) {
                mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }

            pawnPosition.setValues(position.getRow() + 1, position.getColumn() +1);
            boolean canMoveForwardAndRight=
                    getBoard().positionExists(pawnPosition) &&
                            isThereOpponentPiece(pawnPosition);
            if(canMoveForwardAndRight) {
                mat[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }

        }

        return mat;
    }

    @Override
    public String toString(){
        return "P";
    }
}
