package AI;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;


import model.Node;


public class BFS {

    protected Queue<Node> frontier = new LinkedList<Node>();
    protected Hashtable<String, Boolean> inFrontier = new Hashtable<>();
    protected Hashtable<String, Boolean> explored = new Hashtable<>();

    public void search(Node startNode) {

        if (startNode.isGoal()) {
            System.out.println("score : " + startNode.sum);
            printResult(startNode, 0);
            return;
        }

        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);

        while (!frontier.isEmpty()) {

            Node temp = frontier.poll();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(), true);

            ArrayList<Node> children = temp.successor();

            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(explored.containsKey(child.hash()))) {

                    if (child.isGoal()) {
                        printResult(child, 0);
                        System.out.println(child.sum);
                        return;
                    }

                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
                }
            }
        }

        System.out.println("no solution");

    }

    public Queue<Node> getFrontier() {
        return frontier;
    }

    public void setFrontier(Queue<Node> frontier) {
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

        System.out.println(node.toString());
        node.drawState();
        printResult(node.parent, depthCounter + 1);
    }


}
