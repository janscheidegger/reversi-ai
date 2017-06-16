package me.janscheidegger.reversi.ai;

import me.janscheidegger.reversi.GameBoard;
import me.janscheidegger.reversi.GameField;
import me.janscheidegger.reversi.Move;

import java.util.List;

/**
 * Ply is adjustable for search depth
 */
public class AiPlayer {

    public AiPlayer(int ply) {
        this.ply = ply;
    }

    private int ply;
    // Corners are very smart, but its not Smart to open a way to the corners for your opponent

    private static int[][] BOARD_VALUES = {
            {100, -1, 5, 2, 2, 5, -1, 100},
            {-1, -10, 1, 1, 1, 1, -10, -1},
            {5, 1, 1, 1, 1, 1, 1, 5},
            {2, 1, 1, 0, 0, 1, 1, 2},
            {2, 1, 1, 0, 0, 1, 1, 2},
            {5, 1, 1, 1, 1, 1, 1, 5},
            {-1, -10, 1, 1, 1, 1, -10, -1},
            {100, -1, 5, 2, 2, 5, -1, 100}};

    public Move getNextMove(GameBoard gameBoard, GameField.State currentPlayer) {
        MoveScore moveScore = negaMax(gameBoard, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
        System.out.println(moveScore.score);
        return moveScore.move;
    }

    public MoveScore negaMax(GameBoard gameBoard, int depth, int alpha, int beta, GameField.State currentPlayer) {

        GameField.State opponent = currentPlayer.equals(GameField.State.BLACK) ? GameField.State.WHITE : GameField.State.BLACK;

        // If Ply is reached, evaluate the current Sitation
        if (depth >= ply) {
            return new MoveScore(null, evaluateSituation(gameBoard, currentPlayer, opponent));
        }
        MoveScore best = new MoveScore(null, alpha);
        final List<Move> validMoves = gameBoard.getValidMoves(currentPlayer);

        if (validMoves.isEmpty()) {
            // If there is no valid move, just continue
            best = negaMax(gameBoard, depth + 1, -beta, -alpha, opponent).neg();
        } else {
            for (Move move : validMoves) {
                GameBoard copy = gameBoard.copy();
                copy.executeMove(move, currentPlayer);
                int score = negaMax(copy, depth + 1, -beta, -alpha, opponent).neg().score;
                if (alpha < score) {
                    alpha = score;
                    best = new MoveScore(move, score);
                }

                // Prune!
                if (alpha >= beta) {
                    return best;
                }
            }
        }

        return best;

    }


    private int evaluateSituation(GameBoard gameBoard, GameField.State currentPlayer, GameField.State otherPlayer) {
        int score = 0;
        final GameField[][] gameFields = gameBoard.getGameFields();
        for (int i = 0; i < gameFields.length; i++) {
            for (int j = 0; j < gameFields[i].length; j++) {
                if (gameFields[i][j].getState().equals(currentPlayer)) {
                    score += BOARD_VALUES[i][j];
                } else if (gameFields[i][j].getState().equals(otherPlayer)) {
                    score -= BOARD_VALUES[i][j];
                }
            }
        }
        return score;
    }


}
