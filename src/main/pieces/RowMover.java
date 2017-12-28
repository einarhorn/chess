package main.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.core.MovementPattern;
import main.core.MovementPattern.MovementDirection;

/**
 * Row Mover piece
 * This piece has two kinds of movements: capture moments and non-capture movements
 * Capture movement - Can move to any spot in the previous row, as long as it captures a piece
 * Non-capture movement - Can move to any spot in the next row, as long as it does not capture a piece
 * @author einar
 *
 */
public class RowMover extends Piece {
	// Store the width of the board, which is used in generating the move pattern
	private static final int BOARD_WIDTH = 8;

	public RowMover(Color color) {
		super(color);
	}

	@Override
	/**
	 * Piece can only make a non-capture move by going anywhere on the next row
	 * @param availableMovements	set of movement patterns used by the piece class to validate non-capture movements
	 */
	protected void generateAvailableMovements(Set<MovementPattern> availableMovements) {
		for (int i=0; i<BOARD_WIDTH; i++) {
			availableMovements.add(generatePieceMovementForRowMovePiece(MovementDirection.FORWARD, MovementDirection.LEFT, i));
			availableMovements.add(generatePieceMovementForRowMovePiece(MovementDirection.FORWARD, MovementDirection.RIGHT, i));
		}
	}
	
	@Override
	/**
	 * Piece can only capture by moving anywhere on the previous row
	 * @param availableMovements	set of movement patterns used by the piece class to validate capture movements
	 */
	protected void generateAvailableCaptureMovements(Set<MovementPattern> availableMovements) {
		for (int i=0; i<BOARD_WIDTH; i++) {
			availableMovements.add(generatePieceMovementForRowMovePiece(MovementDirection.BACKWARD, MovementDirection.LEFT, i));
			availableMovements.add(generatePieceMovementForRowMovePiece(MovementDirection.BACKWARD, MovementDirection.RIGHT, i));
		}
	}

	@Override
	public char getCharacterRepresentation() {
		switch (getColor()) {
			case WHITE:
				return '☖';
			case BLACK:
				return '☗';		
			default:
				return ' ';
		}
	}
	
	/**
	 * Generate a custom MovementPattern object for the RowMover with a custom number of horizontal moves
	 * @param verticalDirection			vertical direction in which to traverse
	 * @param horizontalDirection		horizontal direction in which to traverse
	 * @param horizontalIterations		number of times to traverse in the horizontal direction
	 * @return	MovementPattern 		object that represents a movement that can be made by the piece
	 */
	protected MovementPattern generatePieceMovementForRowMovePiece(MovementDirection verticalDirection, MovementDirection horizontalDirection, int horizontalIterations) {
		List<MovementDirection> movementDirections = new ArrayList<MovementDirection>();
		movementDirections.add(verticalDirection);
		// Add horizontal movement to the piece
		for (int horizontalIteration=0; horizontalIteration<horizontalIterations; horizontalIteration++){
			movementDirections.add(horizontalDirection);
		}
		return new MovementPattern(movementDirections, 1);
	}

	@Override
	public String getName() {
		return "Row Mover";
	}

	@Override
	public boolean canJump() {
		return false;
	}

	@Override
	protected boolean hasCustomCaptureMoves() {
		return true;
	}

}
