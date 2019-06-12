package chapter5_bit_manipulation;

/**
 * Created by Rene Argento on 09/06/19.
 */
public class Exercise2_BinaryToString {

    // O(1) runtime
    // O(1) space
    public static String getBinary(double number) {
        if (number <= 0 || number >= 1) {
            return "ERROR";
        }

        StringBuilder binary = new StringBuilder(".");

        while (number > 0) {
            if (binary.length() >= 32) {
                return "ERROR";
            }

            double doubleNumber = number * 2;
            if (doubleNumber >= 1) {
                binary.append("1");
                number = doubleNumber - 1;
            } else {
                binary.append("0");
                number = doubleNumber;
            }
        }

        return binary.toString();
    }

    public static void main(String[] args) {
        for (int i = 1; i < 1000; i++) {
            double number = i / 1000.0;
            String binary = getBinary(number);

            if (!binary.equals("ERROR")) {
                System.out.println("Number: " + number + " Binary: " + binary);
            }
        }
    }

}
