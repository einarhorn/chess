package main.pieces;

import java.util.Set;

import main.core.MovementPattern;
import main.core.MovementPattern.MovementDirection;

public class Rook extends Piece {
	
	public Rook(Color color) {
		super(color);
	}
	
	@Override
	/**
	 * Available movements for the Rook class
	 */
	protected void generateAvailableMovements(Set<MovementPattern> availableMovements) {
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.FORWARD, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.BACKWARD, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.LEFT, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.RIGHT, Integer.MAX_VALUE));
	}
	
	/**
	 * Whether the piece is allowed to jump over other main.pieces
	 * @return
	 */
	public boolean canJump() {
		return false;
	}

	@Override
	/**
	 * Get the character representation of the current piece
	 * Used in printing a string representation of the board
	 * @return
	 */
	public char getCharacterRepresentation() {
		switch (getColor()) {
			case WHITE:
				return '♖';
			case BLACK:
				return '♜';		
			default:
				return ' ';
		}
	}

	@Override
	/**
	 * Whether this piece has moves specific to capturing
	 */
	protected boolean hasCustomCaptureMoves() {
		return false;
	}

	@Override
	public String getName() {
		return "Rook";
	}
}
