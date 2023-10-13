package org.chess;

import org.chess.boardgame.Board;
import org.chess.chess.ChessMatch;
import org.chess.chess.ChessPiece;
import org.chess.chess.ChessPosition;
import org.chess.chess.exceptions.ChessException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
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
                if(capturedPiece != null) {
                    captured.add(capturedPiece);
                }
                if(chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B/N/R/Q)");
                    String type = scanner.nextLine().toUpperCase();
                    while(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q)");
                        type = scanner.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);
                }

            } catch (ChessException exception) {
                System.out.println(exception.getMessage());
                scanner.nextLine();

            } catch (InputMismatchException exception) {
                System.out.println(exception.getMessage());
                scanner.nextLine();
            }

        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);

        scanner.close();
    }
}