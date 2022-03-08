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
        frontierHash.put(startNode.toString(), startNode);

        frontierRev.add(goalNode);
        inFrontierRev.put(goalNode.hash(), true);
        frontierHashRev.put(goalNode.toString(), goalNode);

        if (frontierHash.containsKey(goalNode.toString())) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        while (!frontier.isEmpty() && !frontierRev.isEmpty()) {

            Node temp = frontier.poll();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(), true);
            frontierHash.remove(temp.toString());

            ArrayList<Node> children = temp.successor();

            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(explored.containsKey(child.hash()))) {
                    if (frontierHashRev.containsKey(child.toString())) {
                        if (child.sum >= frontierHashRev.get(child.toString()).sum) {
                            if (!checkRepeated(child, frontierHashRev.get(child.toString()))) {
                                printResult(child, 0);
                                printResult(frontierHashRev.get(child.toString()), 0);
                                System.out.println(child.sum);
                                return;
                            }
                        }
                    }

                    frontier.add(child);
                    inFrontier.put(child.hash(), true);

                    if (frontierHash.containsKey(child.toString()) && (child.sum > frontierHash.get(child.toString()).sum)) {
                        frontierHash.put(child.toString(), child);
                    } else if ( !frontierHash.containsKey(child.toString()) ) {
                      frontierHash.put(child.toString(), child);
                    }
                }
            }

            Node tempRev = frontierRev.poll();
            inFrontierRev.remove(tempRev.hash());
            exploredRev.put(tempRev.hash(), true);
            frontierHashRev.remove(tempRev.toString());

            ArrayList<Node> childrenRev = tempRev.newSuccessor();

            for (Node child : childrenRev) {
                if (!(inFrontierRev.containsKey(child.hash())) && !(exploredRev.containsKey(child.hash()))) {
                    if (frontierHash.containsKey(child.toString())) {
                        if (child.sum <= frontierHash.get(child.toString()).sum) {
                            if (!checkRepeated(child,frontierHash.get(child.toString()))) {
                                printResult(child, 0);
                                printResult(frontierHash.get(child.toString()), 0);
                                return;
                            }
                        }
                    }

                    frontierRev.add(child);
                    inFrontierRev.put(child.hash(), true);

                    if (frontierHashRev.containsKey(child.toString()) && (child.sum < frontierHashRev.get(child.toString()).sum)) {
                        frontierHashRev.put(child.toString(), child);
                    } else if ( !frontierHashRev.containsKey(child.toString()) ) {
                        frontierHashRev.put(child.toString(), child);
                    }
                }
            }
        }

        System.out.println("no solution");
    }

    public boolean checkRepeated(Node child, Node frontierHash) {
        Set<String> keys = child.repeatedStates.keySet();
        for (String key : keys) {
            if(frontierHash.repeatedStates.containsKey(key)) {
                if (!child.toString().equals(key)) {
                    return true;
                }
            }
        }
        return false;
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