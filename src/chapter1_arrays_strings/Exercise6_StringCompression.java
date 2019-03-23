package chapter1_arrays_strings;

/**
 * Created by Rene Argento on 17/03/19.
 */
public class Exercise6_StringCompression {

    // O(n) runtime, where n is the length of the string
    // O(n) space
    public static String compressString(String string) {
        if (string == null || string.length() == 0) {
            return "";
        }

        StringBuilder compressedString = new StringBuilder();

        int count = 0;

        for (int i = 0; i < string.length(); i++) {
            count++;
            char currentChar = string.charAt(i);

            if (i == string.length() - 1 || currentChar != string.charAt(i + 1)) {
                compressedString.append(currentChar).append(count);
                count = 0;
            }
        }

        if (compressedString.length() < string.length()) {
            return compressedString.toString();
        } else {
            return string;
        }
    }

    public static void main(String[] args) {
        String string1 = "aabcccccaaa";
        System.out.println("Compressed string: " + compressString(string1) + " Expected: a2b1c5a3");

        String string2 = "aabbccddee";
        System.out.println("Compressed string: " + compressString(string2) + " Expected: aabbccddee");

        String string3 = "aaaaaaaa";
        System.out.println("Compressed string: " + compressString(string3) + " Expected: a8");

        String string4 = "";
        System.out.println("Compressed string: " + compressString(string4) + " Expected: ");

        System.out.println("Compressed string: " + compressString(null) + " Expected: ");
    }

}