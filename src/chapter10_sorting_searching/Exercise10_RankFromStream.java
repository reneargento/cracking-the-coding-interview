package chapter10_sorting_searching;

/**
 * Created by Rene Argento on 15/12/19.
 */
public class Exercise10_RankFromStream {

    public static class Tree {

        class Node {
            int value;
            Node left;
            Node right;
            int size;

            Node(int value) {
                this.value = value;
                size = 1;
            }
        }

        private Node root;

        // O(n) runtime, where n is the number of nodes in the tree, on an unbalanced tree. (O(lg n) if it were a balanced tree)
        // O(n) space. (O(lg n) if it were a balanced tree)
        public void track(int value) {
            root = put(root, value);
        }

        private Node put(Node node, int value) {
            if (node == null) {
                node = new Node(value);
                return node;
            }

            if (value <= node.value) {
                node.left = put(node.left, value);
            } else {
                node.right = put(node.right, value);
            }
            node.size++;
            return node;
        }

        // O(n) runtime, where n is the number of nodes in the tree, on an unbalanced tree. (O(lg n) if it were a balanced tree)
        // O(n) space. (O(lg n) if it were a balanced tree)
        public int getRankOfNumber(int value) {
            return rank(root, value);
        }

        private int rank(Node node, int value) {
            if (node == null) {
                return -1;
            }

            if (value < node.value) {
                return rank(node.left, value);
            } else if (value > node.value) {
                int rightRank = rank(node.right, value);
                if (rightRank == -1) {
                    return -1;
                }
                return size(node.left) + 1 + rightRank;
            } else {
                return size(node.left);
            }
        }

        private int size(Node node) {
            if (node == null) {
                return 0;
            }
            return node.size;
        }
    }

    public static void getOnlineRank(int[] stream) {
        Tree tree = new Tree();

        for (int value : stream) {
            tree.track(value);
        }

        int rank1 = tree.getRankOfNumber(1);
        System.out.println("Rank of 1: " + rank1 + " Expected: 0");

        int rank2 = tree.getRankOfNumber(3);
        System.out.println("Rank of 3: " + rank2 + " Expected: 1");

        int rank3 = tree.getRankOfNumber(4);
        System.out.println("Rank of 4: " + rank3 + " Expected: 3");

        int rank4 = tree.getRankOfNumber(14);
        System.out.println("Rank of 14: " + rank4 + " Expected: -1");
    }

    public static void main(String[] args) {
        int[] stream = {5, 1, 4, 4, 5, 9, 7, 13, 3};
        getOnlineRank(stream);
    }

}
