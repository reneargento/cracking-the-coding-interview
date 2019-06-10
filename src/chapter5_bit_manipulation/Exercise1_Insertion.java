package chapter5_bit_manipulation;

/**
 * Created by Rene Argento on 08/06/19.
 */
public class Exercise1_Insertion {

    // Inserts bits2 into bits1 on the range [startIndex, endIndex]
    // O(1) runtime
    // O(1) space
    public static int insertBits(int bits1, int bits2, int startIndex, int endIndex) {
        // Creates a mask of 1s like 11111111111
        int allOnesMask = ~0;

        // 1s before endIndex, then 0s. Example: 11110000000
        int leftMask = allOnesMask << (endIndex + 1);
        // 1s after startIndex. Example: 00000000011
        int rightMask = (1 << startIndex) - 1;
        // All 1s, except for 0s between startIndex and endIndex. Example: 11110000011
        int mask = leftMask | rightMask;

        // Clear the bits between startIndex and endIndex
        int bits1Cleared = bits1 & mask;
        // Move bits2 to the correct position
        bits2 <<= startIndex;

        return bits1Cleared | bits2;
    }

    // O(1) runtime
    // O(1) space
    public static int insertBitsMethod2(int bits1, int bits2, int startIndex, int endIndex) {
        int maskBit1 = 1 << startIndex;
        int maskBit2 = 1;

        for (int i = startIndex; i <= endIndex; i++) {
            int bit1 = getBit(bits1, maskBit1);
            int bit2 = getBit(bits2, maskBit2);

            if (bit1 != bit2) {
                if (bit2 == 0) {
                    bits1 = clearBit(bits1, maskBit1);
                } else {
                    bits1 = setBit(bits1, maskBit1);
                }
            }

            maskBit1 <<= 1;
            maskBit2 <<= 1;
        }

        return bits1;
    }

    private static int getBit(int bits, int mask) {
        if ((bits & mask) != 0) {
            return 1;
        }
        return 0;
    }

    private static int setBit(int number, int mask) {
        return number | mask;
    }

    private static int clearBit(int number, int mask) {
        return number ^ mask;
    }

    public static void main(String[] args) {
        System.out.println("Method 1");
        int number1 = 1024; // 10000000000
        int number2 = 19; // 10011
        int expectedResult1 = 1100; // 10001001100
        int result1 = insertBits(number1, number2, 2, 6);
        System.out.println("Result: " + result1 + " Expected: " + expectedResult1);

        int number3 = 1000; // 1111101000
        int number4 = 25; // 11001
        int expectedResult2 = 920; // 1110011000
        int result2 = insertBits(number3, number4, 4, 8);
        System.out.println("Result: " + result2 + " Expected: " + expectedResult2);

        int number5 = 2222; // 100010101110
        int number6 = 37; // 100101
        int expectedResult3 = 2414; // 100101101110
        int result3 = insertBits(number5, number6, 6, 11);
        System.out.println("Result: " + result3 + " Expected: " + expectedResult3);

        System.out.println("\nMethod 2");

        int result4 = insertBitsMethod2(number1, number2, 2, 6);
        System.out.println("Result: " + result4 + " Expected: " + expectedResult1);

        int result5 = insertBitsMethod2(number3, number4, 4, 8);
        System.out.println("Result: " + result5 + " Expected: " + expectedResult2);

        int result6 = insertBitsMethod2(number5, number6, 6, 11);
        System.out.println("Result: " + result6 + " Expected: " + expectedResult3);
    }

}
