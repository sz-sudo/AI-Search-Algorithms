package AI;

import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

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

            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(explored.containsKey(child.hash()))) {

                    for (Node child2 : childrenRev) {
                        if (child.hash().equals(child2.hash())) {
                            System.out.println("first one");
                            if ((startNode.sum + goalNode.sum) >= startNode.getGoalValue()) {
                                System.out.println("second one");
                                printResult(child, 0);
                                printResult(child2, 0);
                                System.out.println(child.sum + child2.sum);
                                return;
                            }
                        }
                        frontierRev.add(child2);
                        inFrontierRev.put(child2.hash(), true);
                    }

                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
                }
            }
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


}
