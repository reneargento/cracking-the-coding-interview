package chapter1_arrays_strings;

import java.util.Arrays;

/**
 * Created by Rene Argento on 17/03/19.
 */
public class Exercise2_CheckPermutation {

    // O(s1 lg s1 + s2 lg s2) runtime
    // O(s1 + s2) space
    // where s1 is the length of the first string and s2 is the length of the second string
    public static boolean checkPermutation(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }

        char[] string1Chars = string1.toCharArray();
        char[] string2Chars = string2.toCharArray();

        Arrays.sort(string1Chars);
        Arrays.sort(string2Chars);

        String sortedString1 = new String(string1Chars);
        String sortedString2 = new String(string2Chars);
        return sortedString1.equals(sortedString2);
    }

    public static void main(String[] args) {
        String string1 = "rene";
        String string2 = "ener";
        String string3 = "ennr";
        String string4 = "abcdefg";
        String string5 = "gfedcba";

        System.out.println("Is permutation: " + checkPermutation(string1, string2) + " Expected: " + true);
        System.out.println("Is permutation: " + checkPermutation(string1, string3) + " Expected: " + false);
        System.out.println("Is permutation: " + checkPermutation(string4, string5) + " Expected: " + true);
        System.out.println("Is permutation: " + checkPermutation(string1, string5) + " Expected: " + false);
    }

}