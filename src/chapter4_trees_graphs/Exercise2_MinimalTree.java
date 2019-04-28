package chapter4_trees_graphs;

/**
 * Created by Rene Argento on 24/04/19.
 */
public class Exercise2_MinimalTree {

    // O(n) runtime
    // O(n) space
    public static TreeNode buildMinimalTree(int[] values) {
        return buildTree(values, 0, values.length - 1);
    }

    private static TreeNode buildTree(int[] values, int start, int end) {
        if (start > end) {
            return null;
        }

        int middle = start + (end - start) / 2;

        TreeNode<Integer> node = new TreeNode<>(values[middle]);
        node.left = buildTree(values, start, middle - 1);
        node.right = buildTree(values, middle + 1, end);

        return node;
    }

    public static void main(String[] args) {
        int[] values1 = {1, 2, 3, 4, 5, 6};
        TreeNode tree1 = buildMinimalTree(values1);
        System.out.println("Height: " + getTreeHeight(tree1) + " Expected: 2");

        int[] values2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode tree2 = buildMinimalTree(values2);
        System.out.println("Height: " + getTreeHeight(tree2) + " Expected: 3");
    }

    private static int getTreeHeight(TreeNode node) {
        return computeTreeHeight(node);
    }

    private static int computeTreeHeight(TreeNode node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = computeTreeHeight(node.left);
        int rightHeight = computeTreeHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
