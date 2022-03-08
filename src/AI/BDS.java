package AI;

import model.Node;

import java.util.*;

public class BDS {

    // for the search from start to goal
    protected Queue<Node> frontier = new LinkedList<Node>();
    protected Hashtable<String, Boolean> inFrontier = new Hashtable<>();
    protected Hashtable<String, Boolean> explored = new Hashtable<>();
    protected Hashtable<String, Node> frontierHash = new Hashtable<>();


    // for the search from goal to start
    protected Queue<Node> frontierRev = new LinkedList<Node>();
    protected Hashtable<String, Boolean> inFrontierRev = new Hashtable<>();
    protected Hashtable<String, Boolean> exploredRev = new Hashtable<>();
    protected Hashtable<String, Node> frontierHashRev = new Hashtable<>();



    public void search(Node startNode, Node goalNode) {

        if (startNode.isGoal() || goalNode.isStart()) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);
        frontierHash.put(startNode.newHash(), startNode);

        frontierRev.add(goalNode);
        inFrontierRev.put(goalNode.hash(), true);
        frontierHashRev.put(goalNode.newHash(), goalNode);

        if (frontierHash.containsKey(goalNode.newHash())) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        while (!frontier.isEmpty() && !frontierRev.isEmpty()) {

            Node temp = frontier.poll();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(), true);
            frontierHash.remove(temp.newHash());

            ArrayList<Node> children = temp.successor();

            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(explored.containsKey(child.hash()))) {
                    if (frontierHashRev.containsKey(child.newHash())) {
                        if (child.sum >= frontierHashRev.get(child.newHash()).sum) {
                            printResult(child, 0);
                            printResult(frontierHashRev.get(child.newHash()), 0);
                            return;
                        }
                    }
                    frontier.add(child);
                    inFrontier.put(child.hash(), true);

                    if (frontierHash.containsKey(child.newHash()) && (child.sum > frontierHash.get(child.newHash()).sum)) {
                        frontierHash.put(child.newHash(), child);
                    } else if ( !frontierHash.containsKey(child.newHash()) ) {
                      frontierHash.put(child.newHash(), child);
                    }
                }
            }

            Node tempRev = frontierRev.poll();
            inFrontierRev.remove(tempRev.hash());
            exploredRev.put(tempRev.hash(), true);
            frontierHashRev.remove(tempRev.newHash());

            ArrayList<Node> childrenRev = tempRev.newSuccessor();

            for (Node child : childrenRev) {
                if (!(inFrontierRev.containsKey(child.hash())) && !(exploredRev.containsKey(child.hash()))) {
                    if (frontierHash.containsKey(child.newHash())) {
                        if (child.sum <= frontierHash.get(child.newHash()).sum) {
                            printResult(child, 0);
                            printResult(frontierHash.get(child.newHash()), 0);
                            return;
                        }
                    }
                    frontierRev.add(child);
                    inFrontierRev.put(child.hash(), true);

                    if (frontierHashRev.containsKey(child.newHash()) && (child.sum < frontierHashRev.get(child.newHash()).sum)) {
                        frontierHashRev.put(child.newHash(), child);
                    } else if ( !frontierHashRev.containsKey(child.newHash()) ) {
                        frontierHashRev.put(child.newHash(), child);
                    }
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