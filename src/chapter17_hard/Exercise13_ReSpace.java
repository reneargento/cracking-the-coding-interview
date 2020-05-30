package chapter17_hard;

import java.util.*;

/**
 * Created by Rene Argento on 13/05/20.
 */
public class Exercise13_ReSpace {

    private static class ParseResult {
        String parsedSubstring;
        int invalidCharacters;

        public ParseResult(String parsedSubstring, int invalidCharacters) {
            this.parsedSubstring = parsedSubstring;
            this.invalidCharacters = invalidCharacters;
        }
    }

    // O(n^2) runtime, where n is the length of the document
    // O(n) space
    public static String reSpace(List<String> dictionary, String document) {
        if (dictionary == null || document == null || document.isEmpty()) {
            return null;
        }

        Set<String> dictionarySet = new HashSet<>(dictionary);
        ParseResult[] memo = new ParseResult[document.length()];
        ParseResult result = reSpace(dictionarySet, document, 0, memo);
        return groupUnrecognizedCharacters(dictionarySet, result.parsedSubstring);
    }

    private static ParseResult reSpace(Set<String> dictionary, String document, int startIndex, ParseResult[] memo) {
        if (startIndex == document.length()) {
            return new ParseResult("", 0);
        }
        if (memo[startIndex] != null) {
            return memo[startIndex];
        }

        int bestInvalid = Integer.MAX_VALUE;
        String bestParsedSubstring = "";
        StringBuilder partial = new StringBuilder();
        int index = startIndex;

        while (index < document.length()) {
            char currentChar = document.charAt(index);
            partial.append(currentChar);

            int invalidFirstPart = dictionary.contains(partial.toString()) ? 0 : partial.length();
            if (invalidFirstPart < bestInvalid) { // Short circuit
                // Recurse, putting a space after this character.
                ParseResult secondPart = reSpace(dictionary, document, index + 1, memo);
                if (invalidFirstPart + secondPart.invalidCharacters < bestInvalid) {
                    bestInvalid = invalidFirstPart + secondPart.invalidCharacters;
                    bestParsedSubstring = partial.toString() + " " + secondPart.parsedSubstring;

                    // Short circuit
                    if (bestInvalid == 0) {
                        break;
                    }
                }
            }
            index++;
        }
        memo[startIndex] = new ParseResult(bestParsedSubstring, bestInvalid);
        return memo[startIndex];
    }

    private static String groupUnrecognizedCharacters(Set<String> dictionary, String parsedDocument) {
        StringBuilder result = new StringBuilder();
        String[] words = parsedDocument.split(" ");

        for (int i = 0; i < words.length; i++) {
            result.append(words[i]);

            if (i < words.length - 1) {
                boolean isCurrentWordInDictionary = dictionary.contains(words[i]);
                boolean isNextWordInDictionary = dictionary.contains(words[i + 1]);

                if ((isCurrentWordInDictionary && isNextWordInDictionary)
                        || (isCurrentWordInDictionary && !isNextWordInDictionary)
                        || (!isCurrentWordInDictionary && isNextWordInDictionary)) {
                    result.append(" ");
                }
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String document1 = "jesslookedjustliketimherbrother";
        List<String> dictionary1 = new ArrayList<>();
        dictionary1.add("looked");
        dictionary1.add("just");
        dictionary1.add("like");
        dictionary1.add("her");
        dictionary1.add("brother");

        String parsedDocument1 = reSpace(dictionary1, document1);
        System.out.println("Parsed document: " + parsedDocument1);
        System.out.println("Expected: jess looked just like tim her brother");

        String document2 = "novalidwords";
        String parsedDocument2 = reSpace(dictionary1, document2);
        System.out.println("\nParsed document: " + parsedDocument2);
        System.out.println("Expected: novalidwords");

        String document3 = "thisismikesfavoritefood";
        List<String> dictionary2 = new ArrayList<>();
        dictionary2.add("this");
        dictionary2.add("is");
        dictionary2.add("mikes");
        dictionary2.add("favorite");
        dictionary2.add("food");

        String parsedDocument3 = reSpace(dictionary2, document3);
        System.out.println("\nParsed document: " + parsedDocument3);
        System.out.println("Expected: this is mikes favorite food");
    }

}
