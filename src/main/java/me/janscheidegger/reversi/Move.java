package me.janscheidegger.reversi;

import java.util.ArrayList;
import java.util.List;

public class Move {

    Coordinate coordinate;
    List<Coordinate> removedStones = new ArrayList<>();

    public Move(int i, int j) {
        coordinate = new Coordinate(i, j);
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

    public void addStonesToRemoveList(List<Coordinate> stoneList) {
        this.removedStones.addAll(stoneList);
    }
}
