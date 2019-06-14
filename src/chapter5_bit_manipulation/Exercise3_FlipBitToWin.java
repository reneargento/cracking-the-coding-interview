package chapter5_bit_manipulation;

/**
 * Created by Rene Argento on 12/06/19.
 */
public class Exercise3_FlipBitToWin {

    // O(b) runtime, where b is the number of bits in the number
    // O(1) space
    public static int countMaxSequence(int number) {
        int previousLength = 0;
        int currentLength = 0;
        int maxLength = 1;

        while (number > 0) {
            if ((number & 1) == 1) {
                currentLength++;
            } else {
                previousLength = (number & 2) == 0 ? 0 : currentLength;
                currentLength = 0;
            }
            maxLength = Math.max(previousLength + 1 + currentLength, maxLength);
            number >>= 1;
        }

        return maxLength;
    }

    public static void main(String[] args) {
        int maxSequence1 = countMaxSequence(1775);
        System.out.println("Max sequence: " + maxSequence1 + " Expected: 8");

        int maxSequence2 = countMaxSequence(1240);
        System.out.println("Max sequence: " + maxSequence2 + " Expected: 5");

        int maxSequence3 = countMaxSequence(1625);
        System.out.println("Max sequence: " + maxSequence3 + " Expected: 4");

        int maxSequence4 = countMaxSequence(1999);
        System.out.println("Max sequence: " + maxSequence4 + " Expected: 6");

        int maxSequence5 = countMaxSequence(2000);
        System.out.println("Max sequence: " + maxSequence5 + " Expected: 7");

        int maxSequence6 = countMaxSequence(2010);
        System.out.println("Max sequence: " + maxSequence6 + " Expected: 8");
    }

}
