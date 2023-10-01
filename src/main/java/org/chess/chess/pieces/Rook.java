package org.chess.chess.pieces;

import org.chess.boardgame.Board;
import org.chess.chess.ChessPiece;
import org.chess.chess.enums.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] board = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return board;
    }
}
