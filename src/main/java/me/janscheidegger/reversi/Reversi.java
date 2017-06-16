package me.janscheidegger.reversi;

import me.janscheidegger.reversi.ai.AiPlayer;

import java.util.List;
import java.util.Scanner;

public class Reversi {

    public static void main(String[] args) {
        new Reversi();
    }

    Scanner scanner = new Scanner(System.in);
    GameField.State currentColor = GameField.State.BLACK;
    AiPlayer ai = new AiPlayer();


    public Reversi() {
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


