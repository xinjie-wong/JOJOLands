package JOJOLAND;

public class TreeEdge<T extends Comparable<T>> {
    TreeNode<T> parentNode;
    TreeEdge<T> nextEdge;
    
    public TreeEdge(TreeNode<T> parentNode, TreeEdge<T> nextEdge){
        this.parentNode = parentNode;
        this.nextEdge = nextEdge;
    }
}
