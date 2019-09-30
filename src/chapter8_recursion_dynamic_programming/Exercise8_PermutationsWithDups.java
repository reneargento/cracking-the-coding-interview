package chapter8_recursion_dynamic_programming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rene Argento on 26/09/19.
 */
public class Exercise8_PermutationsWithDups {

    // O(n^2 * n!) runtime, where n is the string size
    // O(n!) space
    public static List<String> permutationsWithDups(String string) {
        List<String> permutations = new ArrayList<>();
        Map<Character, Integer> frequencyMap = buildFrequencyMap(string);
        permutationsWithDups("", string.length(), frequencyMap, permutations);
        return permutations;
    }

    private static Map<Character, Integer> buildFrequencyMap(String string) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char character : string.toCharArray()) {
            int frequency = frequencyMap.getOrDefault(character, 0);
            frequency++;
            frequencyMap.put(character, frequency);
        }

        return frequencyMap;
    }

    public static void permutationsWithDups(String prefix, int remaining, Map<Character, Integer> frequencyMap,
                                            List<String> permutations) {
        // Base case
        if (remaining == 0) {
            permutations.add(prefix);
            return;
        }

        for (Character character : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(character);
            if (frequency > 0) {
                frequencyMap.put(character, frequency - 1);
                permutationsWithDups(prefix + character, remaining - 1, frequencyMap, permutations);
                frequencyMap.put(character, frequency);
            }
        }
    }

    public static void main(String[] args) {
        String string = "rene";
        List<String> permutations = permutationsWithDups(string);

        System.out.println("Permutations");
        for (String permutation : permutations) {
            System.out.println(permutation);
        }

        System.out.println("\nExpected");
        System.out.println("reen\n" +
                "rene\n" +
                "rnee\n" +
                "eren\n" +
                "erne\n" +
                "eern\n" +
                "eenr\n" +
                "enre\n" +
                "ener\n" +
                "nree\n" +
                "nere\n" +
                "neer");
    }

}
