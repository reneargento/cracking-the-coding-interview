package chapter17_hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 27/04/20.
 */
public class Exercise4_MissingNumber {

    private static class BitInteger {
        public static final int INTEGER_BITS_SIZE = 32;
        private int value;

        public BitInteger(int value) {
            this.value = value;
        }

        public int getBit(int bit) {
            int bitSet = 1 << bit;
            return (value & bitSet) != 0 ? 1 : 0;
        }
    }

    // O(n) runtime, where n is the number of elements in the array
    // O(n) space
    public static int missingNumber(BitInteger[] array) {
        List<BitInteger> numbersList = Arrays.asList(array);
        return missingNumber(numbersList, 0);
    }

    public static int missingNumber(List<BitInteger> array, int index) {
        if (index == BitInteger.INTEGER_BITS_SIZE) {
            return 0;
        }

        List<BitInteger> zeroBits = new ArrayList<>(array.size() / 2);
        List<BitInteger> oneBits = new ArrayList<>(array.size() / 2);

        for (BitInteger number : array) {
            int bit = number.getBit(index);
            if (bit == 0) {
                zeroBits.add(number);
            } else {
                oneBits.add(number);
            }
        }

        if (zeroBits.size() <= oneBits.size()) {
            int missingNumber = missingNumber(zeroBits, index + 1);
            return (missingNumber << 1) | 0;
        } else {
            int missingNumber = missingNumber(oneBits, index + 1);
            return (missingNumber << 1) | 1;
        }
    }

    public static void main(String[] args) {
        BitInteger[] array1 = getArrayWithMissingNumber(20, 13);
        int missingNumber1 = missingNumber(array1);
        System.out.println("Missing number: " + missingNumber1 + " Expected: 13");

        BitInteger[] array2 = getArrayWithMissingNumber(80, 32);
        int missingNumber2 = missingNumber(array2);
        System.out.println("Missing number: " + missingNumber2 + " Expected: 32");

        BitInteger[] array3 = getArrayWithMissingNumber(11, 0);
        int missingNumber3 = missingNumber(array3);
        System.out.println("Missing number: " + missingNumber3 + " Expected: 0");

        BitInteger[] array4 = getArrayWithMissingNumber(15, 15);
        int missingNumber4 = missingNumber(array4);
        System.out.println("Missing number: " + missingNumber4 + " Expected: 15");
    }

    private static BitInteger[] getArrayWithMissingNumber(int maxValue, int missingNumber) {
        BitInteger[] array = new BitInteger[maxValue];
        int arrayIndex = 0;

        for (int i = 0; i <= maxValue; i++) {
            if (i == missingNumber) {
                continue;
            }
            array[arrayIndex++] = new BitInteger(i);
        }
        return array;
    }

}
