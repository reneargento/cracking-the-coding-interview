package chapter10_sorting_searching;

import java.util.StringJoiner;

/**
 * Created by Rene Argento on 03/12/19.
 */
public class Exercise1_SortedMerge {

    // O(a + b) runtime, where a is the number of elements in array A and b is the number of elements in array B
    // O(1) space
    @SuppressWarnings("unchecked")
    public static void sortedMerge(Comparable[] arrayA, Comparable[] arrayB, int arrayALength, int arrayBLength) {
        int arrayAEndIndex = arrayALength - 1;
        int arrayBEndIndex = arrayBLength - 1;
        int mergedArrayEndIndex = arrayALength + arrayBLength - 1;

        while (arrayBEndIndex >= 0) {
            if (arrayAEndIndex >= 0 && arrayA[arrayAEndIndex].compareTo(arrayB[arrayBEndIndex]) > 0) {
                arrayA[mergedArrayEndIndex] = arrayA[arrayAEndIndex];
                arrayAEndIndex--;
            } else {
                arrayA[mergedArrayEndIndex] = arrayB[arrayBEndIndex];
                arrayBEndIndex--;
            }
            mergedArrayEndIndex--;
        }
    }

    public static void main(String[] args) {
        Integer[] arrayA1 = {0, 2, 4, 6, 10, null, null, null};
        Integer[] arrayB1 = {1, 3, 5};

        sortedMerge(arrayA1, arrayB1, 5, 3);
        System.out.print("Sorted array 1: ");
        printArray(arrayA1);
        System.out.println("Expected: 0 1 2 3 4 5 6 10");

        Integer[] arrayA2 = {0, 1, null, null, null};
        Integer[] arrayB2 = {2, 3, 4};

        sortedMerge(arrayA2, arrayB2, 2, 3);
        System.out.print("\nSorted array 2: ");
        printArray(arrayA2);
        System.out.println("Expected: 0 1 2 3 4");

        Integer[] arrayA3 = {3, 4, 5, null, null};
        Integer[] arrayB3 = {0, 1};

        sortedMerge(arrayA3, arrayB3, 3, 2);
        System.out.print("\nSorted array 3: ");
        printArray(arrayA3);
        System.out.println("Expected: 0 1 3 4 5");
    }

    private static void printArray(Comparable[] array) {
        StringJoiner arrayValues = new StringJoiner(" ");
        for (Comparable value : array) {
            arrayValues.add(String.valueOf(value));
        }
        System.out.println(arrayValues.toString());
    }

}
