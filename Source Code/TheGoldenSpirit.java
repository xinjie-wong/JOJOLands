package JOJOLAND;

import java.util.ArrayList;
import java.util.Scanner;

public class TheGoldenSpirit {
    
    static TreeMap familyTree = new TreeMap<>();
    static String personOne, personTwo;
    static Scanner sc = new Scanner(System.in);
    static boolean exit = false;

    public TheGoldenSpirit(){
        TheGoldenSpirit();
    }
    
    public static void TheGoldenSpirit(){
        createTreeMap();
        getJoestarName();
        getInput();
        if(exit)
            return;
        lowestCommonAncestors(personOne, personTwo);
        System.out.print("Do you want to try again? [y/n]: ");
        String input = sc.nextLine();
        if(input.equals("y")){
            System.out.println("");
            TheGoldenSpirit();
            System.out.println("\n=".repeat(200));
        }
        else
            System.out.println("Returning back....");
    }
    
    public static void getJoestarName(){
        int i = 0;
        for(Object name : familyTree.getAllTreeNodeObjects()){
            System.out.printf("[%s] %s\n", i, name);
            i++;
        }
        System.out.println("");  
    }
    
    public static void createTreeMap(){
        familyTree.addTreeEdge("Jolyne Cujoh", "Jotaro Kujo");
        familyTree.addTreeEdge("Jotaro Kujo", "Sadao Kujo");
        familyTree.addTreeEdge("Jotaro Kujo", "Holy Kujo");
        familyTree.addTreeEdge("Holy Kujo", "Suzi Q");
        familyTree.addTreeEdge("Holy Kujo", "Joseph Joestar");
        familyTree.addTreeEdge("Josuke Higashikata", "Tomoko Higashikata");
        familyTree.addTreeEdge("Josuke Higashikata", "Joseph Joestar");
        familyTree.addTreeEdge("Joseph Joestar", "Lisa Lisa");
        familyTree.addTreeEdge("Joseph Joestar", "George Joestar II");
        familyTree.addTreeEdge("George Joestar II", "Erina Joestar");
        familyTree.addTreeEdge("George Joestar II", "Jonathan Joestar");
        familyTree.addTreeEdge("Giorno Giovanna", "DIO");
        familyTree.addTreeEdge("Giorno Giovanna", "Jonathan Joestar");
        familyTree.addTreeEdge("Jonathan Joestar", "Mary Joestar");
        familyTree.addTreeEdge("Jonathan Joestar", "George Joestar I");
    }
    
    public static void lowestCommonAncestors(String personOne, String personTwo){
        ArrayList<String> ancestorsListOne = new ArrayList<>();
        ArrayList<String> ancestorsListTwo = new ArrayList<>();
        TreeNode<String> lowestCommonAncestors = null;
        String shorter = null;
        
        //searching for all ancestors for personOne
        ancestorsListOne = findAncestors(personOne);
        
        //searching for all ancestors for personTwo
        ancestorsListTwo = findAncestors(personTwo);
        
        //compare to get the list with fewer ancestors
        ArrayList<String> shorterList, longerList;
        if(ancestorsListOne.size()<ancestorsListTwo.size()){
            shorterList = ancestorsListOne;
            longerList = ancestorsListTwo;
            shorter = personOne;
        }else{
            shorterList = ancestorsListTwo;
            longerList = ancestorsListOne;
            shorter = personTwo;
        }
                
        //compare and get the lowest common ancestors
        boolean found = false;
        for(int i=0; i<shorterList.size(); i++){
            for(int j=0; j<longerList.size(); j++){
                if(shorterList.get(i).equals(longerList.get(j))) {
                    if (!longerList.get(j).equals(personOne) && !longerList.get(j).equals(personTwo)) {
                        if(i>0)
                            lowestCommonAncestors = familyTree.getTreeNode(shorterList.get(i-1));
                        else
                            lowestCommonAncestors = familyTree.getTreeNode(shorter);
                        found = true;
                        break;
                    }
                }                
            }
            if(found)
                break;
        }
        
        //print the lowest common ancestors
        System.out.printf("Lowest Common Ancestors of %s and %s:\n", personOne, personTwo);
        if(found){
            TreeEdge<String> ancestor = lowestCommonAncestors.firstTreeEdge;
            System.out.println(ancestor.parentNode.treeNodeInfo);
            ancestor = ancestor.nextEdge;
            while(ancestor!=null && longerList.contains(ancestor.parentNode.treeNodeInfo)){
                System.out.print(", " + ancestor.parentNode.treeNodeInfo);
                ancestor = ancestor.nextEdge;
            }
        }else{
            System.out.println("None");
        }
        System.out.println("=".repeat(200));
    }
    
    public static ArrayList<String> findAncestors(String person){
        ArrayList<String> list = new ArrayList<>();
        TreeNode<String> childNode = familyTree.getTreeNode(person);
        TreeEdge<String> edge = childNode.firstTreeEdge;
        while(edge!=null){
            list.add(edge.parentNode.treeNodeInfo);
            if(edge.nextEdge!=null){
                TreeEdge<String> nextEdge = edge.nextEdge;
                list.add(nextEdge.parentNode.treeNodeInfo);
            }
            childNode = edge.parentNode;
            edge = childNode.firstTreeEdge;
        }
        return list;
    }
    
    public static void getInput(){
        System.out.print("Enter the name of the first Joestar: ");
        personOne = sc.nextLine();
        System.out.print("Enter the name of the second Joestar: ");
        personTwo = sc.nextLine();
        System.out.println("=".repeat(200));
        if(!familyTree.hasTreeNode(personOne))
            System.out.println("Invalid input for first Joestar");
        if(!familyTree.hasTreeNode(personTwo))
            System.out.println("Invalid input for second Joestar");
        
        if(!familyTree.hasTreeNode(personOne) || !familyTree.hasTreeNode(personTwo)){
            System.out.print("Do you want to try again? [y/n]: ");
            String input = sc.nextLine();
            if(input.equals("y")){
                System.out.println("");
                getJoestarName();
                getInput();
            }
            else{
                System.out.println("Returning back....");
                System.out.println("=".repeat(200));
                exit = true;
            }
        }
    } 
}