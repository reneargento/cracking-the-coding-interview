package chapter17_hard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 30/04/20.
 */
public class Exercise5_LettersAndNumbers {

    // O(n) runtime, where n is the number of characters in the array
    // O(n) space
    public static char[] equalLettersNumbers(char[] characters) {
        int[] maxSubarrayIndices = new int[2];

        // Maps the difference between letters and number occurrences and the first index in which this difference appeared
        Map<Integer, Integer> firstTimeDifference = new HashMap<>();
        firstTimeDifference.put(0, -1);

        int difference = 0;

        for (int i = 0; i < characters.length; i++) {
            char character = characters[i];
            if (Character.isLetter(character)) {
                difference++;
            } else if (Character.isDigit(character)) {
                difference--;
            }

            if (firstTimeDifference.containsKey(difference)) {
                int firstIndexDifference = firstTimeDifference.get(difference);

                int length = i - firstIndexDifference;
                int maxLength = maxSubarrayIndices[1] - maxSubarrayIndices[0];
                if (length > maxLength) {
                    maxSubarrayIndices[0] = firstIndexDifference;
                    maxSubarrayIndices[1] = i;
                }
            } else {
                firstTimeDifference.put(difference, i);
            }
        }
        return extractSubarray(characters, maxSubarrayIndices[0] + 1, maxSubarrayIndices[1]);
    }

    private static char[] extractSubarray(char[] array, int start, int end) {
        char[] subarray = new char[end - start + 1];
        for (int i = start; i <= end; i++) {
            subarray[i - start] = array[i];
        }
        return subarray;
    }

    public static void main(String[] args) {
        String characters1 = "AB1Z2F98";
        char[] subarray1 = equalLettersNumbers(characters1.toCharArray());
        System.out.println("Subarray: " + new String(subarray1) + " Expected: AB1Z2F98");

        String characters2 = "RE12NE3Z";
        char[] subarray2 = equalLettersNumbers(characters2.toCharArray());
        System.out.println("Subarray: " + new String(subarray2) + " Expected: E12NE3");

        String characters3 = "AB12";
        char[] subarray3 = equalLettersNumbers(characters3.toCharArray());
        System.out.println("Subarray: " + new String(subarray3) + " Expected: AB12");

        String characters4 = "RENE";
        char[] subarray4 = equalLettersNumbers(characters4.toCharArray());
        System.out.println("Subarray: " + new String(subarray4) + " Expected: ");

        String characters5 = "ABC0XYZ";
        char[] subarray5 = equalLettersNumbers(characters5.toCharArray());
        System.out.println("Subarray: " + new String(subarray5) + " Expected: C0");

        String characters6 = "AA1AA1A";
        char[] subarray6 = equalLettersNumbers(characters6.toCharArray());
        System.out.println("Subarray: " + new String(subarray6) + " Expected: 1AA1");
    }
}
