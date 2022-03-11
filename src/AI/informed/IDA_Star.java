package AI.informed;

import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class IDA_Star {


    public void search(Node startNode, int cutoff) {
        Stack<Node> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Stack<String> exp = new Stack<>();
        int min = Integer.MAX_VALUE;
        boolean s = false;

            frontier.add(startNode);
            inFrontier.put(startNode.hash(), true);


            while (!frontier.isEmpty()) {
                Node temp = frontier.pop();

                if (temp != startNode) {
                    while (!temp.parent.hash().equals(exp.peek())) {
                        //System.out.println("I'm in");
                        exp.pop();
                    }
                }


                inFrontier.remove(temp.hash());
                exp.push(temp.hash());

                if (temp.isGoal()) {
                    printResult(temp, 0);
                    System.out.println(temp.sum);
                    System.exit(0);
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


            if (s)
                search(startNode, cutoff);
        System.out.println("no solution");
        System.exit(0);

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
