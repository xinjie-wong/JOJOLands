package JOJOLAND;

import java.util.ArrayList;

public class TreeMap<T extends Comparable<T>> {
    
    TreeNode<T> head;
    
    //check if the tree node (person) 
    public boolean hasTreeNode(T v){
        if (head==null)
            return false;
        TreeNode<T> temp = head;
        while (temp!=null){
            if ( temp.treeNodeInfo.compareTo( v ) == 0 )
                return true;
            temp=temp.nextTreeNode;
        }
        return false;
    }
    
    //add the tree node
    public void addTreeNode(T v){
        if (hasTreeNode(v)==false){
            TreeNode<T> temp=head;
            TreeNode<T> newTreeNode = new TreeNode<>(v, null);
            if (head==null)   
                head=newTreeNode;
            else{
                TreeNode<T> previous=head;
                while (temp!=null){
                    previous=temp;
                    temp=temp.nextTreeNode;
                }
                previous.nextTreeNode=newTreeNode;
            }
        }
    }
    
    //add edge between the nodes 
    public void addTreeEdge(T current, T parent){
        addTreeNode(current);
        addTreeNode(parent);
        TreeNode<T> currentTreeNode = head;
        while (currentTreeNode!=null){
            if ( currentTreeNode.treeNodeInfo.compareTo( current ) == 0 ){
                // Reached source node, look for destination now
                TreeNode<T> parentTreeNode = head;
                while (parentTreeNode!=null){
                    if ( parentTreeNode.treeNodeInfo.compareTo( parent ) == 0 ){
                        // Reached destination vertex, add edge here
                        TreeEdge<T> newTreeEdge = new TreeEdge<>(parentTreeNode, currentTreeNode.firstTreeEdge);
                        currentTreeNode.firstTreeEdge=newTreeEdge;
                    }
                    parentTreeNode=parentTreeNode.nextTreeNode;
                }
            }
            currentTreeNode=currentTreeNode.nextTreeNode;
        }
    }
    
    //get all the people's name
    public ArrayList<T> getAllTreeNodeObjects(){
        ArrayList<T> list = new ArrayList<>();
        TreeNode<T> temp = head;
        while (temp!=null){
            list.add(temp.treeNodeInfo);
            temp=temp.nextTreeNode;
        }
        return list;
    }
    
    //get the required node
    public TreeNode<T> getTreeNode(T v){
        TreeNode<T> temp = head;
        while (temp != null) {
            if (temp.treeNodeInfo.compareTo(v) == 0) {
                return temp;
            }
            temp = temp.nextTreeNode;
        }
        return null;
    }
}