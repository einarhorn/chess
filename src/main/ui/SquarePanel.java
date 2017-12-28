package main.ui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * A square on the chess board panel
 * @author einar
 *
 */
public class SquarePanel extends JPanel {
	private int index;
	private int row, col;
	
	/**
	 * Creates an instance of a square on the chess board
	 * @param index		index of the square component
	 * @param row		row of the square
	 * @param col		column of the square
	 * @param layout	type of layout to use
	 */
	public SquarePanel(int index, int row, int col, LayoutManager layout) {
		super(layout);
		this.index = index;
		this.row = row;
		this.col = col;
	}
	
	public int getIndex() { return index; }
	
	public int getRow() { return row; }
	
	public int getCol() { return col; }
}
