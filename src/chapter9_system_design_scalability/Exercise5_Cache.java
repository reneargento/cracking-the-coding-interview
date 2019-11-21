package chapter9_system_design_scalability;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 07/11/19.
 */
public class Exercise5_Cache {

    public class Cache {

        private class QueryResultNode {
            private String query;
            private String[] results;
            private QueryResultNode previous;
            private QueryResultNode next;

            QueryResultNode(String query, String[] results) {
                this.query = query;
                this.results = results;
            }
        }

        private Map<String, QueryResultNode> resultsMap;
        private QueryResultNode doublyLinkedListHead;
        private QueryResultNode doublyLinkedListTail;
        private int maxSize;
        private int size;

        public Cache(int maxSize) {
            resultsMap = new HashMap<>();
            this.maxSize = maxSize;
        }

        // O(1) runtime
        public String[] getResults(String query) {
            if (!resultsMap.containsKey(query)) {
                return null;
            }

            QueryResultNode resultNode = resultsMap.get(query);
            String[] results = resultNode.results;
            addOrUpdateEntry(query, results);
            return results;
        }

        // O(1) runtime
        public void addOrUpdateEntry(String query, String[] results) {
            QueryResultNode resultNode;

            if (!resultsMap.containsKey(query)) {
                if (size == maxSize) {
                    removeOldestEntry();
                }
                resultNode = new QueryResultNode(query, results);
            } else {
                resultNode = resultsMap.get(query);
                deleteEntry(resultNode);
            }

            addEntry(resultNode);
        }

        // O(1) runtime
        private void addEntry(QueryResultNode resultNode) {
            if (isEmpty()) {
                doublyLinkedListHead = resultNode;
            } else {
                doublyLinkedListTail.next = resultNode;
                resultNode.previous = doublyLinkedListTail;
            }
            doublyLinkedListTail = resultNode;

            resultsMap.put(resultNode.query, resultNode);
            size++;
        }

        // O(1) runtime
        private void removeOldestEntry() {
            if (isEmpty()) {
                return;
            }

            String oldestQueryEntry = doublyLinkedListHead.query;
            resultsMap.remove(oldestQueryEntry);

            doublyLinkedListHead = doublyLinkedListHead.next;
            doublyLinkedListHead.previous = null;
            if (size == 1) {
                doublyLinkedListTail = null;
            }

            size--;
        }

        // O(1) runtime
        private void deleteEntry(QueryResultNode resultNode) {
            if (resultNode.previous != null) {
                resultNode.previous.next = resultNode.next;
            }
            if (resultNode.next != null) {
                resultNode.next.previous = resultNode.previous;
            }
            resultNode.previous = null;
            resultNode.next = null;
            size--;
        }

        // O(1) runtime
        public boolean isOnCache(String query) {
            return resultsMap.containsKey(query);
        }

        // O(1) runtime
        public boolean isEmpty() {
            return size == 0;
        }

        // O(n)
        public String getCacheDescription() {
            StringJoiner stringJoiner = new StringJoiner(" -> ");
            QueryResultNode currentNode = doublyLinkedListHead;

            while (currentNode != null) {
                stringJoiner.add(currentNode.query);
                currentNode = currentNode.next;
            }
            return stringJoiner.toString();
        }
    }

    public static void main(String[] args) {
        Cache cache = new Exercise5_Cache().new Cache(5);

        cache.addOrUpdateEntry("Rene", new String[]{"ReneResult"});
        cache.addOrUpdateEntry("Arrays", new String[]{"ArraysResult"});
        cache.addOrUpdateEntry("Strings", new String[]{"StringsResult"});
        cache.addOrUpdateEntry("LinkedLists", new String[]{"LinkedListsResult"});
        cache.addOrUpdateEntry("Stacks", new String[]{"StacksResult"});

        boolean isOnCache1 = cache.isOnCache("Rene");
        System.out.println("Cache test 1: " + isOnCache1 + " Expected: true");

        cache.addOrUpdateEntry("Queues", new String[]{"QueuesResult"});
        boolean isOnCache2 = cache.isOnCache("Rene");
        System.out.println("Cache test 2: " + isOnCache2 + " Expected: false");

        String cacheDescription1 = cache.getCacheDescription();
        System.out.println("\nCache description 1: " + cacheDescription1);
        System.out.println("Expected: Arrays -> Strings -> LinkedLists -> Stacks -> Queues");

        cache.getResults("Strings");
        String cacheDescription2 = cache.getCacheDescription();
        System.out.println("\nCache description 2: " + cacheDescription2);
        System.out.println("Expected: Arrays -> LinkedLists -> Stacks -> Queues -> Strings");
    }

}
