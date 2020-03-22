package chapter16_moderate;

/**
 * Created by Rene Argento on 21/03/20.
 */
public class Exercise9_Operations {

    // O((lg number)^2) runtime - halving number takes O(lg number) work and it is done O(lg number) times
    // O(1) space
    private static int negate(int number) {
        int negatedNumber = 0;

        int newSign = number < 0 ? 1 : -1;
        int delta = newSign;

        while (number != 0) {
            boolean areDifferentSigns = (number > 0) != (number + delta > 0);
            if (number + delta != 0 && areDifferentSigns) { // If delta is too big, reset it.
                delta = newSign;
            }
            negatedNumber += delta;
            number += delta;
            delta += delta; // Double delta
        }
        return negatedNumber;
    }

    // O((lg number)^2) runtime
    // O(1) space
    private static int abs(int number) {
        if (number < 0) {
            return negate(number);
        }
        return number;
    }

    // O((lg number)^2) runtime
    // O(1) space
    public static int subtract(int number1, int number2) {
        return number1 + negate(number2);
    }

    // O(s) runtime, where s is the smallest number between number1 and number2
    // O(1) space
    public static int multiply(int number1, int number2) {
        if (number1 < number2) {
            return multiply(number2, number1); // Algorithm is faster if number2 is smaller than number1
        }
        int result = 0;

        for (int i = 0; i < abs(number2); i++) {
            result += number1;
        }

        if (number2 < 0) {
            result = negate(result);
        }
        return result;
    }

    // O(b) runtime, where b is the biggest number between number1 and number2
    // O(1) space
    public static int divide(int number1, int number2) throws ArithmeticException {
        if (number2 == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        int result = 0;
        int product = 0;

        // x = number1 / number2
        // number1 = x * number2
        while (product + abs(number2) <= abs(number1)) {
            product += abs(number2);
            result++;
        }

        if ((number1 > 0 && number2 > 0) || (number1 < 0 && number2 < 0)) {
            return result;
        } else {
            return negate(result);
        }
    }

    public static void main(String[] args) {
        int number1 = 6;
        int number2 = 2;
        int number3 = -2;

        int multiply1 = multiply(number1, number2);
        System.out.println("Multiply: " + multiply1 + " Expected: 12");

        int multiply2 = multiply(number1, number3);
        System.out.println("Multiply: " + multiply2 + " Expected: -12");

        int subtract1 = subtract(number1, number2);
        System.out.println("Subtract: " + subtract1 + " Expected: 4");

        int subtract2 = subtract(number1, number3);
        System.out.println("Subtract: " + subtract2 + " Expected: 8");

        int divide1 = divide(number1, number2);
        System.out.println("Divide: " + divide1 + " Expected: 3");

        int divide2 = divide(number1, number3);
        System.out.println("Divide: " + divide2 + " Expected: -3");

        int divide3 = divide(number2, number1);
        System.out.println("Divide: " + divide3 + " Expected: 0");
    }

}
