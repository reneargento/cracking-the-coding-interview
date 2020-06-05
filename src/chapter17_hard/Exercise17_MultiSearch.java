package chapter17_hard;

import util.HashMapList;

import java.util.*;

/**
 * Created by Rene Argento on 19/05/20.
 */
public class Exercise17_MultiSearch {

    public static class Trie {

        private class Node {
            private Map<Character, Node> children;
            private char value;
            private boolean isEndOfWord;

            public Node(char value) {
                children = new HashMap<>();
                this.value = value;
            }
        }

        private Node root;

        Trie() {
            root = new Node('*');
        }

        // O(l) runtime, where l is the length of the string
        public void insert(String string) {
            insert(root, string, 0);
        }

        private void insert(Node node, String string, int index) {
            if (index == string.length()) {
                node.isEndOfWord = true;
                return;
            }

            char character = string.charAt(index);
            Node nextNode = node.children.get(character);

            if (nextNode == null) {
                nextNode = new Node(character);
                node.children.put(character, nextNode);
            }
            insert(nextNode, string, index + 1);
        }

        // Searches the locations of all strings in the trie inside string
        // O(l^2) runtime, where l is the length of the string
        // O(l^2) space
        public HashMapList<String, Integer> multiSearch(String string) {
            HashMapList<String, Integer> stringLocations = new HashMapList<>();
            for (int i = 0; i < string.length(); i++) {
                multiSearch(root, string, i, new StringBuilder(), stringLocations);
            }
            return stringLocations;
        }

        // Search mainString starting from index
        private void multiSearch(Node node, String mainString, int index, StringBuilder currentString,
                                 HashMapList<String, Integer> stringLocations) {
            if (node.isEndOfWord) {
                String stringValue = currentString.toString();
                int startIndex = index - stringValue.length();
                stringLocations.put(stringValue, startIndex);
            }
            if (index == mainString.length()) {
                return;
            }

            char character = mainString.charAt(index);
            Node nextNode = node.children.get(character);
            if (nextNode == null) {
                return;
            }

            multiSearch(nextNode, mainString, index + 1, currentString.append(character), stringLocations);
        }
    }

    // O(k * t + k * l) runtime, where k is the length of the longest string in the strings[] array, t is the number of
    // strings in the strings[] array and l is the length of mainString
    // O(k * l) space
    public static HashMapList<String, Integer> searchAllStrings(String mainString, String[] strings) {
        int maxLength = mainString.length();
        Trie trie = createTrieFromStrings(strings, maxLength);
        return trie.multiSearch(mainString);
    }

    private static Trie createTrieFromStrings(String[] strings, int maxLength) {
        Trie trie = new Trie();
        for (String string : strings) {
            if (string.length() <= maxLength) {
                trie.insert(string);
            }
        }
        return trie;
    }

    public static void main(String[] args) {
        String mainString1 = "renegoalgorithmsreabcabc";
        String[] strings1 = {"algorithm", "rene", "algorithms", "abc", "trie", "tree", "go"};
        HashMapList<String, Integer> stringLocations1 = searchAllStrings(mainString1, strings1);

        printStringLocations(strings1, stringLocations1);
        System.out.println("\nExpected:");
        System.out.println("algorithm: 6\nrene: 0\nalgorithms: 6\nabc: 18, 21\ntrie: not in the string");
        System.out.println("tree: not in the string\ngo: 4, 8");

        String mainString2 = "mississippi";
        String[] strings2 = {"is", "ppi", "hi", "sis", "i", "ssippi"};
        HashMapList<String, Integer> stringLocations2 = searchAllStrings(mainString2, strings2);

        System.out.println();
        printStringLocations(strings2, stringLocations2);
        System.out.println("\nExpected:");
        System.out.println("is: 1, 4\nppi: 8\nhi: not in the string\nsis: 3\ni: 1, 4, 7, 10\nssippi: 5");
    }

    private static void printStringLocations(String[] strings, HashMapList<String, Integer> stringLocations) {
        System.out.println("String locations:");

        for (String string : strings) {
            System.out.print(string + ": ");
            List<Integer> locations = stringLocations.get(string);
            if (locations != null) {
                StringJoiner locationsDescription = new StringJoiner(", ");
                for (int location : locations) {
                    locationsDescription.add(String.valueOf(location));
                }
                System.out.println(locationsDescription);
            } else {
                System.out.println("not in the string");
            }
        }
    }
}
