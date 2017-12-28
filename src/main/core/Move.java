package main.core;

import main.pieces.Piece;

public class Move {
	private Square startSquare, endSquare;
	private Player player;
	private MovementPattern movementPattern = null;
	private Piece pieceToMove;
	private Piece capturedPiece;
	private boolean isCaptureMove;
	
	
	/**
	 * Initializes the move class
	 * This is a specific move from one square to another, made by a specific player
	 * @param startSquare	The square from which the move is made. This square must have a piece on it
	 * @param endSquare		The square to which the piece is moved. This can optionally have a piece on it
	 * @param player		The player making the move
	 */
	public Move(Square startSquare, Square endSquare, Player player) {
		this.startSquare = startSquare;
		this.endSquare = endSquare;
		this.player = player;
		this.pieceToMove = startSquare.getPiece();
		this.capturedPiece = endSquare.getPiece();
		
		// Finds the movement pattern instance corresponding to this move
		calculateIsCaptureMove();
		calculateMovementPattern();
	}
	
	/**
	 * Finds a movement pattern corresponding to this move
	 * This is done by checking the movement patterns for the piece,  and
	 * seeing if this move can be made by one of those movement patterns
	 */
	private void calculateMovementPattern() {
		Piece pieceToMove = startSquare.getPiece();
		this.movementPattern = pieceToMove.findValidMovementPatternForMove(this);
	}
	
	/**
	 * Calculates whether the current move captures another piece
	 */
	private void calculateIsCaptureMove() {
		if (endSquare.hasPiece()) {
			Piece startPiece = startSquare.getPiece();
			Piece endPiece = endSquare.getPiece();
			// Check if the target piece is of a different color
			if (startPiece.getColor() != endPiece.getColor()) {
				this.isCaptureMove = true;
			}
		} else {
			this.isCaptureMove = false;
		}
		
	}
	
	/**
	 * Whether the current move captures another piece
	 * @return true if the current move captures another piece
	 */
	public boolean isCaptureMove() {
		return isCaptureMove;
	}
	
	/**
	 * Whether the current move has a valid corresponding movement pattern for the piece
	 * @return True if there is a valid corresponding movement pattern
	 */
	public boolean hasValidMovementPattern() {
		return (movementPattern != null);
	}
	
	/**
	 * Returns the corresponding movement pattern
	 * @return	True if there is a valid corresponding movement pattern
	 */
	public MovementPattern getMovementPattern() {
		return movementPattern;
	}

	/**
	 * Get the row of the start square
	 * @return row of the start square
	 */
	public int getStartRow() {
		return startSquare.getRow();
	}
	
	/**
	 * Get the column of the start square
	 * @return column of the start square
	 */
	public int getStartCol() {
		return startSquare.getCol();
	}
	
	/**
	 * Get the row of the end square
	 * @return row of the end square
	 */
	public int getEndRow() {
		return endSquare.getRow();
	}
	
	/**
	 * Get the column of the end square
	 * @return column of the end square
	 */
	public int getEndCol() {
		return endSquare.getCol();
	}
	
	/**
	 * Get the row of the start square from the players perspective
	 * @return row of the start square from the players perspective
	 */
	public int getStartRowFromPlayerPerspective() {
		return startSquare.getRowFromDirectionPerspective(player.getDirection());
	}
	
	/**
	 * Get the column of the start square from the players perspective
	 * @return column of the start square from the players perspective
	 */
	public int getStartColFromPlayerPerspective() {
		return startSquare.getColFromDirectionPerspective(player.getDirection());
	}
	
	/**
	 * Get the row of the end square from the players perspective
	 * @return row of the end square from the players perspective
	 */
	public int getEndRowFromPlayerPerspective() {
		return endSquare.getRowFromDirectionPerspective(player.getDirection());
	}
	
	/**
	 * Get the column of the end square from the players perspective
	 * @return column of the end square from the players perspective
	 */
	public int getEndColFromPlayerPerspective() {
		return endSquare.getColFromDirectionPerspective(player.getDirection());
	}
	
	/**
	 * Get the net change in rows from the players perspective
	 * @return net change in rows from the players perspective
	 */
	public int getRowMovementFromPlayerPerspective() {
		return getEndRowFromPlayerPerspective() - getStartRowFromPlayerPerspective();
	}
	
	/**
	 * Get the net change in columns from the players perspective
	 * @return net change in columns from the players perspective
	 */
	public int getColMovementFromPlayerPerspective() {
		return getEndColFromPlayerPerspective() - getStartColFromPlayerPerspective();
	}
	
	/**
	 * Get the starting square for the move
	 * @return starting square for the move
	 */
	public Square getStartSquare() {
		return startSquare;
	}
	
	/**
	 * Get the end square for the move
	 * @return end square for the move
	 */
	public Square getEndSquare() {
		return endSquare;
	}
	
	/**
	 * Get the piece making the move
	 * @return piece making the move
	 */
	public Piece getPiece() {
		return pieceToMove;
	}
	
	/**
	 * Get the player making the move
	 * @return player making the move
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the piece that would be captured by this move
	 * If there is no piece to be captured, returns null
	 * @return the piece that is captured by this move
	 */
	public Piece getCapturedPiece() {
		return capturedPiece;
	}
}
