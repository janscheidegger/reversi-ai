package me.janscheidegger.reversi;

import java.util.List;
import java.util.Scanner;

public class Reversi {

    public static void main(String[] args) {
        new Reversi();
    }

    Scanner scanner = new Scanner(System.in);
    GameField.State currentColor = GameField.State.BLACK;


    public Reversi() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.print();

        while (!hasWinner()) {
            List<Move> validMoves = gameBoard.getValidMoves(currentColor);
            for (int i = 0; i < validMoves.size();i++) {
                System.out.println(i +") "+validMoves.get(i));
            }
            int moveNumber = readline(validMoves.size());
            Move move = validMoves.get(moveNumber);
            gameBoard.executeMove(move, currentColor);
            changeCurrentPlayer();
            gameBoard.print();

        }
    }

    private boolean hasWinner() {
        return false;
    }

    private int readline(int max) {
        for (; ; ) {
            String line = scanner.nextLine();
            try {
                int i = Integer.parseInt(line);
                if (i > max) {
                    System.err.println("Invalid Number [" + line+"]");
                }
                return i;
            } catch (NumberFormatException e) {
                System.err.println("Invalid Number"+ line+"]");
            }


        }
    }

    private void changeCurrentPlayer() {
        currentColor = currentColor == GameField.State.BLACK ?
                GameField.State.WHITE :
                GameField.State.BLACK;
    }
}


