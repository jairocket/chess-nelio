package org.chess.chess;

import org.chess.boardgame.Board;
import org.chess.boardgame.Piece;
import org.chess.boardgame.Position;
import org.chess.chess.enums.Color;
import org.chess.chess.exceptions.ChessException;
import org.chess.chess.pieces.King;
import org.chess.chess.pieces.Rook;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] chessPiece = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                chessPiece[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return chessPiece;
    }

    public ChessPiece performChessMove(ChessPosition originPosition, ChessPosition targetPosition) {
        Position origin = originPosition.toPosition();
        Position target = targetPosition.toPosition();

        validateOriginPosition(origin);

        Piece capturedPiece = makeMove(origin, target);
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position origin, Position target) {
        Piece originPiece = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(originPiece, target);
        return capturedPiece;
    }

    private void validateOriginPosition(Position origin) {
        if(!board.isThereAPiece(origin)){
            throw new ChessException("There is no piece on origin position");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece chessPiece) {
        board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
