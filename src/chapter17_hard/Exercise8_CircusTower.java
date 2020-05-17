package chapter17_hard;

import java.util.*;

/**
 * Created by Rene Argento on 03/05/20.
 */
public class Exercise8_CircusTower {

    public static class Person implements Comparable<Person> {
        int height;
        int weight;

        public Person(int height, int weight) {
            this.height = height;
            this.weight = weight;
        }

        public boolean isBefore(Person otherPerson) {
            return height < otherPerson.height && weight < otherPerson.weight;
        }

        @Override
        public int compareTo(Person otherPerson) {
            if (this.height != otherPerson.height) {
                return this.height - otherPerson.height;
            } else {
                return this.weight - otherPerson.weight;
            }
        }

        @Override
        public String toString() {
            return "(" + height + ", " + weight + ")";
        }
    }

    // O(p^2) runtime, where p is the number of people
    // O(p) space
    public static List<Person> getHighestTower(List<Person> people) {
        if (people == null || people.isEmpty()) {
            return new ArrayList<>();
        }
        Collections.sort(people);

        int peopleLength = people.size();
        int[] longestIncreasingSubsequence = new int[peopleLength];
        int[] previousPerson = new int[peopleLength];
        Arrays.fill(previousPerson, -1);

        for (int i = 0; i < peopleLength; i++) {
            for (int j = i + 1; j < peopleLength; j++) {
                if (people.get(i).isBefore(people.get(j))
                        && longestIncreasingSubsequence[i] + 1 > longestIncreasingSubsequence[j]) {
                    longestIncreasingSubsequence[j] = longestIncreasingSubsequence[i] + 1;
                    previousPerson[j] = i;
                }
            }
        }
        return getSequence(people, longestIncreasingSubsequence, previousPerson);
    }

    private static List<Person> getSequence(List<Person> people, int[] longestIncreasingSubsequence, int[] previousPerson) {
        int highestTowerEnd = 0;
        int highestTowerLength = 0;

        for (int i = 0; i < longestIncreasingSubsequence.length; i++) {
            if (longestIncreasingSubsequence[i] > highestTowerLength) {
                highestTowerLength = longestIncreasingSubsequence[i];
                highestTowerEnd = i;
            }
        }

        LinkedList<Person> sequence = new LinkedList<>();

        while (highestTowerEnd != -1) {
            Person person = people.get(highestTowerEnd);
            sequence.addFirst(person);
            highestTowerEnd = previousPerson[highestTowerEnd];
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
