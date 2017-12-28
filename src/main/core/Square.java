package main.core;

import main.pieces.Piece;

/**
 * Square class
 * A square is one of the 64 squares on a main.core board
 * A square can optionally have a Piece on it
 * @author einar
 *
 */
public class Square {
	
	// Piece variables
	private boolean hasPiece;
	private Piece piece;
	
	// Position variables
	private int row;
	private int col;
	private Board board;

	/**
	 * Initialize a square without a piece on it
	 * @param row		The row on which this square rests
	 * @param col		The column on which this square rests
	 * @param board		The board on which this square rests
	 */
	public Square(int row, int col, Board board) {
		this.hasPiece = false;
		this.piece = null;
		this.row = row;
		this.col = col;
		this.board = board;
	}
	
	/*******************
	 * PUBLIC METHODS
	 * 
	 *******************/

	/**
	 * Print the main.core string representation of the position on the square (e.g. "a1")
	 * @return String representation of the location of the square
	 */
	public String toString() {
		char colChar = (char) ('a' + col);
		return colChar + Integer.toString(board.getNumRows() - row);
	}
	
	/**
	 * Returns true if the square has a piece on it
	 * @return true if the square has a piece on it
	 */
	public boolean hasPiece() {
		return hasPiece;
	}
	
	/**
	 * Check if a piece can be placed at this square
	 * A piece cannot be placed if there is already another piece located at this square
	 * @return True if a piece can be placed at the square
	 */
	public boolean canPlacePiece() {
		if (hasPiece) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Removes the piece on this square
	 */
	public void removePiece() {
		this.piece.setSquare(null);
		this.piece = null;
		hasPiece = false;
	}
	
	/*******************
	 * GET/SET
	 * 
	 *******************/
	
	/**
	 * Returns the piece resting on the current square
	 * Otherwise returns null
	 * @return Piece resting on square
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Returns the row the square is on
	 * @return row the square is on
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the column the square is on
	 * @return column the square is on
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Get the row of the square as seen from a specific perspective
	 * @param direction	GameDirection the square is being seen from - can be UPWARDS or DOWNWARDS
	 * @return row of the square from a specific perspective
	 */
	public int getRowFromDirectionPerspective(GameDirection direction) {
		if (direction == GameDirection.UPWARDS) {
			return board.getNumRows() - getRow();
		} else {
			return getRow();
		}
	}
	
	/**
	 * Get the column of the square as seen from a specific perspective
	 * @param direction	GameDirection the square is being seen from - can be UPWARDS or DOWNWARDS
	 * @return column of the square from a specific perspective
	 */
	public int getColFromDirectionPerspective(GameDirection direction) {
		if (direction == GameDirection.UPWARDS) {
			return getCol();
		} else {
			return board.getNumCols() - getCol();
		}
	}
	
	/**
	 * Places a piece on the current square
	 * NOTE: Assumes that canPlacePiece() has been checked first
	 * @param piece	Piece to place on the current square
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
		this.hasPiece = true;
		
		// We also provide a reference to the square for the piece
		piece.setSquare(this);
	}
	

}
