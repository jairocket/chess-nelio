package org.chess.chess;

import org.chess.boardgame.Board;
import org.chess.boardgame.Piece;
import org.chess.chess.enums.Color;

public class ChessPiece extends Piece {
    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
