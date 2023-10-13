package org.chess.chess;

import org.chess.boardgame.Board;
import org.chess.boardgame.Piece;
import org.chess.boardgame.Position;
import org.chess.chess.enums.Color;
import org.chess.chess.exceptions.ChessException;
import org.chess.chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private int turn;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private Color currentPlayer;
    private ChessPiece enPassantVulnerable;

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

    public boolean getCheckMate() {
        return checkMate;
    }

    public boolean[][] possibleMovements(ChessPosition originPosition) {
        Position position = originPosition.toPosition();
        validateOriginPosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public boolean getCheck() {
        return check;
    }

    public ChessPiece performChessMove(ChessPosition originPosition, ChessPosition targetPosition) {
        Position origin = originPosition.toPosition();
        Position target = targetPosition.toPosition();

        validateOriginPosition(origin);
        validateTargetPosition(origin, target);

        Piece capturedPiece = makeMove(origin, target);

        if(testCheck(currentPlayer)) {
            undoMove(origin, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        ChessPiece movedPiece = (ChessPiece)board.piece(target);

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if(testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        if(movedPiece instanceof Pawn && (target.getRow() == origin.getRow() + 2 || target.getRow() == origin.getRow() - 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private Piece makeMove(Position origin, Position target) {
        ChessPiece originPiece = (ChessPiece) board.removePiece(origin);
        originPiece.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(originPiece, target);
        if(capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        if(originPiece instanceof King && target.getColumn() == origin.getColumn() + 2) {
            Position rookOriginPosition = new Position(origin.getRow(), origin.getColumn() + 3);
            Position rookTargetPosition = new Position(origin.getRow(), origin.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookOriginPosition);
            board.placePiece(rook, rookTargetPosition);
            rook.increaseMoveCount();
        }

        if(originPiece instanceof King && target.getColumn() == origin.getColumn() - 2) {
            Position rookOriginPosition = new Position(origin.getRow(), origin.getColumn() - 4);
            Position rookTargetPosition = new Position(origin.getRow(), origin.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookOriginPosition);
            board.placePiece(rook, rookTargetPosition);
            rook.increaseMoveCount();
        }

        if(originPiece instanceof Pawn) {
            if(origin.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if(originPiece.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() -1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }
        return capturedPiece;
    }

    private void undoMove(Position origin, Position target, Piece capturedPiece){
        ChessPiece removedPiece = (ChessPiece) board.removePiece(target);
        removedPiece.decreaseMoveCount();
        board.placePiece(removedPiece, origin);

        if(capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        if(removedPiece instanceof King && target.getColumn() == origin.getColumn() + 2) {
            Position rookOriginPosition = new Position(origin.getRow(), origin.getColumn() + 3);
            Position rookTargetPosition = new Position(origin.getRow(), origin.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookTargetPosition);
            board.placePiece(rook, rookOriginPosition);
            rook.decreaseMoveCount();
        }

        if(removedPiece instanceof King && target.getColumn() == origin.getColumn() - 2) {
            Position rookOriginPosition = new Position(origin.getRow(), origin.getColumn() - 4);
            Position rookTargetPosition = new Position(origin.getRow(), origin.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(rookTargetPosition);
            board.placePiece(rook, rookOriginPosition);
            rook.decreaseMoveCount();
        }
        if(removedPiece instanceof Pawn) {
            if(origin.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)board.removePiece(target);

                Position pawnPosition;
                if(removedPiece.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }
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

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list) {
            if(p instanceof King) {
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece piece : opponentPieces) {
            boolean[][] mat = piece.possibleMoves();
            if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if(!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p: list) {
            boolean[][] mat = p.possibleMoves();
            for(int i=0; i < board.getRows(); i++){
                for(int j=0; j < board.getColumns(); j++) {
                    if(mat[i][j]){
                        Position origin = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(origin, target);
                        boolean testCheck = testCheck(color);
                        undoMove(origin, target, capturedPiece);
                        if(!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece chessPiece) {
        board.placePiece(chessPiece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(chessPiece);
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));

        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));


        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));

        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));

    }
}
