package chapter17_hard;

import java.util.*;

/**
 * Created by Rene Argento on 02/05/20.
 */
@SuppressWarnings("unchecked")
public class Exercise7_BabyNames {

    private static class Vertex {
        String name;
        int frequency;
        boolean visited;

        Vertex(String name, int frequency) {
            this.name = name;
            this.frequency = frequency;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Vertex)) {
                return false;
            }
            return name.equals(((Vertex) other).name);
        }
    }

    private static class Graph {
        Map<String, Vertex> vertices;
        Map<Vertex, List<Vertex>> adjacencyList;

        Graph() {
            vertices = new HashMap<>();
            adjacencyList = new HashMap<>();
        }

        public void addVertex(String name, int frequency) {
            Vertex vertex = new Vertex(name, frequency);
            vertices.put(name, vertex);
            adjacencyList.put(vertex, new ArrayList<>());
        }

        public void addEdge(String name1, String name2) {
            Vertex vertex1 = vertices.get(name1);
            Vertex vertex2 = vertices.get(name2);

            if (vertex1 != null && vertex2 != null) {
                adjacencyList.get(vertex1).add(vertex2);
                adjacencyList.get(vertex2).add(vertex1);
            }
        }
    }

    private static class Synonym {
        String name1;
        String name2;

        Synonym(String name1, String name2) {
            this.name1 = name1;
            this.name2 = name2;
        }
    }

    // O(n + s) runtime, where n is the number of names and s is the number of synonyms
    // O(n + s) space
    public static void computeBabyNames(Map<String, Integer> names, List<Synonym> synonyms) {
        Graph graph = constructGraph(names);
        addEdges(graph, synonyms);

        Map<String, Integer> finalNames = dfsToGetFinalNames(graph);
        printNames(finalNames);
    }

    private static Graph constructGraph(Map<String, Integer> names) {
        Graph graph = new Graph();

        for (Map.Entry<String, Integer> nameInformation : names.entrySet()) {
            String name = nameInformation.getKey();
            int frequency = nameInformation.getValue();
            graph.addVertex(name, frequency);
        }
        return graph;
    }

    private static void addEdges(Graph graph, List<Synonym> synonyms) {
        for (Synonym synonym : synonyms) {
            graph.addEdge(synonym.name1, synonym.name2);
        }
    }

    private static Map<String, Integer> dfsToGetFinalNames(Graph graph) {
        Map<String, Integer> finalNames = new HashMap<>();

        for (Vertex vertex : graph.vertices.values()) {
            if (!vertex.visited) {
                int frequency = dfs(graph, vertex);
                String name = vertex.name;
                finalNames.put(name, frequency);
            }
        }
        return finalNames;
    }

    private static int dfs(Graph graph, Vertex vertex) {
        vertex.visited = true;
        int totalFrequency = vertex.frequency;

        for (Vertex neighbor : graph.adjacencyList.get(vertex)) {
            if (!neighbor.visited) {
                totalFrequency += dfs(graph, neighbor);
            }
        }
        return totalFrequency;
    }

    private static void printNames(Map<String, Integer> names) {
        StringJoiner namesString = new StringJoiner(", ");

        for (Map.Entry<String, Integer> nameInformation : names.entrySet()) {
            namesString.add(nameInformation.getKey() + ": " + nameInformation.getValue());
        }
        System.out.println("Names: " + namesString);
    }

    public static void main(String[] args) {
        Map<String, Integer> names1 = new HashMap<>();
        names1.put("John", 15);
        names1.put("Jon", 12);
        names1.put("Chris", 13);
        names1.put("Kris", 4);
        names1.put("Christopher", 19);

        List<Synonym> synonyms1 = new ArrayList<>();
        synonyms1.add(new Synonym("Jon", "John"));
        synonyms1.add(new Synonym("John", "Johnny"));
        synonyms1.add(new Synonym("Chris", "Kris"));
        synonyms1.add(new Synonym("Chris", "Christopher"));

        computeBabyNames(names1, synonyms1);
        System.out.println("Expected: Kris: 36, John: 27\n");

        Map<String, Integer> names2 = new HashMap<>();
        names2.put("John", 10);
        names2.put("Jon", 3);
        names2.put("Davis", 2);
        names2.put("Kari", 3);
        names2.put("Johnny", 11);
        names2.put("Carlton", 8);
        names2.put("Carleton", 2);
        names2.put("Jonathan", 9);
        names2.put("Carrie", 5);

        List<Synonym> synonyms2 = new ArrayList<>();
        synonyms2.add(new Synonym("Jonathan", "John"));
        synonyms2.add(new Synonym("Jon", "Johnny"));
        synonyms2.add(new Synonym("Johnny", "John"));
        synonyms2.add(new Synonym("Kari", "Carrie"));
        synonyms2.add(new Synonym("Carleton", "Carlton"));

        computeBabyNames(names2, synonyms2);
        System.out.println("Expected: Carleton: 10, Johnny: 33, Carrie: 8, Davis: 2");
    }

}
