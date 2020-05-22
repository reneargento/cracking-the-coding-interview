package chapter17_hard;

/**
 * Created by Rene Argento on 08/05/20.
 */
public class Exercise10_MajorityElement {

    // O(n) runtime, where n is the number of elements in the array
    // O(1) space
    public static int majorityElement(int[] array) {
        if (array == null) {
            return -1;
        }

        int candidate = getCandidate(array);
        return validate(array, candidate) ? candidate : -1;
    }

    private static int getCandidate(int[] array) {
        int majority = 0;
        int majorityCount = 0;

        for (int value : array) {
            if (majorityCount == 0) { // No majority element was found in the previous set
                majority = value;
            }

            if (value == majority) {
                majorityCount++;
            } else {
                majorityCount--;
            }
        }
        return majority;
    }

    private static boolean validate(int[] array, int majority) {
        int count = 0;

        for (int value : array) {
            if (value == majority) {
                count++;
            }
        }
        return count > array.length / 2;
    }

    public static void main(String[] args) {
        int[] array1 = {1, 2, 5, 9, 5, 9, 5, 5, 5};
        int majorityElement1 = majorityElement(array1);
        System.out.println("Majority element: " + majorityElement1 + " Expected: 5");

        int[] array2 = {1, 1, 1, 2, 2, 2, 2, 1, 1};
        int majorityElement2 = majorityElement(array2);
        System.out.println("Majority element: " + majorityElement2 + " Expected: 1");

        int[] array3 = {1, 2, 3, 4, 5};
        int majorityElement3 = majorityElement(array3);
        System.out.println("Majority element: " + majorityElement3 + " Expected: -1");
    }

}
