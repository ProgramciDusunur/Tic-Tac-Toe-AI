package ai;

import java.util.ArrayList;
import java.util.List;

import gui.GUI;

public class Engine {
		
	GUI gui = new GUI();
	Analyse a = new Analyse();
	int arraySize = gui.getGameArea()*gui.getGameArea();
	int alphaMove;
	long nodes = 0;
	int[] graphicBoard = new int[gui.getGameArea()*gui.getGameArea()];
	List<Integer> board = initBoard(graphicBoard);
		
	public int bestMoveInTargetDepth(List<Integer> board, int maximumDepth, boolean whoIsNext) {
		nodes = 0;		
		double minmax = 0;
		long mili = System.nanoTime();		
		for (int depth = 1;depth <= maximumDepth;depth++) {
			System.out.println("Target Depth: "+depth);
			minmax = negascoutRoot(board, whoIsNext, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if (minmax == Double.POSITIVE_INFINITY) {
				System.out.println("Winning");
				break;
			}
		}
		if (minmax == Double.NEGATIVE_INFINITY) {
			System.out.println("Computer Resigns");
		}
		System.out.println("Minmax: "+minmax);
		System.out.println("Nodes: "+nodes);
		double howManySeconds = ((System.nanoTime() - mili)/ 1000000000.0);
        System.out.println(howManySeconds);
        System.out.println("Nodes In Second = "+(int)(nodes/howManySeconds));
		return alphaMove;		
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
	public double negascoutRoot(List<Integer> board, boolean whoIsNext, int depth, double alpha, double beta) {
		List<Integer> moves = getMoves(board);
		double max = Double.NEGATIVE_INFINITY;
		for (int i : moves) {
			makeMove(board,i, whoIsNext);
			double score = Math.max(max, -negascout(board, !whoIsNext, depth-1 , -beta, -alpha));
			alpha = Math.max(alpha, score);
			unMakeMove(board,i);
			if (score > max) {
				max = score;
				System.out.println("Alpha move: "+ i);
				alphaMove = i-1;
			}			
		}
		return max;
	}
	public double negascout(List<Integer> board, boolean whoIsNext, int depth, double alpha, double beta) {
		if (depth == 0) {
			nodes++;
			return a.negaScoutScore(board,whoIsNext);
		}
		List<Integer> moves = getMoves(board);		
		double score = Double.NEGATIVE_INFINITY;
		for (int i : moves) {
			makeMove(board,i, whoIsNext);
			score = Math.max(score, -negascout(board, !whoIsNext, depth-1 , -beta, -alpha));
			alpha = Math.max(alpha, score);
			unMakeMove(board,i);
			 if (beta <= alpha) {
		         return beta;
		     }			
		}
		return score;
	}	
	public double minmaxRoot(List<Integer> board, boolean whoIsNext, int depth, double alpha, double beta) {		
		List<Integer> moves = getMoves(board);		
		double max = Double.NEGATIVE_INFINITY;
		for (int i : moves) {
			makeMove(board, i, whoIsNext);			
			double score = min(board, !whoIsNext, depth-1, alpha, beta);
			if(score > max) {
	        	max = score;
	        	System.out.println("Alpha move: "+(i-1));
	        	alphaMove = i-1;
	        }			
			unMakeMove(board,i);			
		}
		return max;
	}
	public double min(List<Integer> board, boolean whoIsNext, int depth, double alpha, double beta) {
	    if (depth == 0) {
	        nodes++;
	        return a.minMaxScore(board, whoIsNext);
	    }
	    List<Integer> moves = getMoves(board);
	    double min = Double.POSITIVE_INFINITY;
	    for (int i : moves) {
	        makeMove(board, i, whoIsNext);
	        min = max(board, !whoIsNext, depth - 1, alpha, beta);
	        unMakeMove(board, i);
	        beta = Math.min(beta, min);
	        if (beta <= alpha) {
	            return beta;
	        }	      
	    }
	    return min;
	}

	public double max(List<Integer> board, boolean whoIsNext, int depth, double alpha, double beta) {
	    if (depth == 0) {
	        nodes++;
	        return a.minMaxScore(board, whoIsNext);
	    }
	    List<Integer> moves = getMoves(board);
	    double max = Double.NEGATIVE_INFINITY;
	    for (int i : moves) {
	        makeMove(board, i, whoIsNext);
	        max = min(board, !whoIsNext, depth - 1, alpha, beta);
	        unMakeMove(board, i);
	        alpha = Math.max(alpha, max);
	        if (alpha >= beta) {
	            return alpha;
	        }	       
	    }
	    return max;
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
}