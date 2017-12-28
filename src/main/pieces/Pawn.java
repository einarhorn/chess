package main.pieces;


import java.util.Arrays;

import java.util.Set;

import main.core.MovementPattern;
import main.core.MovementPattern.MovementDirection;

public class Pawn extends Piece {

	/**
	 * Constructor for the pawn class
	 * @param color		color of this piece
	 */
	public Pawn(Color color) {
		super(color);
	}


	@Override
	/**
	 * Whether this piece can jump over others
	 */
	public boolean canJump() {
		return false;
	}
	
	@Override
	/**
	 * Generate the set of movements available to this piece
	 */
	protected void generateAvailableMovements(Set<MovementPattern> availableMovements) {	
		// Pawn can move forward by one space
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.FORWARD, 1));
	}

	@Override
	/**
	 * Whether this piece has moves specific for capturing
	 */
	protected boolean hasCustomCaptureMoves() {
		return true;
	}

	@Override
	/**
	 * Generate the capture moves for this piece
	 */
	protected void generateAvailableCaptureMovements(Set<MovementPattern> availableMovements) {
		// The pawn can only capture main.pieces by moving forward one space, and horizontally one space
		availableMovements.add(new MovementPattern(Arrays.asList(MovementDirection.FORWARD, MovementDirection.RIGHT), 1));
		availableMovements.add(new MovementPattern(Arrays.asList(MovementDirection.FORWARD, MovementDirection.LEFT), 1));
	}
	
	protected void generateInitialMovements(Set<MovementPattern> availableMovements) {
		// Pawn can move forward by one space
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.FORWARD, 1));
		
		// Pawn can move forward by two spaces - This movement capability is removed after the pawn's first move
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.FORWARD, 2));
	}
	
	@Override
	/**
	 * Removes the double jump capability after the pawns first move
	 */
	public void afterMove() {
		availableMovements.remove(generatePieceMovementWithSingleDirection(MovementDirection.FORWARD, 2));
	}
	
	@Override
	/**
	 * Get the character representation of the piece, used for printing the board state
	 */
	public char getCharacterRepresentation() {
		switch (getColor()) {
			case WHITE:
				return '♙';
			case BLACK:
				return '♟';		
			default:
				return ' ';
		}
	}


	@Override
	public String getName() {
		return "Pawn";
	}
	
	public boolean hasInitialMovements() {
		return true;
	}
	
	
}
