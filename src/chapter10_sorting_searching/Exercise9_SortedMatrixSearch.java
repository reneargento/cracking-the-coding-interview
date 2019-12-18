package chapter10_sorting_searching;

/**
 * Created by Rene Argento on 12/12/19.
 */
@SuppressWarnings("unchecked")
public class Exercise9_SortedMatrixSearch {

    public static class Coordinate implements Cloneable {
        public int row, column;

        public Coordinate(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public boolean inbounds(Comparable[][] matrix) {
            return row >= 0 && row < matrix.length
                    && column >= 0 && column < matrix[0].length;
        }

        public boolean isBefore(Coordinate otherCoordinate) {
            return row <= otherCoordinate.row && column <= otherCoordinate.column;
        }

        public void setToAverage(Coordinate coordinateA, Coordinate coordinateB) {
            row = (coordinateA.row + coordinateB.row) / 2;
            column = (coordinateA.column + coordinateB.column) / 2;
        }

        public Object clone() {
            try {
                super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return new Coordinate(row, column);
        }
    }

    // O(lg(m * n) * lg(d)) runtime, where m is the number of rows in the matrix, n is the number of columns in the matrix
    // and d is the length of the diagonal in the matrix
    // O(lg(m * n)) space
    public static Coordinate search(Comparable[][] matrix, Comparable value) {
        Coordinate origin = new Coordinate(0, 0);
        Coordinate destination = new Coordinate(matrix.length - 1, matrix[0].length - 1);
        return search(matrix, value, origin, destination);
    }

    public static Coordinate search(Comparable[][] matrix, Comparable value, Coordinate origin, Coordinate destination) {
        if (!origin.inbounds(matrix) || !destination.inbounds(matrix)) {
            return null;
        }

        if (matrix[origin.row][origin.column].equals(value)) {
            return origin;
        } else if (!origin.isBefore(destination)) {
            return null;
        }

        // Set start to start of diagonal and end to the end of the diagonal. Since the grid may not be square, the
        // end of the diagonal may not equal destination.
        int diagonalDistance = Math.min(destination.row - origin.row, destination.column - origin.column);

        Coordinate start = (Coordinate) origin.clone();
        Coordinate end = new Coordinate(start.row + diagonalDistance, start.column + diagonalDistance);
        Coordinate pivot = new Coordinate(0, 0);

        // Do a binary search on the diagonal, looking for the first element that is higher than value
        while (start.isBefore(end)) {
            pivot.setToAverage(start, end);

            int compare = matrix[pivot.row][pivot.column].compareTo(value);
            if (compare < 0) {
                start.row = pivot.row + 1;
                start.column = pivot.column + 1;
            } else {
                end.row = pivot.row - 1;
                end.column = pivot.column - 1;
            }
        }

        return partitionAndSearch(matrix, value, origin, destination, start);
    }

    // Partition the matrix into 4 quadrants and recursively search on the bottom left and top right
    public static Coordinate partitionAndSearch(Comparable[][] matrix, Comparable value, Coordinate origin,
                                                Coordinate destination, Coordinate pivot) {
        Coordinate bottomLeftOrigin = new Coordinate(pivot.row, origin.column);
        Coordinate bottomLeftDestination = new Coordinate(destination.row, pivot.column - 1);
        Coordinate topRightOrigin = new Coordinate(origin.row, pivot.column);
        Coordinate topRightDestination = new Coordinate(pivot.row - 1, destination.column);

        Coordinate bottomLeftLocation = search(matrix, value, bottomLeftOrigin, bottomLeftDestination);
        if (bottomLeftLocation != null) {
            return bottomLeftLocation;
        }
        return search(matrix, value, topRightOrigin, topRightDestination);
    }

    public static void main(String[] args) {
        Integer[][] matrix = {
                {1, 5, 10, 15, 20},
                {3, 8, 12, 16, 30},
                {6, 10, 17, 19, 31}
        };
        System.out.println("Search value 19");
        Coordinate coordinate1 = search(matrix, 19);
        printResults(coordinate1);
        System.out.println("Expected: Row: 2 Column: 3\n");

        System.out.println("Search value 10");
        Coordinate coordinate2 = search(matrix, 10);
        printResults(coordinate2);
        System.out.println("Expected: Row: 2 Column: 1\n");

        System.out.println("Search value 32");
        Coordinate coordinate3 = search(matrix, 32);
        printResults(coordinate3);
        System.out.println("Expected: Not found");
    }

    private static void printResults(Coordinate coordinate) {
        if (coordinate == null) {
            System.out.println("Not found");
        } else {
            System.out.println("Row: " + coordinate.row + " Column: " + coordinate.column);
        }
    }

}
