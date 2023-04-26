package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

import ai.Analyse;
import ai.Engine;
import board.Tools;
import gui.GUI;

public class Graphic  extends JPanel implements MouseListener, MouseMotionListener {
		
	private static final long serialVersionUID = 6811924337320518574L;
	private transient GUI gui = new GUI();
	private transient Engine engine = new Engine();
	private transient Tools t = new Tools(true);
	private transient Analyse a = new Analyse();
	int gameArea = gui.getGameArea();	
	int[] graphicBoard = new int[gameArea*gameArea];
	private List<Integer> board = engine.initBoard(graphicBoard);
	
	int maximumPanelWidth = 900;
	int maximumPanelHeight = 900;
	int gameContainerLastX = maximumPanelWidth*90/100;
	int gameContainerLastY = maximumPanelHeight*90/100;
	int gameContainerFirstX = maximumPanelWidth-gameContainerLastX;
	int gameContainerFirstY = maximumPanelHeight-gameContainerLastY;	
	int squareSizeX = (gameContainerLastX-(maximumPanelWidth-gameContainerLastX))/gameArea; 
	int squareSizeY = (gameContainerLastY-(maximumPanelHeight-gameContainerLastY))/gameArea;
	int ovalFirstX = gameContainerFirstX + (squareSizeX * 13 / 100);
	int ovalFirstY = gameContainerFirstY + (squareSizeY * 13 / 100);
	int ovalSizeX = squareSizeX - (squareSizeX * 25 / 100);
	int ovalSizeY = squareSizeY - (squareSizeY * 25 / 100);
	int lineFirstX = gameContainerFirstX + (squareSizeX * 13 / 100);
	int lineFirstY = gameContainerFirstY + (squareSizeY * 13 / 100);
	int lineLastX = lineFirstX + (squareSizeX * 75 / 100);
	int lineLastY = lineFirstY + (squareSizeY * 75 / 100);
	boolean xORo = true;	
	
	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);		
		this.setBackground(Color.DARK_GRAY);
		if (t.isMouseListeneri()) {
			this.addMouseListener(this);		
	        this.addMouseMotionListener(this);
			t.setMouseListener(false);
		}	
		g.setColor(Color.WHITE);		
		g.drawRect(gameContainerFirstX, gameContainerFirstY, gameContainerLastX-(maximumPanelWidth-gameContainerLastX), gameContainerLastY-(maximumPanelHeight-gameContainerLastY));		
		for (int j = 1, rectFirstY = gameContainerFirstY;j <= gameArea;rectFirstY = squareSizeY * j + gameContainerFirstY, j++) {
			for (int i = 1, rectFirstX = gameContainerFirstX;i <= gameArea;rectFirstX = squareSizeX * i + gameContainerFirstX, i++) {						
				g.drawRect(rectFirstX, rectFirstY, squareSizeX, squareSizeY);				
			}			
		}		
		board.set(65, 2);				
		drawX(g, 65%gui.getGameArea(), 65/gui.getGameArea());	
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {		
		Graphics g = getGraphics();
		g.setColor(Color.white);
		if (e.getX() >= gameContainerFirstX && e.getX() <= gameContainerLastX && e.getY() >= gameContainerFirstY && e.getY() <= gameContainerLastY) {			
			int whichGrillX = (e.getX()-maximumPanelWidth*10/100)/squareSizeX, whichGrillY = (e.getY()-maximumPanelHeight*10/100)/squareSizeY;
			int whichSquare = whichGrillY*gui.getGameArea()+whichGrillX;					
			if (board.get(whichSquare) == 0) {
				if (xORo) {
					drawO(g, whichGrillX ,whichGrillY);
					board.set(whichSquare, 2);
					int bestMove = engine.bestMoveInTargetDepth(board, howManySquareBlank(board), xORo);
					board.set(bestMove, 1);
					gameStatus();
					int bestMoveY = bestMove / gameArea;
					int bestMoveX = bestMove % gameArea;
					drawX(g, bestMoveX ,bestMoveY);
				} else {
					drawX(g, whichGrillX, whichGrillY);
					board.set(whichSquare, 1);					
					int bestMove = engine.bestMoveInTargetDepth(board, howManySquareBlank(board), xORo);
					board.set(bestMove, 2);
					int bestMoveY = bestMove / gameArea;
					int bestMoveX = bestMove % gameArea;
					drawO(g, bestMoveX ,bestMoveY);
				}
			}			
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
				
	}
	@Override
	public void mouseEntered(MouseEvent e) {
				
	}
	@Override
	public void mouseExited(MouseEvent e) {
				
	}
	
	public int getGameArea() {
		return gameArea;
	}
	public void setGameArea(int gameArea) {
		this.gameArea = gameArea;
	}
	public int howManySquareBlank(List<Integer> board) {
		int square = 0;
		for (int i = 0;i < board.size();i++) {
			if (board.get(i) == 0) {
				square++;
			}
		}
		if (board.size() > 14 && square > 5) {
			square = 4;
		}		
		return square;
	}
	public void drawX(Graphics g, int whichGrillX, int whichGrillY) {
		int drawLineFirstX = lineFirstX+squareSizeX*whichGrillX;
		int drawLineFirstY = lineFirstY+squareSizeY*whichGrillY;
		int drawLineLastX = drawLineFirstX + (squareSizeX * 75 / 100);
		int drawLineLastY = drawLineFirstY + (squareSizeY * 75 / 100);
		g.drawLine(drawLineFirstX, drawLineFirstY, drawLineLastX, drawLineLastY);
		g.drawLine(drawLineFirstX, drawLineLastY, drawLineLastX, drawLineFirstY);
	}
	public void drawO(Graphics g, int whichGrillX, int whichGrillY) {
		int drawOvalFirstX = ovalFirstX+squareSizeX*whichGrillX;
		int drawOvalFirstY = ovalFirstY+squareSizeY*whichGrillY;
		g.drawOval(drawOvalFirstX, drawOvalFirstY, ovalSizeX, ovalSizeY);
	}
	public void gameStatus() {
		Graphics g = getGraphics();
		Font font = new Font("Tahoma", Font.BOLD, 20);		
		g.setFont(font);
		if (a.symbolAlignmentCheck(board, 4, 2)) {
			g.setColor(Color.GREEN);
			g.drawString("You Win, Restart App", 335, 50);
		} else if (a.symbolAlignmentCheck(board, 4, 1)) {
			g.setColor(Color.RED);			
			g.drawString("You Lost, Restart App", 335, 50);
		}
	}

}
