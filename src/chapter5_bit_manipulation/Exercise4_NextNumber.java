package chapter5_bit_manipulation;

/**
 * Created by Rene Argento on 15/06/19.
 */
public class Exercise4_NextNumber {

    // O(b) runtime, where b is the number of bits in the number
    // O(b) space
    public static int getNextNumber(int number) {
        int numberOf0s = 0;
        int numberOf1s = 0;
        int copy = number;

        // Count trailing 0s
        while ((copy & 1) == 0 && copy != 0) {
            numberOf0s++;
            copy >>= 1;
        }

        // Count 1 bits before first non-trailing 0
        while ((copy & 1) == 1) {
            numberOf1s++;
            copy >>= 1;
        }

        int indexToShift = numberOf0s + numberOf1s;

        // Check if there is a valid next number with the same number of 1s
        if (indexToShift == 31 || indexToShift == 0) {
            return -1;
        }

        // Flip bit
        number |= 1 << indexToShift;

        // Move all 1 bits before flipped bit to the rightmost positions

        // Clear all bits before flipped bit
        int clearMask = ~0;
        clearMask <<= indexToShift;
        number = number & clearMask;

        numberOf1s--; // Flip one bit

        // Set 1 bits
        int set1Mask = (1 << numberOf1s) - 1;
        return number | set1Mask;
    }

    // O(b) runtime, where b is the number of bits in the number
    // O(b) space
    public static int getPreviousNumber(int number) {
        int numberOf0s = 0;
        int numberOf1s = 0;
        int copy = number;

        // Count trailing 1s
        while ((copy & 1) == 1) {
            numberOf1s++;
            copy >>= 1;
        }

        if (copy == 0) {
            return -1;
        }

        // Count 0 bits before first non-trailing 1
        while ((copy & 1) == 0 && copy != 0) {
            numberOf0s++;
            copy >>= 1;
        }

        int indexToShift = numberOf0s + numberOf1s;

        // Move all 1 bits before flipped bit to the leftmost positions (before the flipped bit)

        // Clears bits from indexToShift onwards
        number &= (~0) << (indexToShift + 1);

        numberOf1s++; // Flip one bit

        // Set 1 bits
        int setMask = (1 << numberOf1s) - 1;
        number |= setMask << (numberOf0s - 1);

        return number;

    }

    public static void main(String[] args) {
        int nextNumber1 = getNextNumber(21); // 10101
        System.out.println("Next number: " + nextNumber1 + " Expected: 22"); // 10110

        int nextNumber2 = getNextNumber(32); // 100000
        System.out.println("Next number: " + nextNumber2 + " Expected: 64"); // 1000000

        int nextNumber3 = getNextNumber(184); // 10111000
        System.out.println("Next number: " + nextNumber3 + " Expected: 195"); // 11000011

        int nextNumber4 = getNextNumber(0); // 0
        System.out.println("Next number: " + nextNumber4 + " Expected: -1");

        int previousNumber1 = getPreviousNumber(22); // 10110
        System.out.println("\nPrevious number: " + previousNumber1 + " Expected: 21"); // 10101

        int previousNumber2 = getPreviousNumber(64); // 1000000
        System.out.println("Previous number: " + previousNumber2 + " Expected: 32"); // 100000

        int previousNumber3 = getPreviousNumber(195); // 11000011
        System.out.println("Previous number: " + previousNumber3 + " Expected: 184"); // 10111000

        int previousNumber4 = getPreviousNumber(0); // 0
        System.out.println("Previous number: " + previousNumber4 + " Expected: -1");

        int previousNumber5 = getPreviousNumber(7); // 111
        System.out.println("Previous number: " + previousNumber5 + " Expected: -1");
    }

}
