package chapter10_sorting_searching;

import java.util.StringJoiner;

/**
 * Created by Rene Argento on 17/12/19.
 */
public class Exercise11_PeaksAndValleys {

    // O(n) runtime, where n is the number of elements in the array
    // O(1) space
    public static void sortPeaksValleys(int[] array) {
        boolean isPeak = true;

        for (int i = 0; i < array.length - 1; i++) {
            int currentElement = array[i];
            int nextElement = array[i + 1];

            if ((isPeak && currentElement < nextElement)
                    || (!isPeak && currentElement > nextElement)) {
                array[i] = nextElement;
                array[i + 1] = currentElement;
            }
            isPeak = !isPeak;
        }
    }

    public static void main(String[] args) {
        int[] array1 = {5, 3, 1, 2, 3};
        sortPeaksValleys(array1);
        printArray(array1);
        System.out.println("Expected: 5 1 3 2 3\n");

        int[] array2 = {5, 8, 6, 2, 3, 4, 6};
        sortPeaksValleys(array2);
        printArray(array2);
        System.out.println("Expected: 8 5 6 2 4 3 6");
    }

    private static void printArray(int[] array) {
        System.out.print("Array: ");
        StringJoiner values = new StringJoiner(" ");
        for (int value : array) {
            values.add(String.valueOf(value));
        }
        System.out.println(values);
    }

}
