package AI.uninformed;

import AI.uninformed.DLS;
import model.Board;
import model.Cell;
import model.Node;

import java.util.Hashtable;

public class IDS {
    private int maxDepth = Integer.MAX_VALUE;
    private Node node;
    private Board gameBoard;
    private  Hashtable<String, Boolean> initHash;

    public IDS(int maxDepth, Board gameBoard, Hashtable<String, Boolean> initHash) {
        this.maxDepth = maxDepth;
        this.gameBoard = gameBoard;
        this.initHash = initHash;
    }

    public void search(){
        for (int i = 0; i<=maxDepth; i++) {
            this.node= new Node(Cell.getStart(), Cell.getStart().getValue(), Cell.getGoal().getValue(), gameBoard, null, initHash);
            DLS dls = new DLS(i);
            if(dls.search(node)){
                //System.out.println("Answer is in depth: "+i);
                return;
            }
            dls = null;
        }
        System.out.println("no solution");

    }

}
