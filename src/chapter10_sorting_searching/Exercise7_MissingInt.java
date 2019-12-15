package chapter10_sorting_searching;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by Rene Argento on 09/12/19.
 */
public class Exercise7_MissingInt {

    // O(n) runtime, where n is the number of integers in the file
    // O(Integer.MAX_VALUE) space
    public static int findMissingInt1(String fileName) throws FileNotFoundException {
        long numberOfInts = ((long) Integer.MAX_VALUE) + 1;
        byte[] bitVector = new byte[(int) (numberOfInts / 8)];
        Scanner scanner = new Scanner(new FileReader(fileName));

        while (scanner.hasNextInt()) {
            int number = scanner.nextInt();
            // Set corresponding bit
            bitVector[number / 8] |= 1 << (number % 8);
        }

        for (int i = 0; i < bitVector.length; i++) {
            for (int j = 0; j < 8; j++) {
                // Check every bit in the byte for an unset bit
                if ((bitVector[i] & (1 << j)) == 0) {
                    return (i * 8) + j;
                }
            }
        }
        return -1;
    }

    // O(n) runtime, where n is the number of integers in the file
    // O(2^20 / 2^3) = O(2^17) space
    // Follow up question - only 10 MB of memory available
    // First pass - count number of integers in each block
    // Second pass - find missing number in a block with count lower than expected
    public static int findMissingInt2(String fileName) throws FileNotFoundException {
        // Range size computation:

        // There are 2^31 non-negative integers
        // Array size = 2^31 / Range size
        // 10 MB of memory = 10 000 KB = 10 000 000 bytes ~= 2^23 bytes = 2^21 integers (because each integer is 4 bytes)
        // 2^31 / Range size <= Array size
        // 2^31 / Range Size <= 2^21
        // Range Size >= 2^10

        // Space for the bit vector
        // 2^23 bytes = 2^26 bits
        // Therefore:
        // 2^11 <= Range Size <= 2^26
        // The nearer the middle of that range, the less memory will be used at any given time
        int rangeSize = (1 << 20); // 2^20 bits
        int[] blocks = getCountPerBlock(fileName, rangeSize);

        // Get block which has a count lower than expected
        int blockIndex = findBlockWithMissingNumber(blocks, rangeSize);
        if (blockIndex < 0) {
            return -1;
        }

        byte[] bitVector = getBitVectorForBlock(fileName, rangeSize, blockIndex);

        int offset = findZero(bitVector);
        if (offset < 0) {
            return -1;
        }
        return rangeSize * blockIndex + offset;
    }

    private static int[] getCountPerBlock(String fileName, int rangeSize) throws FileNotFoundException {
        int arraySize = Integer.MAX_VALUE / rangeSize + 1;
        int[] blocks = new int[arraySize];

        Scanner scanner = new Scanner(new FileReader(fileName));
        while (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            blocks[value / rangeSize]++;
        }
        scanner.close();
        return blocks;
    }

    private static int findBlockWithMissingNumber(int[] blocks, int rangeSize) {
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] < rangeSize) {
                return i;
            }
        }
        return -1;
    }

    private static byte[] getBitVectorForBlock(String fileName, int rangeSize, int blockIndex) throws FileNotFoundException {
        byte[] bitVector = new byte[rangeSize / Byte.SIZE];
        int startRange = rangeSize * blockIndex;
        int endRange = startRange + rangeSize;

        Scanner scanner = new Scanner(new FileReader(fileName));
        while (scanner.hasNextInt()) {
            int value = scanner.nextInt();
            if (startRange <= value && value < endRange) {
                int offset = value - startRange;
                int bitIndex = offset % Byte.SIZE;
                bitVector[offset / Byte.SIZE] |= 1 << bitIndex;
            }
        }
        scanner.close();
        return bitVector;
    }

    private static int findZero(byte[] bitVector) {
        for (int i = 0; i < bitVector.length; i++) {
            if (bitVector[i] != ~0) { // Check if there is at least one bit with value 0
                int bitIndex = getZeroBit(bitVector[i]);
                return i * Byte.SIZE + bitIndex;
            }
        }
        return -1;
    }

    private static int getZeroBit(byte byteValue) {
        for (int i = 0; i < Byte.SIZE; i++) {
            int mask = 1 << i;
            if ((byteValue & mask) == 0) {
                return i;
            }
        }
        return -1;
    }

}
