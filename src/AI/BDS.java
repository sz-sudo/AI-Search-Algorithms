package AI;

import model.Node;

import java.util.*;

public class BDS {

    protected Hashtable<String,ArrayList<Node>> fringe=new Hashtable<>();
    protected Hashtable<String,ArrayList<Node>> Bfringe=new Hashtable<>();


    protected Queue<Node> frontier = new LinkedList<Node>();
    protected Hashtable<String, Boolean> inFrontier = new Hashtable<>();
    protected Hashtable<String, Boolean> explored = new Hashtable<>();

    protected Queue<Node> Bfrontier = new LinkedList<Node>();
    protected Hashtable<String, Boolean> BinFrontier = new Hashtable<>();
    protected Hashtable<String, Boolean> Bexplored = new Hashtable<>();

    public void search(Node startNode, Node goalNode) {

        if (startNode.isGoal()|| goalNode.isStart()) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);
        Bfrontier.add(goalNode);
        BinFrontier.put(goalNode.hash(), true);

        while (!frontier.isEmpty() && !Bfrontier.isEmpty()) {

            Node temp = frontier.poll();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(), true);

            Node Btemp = Bfrontier.poll();
            BinFrontier.remove(Btemp.hash());
            Bexplored.put(Btemp.hash(), true);

            ArrayList<Node> children = temp.successor();
            ArrayList<Node> Bchildren = Btemp.successor();

            for (Node child : children) {
                    if(Bfringe.containsKey(child.currentCell.toString())){
                        for (Node intercourse : Bfringe.get(child.currentCell.toString())){
                            if(checkGoal(child,intercourse,startNode)) {
                                printResult(intercourse, 0);
                                printResult(child, 0);
                                return;
                            }
                        }
                    }
                    addValueForKey(child.currentCell.toString(),child,fringe);
                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
            }
            for (Node Bchild : Bchildren) {
                    if(fringe.containsKey(Bchild.currentCell.toString())){
                        for (Node intercourse : fringe.get(Bchild.currentCell.toString())){
                            if(checkGoal(intercourse,Bchild,startNode)) {
                                printResult(Bchild, 0);
                                printResult(intercourse, 0);
                                return;
                            }
                        }
                    }
                    addValueForKey(Bchild.currentCell.toString(),Bchild,Bfringe);
                    Bfrontier.add(Bchild);
                    BinFrontier.put(Bchild.hash(), true);
            }
            removeValueForKey(temp.currentCell.toString(),temp,fringe);
            removeValueForKey(Btemp.currentCell.toString(),Btemp,Bfringe);

        }
        System.out.println("no solution");

    }

    public void printResult(Node node, int depthCounter) {
        if (node.parent == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }
        System.out.println(node);
//        node.drawState();
        printResult(node.parent, depthCounter + 1);
    }

    private void addValueForKey(String key,Node value,Hashtable<String,ArrayList<Node>> myHashMap) {
        ArrayList<Node> values = myHashMap.get(key);
        if(values == null ){
            values=new ArrayList<Node>();
            values.add( value );
            myHashMap.put(key,values);
            return;
        }
        values.add( value );
    }
    private void removeValueForKey(String key,Node value,Hashtable<String,ArrayList<Node>> myHashMap) {
        ArrayList<Node> values = myHashMap.get( key );
        if(values!=null)
            values.removeIf(n->n.hash().equals(value.hash()));
    }
    private boolean checkGoal(Node forward,Node backward,Node start){
        int sum = forward.sum;
        Node i ;
        int goal = -start.getGoalValue()+forward.parent.getGoalValue()+backward.getGoalValue();
        for (i=backward.parent; i.parent!=null ; i=i.parent) {
            if(forward.isRepeated(i))
                return false;
            sum=i.calculate(i.currentCell,sum);
        }
//            printResult(forward,0);
//            printResult(backward,0);
//        System.out.println(sum + "  dsd a a a ");
        if(goal<=sum){
            System.out.println(sum+ " " + goal);
        }
        return goal<=sum;
    }
}
