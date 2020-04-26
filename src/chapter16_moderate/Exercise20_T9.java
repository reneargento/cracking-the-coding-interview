package chapter16_moderate;

import util.HashMapList;

import java.util.*;

/**
 * Created by Rene Argento on 11/04/20.
 */
public class Exercise20_T9 {

    public static class WordSearcher {
        private HashMapList<String, String> digitsToWordsMap;
        private char[][] digitToLetters = {
                null, null, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m', 'n', 'o'},
                {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}
        };

        WordSearcher(List<String> validWords) {
            digitsToWordsMap = computeDigitsToWordsMap(validWords);
        }

        // O(d) runtime, where d is the number of digits
        public List<String> getWords(String digits) {
            if (!digitsToWordsMap.containsKey(digits)) {
                return new ArrayList<>();
            }
            return digitsToWordsMap.get(digits);
        }

        // O(n * ml) runtime, where n is the number of strings and ml is the maximum length among the strings
        // O(n * ml) space
        private HashMapList<String, String> computeDigitsToWordsMap(List<String> words) {
            HashMapList<String, String> digitsToWordsMap = new HashMapList<>();
            Map<Character, Integer> letterToDigitMap = createLetterToDigitMap();

            for (String word : words) {
                String digits = convertWordToDigits(word, letterToDigitMap);
                digitsToWordsMap.put(digits, word);
            }
            return digitsToWordsMap;
        }

        // Convert mapping of digit -> letters into letter -> digit
        private Map<Character, Integer> createLetterToDigitMap() {
            Map<Character, Integer> letterToDigitMap = new HashMap<>();

            for (int i = 0; i < digitToLetters.length; i++) {
                char[] letters = digitToLetters[i];
                if (letters != null) {
                    for (char letter : letters) {
                        letterToDigitMap.put(letter, i);
                    }
                }
            }
            return letterToDigitMap;
        }

        private String convertWordToDigits(String word, Map<Character, Integer> letterToDigitMap) {
            StringBuilder stringBuilder = new StringBuilder();
            for (char letter : word.toCharArray()) {
                if (letterToDigitMap.containsKey(letter)) {
                    int digit = letterToDigitMap.get(letter);
                    stringBuilder.append(digit);
                }
            }
            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        List<String> validWords = new ArrayList<>();
        validWords.add("tree");
        validWords.add("used");
        validWords.add("rene");
        validWords.add("trie");
        validWords.add("trif");
        validWords.add("algorithms");

        WordSearcher wordSearcher = new WordSearcher(validWords);

        List<String> wordList1 = wordSearcher.getWords("8733");
        String words1 = getWordsDescription(wordList1);
        System.out.println("Words: " + words1 + " Expected: tree, used");

        List<String> wordList2 = wordSearcher.getWords("8743");
        String words2 = getWordsDescription(wordList2);
        System.out.println("Words: " + words2 + " Expected: trie, trif");

        List<String> wordList3 = wordSearcher.getWords("7363");
        String words3 = getWordsDescription(wordList3);
        System.out.println("Words: " + words3 + " Expected: rene");

        List<String> wordList4 = wordSearcher.getWords("2546748467");
        String words4 = getWordsDescription(wordList4);
        System.out.println("Words: " + words4 + " Expected: algorithms");

        List<String> wordList5 = wordSearcher.getWords("25460741840671");
        String words5 = getWordsDescription(wordList5);
        System.out.println("Words: " + words5 + " Expected: ");
    }

    private static String getWordsDescription(List<String> wordList) {
        StringJoiner words = new StringJoiner(", ");
        for (String word : wordList) {
            words.add(word);
        }
        return words.toString();
    }

}
