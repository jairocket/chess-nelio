package org.chess.chess.pieces;

import org.chess.boardgame.Board;
import org.chess.boardgame.Position;
import org.chess.chess.ChessPiece;
import org.chess.chess.enums.Color;

public class Bishop extends ChessPiece {
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] board = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0,0);

        p.setValues(position.getRow() - 1, position.getColumn() -1);
        while(getBoard().positionExists(p) && !getBoard().isThereAPiece(p)) {
            board[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() -1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        while(getBoard().positionExists(p) && !getBoard().isThereAPiece(p)) {
            board[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        while(getBoard().positionExists(p) && !getBoard().isThereAPiece(p)) {
            board[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        while(getBoard().positionExists(p) && !getBoard().isThereAPiece(p)) {
            board[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        return board;
    }
    @Override
    public String toString(){
        return "B";
    }
}
