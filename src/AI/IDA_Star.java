package AI;

import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;

public class IDA_Star {

    protected ArrayList<Node> frontier = new ArrayList();
    protected Hashtable<String, Boolean> inFrontier = new Hashtable<>();
    protected Hashtable<String, Boolean> explored = new Hashtable<>();

    public void search(Node startNode) {


        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);

        int min = Integer.MAX_VALUE;
        Node temp = null;

        while (!frontier.isEmpty()) {
            min = Integer.MAX_VALUE;

            for (Node node : frontier) {
                if (node.getCost() + node.heuristic() < min) {
                    min = node.getCost() + node.heuristic();
                    temp = node;
                }
            }


            //Node temp = frontier.get(0);
            frontier.remove(temp);
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(), true);

            if (temp.isGoal()) {
                printResult(temp, 0);
                System.out.println(temp.sum);
                return;
            }

            ArrayList<Node> children = temp.successor();

            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(explored.containsKey(child.hash()))) {

                    child.setCost(child.pathCost() + temp.getCost());

                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
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
