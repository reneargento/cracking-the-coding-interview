package chapter16_moderate;

/**
 * Created by Rene Argento on 23/03/20.
 */
public class Exercise10_LivingPeople {

    public static class Person {
        public int birthYear;
        public int deathYear;

        public Person(int birthYear, int deathYear) {
            this.birthYear = birthYear;
            this.deathYear = deathYear;
        }
    }

    // O(p + r) runtime, where p is the number of people and r is the size of the range of years
    // O(r) space
    public static int getYearWithMaxAlive(Person[] people, int minYear, int maxYear) {
        int[] populationDeltas = getPopulationDeltas(people, minYear, maxYear);
        int yearWithMaxAlive = getYearWithMaxAlive(populationDeltas);
        return yearWithMaxAlive + minYear;
    }

    private static int[] getPopulationDeltas(Person[] people, int minYear, int maxYear) {
        int[] populationDeltas = new int[maxYear - minYear + 2];
        for (Person person : people) {
            int birthYear = person.birthYear - minYear;
            int deathYear = person.deathYear - minYear;

            populationDeltas[birthYear]++;
            populationDeltas[deathYear + 1]--;
        }
        return populationDeltas;
    }

    private static int getYearWithMaxAlive(int[] populationDeltas) {
        int yearWithMaxAlive = 0;
        int maxAlive = 0;
        int currentlyAlive = 0;

        for (int year = 0; year < populationDeltas.length; year++) {
            currentlyAlive += populationDeltas[year];

            if (currentlyAlive > maxAlive) {
                maxAlive = currentlyAlive;
                yearWithMaxAlive = year;
            }
        }
        return yearWithMaxAlive;
    }

    public static void main(String[] args) {
        int minYear = 1900;
        int maxYear = 2000;
        Person person1 = new Person(1903, 1960);
        Person person2 = new Person(1905, 1964);
        Person person3 = new Person(1906, 1966);
        Person person4 = new Person(1930, 1975);
        Person person5 = new Person(1962, 2000);
        Person person6 = new Person(1963, 1998);
        Person[] people = {person1, person2, person3, person4, person5, person6};
        int yearWithMaxAlive = getYearWithMaxAlive(people, minYear, maxYear);
        System.out.println("Year with max alive: " + yearWithMaxAlive + " Expected: 1963");
    }

}
