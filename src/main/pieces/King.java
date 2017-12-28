package main.pieces;

import java.util.Set;

import main.core.MovementPattern;
import main.core.MovementPattern.MovementDirection;

public class King extends Piece {

	public King(Color color) {
		super(color);
	}

	@Override
	protected void generateAvailableMovements(Set<MovementPattern> availableMovements) {
		int numSteps = 1;
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.FORWARD, numSteps));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.BACKWARD, numSteps));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.LEFT, numSteps));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.RIGHT, numSteps));
		
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.FORWARD, MovementDirection.LEFT, numSteps));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.FORWARD, MovementDirection.RIGHT, numSteps));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.BACKWARD, MovementDirection.LEFT, numSteps));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.BACKWARD, MovementDirection.RIGHT, numSteps));	
	}

	@Override
	public char getCharacterRepresentation() {
		switch (getColor()) {
			case WHITE:
				return '♔';
			case BLACK:
				return '♚';		
			default:
				return ' ';
		}
	}

	@Override
	public String getName() {
		return "King";
	}

	@Override
	public boolean canJump() {
		return false;
	}

	@Override
	protected boolean hasCustomCaptureMoves() {
		return false;
	}

}
