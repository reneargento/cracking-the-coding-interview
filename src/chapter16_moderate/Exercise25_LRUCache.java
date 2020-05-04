package chapter16_moderate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 22/04/20.
 */
public class Exercise25_LRUCache {

    public static class LRUCache<Key, Value> {
        private class Node {
            Key key;
            Value value;
            Node previous;
            Node next;

            public Node(Key key, Value value) {
                this.key = key;
                this.value = value;
            }
        }

        private Node head;
        private Node tail;
        private Map<Key, Node> nodesMap;
        private int maxSize;

        LRUCache(int maxSize) {
            this.maxSize = maxSize;
            nodesMap = new HashMap<>();
        }

        // O(1) runtime
        public void insert(Key key, Value value) {
            if (nodesMap.containsKey(key)) {
                removeKey(key);
            }

            if (nodesMap.size() == maxSize && tail != null) {
                removeKey(tail.key);
            }

            Node node = new Node(key, value);
            insertAtFrontOfLinkedList(node);
            nodesMap.put(key, node);
        }

        // O(1) runtime
        public Value get(Key key) {
            Node node = nodesMap.get(key);
            if (node == null) {
                return null;
            }

            if (node != head) {
                removeFromLinkedList(node);
                insertAtFrontOfLinkedList(node);
            }
            return node.value;
        }

        // O(1) runtime
        private void removeFromLinkedList(Node node) {
            if (node == null) {
                return;
            }

            if (node.previous != null) {
                node.previous.next = node.next;
            }
            if (node.next != null) {
                node.next.previous = node.previous;
            }
            if (node == head) {
                head = node.next;
            }
            if (node == tail) {
                tail = node.previous;
            }
            node.previous = null;
            node.next = null;
        }

        // O(1) runtime
        private void insertAtFrontOfLinkedList(Node node) {
            if (head == null) {
                tail = node;
            } else {
                head.previous = node;
                node.next = head;
            }
            head = node;
        }

        // O(1) runtime
        private void removeKey(Key key) {
            Node node = nodesMap.get(key);
            removeFromLinkedList(node);
            nodesMap.remove(key);
        }
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> lruCache = new LRUCache<>(4);
        lruCache.insert(0, "Lists");
        lruCache.insert(1, "Stacks");
        lruCache.insert(2, "Queues");
        lruCache.insert(3, "Trees");

        System.out.println("Cache:");
        printCacheContent(lruCache);
        System.out.println("Expected values: Trees, Queues, Stacks, Lists\n");

        lruCache.insert(4, "Graphs");
        printCacheContent(lruCache);
        System.out.println("Expected values: Graphs, Trees, Queues, Stacks\n");

        lruCache.get(2);
        printCacheContent(lruCache);
        System.out.println("Expected values: Queues, Graphs, Trees, Stacks");
    }

    private static void printCacheContent(LRUCache<Integer, String> lruCache) {
        for (LRUCache.Node node = lruCache.head; node != null; node = node.next) {
            System.out.println(node.key + ": " + node.value);
        }
    }

}
