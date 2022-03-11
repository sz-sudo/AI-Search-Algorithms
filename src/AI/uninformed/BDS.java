package AI.uninformed;

import model.Node;

import java.util.*;

public class BDS {

    // for the search from start to goal
    protected Queue<Node> frontier = new LinkedList<Node>();
    protected Hashtable<String, ArrayList<Node>> frontierHash = new Hashtable<>();


    // for the search from goal to start
    protected Queue<Node> frontierRev = new LinkedList<Node>();
    protected Hashtable<String, ArrayList<Node>>  frontierHashRev = new Hashtable<>();



    public void search(Node startNode, Node goalNode) {
        ArrayList<Node> nodes = new ArrayList<>();

        frontier.add(startNode);
        nodes.add(startNode);
        frontierHash.put(startNode.toString(), nodes);
        nodes.clear();

        frontierRev.add(goalNode);
        nodes.add(goalNode);
        frontierHashRev.put(goalNode.toString(), nodes);

        if(frontierHash.containsKey(goalNode.toString())) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        while (!frontier.isEmpty() && !frontierRev.isEmpty()) {
            Node temp = frontier.poll();
            frontierHash.get(temp.toString()).remove(temp);

            ArrayList<Node> children = temp.successor();
            for (Node child : children) {
                Node ch = null;
                if (frontierHashRev.containsKey(child.toString())) {
                    ArrayList<Node> arr  = frontierHashRev.get(child.toString());
                    for (Node chN : arr) {
                        if (child.sum >= chN.sum) {
                            boolean isRepeated = false;
                            Set<String> keys = child.repeatedStates.keySet();
                            for (String key : keys) {
                                if (chN.repeatedStates.containsKey(key) && !Objects.equals(key, child.toString())) {
                                    isRepeated = true;
                                    break;
                                }
                            }
                            if(!isRepeated)
                                ch = chN;
                        }
                    }
                }

                if(ch != null) {
                    printResult(child, 0);
                    printResult(ch, 0);
                    ch = ch.parent;
                    while(ch.parent != null) {
                        child.sum = child.calculate(ch.currentCell);
                        ch = ch.parent;
                    }
                    System.out.println(child.sum);
                    return;
                }

                frontier.add(child);
                ArrayList<Node> x = new ArrayList<>();

                if (frontierHash.containsKey(child.toString())) {
                    x = frontierHash.get(child.toString());
                    x.add(child);
                    frontierHash.replace(child.toString(), x);

                } else {
                    x.add(child);
                    frontierHash.put(child.toString(), x);
                }
            }

            Node tempRev = frontierRev.poll();
            frontierHashRev.get(tempRev.toString()).remove(tempRev);

            ArrayList<Node> childrenRev = tempRev.newSuccessor();

            for (Node child : childrenRev) {
                Node ch = null;
                if (frontierHash.containsKey(child.toString())) {
                    ArrayList<Node> arr  = frontierHash.get(child.toString());
                    for (Node chN : arr) {
                        if (child.sum <= chN.sum) {
                            boolean isRepeated = false;
                            Set<String> keys = child.repeatedStates.keySet();
                            for (String key : keys) {
                                if (chN.repeatedStates.containsKey(key) && !Objects.equals(key, child.toString())) {
                                    isRepeated = true;
                                    break;
                                }
                            }
                            if(!isRepeated)
                                ch = chN;
                        }
                    }
                }

                if(ch != null) {
                    printResult(child, 0);
                    printResult(ch, 0);
                    child = child.parent;
                    while(child.parent != null) {
                        ch.sum = ch.calculate(child.currentCell);
                        child = child.parent;
                    }
                    System.out.println(ch.sum);
                    return;
                }

                frontierRev.add(child);
                ArrayList<Node> x = new ArrayList<>();

                if (frontierHashRev.containsKey(child.toString())) {
                    x = frontierHashRev.get(child.toString());
                    x.add(child);
                    frontierHashRev.replace(child.toString(), x);

                } else {
                    x.add(child);
                    frontierHashRev.put(child.toString(), x);
                }
            }


        }

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