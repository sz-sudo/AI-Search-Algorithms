package core;

import AI.informed.A_Star;
import AI.informed.IDA_Star;
import AI.uninformed.*;
import model.Board;
import model.Cell;
import model.Node;

import java.util.Hashtable;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        System.out.println(" pls enter rows and columns of your board : \n");
        Scanner sc = new Scanner(System.in);
        String mn = sc.nextLine();
        int rows = Integer.parseInt(mn.split(" ")[0]);
        int columns = Integer.parseInt(mn.split(" ")[1]);
        String[][] board = new String[rows][columns];
        String[] lines = new String[rows];
        for (int i = 0; i < rows; i++) {
            lines[i] = sc.nextLine();
            String[] line = lines[i].split(" ");
            if (columns >= 0) System.arraycopy(line, 0, board[i], 0, columns);
        }

        Mapper mapper = new Mapper();
        Cell[][] cells = mapper.createCells(board, rows, columns);
        Board gameBoard = mapper.createBoard(cells, rows, columns);
        Hashtable<String, Boolean> initHash = new Hashtable<>();
        //initHash.put(Cell.getStart().toString(), true);

        Hashtable<String, Boolean> initHashGoal = new Hashtable<>();

        Node start = new Node(Cell.getStart(), Cell.getStart().getValue(), Cell.getGoal().getValue(), gameBoard, null, initHash);
        //Node goal = new Node(Cell.getGoal(), 1, Cell.getGoal().getValue(), gameBoard, null, initHashGoal);
        Node goal = new Node(Cell.getGoal(), Cell.getGoal().getValue(), Cell.getGoal().getValue(), gameBoard, null, initHash);
        //goal.setlockGoal(true);


//        System.out.println("The result for BFS is:");
//        BFS bfs = new BFS();
//        bfs.search(start);
//        System.out.println("\n----------------------------");
//
//
//        System.out.println("The result for DFS is:");
//        DFS dfs = new DFS();
//        dfs.search(start);
//        System.out.println("\n----------------------------");


//        System.out.println("The result for DLS is:");
//        DLS dls = new DLS(12);
//        if (!dls.search(start))
//            System.out.println("No Solution!");
//        System.out.println("\n----------------------------");
//
//
//        System.out.println("The result for IDS is:");
//        IDS ids = new IDS(12, gameBoard, initHash);
//        ids.search();
//        System.out.println("\n----------------------------");


//        System.out.println("The result for BDS is:");
//        BDS bds = new BDS();
//        bds.search(start, goal);
//        System.out.println("\n----------------------------");


//        System.out.println("The result for A* is:");
//        A_Star as = new A_Star();
//        as.search(start);
//        System.out.println("\n----------------------------");
//
//        int cutoff = start.heuristic();
//        System.out.println("The result for IDA* is:");
//        IDA_Star idas = new IDA_Star();
//        idas.search(start, cutoff);
//

    }
}
