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

    public void print(List<Move> validMoves) {
        for (int i = 0; i < gameFields.length; i++) {
            for (int j = 0; j < gameFields[i].length; j++) {
                int count = 0;
                for (Move move : validMoves) {
                    if (move.coordinate.x == i && move.coordinate.y == j) {
                        count++;
                    }
                }
                if (count > 0) {
                    System.out.print("O");
                } else {
                    System.out.print(gameFields[i][j].getState());
                }
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
                    getMoves(currentColor, otherColor, i, j).ifPresent(validMoves::add);
                }
            }
        }
        return validMoves;
    }

    private Optional<Move> getMoves(GameField.State currentColor, GameField.State otherColor, int x, int y) {
        Move move = new Move(x, y);
        for (int xDirection = -1; xDirection <= 1; xDirection++) {
            for (int yDirection = -1; yDirection <= 1; yDirection++) {
                List<Coordinate> stoneList = new ArrayList<>();
                int counter = 1;
                int xPos = x + (counter * xDirection);
                int yPos = y + (counter * yDirection);

                if (checkBounds(xPos) || checkBounds(yPos)) {
                    continue;
                }
                System.out.println("[" + x + ", " + y + "] x: " + xPos + ", y: " + yPos);
                while (gameFields[xPos][yPos].getState() == otherColor) {
                    System.out.println("added move");
                    stoneList.add(new Coordinate(xPos, yPos));
                    counter++;

                    if(checkBounds(x + (counter * xDirection))) break;
                    if(checkBounds(y + (counter * yDirection))) break;

                    xPos = x + (counter * xDirection);
                    yPos = y + (counter * yDirection);
                }
                if (gameFields[xPos][yPos].getState() == currentColor) {
                    move.addStonesToRemoveList(stoneList);
                    System.out.println("match");
                }
            }
        }
        if (move.hasStonesToRemove()) {
            return Optional.of(move);
        } else {
            return Optional.empty();
        }
    }

    private boolean checkBounds(int pos) {
        return pos < 0 || pos > 7;
    }

    public GameField.State getOtherColor(GameField.State currentColor) {
        return currentColor == GameField.State.BLACK ?
                GameField.State.WHITE :
                GameField.State.BLACK;
    }

    public void executeMove(Move move, GameField.State currentColor) {
        gameFields[move.coordinate.x][move.coordinate.y].setState(currentColor);
        for (Coordinate coordinate : move.getRemovedStones()) {
            gameFields[coordinate.x][coordinate.y].setState(currentColor);
        }
    }

    public int countStones(GameField.State state) {
        int count = 0;
        for (GameField[] gameField : gameFields) {
            for (GameField aGameField : gameField) {
                if (aGameField.getState().equals(state)) {
                    count++;
                }
            }
        }
        return count;
    }
}
