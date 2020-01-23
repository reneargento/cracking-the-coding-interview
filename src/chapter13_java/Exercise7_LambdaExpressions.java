package chapter13_java;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 21/01/20.
 */
public class Exercise7_LambdaExpressions {

    private static final String EUROPE_CONTINENT = "Europe";
    private static final String SOUTH_AMERICA_CONTINENT = "South America";
    private static final String NORTH_AMERICA_CONTINENT = "North America";
    private static final String AFRICA_CONTINENT = "Africa";
    private static final String ASIA_CONTINENT = "Asia";
    private static final String OCEANIA_CONTINENT = "Oceania";

    public static class Country {
        private String name;
        private String continent;
        private int population;

        Country(String name, String continent, int population) {
            this.name = name;
            this.continent = continent;
            this.population = population;
        }

        public String getContinent() {
            return continent;
        }

        public int getPopulation() {
            return population;
        }
    }

    // O(c) runtime, where c is the number of countries
    // O(1) space
    public static int getPopulation(List<Country> countries, String continent) {
        return countries.stream()
                .mapToInt(country -> country.getContinent().equals(continent) ? country.getPopulation() : 0)
                .sum();
    }

    // O(c) runtime, where c is the number of countries
    // O(1) space
    public static int getPopulation2(List<Country> countries, String continent) {
        return countries.stream()
                .filter(country -> country.getContinent().equals(continent))
                .mapToInt(Country::getPopulation)
                .sum();
    }

    public static void main(String[] args) {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("Spain", EUROPE_CONTINENT, 100));
        countries.add(new Country("Portugal", EUROPE_CONTINENT, 50));
        countries.add(new Country("Brazil", SOUTH_AMERICA_CONTINENT, 500));
        countries.add(new Country("Argentina", SOUTH_AMERICA_CONTINENT, 250));
        countries.add(new Country("United States", NORTH_AMERICA_CONTINENT, 200));
        countries.add(new Country("South Africa", AFRICA_CONTINENT, 300));
        countries.add(new Country("Australia", OCEANIA_CONTINENT, 450));
        countries.add(new Country("China", ASIA_CONTINENT, 1000));

        int europePopulation1 = getPopulation(countries, EUROPE_CONTINENT);
        System.out.println("Europe population method 1: " + europePopulation1 + " Expected: 150");

        int europePopulation2 = getPopulation2(countries, EUROPE_CONTINENT);
        System.out.println("Europe population method 2: " + europePopulation2 + " Expected: 150");

        int southAmericaPopulation1 = getPopulation(countries, SOUTH_AMERICA_CONTINENT);
        System.out.println("South America population method 1: " + southAmericaPopulation1 + " Expected: 750");

        int southAmericaPopulation2 = getPopulation2(countries, SOUTH_AMERICA_CONTINENT);
        System.out.println("South America population method 2: " + southAmericaPopulation2 + " Expected: 750");
    }

}
