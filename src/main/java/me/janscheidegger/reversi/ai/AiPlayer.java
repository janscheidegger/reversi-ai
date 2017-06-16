package me.janscheidegger.reversi.ai;

import me.janscheidegger.reversi.GameBoard;
import me.janscheidegger.reversi.GameField;
import me.janscheidegger.reversi.Move;

import java.util.List;

/**
 * Created by U115281 on 16.06.2017.
 */
public class AiPlayer {
    private static final int MAX_DEPTH = 5;

    // Corners are very smart, but its not Smart to open a way to the corners for your opponent
    //

    private static int[][] BOARD_VALUES = {
            {100, -1, 5, 2, 2, 5, -1, 100},
            {-1, -10,1, 1, 1, 1,-10, -1},
            {5 , 1,  1, 1, 1, 1,  1,  5},
            {2 , 1,  1, 0, 0, 1,  1,  2},
            {2 , 1,  1, 0, 0, 1,  1,  2},
            {5 , 1,  1, 1, 1, 1,  1,  5},
            {-1,-10, 1, 1, 1, 1,-10, -1},
            {100, -1, 5, 2, 2, 5, -1, 100}};

    public Move getNextMove(GameBoard gameBoard, GameField.State currentPlayer) {
        return negaMax(gameBoard, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer).move;
    }

    public MoveScore negaMax(GameBoard gameBoard, int depth, int alpha, int beta, GameField.State currentPlayer) {

        GameField.State opponent = currentPlayer.equals(GameField.State.BLACK) ? GameField.State.WHITE : GameField.State.BLACK;

        if (depth >= MAX_DEPTH) {
            return new MoveScore(null, evaluateSituation(gameBoard, currentPlayer, opponent));
        }
        MoveScore best = new MoveScore(null, alpha);
        final List<Move> validMoves = gameBoard.getValidMoves(currentPlayer);

        if (validMoves.isEmpty()) {
             best = negaMax(gameBoard, depth+1, -beta, -alpha, opponent).neg();
        } else {
            for (Move move : validMoves) {
               GameBoard copy = gameBoard.copy();
               copy.executeMove(move, currentPlayer);
               int score = negaMax(copy,depth+1, -beta, -alpha, opponent).neg().score;
               if(alpha < score) {
                   alpha = score;
                   best = new MoveScore(move, score);
               }

               // Prune!
               if(alpha >= beta) {
                   return best;
               }
            }
        }

        return best;

    }


    private int evaluateSituation(GameBoard gameBoard, GameField.State currentPlayer, GameField.State otherPlayer) {
        int score = 0;
        final GameField[][] gameFields = gameBoard.getGameFields();
        for(int i = 0; i< gameFields.length;i++) {
            for(int j = 0; j < gameFields[i].length; j++) {
                if(gameFields[i][j].getState().equals(currentPlayer)) {
                    score += BOARD_VALUES[i][j];
                } else if(gameFields[i][j].getState().equals(otherPlayer)) {
                    score -= BOARD_VALUES[i][j];
                }
            }
        }
        return score;
    }


}
