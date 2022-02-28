package AI;

import model.Node;

import java.util.*;

public class BDS {

    // for the search from start to goal
    protected Queue<Node> frontier = new LinkedList<Node>();
    protected Hashtable<String, Boolean> inFrontier = new Hashtable<>();
    protected Hashtable<String, Boolean> explored = new Hashtable<>();

    // for the search from goal to start
    protected Queue<Node> frontierRev = new LinkedList<Node>();
    protected Hashtable<String, Boolean> inFrontierRev = new Hashtable<>();
    protected Hashtable<String, Boolean> exploredRev = new Hashtable<>();

    public void search(Node startNode, Node goalNode) {


        if (startNode.isGoal() || startNode.isStart()) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);

        frontierRev.add(goalNode);
        inFrontierRev.put(goalNode.hash(), true);

        while (!frontier.isEmpty() && !frontierRev.isEmpty()) {

            Node temp = frontier.poll();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(), true);
            ArrayList<Node> children = temp.successor();

            Node tempRev = frontierRev.poll();
            inFrontierRev.remove(tempRev.hash());
            exploredRev.put(tempRev.hash(), true);
            ArrayList<Node> childrenRev = tempRev.successor();

            for (Node child2 : childrenRev) {
                if (!(inFrontierRev.containsKey(child2.hash())) && !(exploredRev.containsKey(child2.hash()))) {
                    frontierRev.add(child2);
                    inFrontierRev.put(child2.hash(), true);
                }
            }

            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(explored.containsKey(child.hash()))) {
                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
                }
            }

            for (Node child : frontier){
                for (Node child2 : frontier) {
                    if (child.currentCell.getJ() == child2.currentCell.getJ()) {
                        if (child.currentCell.getI() == child2.currentCell.getI()) {
                            if (checkGoal(child2, child.sum, startNode.getGoalValue())) {
                                debufpath(child);
                                System.out.println("second one");
                                debufpath(child2);
                                System.out.println("I: " + child.currentCell.getI());
                                System.out.println("J: " + child.currentCell.getJ());
                                printResult(child, 0);
//                                printResult(child2, 0);
//                                System.out.println(child.sum + child2.sum);
                                return;
//                            }
                            }
                        }
                    }
                }
            }
            //            for (Node child:children){
//                for (Node child2 : childrenRev){
//                        if ( (child.currentCell.getI() == child2.currentCell.getI()) &&
//                                (child.currentCell.getJ() == child2.currentCell.getJ()) ) {
//                            if (checkGoal(child2,child.sum,startNode.getGoalValue())) {
//                                debufpath(child);
//                                System.out.println("second one");
//                                debufpath(child2);
//                                System.out.println("I: " + child.currentCell.getI());
//                                System.out.println("J: " + child.currentCell.getJ());
//                                printResult(child, 0);
////                                printResult(child2, 0);
////                                System.out.println(child.sum + child2.sum);
//                                return;
//                            }
//                    }
//                }
//            }
        }

        System.out.println("no solution");

    }

    public void printResult(Node node, int depthCounter) {
        if (node.parent == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }

        System.out.println(node.toString());
        node.drawState();
        printResult(node.parent, depthCounter + 1);
    }
    public boolean checkGoal(Node node,int lastInt,int goal){

        int sum=lastInt;
        while (node.parent!=null){
            node=node.parent;
            sum = node.calculate(node.currentCell,sum);
        }
        if(goal<=sum)
            return true;
        else return false;
    }

    public void debufpath(Node st){
        System.out.println(st.currentCell);
        System.out.println("i,j"+ st.currentCell.toString());
        while (st.parent!=null){
            st=st.parent;
            System.out.println("i,j"+ st.currentCell.toString());
        }
    }
}
