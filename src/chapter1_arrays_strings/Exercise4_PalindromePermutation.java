package chapter1_arrays_strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 17/03/19.
 */
public class Exercise4_PalindromePermutation {

    // O(n) runtime, where n is the length of the string
    // O(n) space
    public static boolean palindromePermutation(String string) {
        String lowerCaseString = string.toLowerCase();
        Map<Character, Integer> characterFrequencies = countCharactersExceptSpaces(lowerCaseString);

        int numberOfOddFrequencies = 0;

        for (char character : characterFrequencies.keySet()) {
            int frequency = characterFrequencies.get(character);

            if (frequency % 2 != 0) {
                numberOfOddFrequencies++;
                if (numberOfOddFrequencies > 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private static Map<Character, Integer> countCharactersExceptSpaces(String string) {
        Map<Character, Integer> characterFrequencies = new HashMap<>();

        for (int i = 0; i < string.length(); i++) {
            char currentChar = string.charAt(i);

            if (currentChar == ' ') {
                continue;
            }

            int count = characterFrequencies.getOrDefault(currentChar, 0);
            characterFrequencies.put(currentChar, count + 1);
        }

        return characterFrequencies;
    }

    public static void main(String[] args) {
        String string1 = "Tact Coa";
        String string2 = "Tact Coa ";
        String string3 = "Tact OCoa ";
        String string4 = "Tact boa";

        System.out.println("Is palindrome permutation: " + palindromePermutation(string1) + " Expected: " + true);
        System.out.println("Is palindrome permutation: " + palindromePermutation(string2) + " Expected: " + true);
        System.out.println("Is palindrome permutation: " + palindromePermutation(string3) + " Expected: " + true);
        System.out.println("Is palindrome permutation: " + palindromePermutation(string4) + " Expected: " + false);
    }

}
