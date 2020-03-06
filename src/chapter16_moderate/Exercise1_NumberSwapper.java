package chapter16_moderate;

/**
 * Created by Rene Argento on 05/03/20.
 */
public class Exercise1_NumberSwapper {

    // A = A ^ B
    // B = A ^ B
    // A = A ^ B
    public static void numberSwapperXor(int[] numbers) {
        numbers[0] = numbers[0] ^ numbers[1];
        numbers[1] = numbers[0] ^ numbers[1];
        numbers[0] = numbers[0] ^ numbers[1];
    }

    // A = A - B
    // B = B + A
    // A = B - A
    public static void numberSwapperArithmetic(int[] numbers) {
        numbers[0] = numbers[0] - numbers[1];
        numbers[1] = numbers[1] + numbers[0];
        numbers[0] = numbers[1] - numbers[0];
    }

    public static void main(String[] args) {
        int[] numbers1 = {3, 5};
        numberSwapperXor(numbers1);
        System.out.println("Swapped numbers: " + numbers1[0] + " " + numbers1[1]);
        System.out.printf("%16s %3s","Expected: ", "5 3\n");

        int[] numbers2 = {33432, -123123};
        numberSwapperXor(numbers2);
        System.out.println("\nSwapped numbers: " + numbers2[0] + " " + numbers2[1]);
        System.out.printf("%16s %12s","Expected: ", "-123123 33432\n");

        numberSwapperArithmetic(numbers1);
        System.out.println("\nSwapped numbers: " + numbers1[0] + " " + numbers1[1]);
        System.out.printf("%16s %3s","Expected: ", "3 5\n");

        numberSwapperArithmetic(numbers2);
        System.out.println("\nSwapped numbers: " + numbers2[0] + " " + numbers2[1]);
        System.out.printf("%16s %12s","Expected: ", "33432 -123123\n");
    }
}
