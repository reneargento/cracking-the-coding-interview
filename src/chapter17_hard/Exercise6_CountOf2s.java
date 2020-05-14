package chapter17_hard;

/**
 * Created by Rene Argento on 01/05/20.
 */
public class Exercise6_CountOf2s {

    // O(d) runtime, where d is the number of digits in the number
    // O(d) space
    public static long count2sInRange(int number) {
        long count = 0;
        int length = String.valueOf(number).length();

        for (int digit = 0; digit < length; digit++) {
            count += count2sInRangeAtDigit(number, digit);
        }
        return count;
    }

    private static long count2sInRangeAtDigit(int number, int digit) {
        long powerOf10 = (long) Math.pow(10, digit);
        long nextPowerOf10 = powerOf10 * 10;

        long roundDown = number - (number % nextPowerOf10);
        long roundUp = roundDown + nextPowerOf10;

        long digitValue = (number / powerOf10) % 10;

        if (digitValue < 2) {
            return roundDown / 10;
        } else if (digitValue > 2) {
            return roundUp / 10;
        } else {
            long leftCount = roundDown / 10;
            long rightCount = number % powerOf10;
            return leftCount + 1 + rightCount;
        }
    }

    public static void main(String[] args) {
        long count1 = count2sInRange(25);
        System.out.println("Count: " + count1 + " Expected: 9");

        long count2 = count2sInRange(222);
        System.out.println("Count: " + count2 + " Expected: 69");

        long count3 = count2sInRange(1);
        System.out.println("Count: " + count3 + " Expected: 0");

        long count4 = count2sInRange(372332879);
        System.out.println("Count: " + count4 + " Expected: 400806638");

        long count5 = count2sInRange(Integer.MAX_VALUE);
        System.out.println("Count: " + count5 + " Expected: 2071027783");
    }

}
