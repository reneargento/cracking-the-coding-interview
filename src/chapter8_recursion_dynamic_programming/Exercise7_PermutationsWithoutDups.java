package chapter8_recursion_dynamic_programming;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 23/09/19.
 */
public class Exercise7_PermutationsWithoutDups {

    // O(n^2 * n!) runtime, where n is the string size
    // O(n!) space
    public static List<String> permutationsWithoutDups(String string) {
        if (string == null) return null;

        List<String> permutations = new ArrayList<>();
        permutationsWithoutDups("", string, permutations);
        return permutations;
    }

    private static void permutationsWithoutDups(String prefix, String remainder, List<String> permutations) {
        // Base case
        if (remainder.length() == 0) {
            permutations.add(prefix);
            return;
        }

        for (int i = 0; i < remainder.length(); i++) {
            String start = remainder.substring(0, i);
            String end = remainder.substring(i + 1);
            char removedChar = remainder.charAt(i);

            permutationsWithoutDups(prefix + removedChar, start + end, permutations);
        }
    }

    // O(n^2 * n!) runtime, where n is the string size
    // O(n!) space
    public static List<String> permutationsWithoutDups2(String string) {
        if (string == null) {
            return null;
        }

        List<String> permutations = new ArrayList<>();

        // Base case
        if (string.length() == 0) {
            permutations.add(""); // Add initial, empty string
            return permutations;
        }

        char firstChar = string.charAt(0);
        String remainder = string.substring(1); // Remove the first char
        List<String> parcial = permutationsWithoutDups2(remainder);

        for (String parcialPermutation : parcial) {
            for (int i = 0; i <= parcialPermutation.length(); i++) {
                String start = parcialPermutation.substring(0, i);
                String end = parcialPermutation.substring(i);
                permutations.add(start + firstChar + end);
            }
        }

        return permutations;
    }

    public static void main(String[] args) {
        String string = "abc";
        List<String> permutations = permutationsWithoutDups(string);

        System.out.println("Permutations");
        for (String permutation : permutations) {
            System.out.println(permutation);
        }

        System.out.println("\nExpected");
        System.out.println("abc\n" +
                "acb\n" +
                "bac\n" +
                "bca\n" +
                "cab\n" +
                "cba");
    }

}
