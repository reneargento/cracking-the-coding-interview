package chapter4_trees_graphs;

/**
 * Created by Rene Argento on 26/04/19.
 */
public class TreeNode<Item> {
    TreeNode<Item> left;
    TreeNode<Item> right;
    Item value;

    TreeNode(Item value) {
        this.value = value;
    }
}
