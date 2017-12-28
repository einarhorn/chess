package main.pieces;

import java.util.Set;

import main.core.MovementPattern;
import main.core.MovementPattern.MovementDirection;

public class Queen extends Piece {

	public Queen(Color color) {
		super(color);
	}

	@Override
	protected void generateAvailableMovements(Set<MovementPattern> availableMovements) {
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.FORWARD, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.BACKWARD, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.LEFT, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithSingleDirection(MovementDirection.RIGHT, Integer.MAX_VALUE));
		
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.FORWARD, MovementDirection.LEFT, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.FORWARD, MovementDirection.RIGHT, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.BACKWARD, MovementDirection.LEFT, Integer.MAX_VALUE));
		availableMovements.add(generatePieceMovementWithDoubleDirection(MovementDirection.BACKWARD, MovementDirection.RIGHT, Integer.MAX_VALUE));		
	}

	@Override
	public char getCharacterRepresentation() {
		switch (getColor()) {
			case WHITE:
				return '♕';
			case BLACK:
				return '♛';		
			default:
				return ' ';
		}
	}

	@Override
	public String getName() {
		return "Queen";
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
