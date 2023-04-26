package board;

import javax.swing.JFrame;
import javax.swing.JLabel;

import graphic.Graphic;

public class Board  {
	public JFrame f = new JFrame();
	public JLabel label1 = new JLabel("Test");
	public Graphic graphic = new Graphic();
	
	public Board() {		
		f.setSize(1000,1000);
		f.setResizable(false);
		f.setVisible(true);	
		f.add(graphic);
		f.setTitle("TicTacToe");		
	}	
	public static void main(String[] args) {
		new Board();
	}	
}