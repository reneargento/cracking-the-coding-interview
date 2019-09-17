package chapter8_recursion_dynamic_programming;

import java.util.Arrays;

/**
 * Created by Rene Argento on 11/09/19.
 */
public class Exercise1_TripleStep {

    // O(n) runtime
    // O(n) space
    private static long countWaysToRunBottomUp(int numberOfSteps) {
        if (numberOfSteps == 1) {
            return 1;
        }
        if (numberOfSteps == 2) {
            return 2;
        }

        int[] waysToGetToStep = new int[numberOfSteps + 1];

        // Base case - step 0
        waysToGetToStep[0] = 1;

        for (int i = 0; i < numberOfSteps; i++) {
            int oneStep = i + 1;
            int twoSteps = i + 2;
            int threeSteps = i + 3;

            if (oneStep <= numberOfSteps) waysToGetToStep[oneStep] += waysToGetToStep[i];
            if (twoSteps <= numberOfSteps) waysToGetToStep[twoSteps] += waysToGetToStep[i];
            if (threeSteps <= numberOfSteps) waysToGetToStep[threeSteps] += waysToGetToStep[i];
        }

        return waysToGetToStep[numberOfSteps];
    }

    // O(n) runtime
    // O(n) space
    private static long countWaysToRunTopDown(int numberOfSteps) {
        long[] memo = new long[numberOfSteps + 1];
        Arrays.fill(memo, -1);
        return countWaysToRunTopDown(numberOfSteps, memo);
    }

    private static long countWaysToRunTopDown(int numberOfSteps, long[] memo) {
        if (numberOfSteps < 0) {
            return 0;
        } else if (numberOfSteps == 0) {
            return 1;
        } else if (memo[numberOfSteps] > -1) {
            return memo[numberOfSteps];
        } else {
            memo[numberOfSteps] = countWaysToRunTopDown(numberOfSteps - 1, memo)
                    + countWaysToRunTopDown(numberOfSteps - 2, memo)
                    + countWaysToRunTopDown(numberOfSteps - 3, memo);
            return memo[numberOfSteps];
        }
    }

    public static void main(String[] args) {
        long waysToGetToStep1 = countWaysToRunBottomUp(1);
        System.out.println("Ways to get to step 1: " + waysToGetToStep1 + " Expected: 1");

        long waysToGetToStep2 = countWaysToRunBottomUp(2);
        System.out.println("Ways to get to step 2: " + waysToGetToStep2 + " Expected: 2");

        long waysToGetToStep3 = countWaysToRunBottomUp(3);
        System.out.println("Ways to get to step 3: " + waysToGetToStep3 + " Expected: 4");

        long waysToGetToStep5 = countWaysToRunBottomUp(5);
        System.out.println("Ways to get to step 5: " + waysToGetToStep5 + " Expected: 13");
    }

}
