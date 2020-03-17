package chapter16_moderate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rene Argento on 13/03/20.
 */
public class Exercise4_TicTacWin {
    enum Piece { Empty, Red, Blue }

    private static class Position {
        public int row, column;

        Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class PositionIterator implements Iterator<Position> {
        private int rowIncrement, columnIncrement, size;
        private Position current;

        PositionIterator(Position position, int rowIncrement, int columnIncrement, int size) {
            this.rowIncrement = rowIncrement;
            this.columnIncrement = columnIncrement;
            this.size = size;
            current = new Position(position.row - rowIncrement, position.column - columnIncrement);
        }

        @Override
        public boolean hasNext() {
            return current.row + rowIncrement < size && current.column + columnIncrement < size;
        }

        @Override
        public Position next() {
            current = new Position(current.row + rowIncrement, current.column + columnIncrement);
            return current;
        }
    }

    // O(n) runtime, where n is the number of rows (or columns) of the board
    // O(n) space
    public static Piece hasWon(Piece[][] board) {
        if (board == null || board.length == 0 || board.length != board[0].length) {
            return Piece.Empty;
        }
        int size = board.length;

        List<PositionIterator> iterators = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            iterators.add(new PositionIterator(new Position(0, i), 1, 0, size));
            iterators.add(new PositionIterator(new Position(i, 0), 0, 1, size));
        }
        // Diagonals
        iterators.add(new PositionIterator(new Position(0, 0), 1, 1, size));
        iterators.add(new PositionIterator(new Position(0, size - 1), 1, -1, size));

        for (PositionIterator iterator : iterators) {
            Piece winner = hasWon(board, iterator);
            if (winner != Piece.Empty) {
                return winner;
            }
        }
        return Piece.Empty;
    }

    private static Piece hasWon(Piece[][] board, PositionIterator iterator) {
        Position firstPosition = iterator.next();
        Piece firstPiece = board[firstPosition.row][firstPosition.column];

        while (iterator.hasNext()) {
            Position position = iterator.next();
            if (board[position.row][position.column] != firstPiece) {
                return Piece.Empty;
            }
        }
        return firstPiece;
    }

    public static void main(String[] args) {
        Piece[][] board1 = {
                {Piece.Red, Piece.Blue, Piece.Blue},
                {Piece.Empty, Piece.Red, Piece.Blue},
                {Piece.Blue, Piece.Red, Piece.Red}
        };
        Piece winner1 = hasWon(board1);
        System.out.println("Winner 1: " + winner1 + " Expected: Red");

        Piece[][] board2 = {
                {Piece.Red, Piece.Blue, Piece.Empty, Piece.Red, Piece.Blue},
                {Piece.Red, Piece.Red, Piece.Blue, Piece.Blue, Piece.Blue},
                {Piece.Blue, Piece.Red, Piece.Red, Piece.Red, Piece.Blue},
                {Piece.Red, Piece.Red, Piece.Red, Piece.Blue, Piece.Blue},
                {Piece.Blue, Piece.Red, Piece.Blue, Piece.Red, Piece.Blue}
        };
        Piece winner2 = hasWon(board2);
        System.out.println("Winner 2: " + winner2 + " Expected: Blue");

        Piece[][] board3 = {
                {Piece.Red, Piece.Blue, Piece.Empty},
                {Piece.Red, Piece.Blue, Piece.Blue},
                {Piece.Blue, Piece.Red, Piece.Empty}
        };
        Piece winner3 = hasWon(board3);
        System.out.println("Winner 3: " + winner3 + " Expected: Empty");
    }

}
