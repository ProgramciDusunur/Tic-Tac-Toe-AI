package ai;

import gui.GUI;

public class PositionalEvaluation {
		
	GUI gui = new GUI();
	int gameArea = gui.getGameArea();
	
	double[][] boardEvaluation = new double[gameArea][gameArea];
	double[] boardEvaluation2 = new double[gameArea*gameArea];
	
	public PositionalEvaluation() {
		this.boardEvaluation = evaluation(boardEvaluation);
		this.boardEvaluation2 = toOneDimensional(boardEvaluation);		
	}		
	/**
	 * <li>Method to close best variants for winning.</li>
	 * @param board The Board.
	 * @return Positional values for squares.
	 */
	public double[][] evaluation(double[][] board) {
		int length = board.length/2, doubleOrSingle = 0;
		if (board.length % 2 == 1) {			
			board[length][length] = 1;
			doubleOrSingle = 0;
		} else {
			board[length][length] = 1;
			board[length-1][length-1] = 1;
			board[length-1][length] = 1;
			board[length][length-1] = 1;
			doubleOrSingle = -1;
		}
		for (int x = 2, y = 2;x < length+doubleOrSingle && y < length+doubleOrSingle;x++,y++) {			
			for (int x2 = x;x2 < board.length-x;x2++) {				
				board[x2][y] = 1.0/(length+doubleOrSingle-x+1);								
			}			
			for (int y2 = y;y2 < board.length-y;y2++) {				
				board[x][y2] = 1.0/(length+doubleOrSingle-x+1);
				
			}
			for (int x3 = x;x3 < board.length-x;x3++) {			
				board[x3][board.length-y-1] = 1.0/(length+doubleOrSingle-x+1);
			}
			for (int y3 = y;y3 < board.length-y;y3++) {				
				board[board.length-x-1][board.length-y3-1] = 1.0/(length+doubleOrSingle-x+1);
			}
		}
		return board;
	}
	public double[] toOneDimensional(double[][] evaluation) {
		double[] oneDimension = new double[gameArea*gameArea];
		for (int y = 0;y < gameArea;y++) {
			for (int x = 0;x < gameArea;x++) {
				oneDimension[y*gameArea+x] = evaluation[x][y];
			}
		}
		return oneDimension;
	}
}