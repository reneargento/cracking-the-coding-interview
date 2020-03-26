package chapter16_moderate;

import java.util.*;

/**
 * Created by Rene Argento on 24/03/20.
 */
public class Exercise11_DivingBoard {

    // O(p) runtime, where p is the number of planks
    // O(1) space
    public static Set<Integer> allLengths(int planks, int shorterLength, int longerLength) {
        Set<Integer> lengths = new HashSet<>();
        for (int shorterPlanks = 0; shorterPlanks <= planks; shorterPlanks++) {
            int longerPlanks = planks - shorterPlanks;
            int length = shorterPlanks * shorterLength + longerPlanks * longerLength;
            lengths.add(length);
        }
        return lengths;
    }

    public static void main(String[] args) {
        Set<Integer> lengths = allLengths(6, 1, 2);

        StringJoiner lengthsString = new StringJoiner(" ");
        for (int length : lengths) {
            lengthsString.add(String.valueOf(length));
        }
        System.out.println("All lengths for 6 planks: " + lengthsString);
        System.out.println("Expected: 6 7 8 9 10 11 12");
    }

}
