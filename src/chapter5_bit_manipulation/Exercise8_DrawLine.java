package chapter5_bit_manipulation;

import java.util.StringJoiner;

/**
 * Created by Rene Argento on 23/06/19.
 */
public class Exercise8_DrawLine {

    // O(b) runtime, where b is the number of bytes
    // O(1) space
    public static void drawLine(byte[] screen, int width, int x1, int x2, int y) {
        int rowStart = y * width;
        int startPixel = rowStart + x1;
        int endPixel = rowStart + x2;

        int startByte = startPixel / 8;
        int endByte = endPixel / 8;

        byte startOffset = (byte) (startPixel % 8);
        byte startMask = (byte) (0xFF >> startOffset);

        byte endOffset = (byte) (endPixel % 8);
        byte endMask = (byte) ~(0xFF >> (endOffset + 1));

        // Edge case: All changes are in the same byte
        if (startByte == endByte) {
            screen[startByte] |= startMask & endMask;
            return;
        }

        // Set initial bits
        if (startOffset != 0) {
            screen[startByte] |= startMask;
        } else {
            screen[startByte] = (byte) 0xFF;
        }

        // Set final bits
        screen[endByte] |= endMask;

        // Set middle bits
        for (int index = startByte + 1; index < endByte; index++) {
            screen[index] = (byte) 0xFF;
        }
    }

    public static void main(String[] args) {
        byte[] screen1 = new byte[4];
        byte[] screen2 = new byte[4];
        byte[] screen3 = new byte[4];

        byte[] values = {
                0, // 00000000
                1, // 00000001
                2, // 00000010
                11 // 00001011
        };

        for (int index = 0; index < values.length; index++) {
            screen1[index] = values[index];
            screen2[index] = values[index];
            screen3[index] = values[index];
        }

        drawLine(screen1, 16, 1, 5, 1);
        String bytesStringValue1 = getBytesStringValue(screen1);
        System.out.print("Screen: " + bytesStringValue1 + " Expected: 0 1 126 11");

        drawLine(screen2, 24, 0, 18, 0);
        String bytesStringValue2 = getBytesStringValue(screen2);
        System.out.print("\nScreen: " + bytesStringValue2 + " Expected: -1 -1 -30 11");

        drawLine(screen3, 32, 13, 31, 0);
        String bytesStringValue3 = getBytesStringValue(screen3);
        System.out.print("\nScreen: " + bytesStringValue3 + " Expected: 0 7 -1 -1");
    }

    private static String getBytesStringValue(byte[] bytes) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        for (byte byteValue : bytes) {
            stringJoiner.add(String.valueOf(byteValue));
        }

        return stringJoiner.toString();
    }

}
