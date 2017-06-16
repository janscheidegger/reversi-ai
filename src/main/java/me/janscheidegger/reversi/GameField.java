package me.janscheidegger.reversi;

public class GameField {

    public enum State {
        BLACK("B"), WHITE("W"), EMPTY("");

        private String value;

        State(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    private State state;

    public GameField() {
        this.state = State.EMPTY;
    }

    public GameField(GameField field) {
        state = field.state;
    }

    public GameField(State state) {
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
