package chapter17_hard;

/**
 * Created by Rene Argento on 25/04/20.
 */
public class Exercise1_AddWithoutPlus {

    // O(b) runtime, where b is the number of bits in the numbers
    // O(1) space
    public static long add(int number1, int number2) {
        long number1Long = number1;
        long number2Long = number2;

        while (number2Long != 0) {
            long sum = number1Long ^ number2Long; // Add without carrying
            long carryOvers = (number1Long & number2Long) << 1; // Carry without adding

            number1Long = sum;
            number2Long = carryOvers;
        }
        return number1Long;
    }

    public static void main(String[] args) {
        int number1 = 2;
        int number2 = 5;
        long result1 = add(number1, number2);
        System.out.println("Result: " + result1 + " Expected: 7");

        int number3 = 0;
        int number4 = 10;
        long result2 = add(number3, number4);
        System.out.println("Result: " + result2 + " Expected: 10");

        int number5 = Integer.MAX_VALUE;
        long result3 = add(number4, number5);
        System.out.println("Result: " + result3 + " Expected: 2147483657");

        int number6 = 7;
        long result4 = add(number6, number6);
        System.out.println("Result: " + result4 + " Expected: 14");

        int number7 = -3;
        long result5 = add(number2, number7);
        System.out.println("Result: " + result5 + " Expected: 2");

        int number8 = -12;
        long result6 = add(number7, number8);
        System.out.println("Result: " + result6 + " Expected: -15");
    }

}
