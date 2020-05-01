package chapter16_moderate;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 12/04/20.
 */
public class Exercise21_SumSwap {

    public static class Pair {
        int value1;
        int value2;

        public Pair(int value1, int value2) {
            this.value1 = value1;
            this.value2 = value2;
        }

        @Override
        public String toString() {
            return value1 + ", " + value2;
        }
    }

    // O(a + b) runtime, where a is the number of elements on array1 and b is the number of elements on array2
    // O(b) space
    public static Pair getValuesToSwap(int[] array1, int[] array2) {
        if (array1 == null || array2 == null) {
            return null;
        }
        Long target = getTarget(array1, array2);
        if (target == null) {
            return null;
        }
        Set<Integer> valuesSet = computeValuesSet(array2);
        return getPairToSwap(array1, valuesSet, target);
    }

    private static Long getTarget(int[] array1, int[] array2) {
        long sum1 = sum(array1);
        long sum2 = sum(array2);
        long sumDifference = sum2 - sum1;

        if (sumDifference % 2 != 0) {
            return null;
        }
        return sumDifference / 2;
    }

    private static long sum(int[] array) {
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private static Set<Integer> computeValuesSet(int[] array) {
        Set<Integer> valuesSet = new HashSet<>();
        for (int value : array) {
            valuesSet.add(value);
        }
        return valuesSet;
    }

    private static Pair getPairToSwap(int[] array, Set<Integer> valuesSet, long target) {
        for (int value : array) {
            int targetValue = (int) (value + target);
            if (valuesSet.contains(targetValue)) {
                return new Pair(value, targetValue);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] array1 = { 4, 1, 2, 1, 1, 2 };
        int[] array2 = { 3, 6, 3, 3 };
        Pair pair1 = getValuesToSwap(array1, array2);
        System.out.println("Pair: " + pair1 + " Expected: 4, 6 (or 1, 3)");

        int[] array3 = { 1, 6, 3 };
        int[] array4 = { 2 };
        Pair pair2 = getValuesToSwap(array3, array4);
        System.out.println("Pair: " + pair2 + " Expected: 6, 2");

        int[] array5 = { 1, 2, 3 };
        int[] array6 = { 2, 3 };
        Pair pair3 = getValuesToSwap(array5, array6);
        System.out.println("Pair: " + pair3 + " Expected: null");
    }

}
