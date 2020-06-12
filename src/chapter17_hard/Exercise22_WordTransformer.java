package chapter17_hard;

import util.HashMapList;

import java.util.*;

/**
 * Created by Rene Argento on 05/06/20.
 */
public class Exercise22_WordTransformer {

    private static class PathNode {
        private String word;
        private PathNode previous;

        PathNode(String word, PathNode previous) {
            this.word = word;
            this.previous = previous;
        }

        public String getWord() {
            return word;
        }

        public LinkedList<String> collapse(boolean startsWithRoot) {
            LinkedList<String> path = new LinkedList<>();
            PathNode node = this;

            while (node != null) {
                if (startsWithRoot) {
                    path.addLast(node.word);
                } else {
                    path.addFirst(node.word);
                }
                node = node.previous;
            }
            return path;
        }
    }

    private static class BFSData {
        private Map<String, PathNode> visited;
        private Queue<PathNode> toVisit;

        public BFSData(String sourceWord) {
            visited = new HashMap<>();
            toVisit = new LinkedList<>();

            PathNode sourceNode = new PathNode(sourceWord, null);
            visited.put(sourceWord, sourceNode);
            toVisit.offer(sourceNode);
        }

        public boolean isFinished() {
            return toVisit.isEmpty();
        }
    }

    // O(n * l^2 + e^(d/2)) runtime, where:
    // n is the number of words in the dictionary
    // l is the maximum length of a word in the dictionary
    // e is the maximum number of linked words (words that are one edit away) to a given word
    // d is the distance between word1 and word2
    // O(n^2) space
    public static String transformWord(List<String> dictionary, String word1, String word2) {
        if (dictionary == null || word1 == null || word2 == null || word1.length() != word2.length()) {
            return null;
        }

        HashMapList<String, String> wildcardToWordsMap = createWildcardToWordsMap(dictionary);
        LinkedList<String> wordPath = getWordPath(wildcardToWordsMap, word1, word2);

        if (wordPath == null) {
            return "";
        }
        return getWordPathDescription(wordPath);
    }

    // O(n * l^2) runtime, where:
    // n is the number of words in the dictionary
    // l is the maximum length of a word in the dictionary
    private static HashMapList<String, String> createWildcardToWordsMap(List<String> dictionary) {
        HashMapList<String, String> wildcardToWordsMap = new HashMapList<>();

        for (String word : dictionary) {
            List<String> wildcards = getWildcards(word);
            for (String wildcard : wildcards) {
                wildcardToWordsMap.put(wildcard, word);
            }
        }
        return wildcardToWordsMap;
    }

    private static List<String> getWildcards(String word) {
        List<String> wildcards = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            String wildcard = word.substring(0, i) + "*" + word.substring(i + 1);
            wildcards.add(wildcard);
        }
        return wildcards;
    }

    private static List<String> getLinkedWords(String word, HashMapList<String, String> wildcardToWordsMap) {
        List<String> linkedWords = new ArrayList<>();

        List<String> wildcards = getWildcards(word);
        for (String wildcard : wildcards) {
            List<String> wildcardLinkedWords = wildcardToWordsMap.get(wildcard);

            for (String linkedWord : wildcardLinkedWords) {
                if (!word.equals(linkedWord)) {
                    linkedWords.add(linkedWord);
                }
            }
        }
        return linkedWords;
    }

    // O(e^(d/2)) runtime, where:
    // e is the maximum number of linked words (words that are one edit away) to a given word
    // d is the distance between word1 and word2
    private static LinkedList<String> getWordPath(HashMapList<String, String> wildcardToWordsMap, String word1,
                                                  String word2) {
        BFSData bfsSource = new BFSData(word1);
        BFSData bfsDestination = new BFSData(word2);

        while (!bfsSource.isFinished() && !bfsDestination.isFinished()) {
            // Search from source
            String intersection = searchLevel(wildcardToWordsMap, bfsSource, bfsDestination);
            if (intersection != null) {
                return mergePaths(bfsSource, bfsDestination, intersection);
            }

            // Search from destination
            intersection = searchLevel(wildcardToWordsMap, bfsDestination, bfsSource);
            if (intersection != null) {
                return mergePaths(bfsSource, bfsDestination, intersection);
            }
        }
        return null;
    }

    // Search one level and return intersection, if any
    private static String searchLevel(HashMapList<String, String> wildcardToWordsMap, BFSData bfsPrimary,
                                      BFSData bfsSecondary) {
        // Visit only one level
        int count = bfsPrimary.toVisit.size();

        for (int i = 0; i < count; i++) {
            PathNode pathNode = bfsPrimary.toVisit.poll();
            String word = pathNode.getWord();

            if (bfsSecondary.visited.containsKey(word)) {
                return word;
            }

            List<String> linkedWords = getLinkedWords(word, wildcardToWordsMap);
            for (String linkedWord : linkedWords) {
                if (!bfsPrimary.visited.containsKey(linkedWord)) {
                    PathNode newNode = new PathNode(linkedWord, pathNode);
                    bfsPrimary.toVisit.offer(newNode);
                    bfsPrimary.visited.put(linkedWord, newNode);
                }
            }
        }
        return null;
    }

    private static LinkedList<String> mergePaths(BFSData bfsSource, BFSData bfsDestination, String intersection) {
        PathNode nodeInSourcePath = bfsSource.visited.get(intersection);
        PathNode nodeInDestinationPath = bfsDestination.visited.get(intersection);

        LinkedList<String> pathFromSource = nodeInSourcePath.collapse(false);
        LinkedList<String> pathToDestination = nodeInDestinationPath.collapse(true);
        pathToDestination.removeFirst(); // remove intersection

        pathFromSource.addAll(pathToDestination);
        return pathFromSource;
    }

    private static String getWordPathDescription(LinkedList<String> wordPath) {
        StringJoiner path = new StringJoiner(" -> ");
        for (String word : wordPath) {
            path.add(word);
        }
        return path.toString();
    }

    public static void main(String[] args) {
        List<String> dictionary = new ArrayList<>();
        dictionary.add("RENE");
        dictionary.add("LIKE");
        dictionary.add("DAMP");
        dictionary.add("ALGORITHMS");
        dictionary.add("LIMP");
        dictionary.add("LIME");
        dictionary.add("WORDS");
        dictionary.add("LAMP");
        dictionary.add("DUMP");
        dictionary.add("LAKE");
        dictionary.add("LUKE");
        dictionary.add("RAMP");
        dictionary.add("RUMP");

        String path = transformWord(dictionary, "DAMP", "LIKE");
        System.out.println("Word path: " + path);
        System.out.println("Expected: DAMP -> LAMP -> LIMP -> LIME -> LIKE");
    }

}
