package chapter16_moderate;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 09/04/20.
 */
public class Exercise19_PondSizes {

    // O(r * c) runtime, where r is the number of rows in the matrix and c is the number of columns in the matrix
    // O(r * c) space
    public static List<Integer> getPondSizes(int[][] land) {
        List<Integer> pondSizes = new ArrayList<>();
        if (land == null || land.length == 0) {
            return pondSizes;
        }
        boolean[][] visited = new boolean[land.length][land[0].length];

        for (int row = 0; row < land.length; row++) {
            for (int column = 0; column < land[row].length; column++) {
                if (land[row][column] == 0 && !visited[row][column]) {
                    int pondSize = computeSize(land, visited, row, column);
                    pondSizes.add(pondSize);
                }
            }
        }
        return pondSizes;
    }

    private static int computeSize(int[][] land, boolean[][] visited, int row, int column) {
        int pondSize = 1;
        visited[row][column] = true;

        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaColumn = -1; deltaColumn <= 1; deltaColumn++) {
                int neighborRow = row + deltaRow;
                int neighborColumn = column + deltaColumn;

                if (isValid(land, neighborRow, neighborColumn)
                        && land[neighborRow][neighborColumn] == 0
                        && !visited[neighborRow][neighborColumn]) {
                    pondSize += computeSize(land, visited, neighborRow, neighborColumn);
                }
            }
        }
        return pondSize;
    }

    private static boolean isValid(int[][] matrix, int row, int column) {
        return row >= 0 && row < matrix.length && column >= 0 && column < matrix[row].length;
    }

    public static void main(String[] args) {
        int[][] matrix1 = {
                {0, 2, 1, 0},
                {0, 1, 0, 1},
                {1, 1, 0, 1},
                {0, 1, 0, 1}
        };
        List<Integer> pondSizes1 = getPondSizes(matrix1);
        System.out.println("Pond sizes: " + getPondSizesDescription(pondSizes1) + " Expected: 2,4,1");

        int[][] matrix2 = {
                {0, 2, 1, 0},
                {1, 0, 3, 1},
                {1, 1, 0, 1},
                {0, 1, 1, 0}
        };
        List<Integer> pondSizes2 = getPondSizes(matrix2);
        System.out.println("Pond sizes: " + getPondSizesDescription(pondSizes2) + " Expected: 4,1,1");
    }

    private static String getPondSizesDescription(List<Integer> pondSizes) {
        StringJoiner pondSizesDescription = new StringJoiner(",");
        for (int pondSize : pondSizes) {
            pondSizesDescription.add(String.valueOf(pondSize));
        }
        return pondSizesDescription.toString();
    }
}
