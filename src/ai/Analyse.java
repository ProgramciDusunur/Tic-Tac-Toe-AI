package ai;

import java.util.List;

import gui.GUI;

public class Analyse {
	
	PositionalEvaluation p = new PositionalEvaluation();
	GUI gui = new GUI();
	double[] evaluation = p.boardEvaluation2;
	int symbolPatternSize = gui.getSymbolPatternSize()-1;
	
	/**
	 * <li>X = 1, O = 2, Empty Square = 0</li>
	 * @param board The Board
	 * @param whoIsNext If true calculate for X else calculate for O
	 * @return Positional value for negascout algorithm.
	 */	
	public double negaScoutScore(List<Integer> board, boolean whoIsNext) {			
		double score = 0;		
		for (int i = 0;i < board.size();i++) {
			if (whoIsNext) {
				if (board.get(i) == 1) {		
					if (symbolAlignmentCheck2(board, symbolPatternSize ,1, i)) {
						return Double.POSITIVE_INFINITY;
					}
					score += evaluation[i];
				} else if (board.get(i) == 2) {
					if (symbolAlignmentCheck2(board, symbolPatternSize, 2, i)) {
						return Double.NEGATIVE_INFINITY;
					}
					score -= evaluation[i];
				}
			}
			else {
				if (board.get(i) == 1) {
					if (symbolAlignmentCheck2(board, symbolPatternSize, 1, i)) {
						return Double.NEGATIVE_INFINITY;
					}
					score -= evaluation[i];
				} else if (board.get(i) == 2) {
					if (symbolAlignmentCheck2(board, symbolPatternSize, 2, i)) {
						return Double.POSITIVE_INFINITY;
					}
					score += evaluation[i];
				}
			}
		}		
		return score;
	}
	/**
	 * <li>X = 1, O = 2, Empty Square = 0.</li>
	 * @param board The Board.
	 * @param whoIsNext if true calculate for O else calculate for X.
	 * @return Positional value for minmax algorithm.
	 */
	public double minMaxScore(List<Integer> board, boolean whoIsNext) {		
		double score = 0;
		if (!whoIsNext) {
			if (symbolAlignmentCheck(board,symbolPatternSize ,1)) {
				return Double.POSITIVE_INFINITY;
			}
			
		} else {			
			if (symbolAlignmentCheck(board,symbolPatternSize ,2)) {
				return Double.NEGATIVE_INFINITY;
			}
		}
		for (int i = 0;i < board.size();i++) {
			if (!whoIsNext) {
				if (board.get(i) == 1) {
					score += evaluation[i];
				} else if (board.get(i) == 2) {
					score -= evaluation[i];
				}
			}
			else {
				if (board.get(i) == 1) {
					score -= evaluation[i];
				} else if (board.get(i) == 2) {
					score += evaluation[i];
				}
			}
		}		
		return score;
	}
	public boolean symbolAlignmentCheck(List<Integer> board, int symbolPatternSize, int whoIsNext) {		
		for (int i = 0;i < board.size();i++) {
			if (board.get(i) == whoIsNext && (symbolHorizontalCheck(board, i, symbolPatternSize, whoIsNext)
			|| symbolVerticalCheck(board, i, symbolPatternSize, whoIsNext) || symbolNorthWestAndSouthEastCheck(board, i, symbolPatternSize, whoIsNext)
			|| symbolSouthWestAndNorthEastCheck(board, i, symbolPatternSize, whoIsNext))) {			
				return true;
			}
		}
		return false;
	}
	public boolean symbolAlignmentCheck2(List<Integer> board, int symbolPatternSize, int whoIsNext, int boardIndex) {		
		return (symbolHorizontalCheck(board, boardIndex, symbolPatternSize, whoIsNext)
				|| symbolVerticalCheck(board, boardIndex, symbolPatternSize, whoIsNext) || symbolNorthWestAndSouthEastCheck(board, boardIndex, symbolPatternSize, whoIsNext)
				|| symbolSouthWestAndNorthEastCheck(board, boardIndex, symbolPatternSize, whoIsNext));
	}	
	public boolean symbolHorizontalCheck(List<Integer> board, int boardIndex, int symbolPatternSize, int whoIsNext) {				
		int row = boardIndex % p.gameArea;
		int rightRow = 0;
		int leftRow = 0;
		for (int i = 1;boardIndex+i < p.gameArea && i <= symbolPatternSize && board.get(boardIndex+i) == whoIsNext;i++, rightRow++);
		for (int i = 1;i <= row && i <= symbolPatternSize && board.get(boardIndex-i) == whoIsNext;i++, leftRow++);
		return leftRow == symbolPatternSize || rightRow == symbolPatternSize;
	}
	public boolean symbolVerticalCheck(List<Integer> board, int boardIndex, int symbolPatternSize, int whoIsNext) {
		int column = boardIndex / p.gameArea;
		int row = boardIndex % p.gameArea;
		int upColumn = 0;
		int downColumn = 0;		
		for (int i = column+1;i < p.gameArea && i-column <= symbolPatternSize && board.get(i*p.gameArea+row) == whoIsNext;i++, downColumn++);
		for (int i = column-1;i >= 0 && column-i <= symbolPatternSize  && board.get(i*p.gameArea+row) == whoIsNext;i--, upColumn++);		
		return upColumn == symbolPatternSize || downColumn == symbolPatternSize;
	}
	public boolean symbolNorthWestAndSouthEastCheck(List<Integer> board, int boardIndex, int symbolPatternSize, int whoIsNext) {
		int column = boardIndex / p.gameArea;
		int row = boardIndex % p.gameArea;
		int northWest = 0;
		int southEast = 0;
		for (int x = row-1, y = column-1;x >= 0 && y >= 0 && row-x <= symbolPatternSize && board.get(y*p.gameArea+x) == whoIsNext;x--, y--, northWest++);
		for (int x = row+1, y = column+1;x < p.gameArea && y < p.gameArea && x-row <= symbolPatternSize && board.get(y*p.gameArea+x) == whoIsNext;x++, y++);					
		return northWest == symbolPatternSize || southEast == symbolPatternSize;
	}
	public boolean symbolSouthWestAndNorthEastCheck(List<Integer> board, int boardIndex, int symbolPatternSize, int whoIsNext) {
		int column = boardIndex / p.gameArea;
		int row = boardIndex % p.gameArea;
		int northEast = 0;
		int southWest = 0;
		for (int x = row+1, y = column-1;x < p.gameArea && y >= 0 && x-row <= symbolPatternSize && board.get(y*p.gameArea+x) == whoIsNext;x++, y--, northEast++);
		for (int x = row-1, y = column+1;x >= 0 && y < p.gameArea && row-x <= symbolPatternSize && board.get(y*p.gameArea+x) == whoIsNext;x--, y++, southWest++);						
		return northEast == symbolPatternSize || southWest == symbolPatternSize;
	}	
}