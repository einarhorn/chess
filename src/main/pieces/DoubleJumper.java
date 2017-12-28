package main.pieces;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import main.core.MovementPattern;
import main.core.MovementPattern.MovementDirection;

/**
 * This custom piece can do unlimited double jumps, in all directions.
 * However, non-capture movements can only be vertical and horizontal movements,
 * and capture movements can only be diagonal movements.
 * A "jump" can go over other pieces.
 * 
 * @author einar
 *
 */
public class DoubleJumper extends Piece {

	public DoubleJumper(Color color) {
		super(color);
	}

	@Override
	/**
	 * Piece can move in vertical and horizontal directions, unlimited
	 * @param availableMovements	set of movement patterns used by the piece class to validate non-capture movements
	 */
	protected void generateAvailableMovements(Set<MovementPattern> availableMovements) {
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.FORWARD, MovementDirection.FORWARD, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.BACKWARD, MovementDirection.BACKWARD, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.LEFT, MovementDirection.LEFT, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.RIGHT, MovementDirection.RIGHT, Integer.MAX_VALUE));
	}
	
	@Override
	/**
	 * Piece can only capture in diagonal double jumps
	 * @param availableMovements	set of movement patterns used by the piece class to determine if captures are allowed
	 */
	protected void generateAvailableCaptureMovements(Set<MovementPattern> availableMovements) {
		// Generate each of the possible movement directions for captures
		List<MovementDirection> doubleForwardLeft = Arrays.asList(
			MovementDirection.FORWARD,
			MovementDirection.LEFT, 
			MovementDirection.FORWARD, 
			MovementDirection.LEFT
		);
		List<MovementDirection> doubleForwardRight = Arrays.asList(
				MovementDirection.FORWARD,
				MovementDirection.RIGHT, 
				MovementDirection.FORWARD, 
				MovementDirection.RIGHT
		);
		List<MovementDirection> doubleBackwardLeft = Arrays.asList(
				MovementDirection.BACKWARD,
				MovementDirection.LEFT, 
				MovementDirection.BACKWARD, 
				MovementDirection.LEFT
		);
		List<MovementDirection> doubleBackwardRight = Arrays.asList(
				MovementDirection.BACKWARD,
				MovementDirection.RIGHT, 
				MovementDirection.BACKWARD, 
				MovementDirection.RIGHT
		);
		
		// Generate a movement pattern object for each of the above movements
		availableMovements.add(new MovementPattern(doubleForwardLeft, Integer.MAX_VALUE));
		availableMovements.add(new MovementPattern(doubleForwardRight, Integer.MAX_VALUE));
		availableMovements.add(new MovementPattern(doubleBackwardLeft, Integer.MAX_VALUE));
		availableMovements.add(new MovementPattern(doubleBackwardRight, Integer.MAX_VALUE));
	}

	@Override
	public char getCharacterRepresentation() {
		switch (getColor()) {
			case WHITE:
				return '⚇';
			case BLACK:
				return '⚉';		
			default:
				return ' ';
		}
	}

	@Override
	public String getName() {
		return "Double Jumper";
	}

	@Override
	public boolean canJump() {
		return true;
	}

	@Override
	protected boolean hasCustomCaptureMoves() {
		return true;
	}

}
