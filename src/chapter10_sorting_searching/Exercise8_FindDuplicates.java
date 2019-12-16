package chapter10_sorting_searching;

/**
 * Created by Rene Argento on 12/12/19.
 */
public class Exercise8_FindDuplicates {

    public static class BitSet {
        int[] bitSet;

        public BitSet(int size) {
            bitSet = new int[(size >> 5) + 1]; // Divide by 32
        }

        public boolean get(int position) {
            int valueIndex = position / 32;
            int bitIndex = position % 32;
            return (bitSet[valueIndex] & (1 << bitIndex)) != 0;
        }

        public void set(int position) {
            int valueIndex = position / 32;
            int bitIndex = position % 32;
            bitSet[valueIndex] |= (1 << bitIndex);
        }
    }

    // O(n) runtime, where n is the number of values in the array
    // O(32.000) space
    private static void printDuplicates(int[] array) {
        // 4 KB = 4 000 bytes = 32 000 bits
        BitSet bitSet = new BitSet(32000);

        for (int value : array) {
            // Values go from 1 to 32.000
            if (bitSet.get(value - 1)) {
                System.out.println("Duplicate: " + value);
            } else {
                bitSet.set(value - 1);
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {3, 4, 6, 9, 7, 7, 10, 12, 8, 1, 2, 5, 12, 1, 10, 11};
        printDuplicates(array);
        System.out.println("\nExpected: 7 12 1 10");
    }

}
