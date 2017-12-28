package tests.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.core.Board;
import main.core.GameDirection;
import main.core.Move;
import main.core.MovementPattern;
import main.core.MovementPattern.MovementDirection;
import main.core.Player;
import main.core.Square;
import main.pieces.Pawn;
import main.pieces.Piece;
import main.pieces.Piece.Color;

public class MovementPatternTest {

	private MovementPattern movementPattern;
	
	// This movement pattern allows for two iterations of the movement directions listed below
	private static final int MOVEMENT_ITERATIONS = 2;
	private List<MovementDirection> movementDirectionList;
	private static final int ROW_MOVEMENT = 2;
	private static final int COL_MOVEMENT = 1;
	private static final int TOTAL_ROW_MOVEMENT = 4;
	private static final int TOTAL_COL_MOVEMENT = 2;

	@Before
	public void setUp() throws Exception {
		// Set the movements as an example knight move
		movementDirectionList = new ArrayList<MovementDirection>();
		movementDirectionList.add(MovementDirection.FORWARD);
		movementDirectionList.add(MovementDirection.FORWARD);
		movementDirectionList.add(MovementDirection.RIGHT);
		
		// Set up a movementPattern object with the above information
		movementPattern = new MovementPattern(movementDirectionList, MOVEMENT_ITERATIONS);
	}

	@Test
	public void testGetMovementDirections() {
		assertEquals(movementDirectionList, movementPattern.getMovementDirections());
	}

	@Test
	public void testGetMovementIterations() {
		assertEquals(MOVEMENT_ITERATIONS, movementPattern.getMovementIterations());
	}

	@Test
	// The row movement of two forward moves is 2
	public void testGetRowMovement() {
		assertEquals(ROW_MOVEMENT, movementPattern.getRowMovement());
	}

	@Test
	// The col movement of one right move is 1
	public void testGetColMovement() {
		assertEquals(COL_MOVEMENT, movementPattern.getColMovement());
	}

	@Test
	// The total row movement is the row movement multiplied by the number of iterations
	public void testGetTotalRowMovement() {
		assertEquals(TOTAL_ROW_MOVEMENT, movementPattern.getTotalRowMovement());
	}

	@Test
	// The total col movement is the row movement multiplied by the number of iterations
	public void testGetTotalColMovement() {
		assertEquals(TOTAL_COL_MOVEMENT, movementPattern.getTotalColMovement());
	}

	@Test
	public void testHasUnlimitedIterations() {
		// Since the iterations is set to 2, should not be unlimited
		assertEquals(false, movementPattern.hasUnlimitedIterations());
		
		// Set up a new instance of movement pattern with unlimited moves
		movementPattern = new MovementPattern(movementDirectionList, Integer.MAX_VALUE);
		assertEquals(true, movementPattern.hasUnlimitedIterations());
	}

	@Test
	/**
	 * Set up a move object with two iterations of <Forward,Forward,Right>, which is equivalent to the movement pattern
	 */
	public void testEqualsObjectWhenComparingAgainstEquivalentMove() {
		// Setup start and end square for two iterations of <Forward,Forward,Right>
		Player[] players = new Player[2];
		Board board = new Board(8, 8, new HashMap<String, Piece>(), players);
		Square startSquare = new Square(5, 5, board);
		Square endSquare = new Square(1, 7, board);
		
		// Place a piece on the start square
		Pawn whitePawn = new Pawn(Color.WHITE);
		startSquare.setPiece(whitePawn);
		
		// Setup valid move
		Player player = new Player("PlayerName", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(whitePawn));
		Move validMove = new Move(startSquare, endSquare, player);
		
		// Assert move is equal to movement pattern
		assertEquals(true, movementPattern.equals(validMove));
	}
	
	@Test
	/**
	 * Set up a move object with one iterations of <Forward,Forward,Right>, which is not equivalent to the movement pattern
	 */
	public void testEqualsObjectWhenComparingAgainstNonequivalentMove() {
		// Setup start and end square for *one* iterations of <Forward,Forward,Right>
		Player[] players = new Player[2];
		Board board = new Board(8, 8, new HashMap<String, Piece>(), players);
		Square startSquare = new Square(5, 5, board);
		Square endSquare = new Square(3, 6, board);
		
		// Place a piece on the start square
		Pawn whitePawn = new Pawn(Color.WHITE);
		startSquare.setPiece(whitePawn);
		
		// Setup valid move
		Player player = new Player("PlayerName", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(whitePawn));
		Move validMove = new Move(startSquare, endSquare, player);
		
		// Assert move is equal to movement pattern
		assertEquals(false, movementPattern.equals(validMove));
	}
	
	@Test
	/**
	 * Set up a new movement pattern object with two iterations of <Forward,Forward,Right>, which is equivalent to this movement pattern
	 */
	public void testEqualsObjectWhenComparingAgainstEquivalentMovementPattern() {
		// Setup same <Forward, Forward, Right> MovementDirection list
		List<MovementDirection> sameMovementDirectionList = new ArrayList<MovementDirection>();
		sameMovementDirectionList = new ArrayList<MovementDirection>();
		sameMovementDirectionList.add(MovementDirection.FORWARD);
		sameMovementDirectionList.add(MovementDirection.FORWARD);
		sameMovementDirectionList.add(MovementDirection.RIGHT);
		
		// Setup same number of movement iterations
		int sameMovementIterations = MOVEMENT_ITERATIONS;
		
		// Set up a new movement pattern object with the above information
		MovementPattern newMovementPattern = new MovementPattern(sameMovementDirectionList, sameMovementIterations);
		
		// Check that these two movement patterns are the same
		assertEquals(true, movementPattern.equals(newMovementPattern));
	}
	
	@Test
	/**
	 * Set up a new movement pattern object with one iterations of <Forward,Forward,Right>, which is not equivalent to the movement pattern
	 */
	public void testEqualsObjectWhenComparingAgainstNonequivalentMovementPattern() {
		// Setup same <Forward, Forward, Right> MovementDirection list
		List<MovementDirection> sameMovementDirectionList = new ArrayList<MovementDirection>();
		sameMovementDirectionList = new ArrayList<MovementDirection>();
		sameMovementDirectionList.add(MovementDirection.FORWARD);
		sameMovementDirectionList.add(MovementDirection.FORWARD);
		sameMovementDirectionList.add(MovementDirection.RIGHT);
		
		// Setup different number of movement iterations
		int differentMovementIterations = 1;
		
		// Set up a new movement pattern object with the above information
		MovementPattern newMovementPattern = new MovementPattern(sameMovementDirectionList, differentMovementIterations);
		
		// Check that these two movement patterns are the same
		assertEquals(false, movementPattern.equals(newMovementPattern));
	}

}
