package chapter16_moderate;

/**
 * Created by Rene Argento on 16/03/20.
 */
public class Exercise5_FactorialZeros {

    // O(n) runtime, where n is the number to compute the factorial
    // O(1) space
    public static int factorialZeros(int number) {
        int zeros = 0;
        if (number < 0) {
            return -1;
        }

        for (int i = 5; number / i > 0; i *= 5) {
            zeros += number / i;
        }
        return zeros;
    }

    public static void main(String[] args) {
        int factorialZeros1 = factorialZeros(5);
        System.out.println("N = 5, factorial zeros: " + factorialZeros1 + " Expected: 1");

        int factorialZeros2 = factorialZeros(10);
        System.out.println("N = 10, factorial zeros: " + factorialZeros2 + " Expected: 2");

        int factorialZeros3 = factorialZeros(24);
        System.out.println("N = 24, factorial zeros: " + factorialZeros3 + " Expected: 4");

        int factorialZeros4 = factorialZeros(25);
        System.out.println("N = 25, factorial zeros: " + factorialZeros4 + " Expected: 6");
    }

}
