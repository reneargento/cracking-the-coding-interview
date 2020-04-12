package chapter16_moderate;

/**
 * Created by Rene Argento on 06/04/20.
 */
public class Exercise17_ContiguousSequence {

    // O(n) runtime, where n is the number of elements in the array
    // O(1) space
    public static long kadane(int[] array) {
        long maxSum = 0;
        long sum = 0;

        for (int value : array) {
            sum += value;
            maxSum = Math.max(maxSum, sum);

            if (sum < 0) {
                sum = 0;
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[] array1 = {-8, 3, -2, 4, -10};
        long maxSum1 = kadane(array1);
        System.out.println("Sum: " + maxSum1 + " Expected: 5");

        int[] array2 = {2, -3, 1, 2, -1, 5, -3, 1};
        long maxSum2 = kadane(array2);
        System.out.println("Sum: " + maxSum2 + " Expected: 7");

        int[] array3 = {-1, -4, -2, -3};
        long maxSum3 = kadane(array3);
        System.out.println("Sum: " + maxSum3 + " Expected: 0");
    }
}
