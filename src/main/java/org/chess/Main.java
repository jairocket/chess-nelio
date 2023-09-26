package org.chess;

import org.chess.boardgame.Board;
import org.chess.chess.ChessMatch;
import org.chess.chess.ChessPiece;
import org.chess.chess.ChessPosition;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while(true) {
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.print("Origin: ");
            ChessPosition origin = UI.readChessPosition(scanner);

            System.out.println();
            System.out.print("Target: ");
            ChessPosition target = UI.readChessPosition(scanner);

            ChessPiece capturedPiece = chessMatch.performChessMove(origin, target);
        }


//        scanner.close();
    }
}