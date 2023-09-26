package org.chess.boardgame.exceptions;

public class BoardException extends RuntimeException {
    public BoardException(String errorMessage) {
        super(errorMessage);
    }
}
