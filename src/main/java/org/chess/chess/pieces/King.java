package org.chess.chess.pieces;

import org.chess.boardgame.Board;
import org.chess.boardgame.Position;
import org.chess.chess.ChessMatch;
import org.chess.chess.ChessPiece;
import org.chess.chess.enums.Color;

public class King extends ChessPiece {
    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }



    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece)getBoard().piece(position);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] board = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);
        p.setValues(position.getRow() - 1, position.getColumn());
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + 1, position.getColumn());
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow(), position.getColumn() + 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow(), position.getColumn() - 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() -1, position.getColumn() - 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() -1, position.getColumn() + 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() +1, position.getColumn() - 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() +1, position.getColumn() + 1);
        if(getBoard().positionExists(p) && canMove(p)) {
            board[p.getRow()][p.getColumn()] = true;
        }

        if(getMoveCount() == 0 && !chessMatch.getCheck()) {
            Position rookPositionOne = new Position(position.getRow(), position.getColumn() + 3);
            if(testRookCastling(rookPositionOne)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
                    board[position.getRow()][position.getColumn() + 2] = true;
                }
            }

            Position rookPositionTwo = new Position(position.getRow(), position.getColumn() - 4);
            if(testRookCastling(rookPositionTwo)) {
                Position p3 = new Position(position.getRow(), position.getColumn() - 1);
                Position p4 = new Position(position.getRow(), position.getColumn() - 2);
                Position p5 = new Position(position.getRow(), position.getColumn() - 3);
                if(getBoard().piece(p3) == null && getBoard().piece(p4) == null && getBoard().piece(p5) == null) {
                    board[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return board;
    }
}
