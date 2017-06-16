package me.janscheidegger.reversi.ai;

import me.janscheidegger.reversi.Move;

/**
 * Created by U115281 on 16.06.2017.
 */
public class MoveScore {


    Move move;
    int score;

    public MoveScore(Move move, int score) {
        this.move = move;
        this.score = score;
    }

    public MoveScore neg() {
        return new MoveScore(move, -score);
    }
}
