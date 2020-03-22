package chapter16_moderate;

/**
 * Created by Rene Argento on 18/03/20.
 */
public class Exercise7_NumberMax {

    // Returns 1 if the number is positive, 0 if the number is negative
    private static int sign(int number) {
        return flip(number >>> 31); // Logical shift
    }

    // Flips a 1 to a 0 and a 0 to a 1
    private static int flip(int bit) {
        return 1 ^ bit;
    }

    // O(1) runtime
    // O(1) space
    public static int numberMax(int number1, int number2) {
        int number1Sign = sign(number1);
        int number2Sign = sign(number2);
        int differenceSign = sign(number1 - number2);

        /* Goal: define a value k that is 1 if a > b and 0 if a < b.
        If a = b, it doesn't matter what value k is */

        // If numbers have different signs an overflow can happen, so k = sign of number 1
        int useNumber1Sign = number1Sign ^ number2Sign;

        // If numbers have the same sign an overflow is not possible, so k = sign of the difference
        int useDifferenceSign = flip(number1Sign ^ number2Sign);

        int k = useNumber1Sign * number1Sign + useDifferenceSign * differenceSign;
        int kInverse = flip(k);
        return k * number1 + kInverse * number2;
    }

    public static void main(String[] args) {
        int numberMax1 = numberMax(10, 1);
        System.out.println("Number max: " + numberMax1 + " Expected: 10");

        int numberMax2 = numberMax(230123, 999999);
        System.out.println("Number max: " + numberMax2 + " Expected: 999999");

        int numberMax3 = numberMax(-12, -11);
        System.out.println("Number max: " + numberMax3 + " Expected: -11");

        int numberMax4 = numberMax(Integer.MAX_VALUE - 2, -15);
        System.out.println("Number max: " + numberMax4 + " Expected: 2147483645");
    }

}
