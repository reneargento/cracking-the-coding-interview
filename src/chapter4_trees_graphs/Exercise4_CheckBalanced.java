package chapter4_trees_graphs;

/**
 * Created by Rene Argento on 27/04/19.
 */
public class Exercise4_CheckBalanced {

    private static int ERROR_CODE = Integer.MIN_VALUE;

    // O(n) runtime
    // O(n) space
    public static boolean checkBalanced(TreeNode<Integer> root) {
        return getHeight(root) != ERROR_CODE;
    }

    private static int getHeight(TreeNode<Integer> node) {
        if (node == null) {
            return -1;
        }

        int leftSubtreeHeight = getHeight(node.left);
        int rightSubtreeHeight = getHeight(node.right);
        if (leftSubtreeHeight == ERROR_CODE
                || rightSubtreeHeight == ERROR_CODE
                || Math.abs(leftSubtreeHeight - rightSubtreeHeight) > 1) {
            return ERROR_CODE;
        }
        return Math.max(leftSubtreeHeight, rightSubtreeHeight) + 1;
    }

    public static void main(String[] args) {
        TreeNode<Integer> treeNode1 = new TreeNode<>(5);
        TreeNode<Integer> treeNode2 = new TreeNode<>(3);
        TreeNode<Integer> treeNode3 = new TreeNode<>(7);
        TreeNode<Integer> treeNode4 = new TreeNode<>(2);
        TreeNode<Integer> treeNode5 = new TreeNode<>(4);
        TreeNode<Integer> treeNode6 = new TreeNode<>(1);

        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        //   5
        // 3  7
        System.out.println("Check balanced: " + checkBalanced(treeNode1) + " Expected: true");

        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        //     5
        //  3    7
        // 2 4
        System.out.println("Check balanced: " + checkBalanced(treeNode1) + " Expected: true");

        treeNode4.left = treeNode6;
        //      5
        //   3    7
        //  2 4
        // 1
        System.out.println("Check balanced: " + checkBalanced(treeNode1) + " Expected: false");
    }

}
