package chapter17_hard;

import java.util.*;

/**
 * Created by Rene Argento on 03/05/20.
 */
// Based on https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
// and https://www.geeksforgeeks.org/construction-of-longest-monotonically-increasing-subsequence-n-log-n/
public class Exercise8_CircusTower_NlgN {

    public static class Person implements Comparable<Person> {
        int height;
        int weight;

        public Person(int height, int weight) {
            this.height = height;
            this.weight = weight;
        }

        @Override
        public int compareTo(Person otherPerson) {
            if (this.height != otherPerson.height) {
                return this.height - otherPerson.height;
            } else {
                return this.weight - otherPerson.weight;
            }
        }

        public boolean isBefore(Person otherPerson) {
            return height < otherPerson.height && weight < otherPerson.weight;
        }

        public int compareWeight(Person otherPerson) {
            return this.weight - otherPerson.weight;
        }

        @Override
        public String toString() {
            return "(" + height + ", " + weight + ")";
        }
    }

    // O(p lg p) runtime, where p is the number of people
    // O(p) space
    public static List<Person> getHighestTower(List<Person> people) {
        if (people == null || people.isEmpty()) {
            return new ArrayList<>();
        }
        Collections.sort(people);

        int peopleLength = people.size();
        int[] endIndexes = new int[peopleLength];
        int[] previousPerson = new int[peopleLength];

        Arrays.fill(previousPerson, -1);
        int length = 1;

        for (int i = 1; i < peopleLength; i++) {
            // Case 1 - smallest end element
            if (people.get(i).compareWeight(people.get(endIndexes[0])) < 0) {
                endIndexes[0] = i;
            } else if (people.get(endIndexes[length - 1]).isBefore(people.get(i))) {
                // Case 2 - highest end element - extends longest increasing subsequence
                previousPerson[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(people, endIndexes, -1, length - 1, people.get(i));

                Person personToBeReplaced = people.get(endIndexes[indexToReplace]);
                if (personToBeReplaced.compareWeight(people.get(i)) > 0) {
                    previousPerson[i] = endIndexes[indexToReplace - 1];
                    endIndexes[indexToReplace] = i;
                }
            }
        }
        return getSequence(people, endIndexes, previousPerson, length);
    }

    private static int ceilIndex(List<Person> people, int[] endIndexes, int low, int high, Person key) {
        while (high - low > 1) {
            int middle = low + (high - low) / 2;

            if (people.get(endIndexes[middle]).compareWeight(key) >= 0) {
                high = middle;
            } else {
                low = middle;
            }
        }
        return high;
    }

    private static List<Person> getSequence(List<Person> people, int[] endIndexes, int[] previousPerson, int length) {
        LinkedList<Person> sequence = new LinkedList<>();

        for (int i = endIndexes[length - 1]; i >= 0; i = previousPerson[i]) {
            Person person = people.get(i);
            sequence.addFirst(person);
        }
        return sequence;
    }

    public static void main(String[] args) {
        List<Person> people1 = new ArrayList<>();
        people1.add(new Person(65, 100));
        people1.add(new Person(70, 150));
        people1.add(new Person(56, 90));
        people1.add(new Person(75, 190));
        people1.add(new Person(60, 95));
        people1.add(new Person(68, 110));
        List<Person> tower1 = getHighestTower(people1);
        printTower(tower1);
        System.out.println("Expected: (56, 90) (60, 95) (65, 100) (68, 110) (70, 150) (75, 190)\n");

        List<Person> people2 = new ArrayList<>();
        List<Person> tower2 = getHighestTower(people2);
        printTower(tower2);
        System.out.println("Expected: \n");

        List<Person> people3 = new ArrayList<>();
        people3.add(new Person(50, 100));
        people3.add(new Person(75, 120));
        people3.add(new Person(60, 130));
        people3.add(new Person(63, 115));
        people3.add(new Person(63, 118));
        people3.add(new Person(100, 99));
        people3.add(new Person(100, 110));
        List<Person> tower3 = getHighestTower(people3);
        printTower(tower3);
        System.out.println("Expected: (50, 100) (63, 115) (75, 120)");
    }

    private static void printTower(List<Person> tower) {
        StringJoiner towerDescription = new StringJoiner(" ");
        for (Person person : tower) {
            towerDescription.add(person.toString());
        }
        System.out.println("Tower: " + towerDescription.toString());
    }

}
