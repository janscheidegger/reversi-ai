package me.janscheidegger.reversi;

import java.util.ArrayList;
import java.util.List;

public class Move {

    Coordinate coordinate;
    List<Coordinate> removedStones = new ArrayList<>();

    public Move(int i, int j) {
        coordinate = new Coordinate(i, j);
    }


    public void addStoneToRemoveList(Coordinate coordinateToRemove) {
        this.removedStones.add(coordinateToRemove);
    }

    public boolean hasStonesToRemove() {
        return removedStones.size() > 0;
    }

    public List<Coordinate> getRemovedStones() {
        return removedStones;
    }

    @Override
    public String toString() {
        return "Move{" +
                "coordinate=" + coordinate +
                ", removedStones=" + removedStones +
                '}';
    }
}
