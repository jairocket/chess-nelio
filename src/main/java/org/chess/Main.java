package org.chess;

import org.chess.boardgame.Board;
import org.chess.chess.ChessMatch;
import org.chess.chess.ChessPiece;
import org.chess.chess.ChessPosition;
import org.chess.chess.exceptions.ChessException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Origin: ");
                ChessPosition origin = UI.readChessPosition(scanner);

                boolean[][] possibleMovements = chessMatch.possibleMovements(origin);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMovements);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(origin, target);

            } catch (ChessException exception) {
                System.out.println(exception.getMessage());
                scanner.nextLine();

            } catch (InputMismatchException exception) {
                System.out.println(exception.getMessage());
                scanner.nextLine();
            }

        }


//        scanner.close();
    }
}