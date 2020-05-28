package chapter17_hard;

import java.util.StringJoiner;

/**
 * Created by Rene Argento on 12/05/20.
 */
public class Exercise12_BiNode {

    public static class BiNode {
        public BiNode node1, node2;
        public int data;

        BiNode(int data) {
            this.data = data;
        }
    }

    // O(n) runtime, where n is the number of nodes
    // O(1) space
    public static BiNode convertTreeToDoublyLinkedList(BiNode root) {
        if (root == null) {
            return null;
        }
        convertTreeToDoublyLinkedList(root.node1, null, root);
        convertTreeToDoublyLinkedList(root.node2, root, null);
        return getListHead(root);
    }

    private static void convertTreeToDoublyLinkedList(BiNode node, BiNode min, BiNode max) {
        if (node == null) {
            return;
        }
        convertTreeToDoublyLinkedList(node.node1, min, node);
        convertTreeToDoublyLinkedList(node.node2, node, max);

        if (node.node1 == null) {
            node.node1 = min;

            if (min != null) {
                min.node2 = node;
            }
        }
        if (node.node2 == null) {
            node.node2 = max;

            if (max != null) {
                max.node1 = node;
            }
        }
    }

    private static BiNode getListHead(BiNode node) {
        while (node.node1 != null) {
            node = node.node1;
        }
        return node;
    }

    /**
     *       7
     *    5    9
     *  4  6  8 10
     *            11
     */
    public static void main(String[] args) {
        BiNode biNode7 = new BiNode(7);
        BiNode biNode5 = new BiNode(5);
        BiNode biNode9 = new BiNode(9);
        BiNode biNode4 = new BiNode(4);
        BiNode biNode6 = new BiNode(6);
        BiNode biNode8 = new BiNode(8);
        BiNode biNode10 = new BiNode(10);
        BiNode biNode11 = new BiNode(11);

        biNode7.node1 = biNode5;
        biNode7.node2 = biNode9;
        biNode5.node1 = biNode4;
        biNode5.node2 = biNode6;
        biNode9.node1 = biNode8;
        biNode9.node2 = biNode10;
        biNode10.node2 = biNode11;

        BiNode doublyLinkedList = convertTreeToDoublyLinkedList(biNode7);
        String listDescription1 = getDoublyLinkedListString(doublyLinkedList, true);
        System.out.println("Doubly-linked-list: " + listDescription1);
        System.out.println("Expected: 4 5 6 7 8 9 10 11");

        String listDescription2 = getDoublyLinkedListString(biNode11, false);
        System.out.println("\nDoubly-linked-list reverse: " + listDescription2);
        System.out.println("Expected: 11 10 9 8 7 6 5 4");
    }

    private static String getDoublyLinkedListString(BiNode node, boolean inOrder) {
        StringJoiner list = new StringJoiner(" ");
        while (node != null) {
            list.add(String.valueOf(node.data));
            if (inOrder) {
                node = node.node2;
            } else {
                node = node.node1;
            }
        }
        return list.toString();
    }

}
