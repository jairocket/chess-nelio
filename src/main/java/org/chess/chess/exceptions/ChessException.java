package org.chess.chess.exceptions;

import org.chess.boardgame.exceptions.BoardException;

public class ChessException extends BoardException {
    public ChessException(String message) {
        super(message);
    }
}
