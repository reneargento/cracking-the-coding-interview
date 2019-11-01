package chapter8_recursion_dynamic_programming;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Rene Argento on 13/09/19.
 */
public class Exercise2_RobotInAGrid {

    // O(r * c) runtime, where r is the number of rows and c is the number of columns
    // O(r * c) space
    private static List<Point> getPath(boolean[][] grid) {
        if (grid == null || grid.length == 0) {
            return null;
        }

        List<Point> path = new ArrayList<>();
        Set<Point> visited = new HashSet<>();
        int targetRow = grid.length - 1;
        int targetColumn = grid[0].length - 1;

        if (getPath(grid, path, targetRow, targetColumn, visited)) {
            return path;
        }
        return null;
    }

    private static boolean getPath(boolean[][] grid, List<Point> path, int row, int column, Set<Point> visited) {
        Point cell = new Point(row, column);

        if (!isValidCell(grid, row, column) || visited.contains(cell)) {
            return false;
        }

        visited.add(cell);

        boolean isAtOrigin = row == 0 && column == 0;

        if (isAtOrigin
                || getPath(grid, path, row - 1, column, visited)
                || getPath(grid, path, row, column - 1, visited)) {
            path.add(cell);
            return true;
        }

        return false;
    }

    private static boolean isValidCell(boolean[][] grid, int row, int column) {
        return row >= 0 && row < grid.length
                && column >= 0 && column < grid[0].length
                && grid[row][column];
    }

    public static void main(String[] args) {
        boolean[][] grid1 = {
                {true,  true,  false, false},
                {false, true,  false, false},
                {false, true,  true,  false},
                {false, false, true,  true},
        };
        List<Point> path1 = getPath(grid1);
        System.out.print("Path 1: ");
        printPath(path1);
        System.out.println("Expected: (0, 0) - (0, 1) - (1, 1) - (2, 1) - (2, 2) - (3, 2) - (3, 3)");

        boolean[][] grid2 = {
                {true,  true, false, true},
                {false, true, false, true},
                {false, true, false, true},
                {false, true, true,  true},
        };
        List<Point> path2 = getPath(grid2);
        System.out.print("\nPath 2: ");
        printPath(path2);
        System.out.println("Expected: (0, 0) - (0, 1) - (1, 1) - (2, 1) - (3, 1) - (3, 2) - (3, 3)");

        boolean[][] grid3 = {
                {true,  true,  false, false},
                {false, false, false, false},
                {false, true,  true,  false},
                {false, false, true,  true},
        };
        List<Point> path3 = getPath(grid3);
        System.out.print("\nPath 3: ");
        printPath(path3);
        System.out.println("Expected: There is no path");
    }

    private static void printPath(List<Point> path) {
        if (path != null) {
            StringJoiner pathString = new StringJoiner(" - ");
            for (Point point : path) {
                String pointString = "(" + point.x + ", " + point.y + ')';
                pathString.add(pointString);
            }

            System.out.println(pathString);
        } else {
            System.out.println("There is no path");
        }
    }
}
