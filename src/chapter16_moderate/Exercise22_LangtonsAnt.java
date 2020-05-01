package chapter16_moderate;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 13/04/20.
 */
public class Exercise22_LangtonsAnt {

    private static class Position {
        private int row;
        private int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public Position copy() {
            return new Position(row, column);
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Position) {
                Position otherPosition = (Position) object;
                return otherPosition.row == row && otherPosition.column == column;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (row * 31) ^ column;
        }
    }

    private enum Orientation {
        LEFT, UP, RIGHT, DOWN;

        public Orientation getTurn(boolean clockwise) {
            switch (this) {
                case UP: return clockwise ? RIGHT : LEFT;
                case RIGHT: return clockwise ? DOWN : UP;
                case DOWN: return clockwise ? LEFT : RIGHT;
                default: return clockwise ? UP : DOWN;
            }
        }

        @Override
        public String toString() {
            switch (this) {
                case LEFT: return "\u2190";
                case UP: return "\u2191";
                case RIGHT: return "\u2192";
                default: return "\u2193";
            }
        }
    }

    private static class Ant {
        private Position position;
        private Orientation orientation;

        public Ant() {
            position = new Position(0, 0);
            orientation = Orientation.RIGHT;
        }

        public void turn(boolean clockwise) {
            orientation = orientation.getTurn(clockwise);
        }

        public void move() {
            switch (orientation) {
                case UP: position.row--; break;
                case RIGHT: position.column++; break;
                case DOWN: position.row++; break;
                case LEFT: position.column--; break;
            }
        }
    }

    // Initially all squares are white
    public static class Board {
        private Ant ant;
        private Set<Position> blackSquares;
        private Position topLeftCorner;
        private Position bottomRightCorner;

        private static final int MAX_DIMENSION = 1000;

        // O(d^2) runtime, where d is the max dimension of the grid
        // O(d^2) space
        public Board() {
            ant = new Ant();
            blackSquares = new HashSet<>();
            topLeftCorner = new Position(0, 0);
            bottomRightCorner = new Position(0, 0);
        }

        public void printKMoves(int k) {
            for (int move = 0; move < k; move++) {
                moveAnt();
            }
            System.out.println(this);
        }

        private void moveAnt() {
            ant.turn(!isBlack(ant.position)); // turn clockwise on white, counter-clockwise on black
            flip(ant.position);
            ant.move();
            ensureFit(ant.position);
        }

        private void flip(Position position) {
            if (blackSquares.contains(position)) {
                blackSquares.remove(position);
            } else {
                blackSquares.add(position.copy());
            }
        }

        // Grow grid by tracking the most top-left and bottom-right positions
        private void ensureFit(Position position) {
            int row = position.row;
            int column = position.column;

            topLeftCorner.row = Math.min(topLeftCorner.row, row);
            topLeftCorner.column = Math.min(topLeftCorner.column, column);

            bottomRightCorner.row = Math.max(bottomRightCorner.row, row);
            bottomRightCorner.column = Math.max(bottomRightCorner.column, column);
        }

        private boolean isBlack(Position position) {
            return blackSquares.contains(position);
        }

        private boolean isBlack(int row, int column) {
            return isBlack(new Position(row, column));
        }

        // Print board
        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            int minRow = topLeftCorner.row;
            int maxRow = bottomRightCorner.row;
            int minColumn = topLeftCorner.column;
            int maxColumn = bottomRightCorner.column;

            for (int row = minRow; row <= maxRow; row++) {
                for (int column = minColumn; column <= maxColumn; column++) {
                    if (row == ant.position.row && column == ant.position.column) {
                        stringBuilder.append(ant.orientation);
                    } else if (isBlack(row, column)) {
                        stringBuilder.append("X");
                    } else {
                        stringBuilder.append("_");
                    }
                }
                stringBuilder.append("\n");
            }
            stringBuilder.append("Ant: ").append(ant.orientation);
            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.printKMoves(50);
    }
}
