package chapter4_trees_graphs;

/**
 * Created by Rene Argento on 29/05/19.
 */
import java.util.Random;

public class Exercise11_RandomNode {

    public static class TreeNode<Item extends Comparable<Item>> {
        private TreeNode<Item> left;
        private TreeNode<Item> right;
        private Item value;
        private int size;

        TreeNode(Item value) {
            this.value = value;
            size = 1;
        }
    }

    public static class Tree<Item extends Comparable<Item>> {
        private TreeNode<Item> root;

        // O(1) runtime
        public int size() {
            return size(root);
        }

        private int size(TreeNode<Item> node) {
            if (node == null) {
                return 0;
            }
            return node.size;
        }

        // O(1) runtime
        public boolean isEmpty() {
            return size() == 0;
        }

        // O(n) runtime
        public boolean contains(Item item) {
            return contains(root, item);
        }

        private boolean contains(TreeNode<Item> node, Item item) {
            if (node == null) {
                return false;
            }

            if (node.value == item) {
                return true;
            }

            if (item.compareTo(node.value) < 0) {
                return contains(node.left, item);
            } else {
                return contains(node.right, item);
            }
        }

        // O(n) runtime
        public TreeNode<Item> find(Item item) {
            return find(root, item);
        }

        private TreeNode<Item> find(TreeNode<Item> node, Item item) {
            if (node == null) {
                return null;
            }

            if (node.value == item) {
                return node;
            }

            if (item.compareTo(node.value) < 0) {
                return find(node.left, item);
            } else {
                return find(node.right, item);
            }
        }

        // O(n) runtime
        public void insert(Item item) {
            root = insert(root, item);
        }

        private TreeNode<Item> insert(TreeNode<Item> node, Item item) {
            if (node == null) {
                return new TreeNode<>(item);
            }

            if (item.compareTo(node.value) <= 0) {
                node.left = insert(node.left, item);
            } else {
                node.right = insert(node.right, item);
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
        }

        // O(n) runtime
        public void delete(Item item) {
            if (!contains(item)) {
                return;
            }

            root = delete(root, item);
        }

        private TreeNode<Item> delete(TreeNode<Item> node, Item item) {
            if (item.compareTo(node.value) < 0) {
                node.left = delete(node.left, item);
            } else if (item.compareTo(node.value) > 0) {
                node.right = delete(node.right, item);
            } else {
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                } else {
                    TreeNode<Item> aux = node;
                    node = min(aux.right);
                    node.right = deleteMin(aux.right);
                    node.left = aux.left;
                }
            }

            node.size = size(node.left) + 1 + size(node.right);
            return node;
        }

        private TreeNode<Item> deleteMin(TreeNode<Item> node) {
            if (node == null) {
                return null;
            }

            if (node.left == null) {
                return node.right;
            }

            node.left = deleteMin(node.left);
            node.size = size(node.left) + 1 + size(node.right);
            return node;
        }

        private TreeNode<Item> min(TreeNode<Item> node) {
            if (node == null) {
                return null;
            }

            if (node.left == null) {
                return node;
            }

            return min(node.left);
        }

        // O(n) runtime
        public TreeNode<Item> randomNode() {
            if (isEmpty()) {
                return null;
            }

            Random random = new Random();
            int randomIndex = 1 + random.nextInt(size());
            return select(root, randomIndex);
        }

        private TreeNode<Item> select(TreeNode<Item> node, int index) {
            int nodeIndex = 1 + size(node.left);

            if (index == nodeIndex) {
                return node;
            } else if (index < nodeIndex) {
                return select(node.left, index);
            } else {
                return select(node.right, index - nodeIndex);
            }
        }
    }

    //         5
    //     3      7
    //   2  4   6
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>();
        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(2);
        tree.insert(4);
        tree.insert(6);

        System.out.println("Random node: " + tree.randomNode().value);
        System.out.println("Random node: " + tree.randomNode().value);
        System.out.println("Random node: " + tree.randomNode().value);
        System.out.println("Random node: " + tree.randomNode().value);

        System.out.println("Delete 5");
        tree.delete(5);

        System.out.println("Random node: " + tree.randomNode().value);
        System.out.println("Random node: " + tree.randomNode().value);
        System.out.println("Random node: " + tree.randomNode().value);
        System.out.println("Random node: " + tree.randomNode().value);
    }

}