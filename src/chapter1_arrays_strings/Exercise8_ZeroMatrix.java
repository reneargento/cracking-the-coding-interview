package chapter1_arrays_strings;

/**
 * Created by Rene Argento on 17/03/19.
 */
public class Exercise8_ZeroMatrix {

    // O(m * n) runtime, where m is the number of rows in the matrix and n is the number of columns in the matrix
    // O(1) space
    public static void zeroMatrix(int[][] matrix) {
        boolean firstRowHasZero = false;
        boolean firstColumnHasZero = false;

        // 1- Check first row and first column
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                firstRowHasZero = true;
                break;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                firstColumnHasZero = true;
                break;
            }
        }

        // 2 - Check other cells
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 3- Nullify rows and columns other than first row and first column
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                nullifyRow(matrix, i);
            }
        }

        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                nullifyColumn(matrix, j);
            }
        }

        // 4- Nullify first row and first column
        if (firstRowHasZero) {
            nullifyRow(matrix, 0);
        }

        if (firstColumnHasZero) {
            nullifyColumn(matrix, 0);
        }
    }

    private static void nullifyRow(int[][] matrix, int row) {
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[row][j] = 0;
        }
    }

    private static void nullifyColumn(int[][] matrix, int column) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][column] = 0;
        }
    }

    public static void main(String[] args) {
        int[][] matrix1 = {
                {1, 2, 3, 0},
                {2, 4, 5, 6},
                {0, 5, 6, 7}
        };
        zeroMatrix(matrix1);
        System.out.println("Matrix 1");
        printMatrix(matrix1);

        System.out.println("\nExpected:");
        System.out.println("0 0 0 0");
        System.out.println("0 4 5 0");
        System.out.println("0 0 0 0");

        int[][] matrix2 = {
                {1, 2, 3, 4},
                {5, 0, 7, 8},
                {9, 10, 11, 12}
        };
        zeroMatrix(matrix2);
        System.out.println("\nMatrix 2");
        printMatrix(matrix2);

        System.out.println("\nExpected:");
        System.out.println("1 0 3 4");
        System.out.println("0 0 0 0");
        System.out.println("9 0 11 12");
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

}
