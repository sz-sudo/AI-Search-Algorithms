package AI;

import java.util.Stack;
import java.util.ArrayList;
import java.util.Hashtable;

import model.Node;

public class DFS {
    protected int currDepth =0;
    protected Stack <Node> frontier = new Stack<Node>();
    protected Hashtable<String, Boolean> inFrontier = new Hashtable<>();
    protected Hashtable<String, Boolean> explored = new Hashtable<>();
    protected Stack<String> exp = new Stack<>();


    public boolean search(Node startNode) {

        frontier.push(startNode);
        inFrontier.put(startNode.hash(), true);

        boolean first = true;
        while (!frontier.isEmpty()) {

//            if (startNode.isGoal()) {
//                System.out.println("score : " + startNode.sum);
//                printResult(startNode, 0);
//                return true;
//            }

            Node temp = frontier.pop();

            if (temp.isGoal()) {
                System.out.println("explored size : "+ exp.size());   // linear space complexity
                printResult(temp, 0);
                System.out.println(temp.sum);
                return true;
            }


            if (!first) {
                while (!temp.parent.hash().equals(exp.peek())) {
                    //System.out.println("kir shodim");
                    exp.pop();
                }
            }

            currDepth = temp.getDepth();
            inFrontier.remove(temp.hash());

            //explored.put(temp.hash(), true);
            exp.push(temp.hash());
            first = false;

            ArrayList<Node> children = temp.successor();

            //System.out.println(children);

            currDepth++;

            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(exp.contains(child.hash()))) {
                    child.setDepth(currDepth);



                    frontier.push(child);
                    inFrontier.put(child.hash(), true);
                }
            }
        }
        System.out.println("no solution");
        return false;
    }

    public Stack<Node> getFrontier() {
        return frontier;
    }

    public void setFrontier(Stack<Node> frontier) {
        this.frontier = frontier;
    }

    public Hashtable<String, Boolean> getInFrontier() {
        return inFrontier;
    }

    public void setInFrontier(Hashtable<String, Boolean> inFrontier) {
        this.inFrontier = inFrontier;
    }

    public Hashtable<String, Boolean> getExplored() {
        return explored;
    }

    public void setExplored(Hashtable<String, Boolean> explored) {
        this.explored = explored;
    }


    public void printResult(Node node, int depthCounter) {
        if (node.parent == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }

        System.out.println(node.toString(true));
        node.drawState();
        printResult(node.parent, depthCounter + 1);
        //System.out.println(node.getDepth());
    }
}
