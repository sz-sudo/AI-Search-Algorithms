package AI;

import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class IDA_Star {

    protected Stack<Node> frontier = new Stack<>();
    protected Hashtable<String, Boolean> inFrontier = new Hashtable<>();
    //protected Hashtable<String, Boolean> explored = new Hashtable<>();
    protected Stack<String> exp = new Stack<>();


    public void search(Node startNode) {



        int min = Integer.MAX_VALUE;
        //Node temp = null;
        boolean s = true;
        int cutoff = startNode.heuristic();
        while (s) {
            exp.clear();
            frontier.add(startNode);
            inFrontier.put(startNode.hash(), true);
            //System.out.println(cutoff);
            min = Integer.MAX_VALUE;
            s = false;
            while (!frontier.isEmpty()) {

                Node temp = frontier.pop();


                if (temp != startNode) {
                    while (!temp.parent.hash().equals(exp.peek())) {
                        //System.out.println("kir shodim");
                        exp.pop();
                    }
                }


                inFrontier.remove(temp.hash());
                //explored.put(temp.hash(), true);
                exp.push(temp.hash());

                if (temp.isGoal()) {
                    printResult(temp, 0);
                    System.out.println(temp.sum);
                    return;
                }

                ArrayList<Node> children = temp.successor();

                for (Node child : children) {
                    if (!(inFrontier.containsKey(child.hash())) && !(exp.contains(child.hash()))) {

                        child.setCost(child.pathCost() + temp.getCost());

                        if (child.getCost() + child.heuristic() <= cutoff) {
                            frontier.add(child);
                            inFrontier.put(child.hash(), true);
                        } else {

                            s = true;
                            if (child.getCost() + child.heuristic() < min) {
                                min = child.getCost() + child.heuristic();
                            }
                        }
                    }
                }

            }
            cutoff = min;
        }


        System.out.println("no solution");
        return;
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
