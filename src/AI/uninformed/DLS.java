package AI.uninformed;

import AI.uninformed.DFS;
import model.Node;

import java.util.ArrayList;

public class DLS extends DFS {
    protected int currDepth = 0;
    protected int maxDepth;

    public DLS(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public boolean search(Node startNode) {
//        if (startNode.isGoal()) {
//            System.out.println("score : " + startNode.sum);
//            printResult(startNode, 0);
//            return true;
//        }
        startNode.setDepth(0);
        frontier.push(startNode);
        inFrontier.put(startNode.hash(), true);
        boolean first = true;

        while ((!frontier.isEmpty()) ) {
            Node temp = frontier.pop();



            currDepth = temp.getDepth();
            if( !(maxDepth >= currDepth) ){
                continue;
            }
            if (temp.isGoal() ) {
                printResult(temp, 0);
                System.out.println(temp.sum);
                //System.out.println(currDepth);
                return true;
            }

            if (!first) {
                while (!temp.parent.hash().equals(exp.peek())) {
                    //System.out.println("kir shodim");
                    exp.pop();
                }
            }


            inFrontier.remove(temp.hash());

            //explored.put(temp.hash(), true);
            exp.push(temp.hash());
            first = false;
            ArrayList<Node> children = temp.successor();

            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash())) && !(explored.containsKey(child.hash()))) {
                    child.setDepth(currDepth+1);
//                    if ( child.isGoal() && maxDepth >= child.getDepth() ) {
//                        printResult(child, 0);
//                        System.out.println(child.sum);
//                        //System.out.println(currDepth);
//                        return true;
//                    }
                    frontier.push(child);
                    inFrontier.put(child.hash(), true);
                }
            }
        }
        return false;

    }
}
