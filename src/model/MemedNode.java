package model;

import java.util.Hashtable;

public class MemedNode  extends  Node{
    private int a;
    private int b ;


    public MemedNode(Cell currentCell, int currentValue, int goalValue, Board board, Node parent, Hashtable<String, Boolean> repeated) {
        super(currentCell, currentValue, goalValue, board, parent, repeated);

    }
    public int memedFunction(int start){
        return start*a+b;
    }
    private void reConsider(){
//        switch (currentCell.getOperationType()) {
//            case MINUS, DECREASE_GOAL -> 1;
//            case ADD, INCREASE_GOAL -> 2;
//            case MULT -> 3;
//            case POW -> 4;
//            default -> 0;
//        };
    }
}
