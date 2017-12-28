package main.core;

import java.util.Iterator;
import java.util.List;

public class MovementPattern {
	private List<MovementDirection> movementDirections;
	private int movementIterations;
	private int rowCount;
	private int colCount;
	
	public enum MovementDirection { FORWARD, BACKWARD, LEFT, RIGHT };
	/**
	 * Creates a generalized movement pattern
	 * This is a movement that is independent of start and end positions, and only
	 * contains information about the movement itself
	 * The movement pattern has a fixed number of iterations that is allowed
	 * NOTE: A movementIterations value equal to MAX_INT indicates that unlimited iterations are allowed
	 * 
	 * @param movementDirections	a list of directions that can be moved
	 * @param movementIterations	number of iterations that can be made
	 * @param canJump				whether a jump is allowed in this movement
	 */
	public MovementPattern(List<MovementDirection> movementDirections, int movementIterations) {
		this.movementDirections = movementDirections;
		this.movementIterations = movementIterations;
		calculateRowMovement();
		calculateColMovement();
	}
	
	/**
	 * Calculate the net row movement from perspective of piece
	 */
	private void calculateRowMovement() {
		int rowCount = 0;
		Iterator<MovementDirection> it = movementDirections.iterator(); 
		while(it.hasNext()) { 
			MovementDirection currentMovement = it.next();
			switch(currentMovement){
				case FORWARD:
					rowCount++;
					break;
				case BACKWARD:
					rowCount--;
				default:
					break;
			}
		}
		
		this.rowCount = rowCount;;
	}
	
	/**
	 * Calculate the net column movement from perspective of piece
	 */
	private void calculateColMovement() {
		int colCount = 0;
		Iterator<MovementDirection> it = movementDirections.iterator(); 
		while(it.hasNext()) { 
			MovementDirection currentMovement = it.next();
			
			switch(currentMovement){
				case LEFT:
					colCount--;
					break;
				case RIGHT:
					colCount++;
				default:
					break;
			}
		}
		
		this.colCount = colCount;;
	}
	
	/**
	 * Get the specific movements for this patterns
	 * @return list of movements
	 */
	public List<MovementDirection> getMovementDirections() {
		return movementDirections;
	}
	
	/**
	 * Get the number of iterations of the pattern that is allowed
	 * @return number of movement iterations
	 */
	public int getMovementIterations() {
		return movementIterations;
	}
	
	/**
	 * Get the net row movement in a single iteration
	 * @return row movement per iteration
	 */
	public int getRowMovement() {
		return rowCount;
	}
	
	/**
	 * Get the net column movement in a single iteration
	 * @return column movement per iteration
	 */
	public int getColMovement() {
		return colCount;
	}
	
	/**
	 * Get the total row movement in all iterations
	 * @return	total row movement
	 */
	public int getTotalRowMovement() {
		return rowCount * movementIterations;
	}
	
	/**
	 * Get the total column movement in all iterations
	 * @return total column movement
	 */
	public int getTotalColMovement() {
		return colCount * movementIterations;
	}
	
	/**
	 * A movement pattern is considered to have unlimited iterations if the number
	 * of iterations is set to MAX_VALUE
	 * @return	the number of iterations available
	 */
	public boolean hasUnlimitedIterations() {
		return movementIterations == Integer.MAX_VALUE;
	}

	@Override
	/**
	 * Compares two MovementPattern objects to check if they are equal
	 * Can also compare a Move object to a MovementPattern object
	 */
	public boolean equals(Object o) {
	    // Self check
	    if (this == o) {
	        return true;
	    }
	    
	    // Null check
	    if (o == null) {
	        return false;
	    }
	    
	    // If the object is a move object, we check to see whether the move could have
	    // been made under the constraints of the movement pattern
	    if (o.getClass() == Move.class) {
	    	Move move = (Move) o;
	    	
	    	// We will check whether the vertical and horizontal movement is the same for the two entities
			
			// Depending on whether the movement is allowed to be unlimited, different calculations are made
			if (hasUnlimitedIterations()) {
				if (getRowMovement() != 0) {
					// Check that the rows are multiples of another, and find the scale factor between the two
					boolean rowIsDivisible = (move.getRowMovementFromPlayerPerspective() % getRowMovement() == 0);
					int rowScale = move.getRowMovementFromPlayerPerspective() / getRowMovement();
					
					if (rowIsDivisible && rowScale > 0) {
						// If the scaled version of the column movement is equal to the column movement of the move
						// then the two vectors are scalar multiples
						int scaledCol = rowScale * getColMovement();
						if (scaledCol == move.getColMovementFromPlayerPerspective()) {
							return true;
						} 
					}
					return false;
				} else if (getColMovement() != 0) {
					// Check that the columns are multiples of another, and find the scale factor between the two
					boolean colIsDivisible = (move.getColMovementFromPlayerPerspective() % getColMovement() == 0);
					int colScale = move.getColMovementFromPlayerPerspective() / getColMovement();
					
					if (colIsDivisible && colScale > 0) {
						// If the scaled version of the row movement is equal to the row movement of the move
						// then the two vectors are scalar multiples
						int scaledRow = colScale * getRowMovement();
						if (scaledRow == move.getRowMovementFromPlayerPerspective()) {
							return true;
						}
					}
					return false;
					
				} else {
					// If both row and column movement of the pattern is zero, this 
					// only matches if the move also has net zero movement
					return move.getColMovementFromPlayerPerspective() == 0 
							&& move.getColMovementFromPlayerPerspective() == 0;
				}
			
			} else {
				// If the net movement is the same as the net movement pattern, the moves are equal
				boolean verticalMovementIsSame = getTotalRowMovement() == move.getRowMovementFromPlayerPerspective();
				boolean horizontalMovementIsSame = getTotalColMovement() == move.getColMovementFromPlayerPerspective();
				return verticalMovementIsSame && horizontalMovementIsSame;
			}
	    }
	    
	    // Type check
	    if (getClass() != o.getClass()) {
	        return false;
	    }
	    
	    // Compare two movement entities by checking their movement and number of iterations
	    MovementPattern movement = (MovementPattern) o;
	    return getMovementDirections().equals(movement.getMovementDirections())
	    		&& getMovementIterations() == movement.getMovementIterations();
	}
}
