package chapter17_hard;

/**
 * Created by Rene Argento on 28/05/20.
 */
public class Exercise19_MissingTwo {

    private static class MissingNumbers {
        int number1;
        int number2;

        public MissingNumbers(int number1, int number2) {
            this.number1 = number1;
            this.number2 = number2;
        }

        @Override
        public String toString() {
            return number1 + ", " + number2;
        }
    }

    // O(n) runtime, where n is the number of elements in the array
    // O(1) space
    public static int missingOne(int[] array) {
        int maxNumber = array.length + 1;

        int sum = getSum(array);
        int expectedSum = (1 + maxNumber) * maxNumber / 2;
        return expectedSum - sum;
    }

    private static int getSum(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    // O(n) runtime, where n is the number of elements in the array
    // O(1) space
    public static MissingNumbers missingTwo(int[] array) {
        int maxNumber = array.length + 2;

        int sumDifference = (1 + maxNumber) * maxNumber / 2;
        int squareSumDifference = squareSumToMaxNumber(maxNumber);

        for (int value : array) {
            sumDifference -= value;
            squareSumDifference -= value * value;
        }
        return solveQuadraticEquation(sumDifference, squareSumDifference);
    }

    private static int squareSumToMaxNumber(int maxNumber) {
        int sum = 0;
        for (int i = 1; i <= maxNumber; i++) {
            sum += i * i;
        }
        return sum;
    }

    // Solve equation of the format a * x^2 + b * x + c
    private static MissingNumbers solveQuadraticEquation(int pairSum, int pairSquaredSum) {
        // x + y = pairSum
        // y = pairSum - x
        // x^2 + y^2 = pairSquaredSum
        // x^2 + (pairSum - x)^2 = pairSquaredSum
        // x^2 + (pairSum - x)^2 - pairSquaredSum = 0
        // x^2 + pairSum^2 - (2 * pairSum * x) + x^2 - pairSquaredSum = 0
        // 2 * x^2 + pairSum^2 - (2 * pairSum * x) - pairSquaredSum = 0
        // 2 * x^2 - (2 * pairSum * x) + pairSum^2 - pairSquaredSum = 0

        // Quadratic formula:
        // x = (-b +- sqrt(b^2 - 4 * a * c)) / 2 * a

        int a = 2;
        int b = -2 * pairSum;
        int c = pairSum * pairSum - pairSquaredSum;

        double part1 = -1 * b;
        double part2 = Math.sqrt(b * b - (4 * a * c));
        double part3 = 2 * a;

        // Using only one of the two possible solutions is enough
        int x = (int) ((part1 - part2) / part3);
        int y = pairSum - x;

        return new MissingNumbers(x, y);
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 9, 8, 5, 4, 6, 7, 10};
        int missingOne = missingOne(array);
        System.out.println("Missing one: " + missingOne + " Expected: 3");

        MissingNumbers missingTwo = missingTwo(array);
        System.out.println("Missing two: " + missingTwo + " Expected: 3, 11");
    }

}
