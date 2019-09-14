package chapter7_object_oriented_design;

/**
 * Created by Rene Argento on 06/09/19.
 */
@SuppressWarnings("unchecked")
public class Exercise12_HashTable {

    public class HashTable<Key, Value> {

        private class ItemNode<Key, Value> {
            private Key key;
            private Value value;
            private ItemNode previous;
            private ItemNode next;

            public ItemNode(Key key, Value value) {
                this.key = key;
                this.value = value;
            }
        }

        private ItemNode<Key, Value>[] items;
        private int size;
        private static final int DEFAULT_SIZE = 997;

        public HashTable() {
            this(DEFAULT_SIZE);
        }

        public HashTable(int size) {
            items = new ItemNode[size];
        }

        public ItemNode put(Key key, Value value) {
            ItemNode<Key, Value> itemNode = getNodeForKey(key);

            if (itemNode != null) { // Node already exists, so it is an update
                itemNode.value = value;
                return itemNode;
            }

            itemNode = new ItemNode<>(key, value);
            int hashCode = hashCode(key);
            if (items[hashCode] != null) {
                itemNode.next = items[hashCode];
                itemNode.next.previous = itemNode;
            }
            items[hashCode] = itemNode;

            size++;
            return itemNode;
        }

        public Value get(Key key) {
            ItemNode<Key, Value> itemNode = getNodeForKey(key);
            return itemNode != null ? itemNode.value : null;
        }

        public ItemNode remove(Key key) {
            ItemNode<Key, Value> itemNode = getNodeForKey(key);

            if (itemNode == null) {
                return null;
            }

            if (itemNode.previous != null) {
                itemNode.previous.next = itemNode.next;
            } else {
                // Update head of the list
                int hashCode = hashCode(key);
                items[hashCode] = itemNode.next;
            }

            if (itemNode.next != null) {
                itemNode.next.previous = itemNode.previous;
            }

            size--;
            return itemNode;
        }

        public boolean contains(Key key) {
            return getNodeForKey(key) != null;
        }

        public int size() {
            return size;
        }

        private ItemNode<Key, Value> getNodeForKey(Key key) {
            int hashCode = hashCode(key);
            ItemNode<Key, Value> current = items[hashCode];

            while (current != null) {
                if (current.key == key) {
                    return current;
                }
                current = current.next;
            }
            return null;
        }

        private int hashCode(Key key) {
            return Math.abs(key.hashCode() % items.length);
        }
    }

    public static void main(String[] args) {
        // Test put and contains
        HashTable<Integer, String> hashTable = new Exercise12_HashTable().new HashTable<>();
        hashTable.put(1, "Array & Strings");
        hashTable.put(2, "Linked Lists");
        hashTable.put(3, "Stacks & Queues");
        hashTable.put(4, "Trees");

        boolean contains1 = hashTable.contains(1);
        System.out.println("Contains key 1: " + contains1 + " Expected: true");

        boolean contains2 = hashTable.contains(5);
        System.out.println("Contains key 5: " + contains2 + " Expected: false");

        // Test size
        int size1 = hashTable.size();
        System.out.println("Size: " + size1 + " Expected: 4");

        // Test get
        String get1 = hashTable.get(2);
        System.out.println("\nGet key 2: " + get1 + " Expected: Linked Lists");

        String get2 = hashTable.get(4);
        System.out.println("Get key 4: " + get2 + " Expected: Trees");

        // Test update value
        hashTable.put(4, "Trees & Graphs");
        String get3 = hashTable.get(4);
        System.out.println("Get key 4 (after update): " + get3 + " Expected: Trees & Graphs");

        int size2 = hashTable.size();
        System.out.println("Size: " + size2 + " Expected: 4");

        // Test remove
        hashTable.remove(4);
        String get4 = hashTable.get(4);
        System.out.println("\nGet key 4 (after removal): " + get4 + " Expected: null");

        int size3 = hashTable.size();
        System.out.println("Size: " + size3 + " Expected: 3");
    }

}

