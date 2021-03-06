package model;

import core.Constants;

import java.util.ArrayList;
import java.util.Hashtable;

public class Node {
    public Board board;
    public int sum = 0;
    public Node parent;public Cell currentCell;
    private Cell[][] cells;
    private int goalValue;
    private int depth = 0;
    private int cost = 0;
    public Hashtable<String, Boolean> repeatedStates;
    //private boolean lockGoal = false;


    public Node(Cell currentCell, int currentValue, int goalValue, Board board, Node parent, Hashtable<String, Boolean> repeated) {
        this.currentCell = currentCell;
        this.sum = currentValue;
        this.board = board;
        this.cells = board.getCells();
        this.parent = parent;
        this.goalValue = goalValue;
        this.cost = 0;
        Hashtable<String, Boolean> hashtableTemp = new Hashtable<String, Boolean>(repeated);
        hashtableTemp.put(this.toString(), true);
        this.repeatedStates = hashtableTemp;
        setGoalValue();

        //repeated.put(this.toString(), true);
    }


    public ArrayList<Node> successor() {
        ArrayList<Node> result = new ArrayList<Node>();
        if (canMoveRight()) {
            Cell rightCell = this.cells[this.currentCell.i][this.currentCell.j + 1];
            if (isValidMove(rightCell)) {
                int calculatedValue = calculate(rightCell);
                Node rightNode = new Node(rightCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(rightNode);
            }
        }
        if (canMoveLeft()) {
            Cell leftCell = this.cells[this.currentCell.i][this.currentCell.j - 1];
            if (isValidMove(leftCell)) {
                int calculatedValue = calculate(leftCell);
                Node leftNode = new Node(leftCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(leftNode);
            }
        }
        if (canMoveDown()) {
            Cell downCell = this.cells[this.currentCell.i + 1][this.currentCell.j];
            if (isValidMove(downCell)) {
                int calculatedValue = calculate(downCell);
                Node downNode = new Node(downCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(downNode);
            }

        }
        if (canMoveUp()) {
            Cell upCell = this.cells[this.currentCell.i - 1][this.currentCell.j];
            if (isValidMove(upCell)) {
                int calculatedValue = calculate(upCell);
                Node upNode = new Node(upCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(upNode);
            }
        }
        return result;
    }

    public ArrayList<Node> newSuccessor() {
        ArrayList<Node> result = new ArrayList<Node>();
        if (canMoveRight()) {
            Cell rightCell = this.cells[this.currentCell.i][this.currentCell.j + 1];
            if (isValidMove(rightCell)) {
                int calculatedValue = calculateRev(currentCell);
                Node rightNode = new Node(rightCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(rightNode);
            }
        }
        if (canMoveLeft()) {
            Cell leftCell = this.cells[this.currentCell.i][this.currentCell.j - 1];
            if (isValidMove(leftCell)) {
                int calculatedValue = calculateRev(currentCell);
                Node leftNode = new Node(leftCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(leftNode);
            }
        }
        if (canMoveDown()) {
            Cell downCell = this.cells[this.currentCell.i + 1][this.currentCell.j];
            if (isValidMove(downCell)) {
                int calculatedValue = calculateRev(currentCell);
                Node downNode = new Node(downCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(downNode);
            }

        }
        if (canMoveUp()) {
            Cell upCell = this.cells[this.currentCell.i - 1][this.currentCell.j];
            if (isValidMove(upCell)) {
                int calculatedValue = calculateRev(currentCell);
                Node upNode = new Node(upCell, calculatedValue, goalValue, board, this, repeatedStates);
                result.add(upNode);
            }
        }
        return result;
    }

    private boolean canEnterGoal(Cell downCell) {
        if (downCell != Cell.getGoal()) return true;
        else {
            return sum >= goalValue;
        }

    }


    public int calculate(Cell cell) {
        return switch (cell.getOperationType()) {
            case MINUS -> sum - cell.getValue();
            case ADD -> sum + cell.getValue();
            case POW -> (int) Math.pow(sum, cell.getValue());
            case MULT -> sum * cell.getValue();
            default -> sum;
        };

    }

    public int calculateRev(Cell cell) {
        return switch (cell.getOperationType()) {
            case MINUS, INCREASE_GOAL -> sum + cell.getValue();
            case ADD, DECREASE_GOAL -> sum - cell.getValue();
            case POW -> (int) Math.ceil(Math.pow(sum, 1/cell.getValue()));
            case MULT -> (int) Math.ceil(sum / cell.getValue());
            default -> sum;
        };
    }

    public int calculate(Cell cell,int customVal) {
        return switch (cell.getOperationType()) {
            case MINUS -> customVal - cell.getValue();
            case ADD -> customVal + cell.getValue();
            case POW -> (int) Math.pow(customVal, cell.getValue());
            case MULT -> customVal * cell.getValue();
            default -> customVal;
        };

    }
    private boolean isWall(Cell cell) {
        return cell.getOperationType() == OPERATION_TYPE.WALL;
    }

    private boolean canMoveRight() {
        return this.currentCell.j < this.board.getCol() - 1;
    }

    private boolean canMoveLeft() {
        return this.currentCell.j > 0;
    }

    private boolean canMoveUp() {
        return this.currentCell.i > 0;
    }

    private boolean canMoveDown() {
        return this.currentCell.i < this.board.getRow() - 1;
    }

    private Boolean isValidMove(Cell destCell) {
        return destCell != Cell.getStart() && canEnterGoal(destCell) && !isWall(destCell) && !repeatedStates.containsKey(destCell.toString());
    }

    public boolean isGoal() {
        if (currentCell.getOperationType() == OPERATION_TYPE.GOAL) {
            return sum >= goalValue;
        }
        return false;
    }

    public boolean isStart() {
        if (currentCell.getOperationType() == OPERATION_TYPE.START) {
            return sum >= goalValue;
        }
        return false;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost += cost;
    }

    public int pathCost() {
        return switch (currentCell.getOperationType()) {
            case MINUS, INCREASE_GOAL, GOAL -> 1;
            case ADD, DECREASE_GOAL -> 2;
            case MULT -> 5;
            case POW -> 11;
            default -> 0;
        };

    }

    public int heuristic() {
        return ( Math.abs(Cell.getGoal().i - currentCell.i) + Math.abs(Cell.getGoal().j - currentCell.j) ) ;
    }

    public String hash() {
        StringBuilder hash = new StringBuilder();
        hash.append("i:")
                .append(currentCell.i)
                .append("j:")
                .append(currentCell.j)
                .append("sum:")
                .append(sum)
                .append("op:")
                .append(currentCell.op)
                .append("val:")
                .append(currentCell.getValue());

        return hash.toString();
    }


    public String newHash() {
        StringBuilder hash = new StringBuilder();
        hash.append("i:")
                .append(currentCell.i)
                .append("j:")
                .append(currentCell.j)
                .append("op:")
                .append(currentCell.op)
                .append("val:")
                .append(currentCell.getValue());

        return hash.toString();
    }

    public void drawState() {

        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                if (cells[i][j].getOperationType() == OPERATION_TYPE.GOAL) {
                    System.out.print(Constants.ANSI_BRIGHT_GREEN +
                            OPERATION_TYPE.getOperationTag(cells[i][j].getOperationType())
                            + goalValue + spaceRequired(cells[i][j])
                    );
                    continue;
                }
                if (currentCell.j == j && currentCell.i == i) {
                    System.out.print(Constants.ANSI_BRIGHT_GREEN + Constants.PLAYER + sum + spaceRequired(cells[i][j]));
                } else {
                    System.out.print(Constants.ANSI_BRIGHT_GREEN +
                            OPERATION_TYPE.getOperationTag(cells[i][j].getOperationType())
                            + cells[i][j].getValue() + spaceRequired(cells[i][j])
                    );
                }
            }
            System.out.println();

        }
        System.out.println("-----------------------------------------");

    }


    public int getGoalValue() {
        return goalValue;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Hashtable<String, Boolean> getRepeatedStates() {
        if (this.repeatedStates == null)
            return new Hashtable<String, Boolean>();
        return repeatedStates;
    }

    public void setRepeatedStates(Hashtable<String, Boolean> repeatedStates) {
        this.repeatedStates = repeatedStates;
    }

    private void setGoalValue() {

        if (currentCell.getOperationType() == OPERATION_TYPE.DECREASE_GOAL)
            goalValue -= currentCell.getValue();
        if (currentCell.getOperationType() == OPERATION_TYPE.INCREASE_GOAL)
            goalValue += currentCell.getValue();
    }

    private String spaceRequired(Cell cell) {
        int length = String.valueOf(cell.getValue()).length();
        String result = " ".repeat(5 - length);
        if (cell.op.equals("+") || cell.op.equals("-") || cell.op.equals("*") || cell.op.equals("^")) {
            result += " ";
        }
        return result;
    }

    @Override
    public String toString() {
        return "(" + this.currentCell.i + "," + this.currentCell.j + ")";
    }

//    public String toString(boolean ...x) {
//        return "(" + (this.currentCell.i+1) + "," + (this.currentCell.j+1) + ")";
//    }
}
