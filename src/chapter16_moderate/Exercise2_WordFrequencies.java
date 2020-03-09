package chapter16_moderate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 06/03/20.
 */
public class Exercise2_WordFrequencies {

    // O(n) runtime, where n is the number of words
    // O(1) space
    public static int findFrequency(String[] book, String word) {
        int frequency = 0;
        word = word.trim().toLowerCase();

        for (String string : book) {
            if (word.equals(string.trim().toLowerCase())) {
                frequency++;
            }
        }
        return frequency;
    }

    // O(n) runtime, where n is the number of words
    // O(n) space
    public static Map<String, Integer> setupFrequencyMap(String[] book) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String string : book) {
            string = string.trim().toLowerCase();
            int count = frequencyMap.getOrDefault(string, 0);
            frequencyMap.put(string, count + 1);
        }

        return frequencyMap;
    }

    // O(1) runtime
    // O(1) space
    public static int findFrequencyRepeated(Map<String, Integer> frequencyMap, String query) {
        if (frequencyMap == null || query == null) {
            return -1;
        }
        query = query.trim().toLowerCase();
        return frequencyMap.getOrDefault(query, 0);
    }

    public static void main(String[] args) {
        String[] book = {"Algorithms", "Rene", "HashMap", "Algorithms", "Data Structures", "Algorithms", "Rene"};
        int frequency1 = findFrequency(book, "Rene");
        System.out.println("Rene frequency: " + frequency1 + " Expected: 2\n");

        System.out.println("Repeated tests");
        Map<String, Integer> frequencyMap = setupFrequencyMap(book);
        int frequency2 = findFrequencyRepeated(frequencyMap, "HashMap");
        System.out.println("HashMap frequency: " + frequency2 + " Expected: 1");

        int frequency3 = findFrequencyRepeated(frequencyMap, "Rene");
        System.out.println("Rene frequency: " + frequency3 + " Expected: 2");

        int frequency4 = findFrequencyRepeated(frequencyMap, "Algorithms");
        System.out.println("Algorithms frequency: " + frequency4 + " Expected: 3");
    }
}
