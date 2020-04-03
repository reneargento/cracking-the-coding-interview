package chapter16_moderate;

/**
 * Created by Rene Argento on 31/03/20.
 */
public class Exercise16_SubSort {

    public static class Result {
        public int start;
        public int end;

        public Result(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // O(n) runtime, where n is the number of elements in the array
    // O(1) space
    public static Result getUnsortedSubsequence(int[] array) {
        if (array == null) {
            return null;
        }
        int middleSectionStart = getLeftSubsequenceEnd(array);
        int middleSectionEnd = getRightSubsequenceStart(array);
        return new Result(middleSectionStart, middleSectionEnd);
    }

    private static int getLeftSubsequenceEnd(int[] array) {
        int index = 0;
        int runningMin = Integer.MAX_VALUE;

        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] > runningMin) {
                index = i;
            }
            runningMin = Math.min(runningMin, array[i]);
        }
        return index;
    }

    private static int getRightSubsequenceStart(int[] array) {
        int index = 0;
        int runningMax = Integer.MIN_VALUE;

        for (int i = 0; i < array.length; i++) {
            if (array[i] < runningMax) {
                index = i;
            }
            runningMax = Math.max(runningMax, array[i]);
        }
        return index;
    }

    public static void main(String[] args) {
        int[] array1 = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
        Result result1 = getUnsortedSubsequence(array1);
        System.out.println("Start: (" + result1.start + ", " + result1.end + ")");
        System.out.println("Expected: (3, 9)");

        int[] array2 = {10, 1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
        Result result2 = getUnsortedSubsequence(array2);
        System.out.println("\nStart: (" + result2.start + ", " + result2.end + ")");
        System.out.println("Expected: (0, 10)");

        int[] array3 = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19, 20, 6};
        Result result3 = getUnsortedSubsequence(array3);
        System.out.println("\nStart: (" + result3.start + ", " + result3.end + ")");
        System.out.println("Expected: (3, 14)");

        int[] array4 = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19, -10};
        Result result4 = getUnsortedSubsequence(array4);
        System.out.println("\nStart: (" + result4.start + ", " + result4.end + ")");
        System.out.println("Expected: (0, 13)");

        int[] array5 = {1, 2, 4, 5, 8, 10};
        Result result5 = getUnsortedSubsequence(array5);
        System.out.println("\nStart: (" + result5.start + ", " + result5.end + ")");
        System.out.println("Expected: (0, 0)");

        int[] array6 = {1, 2, 4, 5, 8, 6, 10};
        Result result6 = getUnsortedSubsequence(array6);
        System.out.println("\nStart: (" + result6.start + ", " + result6.end + ")");
        System.out.println("Expected: (4, 5)");
    }

}
