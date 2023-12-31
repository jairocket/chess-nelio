package org.chess.chess.pieces;

import org.chess.boardgame.Board;
import org.chess.boardgame.Position;
import org.chess.chess.ChessPiece;
import org.chess.chess.enums.Color;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece)getBoard().piece(position);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] board = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);
        p.setValues(position.getRow() - 1, position.getColumn() -2);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() -2, position.getColumn() -1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() -2, position.getColumn() + 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() -1, position.getColumn() + 2);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() +1, position.getColumn() + 2);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() +2, position.getColumn() + 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() +2, position.getColumn() - 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() +1, position.getColumn() -2);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        return board;
    }
    @Override
    public String toString(){
        return "N";
    }
}
