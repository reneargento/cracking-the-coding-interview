package chapter8_recursion_dynamic_programming;

/**
 * Created by Rene Argento on 29/09/19.
 */
public class Exercise10_PaintFill {

    private enum Color {
        RED, GREEN, BLUE
    }

    private static int rowOffset[] = {-1, 0, 0, 1};
    private static int columnOffset[] = {0, -1, 1, 0};

    public static void paintFill(Color[][] screen, int row, int column, Color newColor) {
        if (screen == null || screen.length == 0) {
            return;
        }
        paintFill(screen, row, column, screen[row][column], newColor);
    }

    private static void paintFill(Color[][] screen, int row, int column, Color oldColor, Color newColor) {
        screen[row][column] = newColor;

        for (int i = 0; i < 4; i++) {
            int neighborRow = row + rowOffset[i];
            int neighborColumn = column + columnOffset[i];

            if (isValid(neighborRow, neighborColumn, screen)
                    && screen[neighborRow][neighborColumn] == oldColor) {
                paintFill(screen, neighborRow, neighborColumn, oldColor, newColor);
            }
        }
    }

    private static boolean isValid(int row, int column, Color[][] screen) {
        return row >= 0 && row < screen.length && column >= 0 && column < screen[0].length;
    }

    public static void main(String[] args) {
        Color[][] screen = {
                {Color.GREEN, Color.RED, Color.RED, Color.GREEN},
                {Color.GREEN, Color.GREEN, Color.GREEN, Color.RED},
                {Color.RED, Color.GREEN, Color.GREEN, Color.RED},
                {Color.RED, Color.GREEN, Color.GREEN, Color.RED},
                {Color.RED, Color.RED, Color.RED, Color.RED}
        };

        paintFill(screen, 3, 1, Color.BLUE);

        System.out.println("Screen");
        printScreen(screen);

        System.out.println("\nExpected");
        System.out.println("BLUE RED RED GREEN" +
                "\nBLUE BLUE BLUE RED" +
                "\nRED BLUE BLUE RED" +
                "\nRED BLUE BLUE RED" +
                "\nRED RED RED RED");
    }

    private static void printScreen(Color[][] screen) {
        for (int row = 0; row < screen.length; row++) {
            for (int column = 0; column < screen[0].length; column++) {
                System.out.print(screen[row][column] + " ");
            }
            System.out.println();
        }
    }

}
