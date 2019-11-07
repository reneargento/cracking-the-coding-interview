package chapter8_recursion_dynamic_programming;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 21/10/19.
 */
public class Exercise14_BooleanEvaluation {

    // O(n^2) runtime, where n is the number of operators in the expression
    // O(n^2) space
    public static long countEvaluations(String expression, boolean expectedResult) {
        return countEvaluations(expression, expectedResult, new HashMap<>());
    }

    private static long countEvaluations(String expression, boolean expectedResult, Map<String, Long> resultsMap) {
        // Base cases
        if (expression.length() == 0) return 0;
        if (expression.length() == 1) {
            return getBooleanValue(expression) == expectedResult ? 1 : 0;
        }

        String resultsMapKey = expression + expectedResult;
        if (resultsMap.containsKey(resultsMapKey)) {
            return resultsMap.get(resultsMapKey);
        }

        long resultCount = 0;

        for (int i = 1; i < expression.length(); i += 2) {
            String leftString = expression.substring(0, i);
            String rightString = expression.substring(i + 1);

            long countLeftFalse = countEvaluations(leftString, false, resultsMap);
            long countLeftTrue = countEvaluations(leftString, true, resultsMap);
            long countRightFalse = countEvaluations(rightString, false, resultsMap);
            long countRightTrue = countEvaluations(rightString, true, resultsMap);
            long totalCount = (countLeftFalse + countLeftTrue) * (countRightFalse + countRightTrue);

            char operator = expression.charAt(i);
            long trueCount = 0;

            switch (operator) {
                case '&': trueCount = countLeftTrue * countRightTrue;
                    break;
                case '|': trueCount = countLeftTrue * countRightFalse
                        + countLeftTrue * countRightTrue
                        + countLeftFalse * countRightTrue;
                    break;
                case '^': trueCount = countLeftFalse * countRightTrue
                        + countLeftTrue * countRightFalse;
                    break;
            }
            resultCount += expectedResult ? trueCount : totalCount - trueCount;
        }

        resultsMap.put(resultsMapKey, resultCount);
        return resultCount;
    }

    private static boolean getBooleanValue(String string) {
        return string.equals("1");
    }

    public static void main(String[] args) {
        String expression1 = "1^0|0|1";
        long count1 = countEvaluations(expression1, false);
        System.out.println("Count 1: " + count1 + " Expected: 2");

        String expression2 = "0&0&0&1^1|0";
        long count2 = countEvaluations(expression2, true);
        System.out.println("Count 2: " + count2 + " Expected: 10");
    }

}
