package test;

import java.util.ArrayList;
import java.util.List;

import gui.GUI;

public class Perft {
		
	GUI gui = new GUI();
	int arraySize = gui.getGameArea();
	long maximumDepth = 0, nodes = 0;
	int[] graphicBoard = new int[arraySize];
	List<Integer> board = initBoard(graphicBoard);
	
	public Perft() {						
		countTargetDepth(board, false, 0, 11);		
	}
	public static void main(String[] args) {
		new Perft();
	}
	public List<Integer> initBoard(int[] board) {
		List<Integer> moves = new ArrayList<>(arraySize);
		for (int i = 0;i < board.length;i++) {			
			moves.add(0);
		}
		return moves;
	}
	public List<Integer> getMoves(List<Integer> board) {
		List<Integer> moves = new ArrayList<>(arraySize);
		for (int i = 0;i < arraySize;i++) {
			if (board.get(i) == 0) {
				moves.add(i+1);
			}			
		}		
		return moves;		
	}
	/**
	 * 
	 * @param board Calculating board.
	 * @param xORo If parameter true calculate first X else calculate for O.
	 * @param depth Current depth of recursive.
	 * @param maximumDepth Maximum depth of recursive.
	 * @return Value is the target depth for all nodes in the tree.
	 */
	public long countTargetDepth(List<Integer> board, boolean xORo, int depth, int maximumDepth) {
		this.maximumDepth = maximumDepth;
		this.nodes = 0;			
		long mili = System.nanoTime();
		perftRoot(board, xORo, 0);
		System.out.println("Target Depth: "+maximumDepth);
		System.out.println("Nodes: "+nodes);
		double kacSaniye = ((System.nanoTime() - mili)/ 1000000000.0);
        System.out.println(kacSaniye);
        System.out.println("Nodes In Second = "+(int)(nodes/kacSaniye));
        return nodes;
	}
	public long perftRoot(List<Integer> board, boolean xORo, int depth) {		
		List<Integer> moves = getMoves(board);		
		for (int i : moves) {
			makeMove(board,i, xORo);
			if (depth+1==maximumDepth) {nodes++;}
			perft(board, !xORo, depth+1);
			unMakeMove(board,i);
			
		}
		return nodes;
	}
	public long perft(List<Integer> board, boolean xORo, int depth) {
		if (depth < maximumDepth) {
			List<Integer> moves = getMoves(board);
			for (int i : moves) {
				makeMove(board,i, xORo);
				if (depth+1==maximumDepth) {nodes++;}
				perft(board, !xORo, depth+1);
				unMakeMove(board,i);
			}
		}			
		return nodes;
	}		
	public void makeMove(List<Integer> board, int targetSquare, boolean xORo) {
		if (xORo) {			
			board.set(targetSquare-1, 1);
		} else {			
			board.set(targetSquare-1, 2);
		}
	}
	public void unMakeMove(List<Integer> board, int targetSquare) {
		board.set(targetSquare-1, 0);
	}
	public void seeBoard(List<Integer> board) {
		int arraySize = gui.getGameArea();
		for (int y = 0;y < arraySize;y++) {			
			for (int x = 0;x < arraySize;x++) {
				System.out.print(board.get(y*arraySize+x) +" ");
			}
			System.out.println();
		}
		System.out.println();
	}	
}