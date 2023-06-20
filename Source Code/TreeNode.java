package JOJOLAND;

public class TreeNode<T extends Comparable<T>> {
    T treeNodeInfo;
    TreeNode<T> nextTreeNode;
    TreeEdge<T> firstTreeEdge;

    public TreeNode(T item, TreeNode<T> nextTreeNode) {
        treeNodeInfo = item;
        this.nextTreeNode = nextTreeNode;
        firstTreeEdge = null;
    }
}