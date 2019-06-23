package chapter5_bit_manipulation;

/**
 * Created by Rene Argento on 20/06/19.
 */
public class Exercise7_PairwiseSwap {

    // O(b) runtime, where b is the number of bits in the number
    // O(b) space
    public static int pairwiseSwap(int number) {
        int evenBitsMask = 0x55555555; // 010101..01
        int oddBitsMask = 0xAAAAAAAA;  // 101010..10

        int evenBits = number & evenBitsMask;
        int oddBits = number & oddBitsMask;

        evenBits <<= 1;
        oddBits >>= 1;

        return evenBits | oddBits;
    }

    // Same as method above, in one line
    public static int pairwiseSwapOneLine(int number) {
        return ((number & 0xAAAAAAAA) >>> 1) | ((number & 0x55555555) << 1);
    }

    // O(b^2) runtime, where b is the number of bits in the number
    // O(b) space
    public static int pairwiseSwap2(int number) {
        int rightShift = number >> 1;
        number <<= 1;
        int mask = 1;

        for (int i = 0; i < 31; i+= 2) {
            if ((rightShift & mask) > 0) {
                number |= mask;
            } else {
                number &= ~mask;
            }
            mask <<= 2;
        }

        return number;
    }

    public static void main(String[] args) {
        int number1 = 42; // 101010
        int number2 = 41; // 101001
        int number3 = 100; // 1100100
        int number4 = 110; // 01101110
        int number5 = 256; // 100000000

        System.out.println("Method 1");
        int pairwiseSwap1 = pairwiseSwap(number1);
        System.out.println("Pairwise swap: " + pairwiseSwap1 + " Expected: 21");

        int pairwiseSwap2 = pairwiseSwap(number2);
        System.out.println("Pairwise swap: " + pairwiseSwap2 + " Expected: 22");

        int pairwiseSwap3 = pairwiseSwap(number3);
        System.out.println("Pairwise swap: " + pairwiseSwap3 + " Expected: 152");

        int pairwiseSwap4 = pairwiseSwap(number4);
        System.out.println("Pairwise swap: " + pairwiseSwap4 + " Expected: 157");

        int pairwiseSwap5 = pairwiseSwap(number5);
        System.out.println("Pairwise swap: " + pairwiseSwap5 + " Expected: 512");


        System.out.println("\nMethod 2");
        int pairwiseSwap6 = pairwiseSwap2(number1);
        System.out.println("Pairwise swap: " + pairwiseSwap6 + " Expected: 21");

        int pairwiseSwap7 = pairwiseSwap2(number2);
        System.out.println("Pairwise swap: " + pairwiseSwap7 + " Expected: 22");

        int pairwiseSwap8 = pairwiseSwap2(number3);
        System.out.println("Pairwise swap: " + pairwiseSwap8 + " Expected: 152");

        int pairwiseSwap9 = pairwiseSwap2(number4);
        System.out.println("Pairwise swap: " + pairwiseSwap9 + " Expected: 157");

        int pairwiseSwap10 = pairwiseSwap2(number5);
        System.out.println("Pairwise swap: " + pairwiseSwap10 + " Expected: 512");
    }

}
