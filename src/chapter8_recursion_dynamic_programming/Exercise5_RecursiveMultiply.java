package chapter8_recursion_dynamic_programming;

/**
 * Created by Rene Argento on 19/09/19.
 */
public class Exercise5_RecursiveMultiply {

    // O(lg(s)) runtime, where s is the smaller number
    // O(lg(s)) space
    public static long recursiveMultiply(int value1, int value2) {
        int smaller = value1 < value2 ? value1 : value2;
        int bigger = value1 < value2 ? value2 : value1;
        return recursiveMultiplyHelper(smaller, bigger);
    }

    public static long recursiveMultiplyHelper(int value1, int value2) {
        // Base cases
        if (value1 == 0) {
            return 0;
        }
        if (value1 == 1) {
            return value2;
        }

        boolean isOdd = value1 % 2 == 1;

        int value1Half = value1 >> 1; // Divide by 2
        long result = recursiveMultiplyHelper(value1Half, value2);
        result *= 2;

        if (isOdd) {
            result += value2;
        }

        return result;
    }

    public static void main(String[] args) {
        long result1 = recursiveMultiply(6, 4);
        System.out.println("Result 1: " + result1 + " Expected: 24");

        long result2 = recursiveMultiply(7, 10);
        System.out.println("Result 2: " + result2 + " Expected: 70");

        long result3 = recursiveMultiply(7, 9);
        System.out.println("Result 3: " + result3 + " Expected: 63");

        long result4 = recursiveMultiply(0, 10);
        System.out.println("Result 4: " + result4 + " Expected: 0");

        long result5 = recursiveMultiply(70897, 96573);
        System.out.println("Result 5: " + result5 + " Expected: 6846735981");
    }

}
