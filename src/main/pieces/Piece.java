package main.pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import main.core.Move;
import main.core.MovementPattern;
import main.core.Square;
import main.core.MovementPattern.MovementDirection;

public abstract class Piece {
	public enum Color { BLACK, WHITE}
	private Color color;
	
	// Square the piece is on
	private Square square;
	
	// Set of movements that are available to this piece
	protected Set<MovementPattern> availableMovements;
	protected Set<MovementPattern> availableKillMovements;
	protected Set<MovementPattern> initialMovements;
	
	// Number of moves this piece has made
	private int moveCount = 0;
	
	/**
	 * Instantiate a piece with a color
	 * @param color
	 */
	public Piece(Color color) {
		this.color = color;
		this.availableMovements = new HashSet<MovementPattern>();
		this.availableKillMovements = new HashSet<MovementPattern>();
		this.initialMovements = new HashSet<MovementPattern>();
		generateAvailableMovements(availableMovements);
		if (hasCustomCaptureMoves()) {
			generateAvailableCaptureMovements(availableKillMovements);
		} else {
			generateAvailableMovements(availableKillMovements);
		}
		if (hasInitialMovements()) {
			generateInitialMovements(initialMovements);
		}
	}
	
	protected void generateInitialMovements(Set<MovementPattern> availableMovements) {}

	/**
	 * Adds PieceMovements to availableMovements for the specific piece
	 */
	protected abstract void generateAvailableMovements(Set<MovementPattern> availableMovements);
	
	/**
	 * Gets the specific character representation of a piece for printing the board
	 */
	public abstract char getCharacterRepresentation();
	
	/**
	 * Gets the name of the piece
	 * @return name of the piece
	 */
	public abstract String getName();
	
	/**
	 * Returns true if the specific piece can jump other main.pieces
	 */
	public abstract boolean canJump();
	
	/**
	 * This function is called after each move
	 * Can be extended by the specific main.pieces for unique functionality, if necessary
	 */
	public void afterMove() {};

	/**
	 * Whether the piece has special moves for capturing opponent main.pieces (e.g. Pawn)
	 * @return
	 */
	protected abstract boolean hasCustomCaptureMoves();
	
	/**
	 * This will be called if the piece has custom capture moves
	 * This method is expected to be overridden by a piece class if it needs custom capture moves
	 * @param availableMovements
	 */
	protected void generateAvailableCaptureMovements(Set<MovementPattern> availableMovements) {}
	
	/**
	 * Generate a MovementPattern object with a single direction and custom iteration count
	 * @param direction		A single direction that can be traversed
	 * @param iterations	Number of iterations of direction that are allowed
	 * @return	MovementPattern object that represents a movement that can be made by the piece
	 */
	protected MovementPattern generatePieceMovementWithSingleDirection(MovementDirection direction, int iterations) {
		List<MovementDirection> movementDirectionSet = new ArrayList<MovementDirection>();
		movementDirectionSet.add(direction);
		return new MovementPattern(movementDirectionSet, iterations);
	}
	
	/**
	 * Generate a MovementPattern object with a single direction and custom iteration count
	 * @param direction1		A single direction that can be traversed
	 * @param direction1		A single direction that can be traversed
	 * @param iterations	Number of iterations of direction that are allowed
	 * @return	MovementPattern object that represents a movement that can be made by the piece
	 */
	protected MovementPattern generatePieceMovementWithDoubleDirection(MovementDirection direction1, MovementDirection direction2, int iterations) {
		List<MovementDirection> movementDirectionSet = new ArrayList<MovementDirection>();
		movementDirectionSet.add(direction1);
		movementDirectionSet.add(direction2);
		return new MovementPattern(movementDirectionSet, iterations);
	}
	
	/**
	 * Validate the movement of the piece
	 * @param move
	 * @return
	 */
	public MovementPattern findValidMovementPatternForMove(Move move) {
		Iterator<MovementPattern> it;
		if (move.isCaptureMove()) {
			it = availableKillMovements.iterator(); 
		} else {
			if (isInitialMovement() && hasInitialMovements()) {
				it = initialMovements.iterator(); 
			} else {
				it = availableMovements.iterator(); 
			}
			
		}
		while(it.hasNext()) { 
			MovementPattern currentMovement = it.next();
			
			if (currentMovement.equals(move)) {
				return currentMovement;
			}
		}
		return null;
	}
	
	public void incrementMoveCount() {
		moveCount++;
	}
	
	public void decrementMoveCount() {
		moveCount--;
	}
	
	public boolean isInitialMovement() {
		return (moveCount == 0);
	}
	
	public boolean hasInitialMovements() {
		return false;
	}
	
	/**
	 * Get the color of this piece
	 * @return color of this piece
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Set the square on which this piece rests
	 * @param square on which this piece rests
	 */
	public void setSquare(Square square) {
		this.square = square;
	}
	
	/**
	 * Get the square that this piece belongs to
	 * @return square that this piece belongs to
	 */
	public Square getSquare() {
		return square;
	}
	
	/**
	 * Whether this piece is currently on a square
	 * @return true if this piece is on a square
	 */
	public boolean hasSquare() {
		return (square != null);
	}
	
	/**
	 * The set of legal MovementPatterns available to this piece
	 * @return set of legal MovementPatterns available to this piece
	 */
	public Set<MovementPattern> getLegalMovementDirection() {
		return availableMovements;
	}
	
}
