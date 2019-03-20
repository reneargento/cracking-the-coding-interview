package chapter1_arrays_strings;

/**
 * Created by Rene Argento on 17/03/19.
 */
public class Exercise3_URLify {

    // O(n) runtime, where n is the length of the string
    // O(1) space
    public static void urlify(char[] string, int length) {
        int spaces = 0;

        for (int i = 0; i < length; i++) {
            if (string[i] == ' ') {
                spaces++;
            }
        }

        int endIndex = length + (spaces * 2) - 1;

        for (int i = length - 1; i >= 0; i--) {
            if (string[i] != ' ') {
                string[endIndex--] = string[i];
            } else {
                string[endIndex--] = '0';
                string[endIndex--] = '2';
                string[endIndex--] = '%';
            }
        }
    }

    public static void main(String[] args) {
        char[] string = "Mr John Smith    ".toCharArray();
        int length = 13;

        urlify(string, length);

        System.out.println("URLyfied string: " + new String(string));
        System.out.println("Expected: Mr%20John%20Smith");
    }

}