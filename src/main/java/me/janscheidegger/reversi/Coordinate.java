package me.janscheidegger.reversi;

import java.util.ArrayList;
import java.util.List;

public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Coordinate> getSurroundingPositions() {
        List<Coordinate> surroundingPositions = new ArrayList<>();
        if (x > 0) {
            surroundingPositions.add(new Coordinate(x-1, y));
            if(y > 0) surroundingPositions.add(new Coordinate(x-1, y-1));
            if(y < 7) surroundingPositions.add(new Coordinate(x-1,y+1));
        }
        if (x < 7) {
            surroundingPositions.add(new Coordinate(x+1, y));
            if(y > 0) surroundingPositions.add(new Coordinate(x+1, y-1));
            if(y < 7) surroundingPositions.add(new Coordinate(x+1,y+1));
        }

        if(y > 0) surroundingPositions.add(new Coordinate(x, y-1));
        if(y < 7) surroundingPositions.add(new Coordinate(x,y+1));

        return surroundingPositions;

    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
