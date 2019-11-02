package chapter8_recursion_dynamic_programming;

/**
 * Created by Rene Argento on 15/10/19.
 */
public class Exercise12_EightQueens {

    private final static int BOARD_DIMENSION = 8;

    // O(r * c) runtime, where r is the number of rows in the board and c is the number of columns in the board
    // O(c) space
    public static void placeQueens() {
        int[] columns = new int[BOARD_DIMENSION];
        placeQueens(columns, 0);
    }

    private static void placeQueens(int[] columns, int row) {
        if (row == columns.length) {
            printBoard(columns);
            return;
        }

        for (int column = 0; column < columns.length; column++) {
            if (isValidCell(columns, row, column)) {
                columns[row] = column;
                placeQueens(columns, row + 1);
            }
        }
    }

    private static boolean isValidCell(int[] columns, int row, int column) {
        for (int row2 = 0; row2 < row; row2++) {
            // Check columns
            int column2 = columns[row2];
            if (column == column2) {
                return false;
            }

            // Check diagonals: if the distance between the columns equals the distance between the rows, then they
            // are in the same diagonal.
            int columnDistance = Math.abs(column - column2);
            int rowDistance = row - row2;

            if (columnDistance == rowDistance) {
                return false;
            }
        }
        return true;
    }

    private static void printBoard(int[] columns) {
        System.out.println("BOARD");

        for (int row = 0; row < BOARD_DIMENSION; row++) {
            for (int column = 0; column < BOARD_DIMENSION; column++) {
                if (columns[row] == column) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        placeQueens();
    }

}

