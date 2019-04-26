package chapter4_trees_graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 21/04/19.
 */
public class Exercise1_RouteBetweenNodes {

    private static class Graph {
        private List<Node> vertices;

        Graph(List<Node> vertices) {
            this.vertices = vertices;
        }
    }

    private static class Node {
        int id;
        List<Node> adjacent;

        Node(int id) {
            this.id = id;
            adjacent = new ArrayList<>();
        }
    }

    // O(v + e) runtime, where v is the number of vertices and e is the number of edges
    public static boolean isThereARoute(Graph graph, int vertex1, int vertex2) {
        int size = graph.vertices.size();

        if (vertex1 < 0 || vertex1 >= size || vertex2 < 0 || vertex2 >= size) {
            throw new IllegalArgumentException("Invalid vertex ids");
        }

        boolean[] visited = new boolean[size];
        Queue<Node> queue = new LinkedList<>();
        queue.add(graph.vertices.get(vertex1));

        while (!queue.isEmpty()) {
            Node currentVertex = queue.poll();

            if (currentVertex.id == vertex2) {
                return true;
            }

            for (Node vertex : currentVertex.adjacent) {
                if (!visited[vertex.id]) {
                    visited[vertex.id] = true;
                    queue.offer(vertex);
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        List<Node> vertices = new ArrayList<>();
        vertices.add(node0);
        vertices.add(node1);
        vertices.add(node2);
        vertices.add(node3);
        vertices.add(node4);
        vertices.add(node5);
        vertices.add(node6);

        Graph graph = new Graph(vertices);

        node0.adjacent.add(node1);
        node1.adjacent.add(node2);
        node2.adjacent.add(node3);
        node3.adjacent.add(node4);
        node5.adjacent.add(node6);

        System.out.println("Is there a route: " + isThereARoute(graph, 0, 4) + " Expected: true");
        System.out.println("Is there a route: " + isThereARoute(graph, 5, 6) + " Expected: true");
        System.out.println("Is there a route: " + isThereARoute(graph, 4, 5) + " Expected: false");
        System.out.println("Is there a route: " + isThereARoute(graph, 0, 6) + " Expected: false");
    }
}
