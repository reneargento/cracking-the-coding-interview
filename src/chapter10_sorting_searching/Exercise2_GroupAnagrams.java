package chapter10_sorting_searching;

import java.util.*;

/**
 * Created by Rene Argento on 03/11/19.
 */
public class Exercise2_GroupAnagrams {

    // O(n * (s lg s)) runtime, where n is the number of strings and s is the maximum length of a string
    // O(max(n, s lg s)) space
    public static void groupAnagrams(String[] strings) {
        Map<String, List<String>> anagramMap = new HashMap<>();

        for (String string : strings) {
            String key = sortChars(string);
            List<String> anagrams = anagramMap.getOrDefault(key, new ArrayList<>());
            anagrams.add(string);
            anagramMap.put(key, anagrams);
        }

        int index = 0;
        for (String key : anagramMap.keySet()) {
            List<String> anagrams = anagramMap.get(key);
            for (String anagram : anagrams) {
                strings[index] = anagram;
                index++;
            }
        }
    }

    private static String sortChars(String string) {
        char[] chars = string.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static void main(String[] args) {
        String[] strings = {
                "rene",
                "algorithms",
                "sort",
                "ener",
                "ritalgohms",
                "tros",
                "nere",
                "rithmsalgo",
                "rtso"
        };

        groupAnagrams(strings);

        StringJoiner stringJoiner = new StringJoiner(" ");
        for (String string : strings) {
            stringJoiner.add(string);
        }
        System.out.println("Grouped anagrams: " + stringJoiner.toString());
        System.out.println("Expected: sort tros rtso algorithms ritalgohms rithmsalgo rene ener nere");
    }
}