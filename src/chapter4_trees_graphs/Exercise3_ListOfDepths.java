package chapter4_trees_graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 26/04/19.
 */
public class Exercise3_ListOfDepths {

    public static List<List<TreeNode>> getListOfDepths(TreeNode<Integer> treeNode) {
        List<List<TreeNode>> listOfDepths = new ArrayList<>();
        getListOfDepths(treeNode, listOfDepths, 0);
        return listOfDepths;
    }

    // O(n) runtime, where n is the number of nodes in the tree
    // O(n) space
    private static void getListOfDepths(TreeNode<Integer> treeNode, List<List<TreeNode>> listOfDepths, int depth) {
        if (treeNode == null) {
            return;
        }

        if (listOfDepths.size() == depth) {
            listOfDepths.add(new ArrayList<>());
        }

        listOfDepths.get(depth).add(treeNode);

        getListOfDepths(treeNode.left, listOfDepths, depth + 1);
        getListOfDepths(treeNode.right, listOfDepths, depth + 1);
    }

    //    Tree
    //              1
    //          10       11
    //     100    101  110
    //      1001

    public static void main(String[] args) {
        TreeNode<Integer> treeNode1 = new TreeNode<>(1);
        TreeNode<Integer> treeNode10 = new TreeNode<>(10);
        TreeNode<Integer> treeNode11 = new TreeNode<>(11);
        TreeNode<Integer> treeNode100 = new TreeNode<>(100);
        TreeNode<Integer> treeNode101 = new TreeNode<>(101);
        TreeNode<Integer> treeNode110 = new TreeNode<>(110);
        TreeNode<Integer> treeNode1001 = new TreeNode<>(1001);

        treeNode1.left = treeNode10;
        treeNode1.right = treeNode11;
        treeNode10.left = treeNode100;
        treeNode10.right = treeNode101;
        treeNode11.left = treeNode110;
        treeNode100.right = treeNode1001;

        int depth = 0;
        List<List<TreeNode>> listOfDepths = getListOfDepths(treeNode1);
        for (List<TreeNode> list : listOfDepths) {
            System.out.print("Depth " + depth + ": ");

            StringJoiner values = new StringJoiner(" ");
            for (TreeNode treeNode : list) {
                values.add(String.valueOf(treeNode.value));
            }
            System.out.println(values);
            depth++;
        }

        System.out.println("\nExpected:");
        System.out.println("Depth 0: 1");
        System.out.println("Depth 1: 10 11");
        System.out.println("Depth 2: 100 101 110");
        System.out.println("Depth 3: 1001");
    }

}
