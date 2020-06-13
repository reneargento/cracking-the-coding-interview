package chapter17_hard;

/**
 * Created by Rene Argento on 10/06/20.
 */
public class Exercise23_MaxBlackSquare {

    public static class Subsquare {
        private int top;
        private int left;
        private int right;
        private int bottom;
        private int size;

        public Subsquare(int topRow, int leftColumn, int size) {
            top = topRow;
            left = leftColumn;
            right = left + size - 1;
            bottom = top + size - 1;
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        @Override
        public String toString() {
            return "(" + top + ", " + left + ") "
                    + "(" + top + ", " + right + ") "
                    + "(" + bottom + ", " + left + ") "
                    + "(" + bottom + ", " + right + ")";
        }
    }

    private static class Cell {
        private int rightBlackCells;
        private int belowBlackCells;

        public Cell(int rightBlackCells, int belowBlackCells) {
            this.rightBlackCells = rightBlackCells;
            this.belowBlackCells = belowBlackCells;
        }

        public int getRightBlackCells() {
            return rightBlackCells;
        }

        public int getBelowBlackCells() {
            return belowBlackCells;
        }
    }

    public enum Color {
        WHITE, BLACK
    }

    // O(n^3) runtime, where n is one of the matrix dimensions
    // O(n^2) space
    public static Subsquare findMaxBlackSquare(Color[][] matrix) {
        if (matrix == null || matrix.length != matrix[0].length) {
            return null;
        }

        Cell[][] processedMatrix = processMatrix(matrix);
        return findMaxBlackSquare(processedMatrix);
    }

    // Pre-process matrix to get, for each cell, the number of black cells to the right and below
    private static Cell[][] processMatrix(Color[][] matrix) {
        int size = matrix.length;
        Cell[][] processedMatrix = new Cell[size][size];

        for (int row = size - 1; row >= 0; row--) {
            for (int column = size - 1; column >= 0; column--) {
                int rightBlackCells = 0;
                int belowBlackCells = 0;

                if (matrix[row][column] == Color.BLACK) {
                    rightBlackCells++;
                    belowBlackCells++;

                    if (column + 1 < size) {
                        Cell rightCell = processedMatrix[row][column + 1];
                        rightBlackCells += rightCell.getRightBlackCells();
                    }

                    if (row + 1 < size) {
                        Cell belowCell = processedMatrix[row + 1][column];
                        belowBlackCells += belowCell.getBelowBlackCells();
                    }
                }
                processedMatrix[row][column] = new Cell(rightBlackCells, belowBlackCells);
            }
        }
        return processedMatrix;
    }

    private static Subsquare findMaxBlackSquare(Cell[][] processedMatrix) {
        for (int size = processedMatrix.length; size >= 1; size--) {
            Subsquare subsquare = findMaxBlackSquareWithSize(processedMatrix, size);
            if (subsquare != null) {
                return subsquare;
            }
        }
        return null;
    }

    private static Subsquare findMaxBlackSquareWithSize(Cell[][] processedMatrix, int size) {
        int matrixSize = processedMatrix.length;

        // Iterate through all squares with length equals to size
        for (int row = 0; row + size <= matrixSize; row++) {
            for (int column = 0; column + size <= matrixSize; column++) {
                if (hasBordersWithAllBlackCells(processedMatrix, row, column, size)) {
                    return new Subsquare(row, column, size);
                }
            }
        }
        return null;
    }

    private static boolean hasBordersWithAllBlackCells(Cell[][] processedMatrix, int row, int column, int size) {
        Cell topLeft = processedMatrix[row][column];
        Cell topRight = processedMatrix[row][column + size - 1];
        Cell bottomLeft = processedMatrix[row + size - 1][column];

        return topLeft.rightBlackCells >= size && topLeft.belowBlackCells >= size
                && topRight.belowBlackCells >= size && bottomLeft.rightBlackCells >= size;
    }

    public static void main(String[] args) {
        Color[][] matrix1 = {
                {Color.WHITE, Color.BLACK, Color.BLACK, Color.WHITE, Color.WHITE},
                {Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE},
                {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK},
                {Color.BLACK, Color.BLACK, Color.WHITE, Color.BLACK, Color.WHITE},
                {Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE}
        };
        Subsquare subsquare1 = findMaxBlackSquare(matrix1);
        System.out.println("Max black square: " + subsquare1);
        System.out.println("Expected: (2, 1) (2, 3) (4, 1) (4, 3)");

        Color[][] matrix2 = {
                {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK},
                {Color.BLACK, Color.WHITE, Color.WHITE, Color.BLACK},
                {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK},
                {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK}
        };
        Subsquare subsquare2 = findMaxBlackSquare(matrix2);
        System.out.println("\nMax black square: " + subsquare2);
        System.out.println("Expected: (0, 0) (0, 3) (3, 0) (3, 3)");
    }

}
