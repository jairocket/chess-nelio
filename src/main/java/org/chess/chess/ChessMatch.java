package org.chess.chess;

import org.chess.boardgame.Board;
import org.chess.boardgame.Piece;
import org.chess.boardgame.Position;
import org.chess.chess.enums.Color;
import org.chess.chess.exceptions.ChessException;
import org.chess.chess.pieces.King;
import org.chess.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private int turn;
    private Board board;
    private Color currentPlayer;
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
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

    public boolean[][] possibleMovements(ChessPosition originPosition) {
        Position position = originPosition.toPosition();
        validateOriginPosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition originPosition, ChessPosition targetPosition) {
        Position origin = originPosition.toPosition();
        Position target = targetPosition.toPosition();

        validateOriginPosition(origin);
        validateTargetPosition(origin, target);

        Piece capturedPiece = makeMove(origin, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private Piece makeMove(Position origin, Position target) {
        Piece originPiece = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(originPiece, target);
        if(capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void validateOriginPosition(Position origin) {
        if(!board.isThereAPiece(origin)){
            throw new ChessException("There is no piece on origin position");
        }
        if(currentPlayer != ((ChessPiece)board.piece(origin)).getColor()) {
            throw new ChessException("This piece is not yours");
        }
        if(!board.piece(origin).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for this piece");
        }
    }

    private void validateTargetPosition(Position origin, Position target) {
        if(!board.piece(origin).possibleMove(target)) {
            throw new ChessException("Invalid move");
        }
    }

    private void nextTurn() {
        turn ++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void placeNewPiece(char column, int row, ChessPiece chessPiece) {
        board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(chessPiece);
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
