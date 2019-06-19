package chapter5_bit_manipulation;

/**
 * Created by Rene Argento on 19/06/19.
 */
public class Exercise6_Conversion {

    // O(b) runtime, where b is the number of bits in the the highest number between number1 and number2
    // O(b) space
    public static int countBitShifts(int number1, int number2) {
        int xorResult = number1 ^ number2;

        // Count number of bits with value 1
        int count = 0;

        while (xorResult > 0) {
            count++;
            xorResult &= xorResult - 1;
        }

        return count;
    }

    public static void main(String[] args) {
        int number1 = 29; // 11101
        int number2 = 15; // 01111
        int result1 = countBitShifts(number1, number2);
        System.out.println("Bits to shift: " + result1 + " Expected: 2");

        int number3 = 33; // 100001
        int number4 = 41; // 101001
        int result2 = countBitShifts(number3, number4);
        System.out.println("Bits to shift: " + result2 + " Expected: 1");

        int number5 = 0; //   00000000
        int number6 = 199; // 11000111
        int result3 = countBitShifts(number5, number6);
        System.out.println("Bits to shift: " + result3 + " Expected: 5");

        int number7 = 11; // 01011
        int number8 = 19; // 10011
        int result4 = countBitShifts(number7, number8);
        System.out.println("Bits to shift: " + result4 + " Expected: 2");

        int number9 = 100;  // 1100100
        int number10 = 110; // 1101110
        int result6 = countBitShifts(number9, number10);
        System.out.println("Bits to shift: " + result6 + " Expected: 2");

        int number11 = 255; // 011111111
        int number12 = 256; // 100000000
        int result5 = countBitShifts(number11, number12);
        System.out.println("Bits to shift: " + result5 + " Expected: 9");
    }

}
