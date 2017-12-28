package main.pieces;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import main.core.MovementPattern;
import main.core.MovementPattern.MovementDirection;

public class Knight extends Piece {

	/**
	 * Constructor for the knight class
	 * @param color		color of this piece
	 */
	public Knight(Color color) {
		super(color);
	}

	@Override
	/**
	 * Whether this piece can jump over others
	 */
	public boolean canJump() {
		return true;
	}

	@Override
	/**
	 * Generate the set of movements available to this piece
	 */
	protected void generateAvailableMovements(Set<MovementPattern> availableMovements) {
		availableMovements.add(generatePieceMovementForKnight(MovementDirection.FORWARD, MovementDirection.FORWARD, MovementDirection.LEFT, 1));
		availableMovements.add(generatePieceMovementForKnight(MovementDirection.FORWARD, MovementDirection.FORWARD, MovementDirection.RIGHT, 1));
		
		availableMovements.add(generatePieceMovementForKnight(MovementDirection.RIGHT, MovementDirection.RIGHT, MovementDirection.FORWARD, 1));
		availableMovements.add(generatePieceMovementForKnight(MovementDirection.RIGHT, MovementDirection.RIGHT, MovementDirection.BACKWARD, 1));
		
		availableMovements.add(generatePieceMovementForKnight(MovementDirection.BACKWARD, MovementDirection.BACKWARD, MovementDirection.LEFT, 1));
		availableMovements.add(generatePieceMovementForKnight(MovementDirection.BACKWARD, MovementDirection.BACKWARD, MovementDirection.RIGHT, 1));
		
		availableMovements.add(generatePieceMovementForKnight(MovementDirection.LEFT, MovementDirection.LEFT, MovementDirection.FORWARD, 1));
		availableMovements.add(generatePieceMovementForKnight(MovementDirection.LEFT, MovementDirection.LEFT, MovementDirection.BACKWARD, 1));
	}

	/**
	 * Generate a MovementPattern object for the knight with 3 directions and custom iterations
	 * @param direction1		A single direction that can be traversed
	 * @param direction2		A single direction that can be traversed
	 * @param direction3		A single direction that can be traversed
	 * @param iterations	Number of iterations of direction that are allowed
	 * @return	MovementPattern object that represents a movement that can be made by the piece
	 */
	protected MovementPattern generatePieceMovementForKnight(MovementDirection direction1, MovementDirection direction2, MovementDirection direction3, int iterations) {
		List<MovementDirection> movementDirections = Arrays.asList(direction1, direction2, direction3);
		return new MovementPattern(movementDirections, iterations);
	}
	
	@Override
	/**
	 * Get the character representation of the piece, used for printing the board state
	 */
	public char getCharacterRepresentation() {
		switch (getColor()) {
			case WHITE:
				return '♘';
			case BLACK:
				return '♞';		
			default:
				return ' ';
			}
	}

	@Override
	/**
	 * Whether this piece has moves specific for capturing
	 */
	protected boolean hasCustomCaptureMoves() {
		return false;
	}

	@Override
	public String getName() {
		return "Knight";
	}
}
