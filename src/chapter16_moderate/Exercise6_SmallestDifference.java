package chapter16_moderate;

import java.util.Arrays;

/**
 * Created by Rene Argento on 17/03/20.
 */
public class Exercise6_SmallestDifference {

    // O(a lg a + b lg b) runtime, where a is the length of array1 and b is the length of array2
    // O(1) space
    public static int smallestDifference(int[] array1, int[] array2) {
        if (array1 == null || array2 == null || array1.length == 0 || array2.length == 0) {
            return -1;
        }
        Arrays.sort(array1);
        Arrays.sort(array2);

        int array1Index = 0;
        int array2Index = 0;
        int smallestDifference = Integer.MAX_VALUE;

        while (array1Index < array1.length && array2Index < array2.length) {
            int difference = Math.abs(array1[array1Index] - array2[array2Index]);
            smallestDifference = Math.min(smallestDifference, difference);

            if (array1[array1Index] < array2[array2Index]) {
                array1Index++;
            } else {
                array2Index++;
            }
        }
        return smallestDifference;
    }

    public static void main(String[] args) {
        int[] array1 = {1, 3, 15, 11, 2};
        int[] array2 = {23, 127, 235, 19, 8};
        int smallestDifference1 = smallestDifference(array1, array2);
        System.out.println("Smallest difference: " + smallestDifference1 + " Expected: 3");

        int[] array3 = {10, 11, 99, 1, -10};
        int[] array4 = {-9, 101, 4, 43};
        int smallestDifference2 = smallestDifference(array3, array4);
        System.out.println("Smallest difference: " + smallestDifference2 + " Expected: 1");
    }

}
