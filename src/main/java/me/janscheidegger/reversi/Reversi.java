package me.janscheidegger.reversi;

import me.janscheidegger.reversi.ai.AiPlayer;

import java.util.List;
import java.util.Scanner;

/**
 * Reversi ist ein Spiel beim welchem es das Ziel ist, am Ende möglichst viele Steine zu haben.
 * Steine können immer neben einem Stein des Gegner gesetzt werden, wenn auf selber Linie ein eigener Stein liegt.
 * Die dazwischenliegenden Steine des Gegners können anschliessend gedreht werden.
 *
 * Der AI Spieler hat ein Konfigurierbares Ply anschliessend sucht er mit negaMax + AlphaBeta Pruning die optmale Strategie
 *
 * Die Bewertung basiert Feldbewertung. Bei Reversi ist es empfehlenswert nicht die Felder vor den Ecken zu spielen, deshalb
 * habe diese eine schlechtere Bewertung. Eckfelder haben dafür eine sehr hohe Bewertung, da diese nicht mehr zurückerobert
 * werden können, wenn sie mal im Besitz eines Spielers sind.
 *
 * Das Spiel funktioniert in der Konsole. Mögliche Züge sind mit Zahlen markiert auf dem Spielfeld
 * Existierende Steine sind W oder B für die Farben Weiss oder Schwarz
 *
 * Als Hilfe wird zusätzlich noch angezeigt, welche Steine alle entfernt werden.
 *
 * In der Konsole wird immer angezeigt, welcher Zug gespielt wurde.
 */

public class Reversi {

    private static final int PLY = 8;

    public static void main(String[] args) {
        new Reversi();
    }

    private Scanner scanner = new Scanner(System.in);
    private GameField.State currentColor = GameField.State.BLACK;
    private AiPlayer ai = new AiPlayer(PLY);


    private Reversi() {
        GameBoard gameBoard = new GameBoard();
        while (!hasWinner(gameBoard)) {
            List<Move> validMoves = gameBoard.getValidMoves(currentColor);
            gameBoard.print(validMoves);
            System.out.println("-1) PASS");
            for (int i = 0; i < validMoves.size(); i++) {
                System.out.println(i + ") " + validMoves.get(i));
            }
            System.out.println(currentColor.toString() + "'s turn");
            if (currentColor.equals(GameField.State.WHITE)) {
                int moveNumber = readline(validMoves.size());
                if (moveNumber != -1) {
                    Move move = validMoves.get(moveNumber);
                    gameBoard.executeMove(move, currentColor);
                }
            } else {
                final GameBoard copy = gameBoard.copy();
                final Move nextMove = ai.getNextMove(copy, currentColor);
                System.out.println("ai chose move: " + nextMove);
                gameBoard.executeMove(nextMove, currentColor);
            }
            changeCurrentPlayer();

        }

        System.out.println("Black: " + gameBoard.countStones(GameField.State.BLACK));
        System.out.println("White: " + gameBoard.countStones(GameField.State.WHITE));
    }

    private boolean hasWinner(GameBoard gameBoard) {
        int blackMoves = gameBoard.getValidMoves(GameField.State.BLACK).size();
        int whiteMoves = gameBoard.getValidMoves(GameField.State.WHITE).size();

        return whiteMoves == 0 && blackMoves == 0;

    }

    private int readline(int max) {
        for (; ; ) {
            String line = scanner.nextLine();
            try {
                int i = Integer.parseInt(line);
                if (i >= max) {
                    System.err.println("Invalid Number [" + line + "]");
                }
                return i;
            } catch (NumberFormatException e) {
                System.err.println("Invalid Number [" + line + "]");
            }


        }
    }

    private void changeCurrentPlayer() {
        currentColor = currentColor == GameField.State.BLACK ?
                GameField.State.WHITE :
                GameField.State.BLACK;
    }
}


