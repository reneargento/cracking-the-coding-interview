package chapter1_arrays_strings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rene Argento on 15/03/19.
 */
public class Exercise1_IsUnique {

    // This version uses an additional data structure
    // O(n) time and O(n) space where n is the number of characters
    public static boolean areCharactersUnique1(String string) {
        Set<Character> characters = new HashSet<>();

        for (char c : string.toCharArray()) {
            if (characters.contains(c)) {
                return false;
            }

            characters.add(c);
        }

        return true;
    }

    // This version does not use an additional data structure
    // O(n lg n) runtime, where n is the number of characters
    // O(1) space
    public static boolean areCharactersUnique2(String string) {
        char[] chars = string.toCharArray();
        Arrays.sort(chars);

        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i + 1]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String string1 = "abcda";
        System.out.println("Unique: " + areCharactersUnique1(string1) + " Expected: false");
        System.out.println("Unique: " + areCharactersUnique2(string1) + " Expected: false");

        String string2 = "abcdefgg";
        System.out.println("Unique: " + areCharactersUnique1(string2) + " Expected: false");
        System.out.println("Unique: " + areCharactersUnique2(string2) + " Expected: false");

        String string3 = "abcdefgz";
        System.out.println("Unique: " + areCharactersUnique1(string3) + " Expected: true");
        System.out.println("Unique: " + areCharactersUnique2(string3) + " Expected: true");
    }

}
