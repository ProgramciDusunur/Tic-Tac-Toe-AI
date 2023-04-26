package gui;

import java.text.DecimalFormat;
import java.util.List;

public class GUI {
	
	private boolean whoIsNext;
	private int gameArea = 12;
	private int symbolPatternSize = 5;

	public boolean isWhoIsNext() {
		return whoIsNext;
	}

	public void setWhoIsNext(boolean whoIsNext) {
		this.whoIsNext = whoIsNext;
	}

	public int getGameArea() {
		return gameArea;
	}

	public void setGameArea(int gameArea) {
		this.gameArea = gameArea;
	}

	public int getSymbolPatternSize() {
		return symbolPatternSize;
	}

	public void setSymbolPatternSize(int symbolPatternSize) {
		this.symbolPatternSize = symbolPatternSize;
	}
	
	public void seeOneDimensionBoard(List<Integer> board) {
		int arraySize = getGameArea();
		for (int y = 0;y < arraySize;y++) {			
			for (int x = 0;x < arraySize;x++) {
				System.out.print(board.get(y*arraySize+x) +" ");
			}
			System.out.println();
		}
		System.out.println();
	}	
	public void seeOneDimensionBoard(double[] board) {
		DecimalFormat df = new DecimalFormat("0.00");
		for (int y = 0;y < gameArea;y++) {
			System.out.print("|");
			for (int x = 0;x < gameArea;x++) {
				System.out.print(" " + df.format(board[y*gameArea+x]) + " |");
			}
			System.out.println();
			for (int i = 0; i < gameArea * 7 + 1; i++) {
	            System.out.print("-");
	        }
			System.out.println();
		}
	}
	public void seeTwoDimensionBoard(double[][] board) {
	    DecimalFormat df = new DecimalFormat("0.00");
	    for (int y = 0; y < board.length; y++) {
	        System.out.print("|");
	        for (int x = 0; x < board.length; x++) {
	            System.out.print(" " + df.format(board[x][y]) + " |");
	        }
	        System.out.println();
	        for (int i = 0; i < board.length * 7 + 1; i++) {
	            System.out.print("-");
	        }
	        System.out.println();
	    }
	}
}