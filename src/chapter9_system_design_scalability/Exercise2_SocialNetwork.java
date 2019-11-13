package chapter9_system_design_scalability;

import java.util.*;

/**
 * Created by Rene Argento on 03/11/19.
 */
public class Exercise2_SocialNetwork {

    public class Person {
        private List<Integer> friendIDs;
        private int id;
        private String information;

        public Person(int id) {
            this.id = id;
            friendIDs = new ArrayList<>();
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public List<Integer> getFriendIDs() {
            return friendIDs;
        }

        public int getID() {
            return id;
        }

        public void addFriendID(int friendID) {
            friendIDs.add(friendID);
        }
    }

    public class Machine {
        private int id;
        private Map<Integer, Person> people;

        public Machine(int id) {
            this.id = id;
            people = new HashMap<>();
        }

        public int getID() {
            return id;
        }

        public void addPerson(int personID, Person person) {
            people.put(personID, person);
        }

        public Person getPersonWithID(int personID) {
            return people.get(personID);
        }
    }

    public class Server {
        Map<Integer, Machine> machines;
        Map<Integer, Integer> personToMachineMap;

        public Server() {
            machines = new HashMap<>();
            personToMachineMap = new HashMap<>();
        }

        public Machine getMachineWithID(int machineID) {
            return machines.get(machineID);
        }

        public int getMachineIDForPersonID(int personID) {
            Integer machineID = personToMachineMap.get(personID);
            return machineID == null ? -1 : machineID;
        }

        public Person getPersonWithID(int personID) {
            Integer machineID = personToMachineMap.get(personID);
            if (machineID == null) {
                return null;
            }

            Machine machine = getMachineWithID(machineID);
            if (machine == null) {
                return null;
            }
            return machine.getPersonWithID(personID);
        }
    }

    public class PathNode {
        private Person person;
        private PathNode previousNode;

        public PathNode(Person person, PathNode previousNode) {
            this.person = person;
            this.previousNode = previousNode;
        }

        public Person getPerson() {
            return person;
        }

        public LinkedList<Person> collapse(boolean startsWithRoot) {
            LinkedList<Person> path = new LinkedList<>();
            PathNode node = this;

            while (node != null) {
                if (startsWithRoot) {
                    path.addLast(node.person);
                } else {
                    path.addFirst(node.person);
                }
                node = node.previousNode;
            }
            return path;
        }
    }

    public class BFSData {
        public Queue<PathNode> toVisit;
        public Map<Integer, PathNode> visited;

        public BFSData(Person person) {
            toVisit = new LinkedList<>();
            visited = new HashMap<>();
            PathNode pathSource = new PathNode(person, null);
            toVisit.add(pathSource);
            visited.put(person.getID(), pathSource);
        }

        public boolean isFinished() {
            return toVisit.isEmpty();
        }
    }

    // O(k^(p/2)) runtime, where p is the shortest path length between the source and destination node and k is the
    // average number of friends in a person's friend list
    // O(k^(p/2)) space
    public List<Person> findPathBidirectionalBFS(Server server, int sourcePersonID, int destinationPersonID) {
        Person sourcePerson = server.getPersonWithID(sourcePersonID);
        Person destinationPerson = server.getPersonWithID(destinationPersonID);
        BFSData sourceData = new BFSData(sourcePerson);
        BFSData destinationData = new BFSData(destinationPerson);

        while (!sourceData.isFinished() && !destinationData.isFinished()) {
            // Search from source
            Person collision = searchLevel(server, sourceData, destinationData);
            if (collision != null) {
                return mergePaths(sourceData, destinationData, collision.getID());
            }

            // Search from destination
            collision = searchLevel(server, destinationData, sourceData);
            if (collision != null) {
                return mergePaths(sourceData, destinationData, collision.getID());
            }
        }
        return null;
    }

    // Search one level and return a collision, if any
    private Person searchLevel(Server server, BFSData primaryData, BFSData secondaryData) {
        int nodesInLevelCount = primaryData.toVisit.size();
        for (int i = 0; i < nodesInLevelCount; i++) {
            PathNode pathNode = primaryData.toVisit.poll();
            Person person = pathNode.getPerson();
            int personID = person.getID();

            if (secondaryData.visited.containsKey(personID)) {
                return person;
            }

            // Add friends to the queue
            List<Integer> friendIDs = person.getFriendIDs();
            for (int friendID : friendIDs) {
                if (!primaryData.visited.containsKey(friendID)) {
                    Person friend = server.getPersonWithID(friendID);
                    PathNode next = new PathNode(friend, pathNode);
                    primaryData.visited.put(friendID, next);
                    primaryData.toVisit.add(next);
                }
            }
        }
        return null;
    }

    private List<Person> mergePaths(BFSData sourceBFSData, BFSData destinationBFSData, int connectionFriendID) {
        PathNode endNode1 = sourceBFSData.visited.get(connectionFriendID);
        PathNode endNode2 = destinationBFSData.visited.get(connectionFriendID);

        LinkedList<Person> pathOne = endNode1.collapse(false);
        LinkedList<Person> pathTwo = endNode2.collapse(true);

        pathTwo.removeFirst(); // Remove connection friend
        pathOne.addAll(pathTwo);
        return pathOne;
    }
}
