package me.janscheidegger.reversi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameBoard {

    private GameField[][] gameFields = new GameField[8][8];

    public GameBoard() {
        for (int i = 0; i < gameFields.length; i++) {
            for (int j = 0; j < gameFields[i].length; j++) {
                gameFields[i][j] = new GameField();
            }
        }
        gameFields[3][3].setState(GameField.State.WHITE);
        gameFields[4][4].setState(GameField.State.WHITE);
        gameFields[4][3].setState(GameField.State.BLACK);
        gameFields[3][4].setState(GameField.State.BLACK);
    }

    public void putStone(Move move, GameField.State currentColor) {
        gameFields[move.coordinate.x][move.coordinate.y].setState(currentColor);

    }


    public void print() {
        for (GameField[] gameLine : gameFields) {
            for (GameField aGameField : gameLine) {
                System.out.print(aGameField.getState());
            }
            System.out.println();
        }
    }

    public List<Move> getValidMoves(GameField.State currentColor) {
        GameField.State otherColor = getOtherColor(currentColor);
        List<Move> validMoves = new ArrayList<>();
        for (int i = 0; i < gameFields.length; i++) {
            for (int j = 0; j < gameFields[i].length; j++) {
                if (gameFields[i][j].getState() == GameField.State.EMPTY) {
                    getMove(currentColor, otherColor, validMoves, i, j).ifPresent(validMoves::add);
                }
            }
        }
        return validMoves;
    }

    private Optional<Move> getMove(GameField.State currentColor, GameField.State otherColor, List<Move> validMoves, int x, int y) {
        Move move = new Move(x, y);
        for(int xDirection = -1; xDirection <= 1; xDirection++) {
            for (int yDirection = -1; yDirection <= 1; yDirection++) {
                int counter = 1;
                if((x + (counter * xDirection) < 0 || x + (counter * xDirection) > 7) || (y + (counter * yDirection) < 0 || y + (counter * yDirection) > 7)) continue;
                if (gameFields[x + (counter * xDirection)][y + (counter * yDirection)].getState() == otherColor) {
                    move.addStoneToRemoveList(new Coordinate(x + (counter * xDirection), y + (counter * yDirection)));
                } else if (gameFields[x + (counter * xDirection)][y + (counter * yDirection)].getState() == currentColor && move.hasStonesToRemove()) {
                    return Optional.of(move);
                }
            }
        }
        return Optional.empty();
    }

    public GameField.State getOtherColor(GameField.State currentColor) {
        return currentColor == GameField.State.BLACK ?
                GameField.State.WHITE :
                GameField.State.BLACK;
    }

    public void executeMove(Move move, GameField.State currentColor) {
        gameFields[move.coordinate.x][move.coordinate.y].setState(currentColor);
        for(Coordinate coordinate : move.getRemovedStones()) {
            gameFields[coordinate.x][coordinate.y].setState(currentColor);
        }
    }
}
