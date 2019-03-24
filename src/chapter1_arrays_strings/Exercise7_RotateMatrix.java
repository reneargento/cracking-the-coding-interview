package chapter1_arrays_strings;

/**
 * Created by Rene Argento on 17/03/19.
 */
public class Exercise7_RotateMatrix {

    // O(n x n) runtime
    // O(1) space
    public static void rotateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length != matrix[0].length) {
            return;
        }

        for (int layer = 0; layer < matrix.length / 2; layer++) {
            int start = layer;
            int end = matrix.length - 1 - layer;

            for (int i = start; i < end; i++) {
                int offset = i - start;

                int topCopy = matrix[start][i];

                // Top = left
                matrix[start][i] = matrix[end - offset][start];

                // Left = bottom
                matrix[end - offset][start] = matrix[end][end - offset];

                // Bottom = right
                matrix[end][end - offset] = matrix[start + offset][end];

                // Right = top
                matrix[start + offset][end] = topCopy;
            }
        }
    }

    public static void main(String[] args) {
        // 3x3 matrix
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        rotateMatrix(matrix1);
        System.out.println("3x3 matrix");
        printMatrix(matrix1);

        System.out.println("\nExpected:");
        System.out.println("7 4 1");
        System.out.println("8 5 2");
        System.out.println("9 6 3");

        // 4x4 matrix
        int[][] matrix2 = {
                {1,   2,  3,  4},
                {5,   6,  7,  8},
                {9,  10, 11, 12},
                {13, 14, 15, 16}
        };
        rotateMatrix(matrix2);
        System.out.println("\n4x4 matrix");
        printMatrix(matrix2);

        System.out.println("\nExpected:");
        System.out.println("13 9 5 1");
        System.out.println("14 10 6 2");
        System.out.println("15 11 7 3");
        System.out.println("16 12 8 4");
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
