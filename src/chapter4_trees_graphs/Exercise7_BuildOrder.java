package chapter4_trees_graphs;

import java.util.*;

/**
 * Created by Rene Argento on 17/05/19.
 */
public class Exercise7_BuildOrder {

    private static class Graph {
        private List<Project> vertices;

        Graph(List<Project> vertices) {
            this.vertices = vertices;
        }
    }

    private static class Project {
        private enum State { UNVISITED, VISITING, VISITED }
        private int id;
        private String name;
        private List<Project> adjacent;
        private State state;

        Project(int id, String name) {
            this.id = id;
            this.name = name;
            adjacent = new ArrayList<>();
            state = State.UNVISITED;
        }
    }

    public static class Dependency {
        private String project1;
        private String project2;

        Dependency(String project1, String project2) {
            this.project1 = project1;
            this.project2 = project2;
        }
    }

    public static void main(String[] args) {
        String[] projects = {"a", "b", "c", "d", "e", "f"};

        List<Dependency> dependencies = new ArrayList<>();
        dependencies.add(new Dependency("a", "d"));
        dependencies.add(new Dependency("f", "b"));
        dependencies.add(new Dependency("b", "d"));
        dependencies.add(new Dependency("f", "a"));
        dependencies.add(new Dependency("d", "c"));

        List<String> buildOrder = getBuildOrder(projects, dependencies);

        if (buildOrder == null) {
            System.out.println("There is no valid build order");
            return;
        }

        StringJoiner projectList = new StringJoiner(" ");
        for (String project : buildOrder) {
            projectList.add(project);
        }

        System.out.print("Build order: ");
        System.out.println(projectList);
        System.out.println("Expected: f e b a d c");
    }

    // O(v + e) runtime, where v is the number of vertices and e is the number of edges
    // O(v) space, where v is the number of vertices
    public static List<String> getBuildOrder(String[] projects, List<Dependency> dependencies) {
        if (!isValidInput(projects, dependencies)) {
            throw new IllegalArgumentException("Invalid input");
        }

        Graph graph = buildGraph(projects, dependencies);

        Deque<Integer> finishTimes = getFinishTimes(graph);
        if (finishTimes == null) {
            return null;
        }

        List<Integer> topologicalOrder = new ArrayList<>();
        while (!finishTimes.isEmpty()) {
            topologicalOrder.add(finishTimes.pop());
        }

        return getProjectNames(topologicalOrder, graph);
    }

    private static boolean isValidInput(String[] projects, List<Dependency> dependencies) {
        if (projects == null || dependencies == null) {
            return false;
        }

        Set<String> projectsSet = new HashSet<>(Arrays.asList(projects));
        for (Dependency dependency : dependencies) {
            if (!projectsSet.contains(dependency.project1) || !projectsSet.contains(dependency.project2)) {
                return false;
            }
        }

        return true;
    }

    private static Graph buildGraph(String[] projects, List<Dependency> dependencies) {
        int projectId = 0;
        List<Project> vertices = new ArrayList<>();
        Map<String, Integer> vertexNameToId = new HashMap<>();

        for (String project : projects) {
            vertices.add(new Project(projectId, project));
            vertexNameToId.put(project, projectId);
            projectId++;
        }

        Graph graph = new Graph(vertices);

        for (Dependency dependency : dependencies) {
            int vertexId1 = vertexNameToId.get(dependency.project1);
            int vertexId2 = vertexNameToId.get(dependency.project2);

            Project vertex1 = vertices.get(vertexId1);
            Project vertex2 = vertices.get(vertexId2);

            vertex1.adjacent.add(vertex2);
        }

        return graph;
    }

    private static Deque<Integer> getFinishTimes(Graph graph) {
        Deque<Integer> finishTimes = new ArrayDeque<>();

        for (Project vertex : graph.vertices) {
            if (vertex.state == Project.State.UNVISITED) {
                if (!dfs(vertex, finishTimes)) {
                    return null;
                }
            }
        }

        return finishTimes;
    }

    private static boolean dfs(Project vertex, Deque<Integer> finishTimes) {
        vertex.state = Project.State.VISITING;

        for (Project neighbor : vertex.adjacent) {
            if (neighbor.state == Project.State.VISITING) {
                return false;
            } else if (neighbor.state == Project.State.UNVISITED) {
                dfs(neighbor, finishTimes);
            }
        }

        finishTimes.push(vertex.id);
        vertex.state = Project.State.VISITED;
        return true;
    }

    private static List<String> getProjectNames(List<Integer> projectIds, Graph graph) {
        List<String> projectNames = new ArrayList<>();

        for (int projectId : projectIds) {
            projectNames.add(graph.vertices.get(projectId).name);
        }

        return projectNames;
    }

}
