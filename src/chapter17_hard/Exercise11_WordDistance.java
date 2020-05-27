package chapter17_hard;

import util.HashMapList;

import java.util.List;

/**
 * Created by Rene Argento on 09/05/20.
 */
public class Exercise11_WordDistance {

    public static class LocationPair {
        int location1;
        int location2;

        public LocationPair(int location1, int location2) {
            setLocations(location1, location2);
        }

        private void setLocations(int location1, int location2) {
            this.location1 = location1;
            this.location2 = location2;
        }

        public int distance() {
            return Math.abs(location1 - location2);
        }

        public boolean isValid() {
            return location1 >= 0 && location2 >= 0;
        }

        public void updateWithMinimumDistance(LocationPair otherLocationPair) {
            if (!isValid() || otherLocationPair.distance() < distance()) {
                setLocations(otherLocationPair.location1, otherLocationPair.location2);
            }
        }

        @Override
        public String toString() {
            return location1 + ", " + location2 + " distance: " + distance();
        }
    }

    // O(n) runtime, where n is the number of words in the array
    // O(1) space
    public static LocationPair wordDistanceOneTime(String[] words, String word1, String word2) {
        LocationPair bestLocationPair = new LocationPair(-1, -1);
        LocationPair currentLocationPair = new LocationPair(-1, -1);

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                currentLocationPair.location1 = i;
                bestLocationPair.updateWithMinimumDistance(currentLocationPair);
            } else if (words[i].equals(word2)) {
                currentLocationPair.location2 = i;
                bestLocationPair.updateWithMinimumDistance(currentLocationPair);
            }
        }
        return bestLocationPair.isValid() ? bestLocationPair : null;
    }

    private static HashMapList<String, Integer> wordToLocations;

    // O(n) runtime, where n is the number of words in the array
    // O(n) space
    public static void wordDistanceManyTimesInit(String[] words) {
        wordToLocations = new HashMapList<>();

        for (int i = 0; i < words.length; i++) {
            wordToLocations.put(words[i], i);
        }
    }

    // O(a + b) runtime, where a is the number of occurrences of word1 and b is the number of occurrences of word2
    // O(1) space
    public static LocationPair wordDistanceManyTimesQuery(String word1, String word2) {
        if (wordToLocations == null || word1 == null || word2 == null) {
            return null;
        }

        List<Integer> word1Locations = wordToLocations.get(word1);
        List<Integer> word2Locations = wordToLocations.get(word2);

        if (word1Locations == null || word2Locations == null || word1Locations.isEmpty() || word2Locations.isEmpty()) {
            return null;
        }

        LocationPair bestLocationPair = new LocationPair(word1Locations.get(0), word2Locations.get(0));
        LocationPair currentLocationPair = new LocationPair(word1Locations.get(0), word2Locations.get(0));

        int indexWord1 = 0;
        int indexWord2 = 0;

        while (indexWord1 < word1Locations.size() && indexWord2 < word2Locations.size()) {
            int location1 = word1Locations.get(indexWord1);
            int location2 = word2Locations.get(indexWord2);

            currentLocationPair.setLocations(location1, location2);
            bestLocationPair.updateWithMinimumDistance(currentLocationPair);

            if (location1 < location2) {
                indexWord1++;
            } else {
                indexWord2++;
            }
        }
        return bestLocationPair;
    }

    public static void main(String[] args) {
        String[] words = {"Graph", "Algorithms", "Queue", "Deque", "List", "Rene", "Complexity", "Tree", "Algorithms",
                "Stack", "Queue"};
        wordDistanceManyTimesInit(words);

        LocationPair locationPair1 = wordDistanceOneTime(words, "Algorithms", "Rene");
        LocationPair locationPair2 = wordDistanceManyTimesQuery("Algorithms", "Rene");
        System.out.println("Location pair (one time): " + locationPair1 + " Expected: 8, 5 distance: 3");
        System.out.println("Location pair (many times): " + locationPair2 + " Expected: 8, 5 distance: 3");

        LocationPair locationPair3 = wordDistanceOneTime(words, "Algorithms", "Dynamic");
        LocationPair locationPair4 = wordDistanceManyTimesQuery("Algorithms", "Dynamic");
        System.out.println("\nLocation pair (one time): " + locationPair3 + " Expected: null");
        System.out.println("Location pair (many times): " + locationPair4 + " Expected: null");

        LocationPair locationPair5 = wordDistanceOneTime(words, "Graph", "Stack");
        LocationPair locationPair6 = wordDistanceManyTimesQuery("Graph", "Stack");
        System.out.println("\nLocation pair (one time): " + locationPair5 + " Expected: 0, 9 distance: 9");
        System.out.println("Location pair (many times): " + locationPair6 + " Expected: 0, 9 distance: 9");

        LocationPair locationPair7 = wordDistanceOneTime(words, "Queue", "Deque");
        LocationPair locationPair8 = wordDistanceManyTimesQuery("Queue", "Deque");
        System.out.println("\nLocation pair (one time): " + locationPair7 + " Expected: 2, 3 distance: 1");
        System.out.println("Location pair (many times): " + locationPair8 + " Expected: 2, 3 distance: 1");
    }

}
