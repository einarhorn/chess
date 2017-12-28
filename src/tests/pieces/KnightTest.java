package tests.pieces;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import main.core.Board;
import main.core.GameDirection;
import main.core.Player;
import main.exceptions.InvalidMoveException;
import main.pieces.Knight;
import main.pieces.Piece;
import main.pieces.Piece.Color;
import tests.ChessBaseTest;

public class KnightTest extends ChessBaseTest{

	private Board singleKnightBoard;
	private int NUM_ROWS = 8;
	private int NUM_COLUMNS = 8;
	private Map<String, Piece> singleKnightBoardMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Setup a single white knight
		Knight knight = new Knight(Color.WHITE);
		
		// Setup the knight on d4
		singleKnightBoardMapping = new HashMap<String, Piece>();
		singleKnightBoardMapping.put("d4", knight);
		
		// Setup two players
		this.players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(knight));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board with above information
		singleKnightBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleKnightBoardMapping, players);
	}

	/**
	 * Reset the board to its initial state
	 */
	private void resetBoardToInitial() {
		singleKnightBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleKnightBoardMapping, players);
	}

	@Test
	/**
	 * Test that the knight can move in all directions in an L shape
	 */
	public void testKnightCanMakeLMovement() {
		try {
			singleKnightBoard.move("d4", "e6", players[0]);
			assertEquals("Knight", singleKnightBoard.getNameOfPieceAtPosition("e6"));
			
			resetBoardToInitial();
			singleKnightBoard.move("d4", "c6", players[0]);
			assertEquals("Knight", singleKnightBoard.getNameOfPieceAtPosition("c6"));
			
			resetBoardToInitial();
			singleKnightBoard.move("d4", "f5", players[0]);
			assertEquals("Knight", singleKnightBoard.getNameOfPieceAtPosition("f5"));
			
			resetBoardToInitial();
			singleKnightBoard.move("d4", "f3", players[0]);
			assertEquals("Knight", singleKnightBoard.getNameOfPieceAtPosition("f3"));
			
			resetBoardToInitial();
			singleKnightBoard.move("d4", "e2", players[0]);
			assertEquals("Knight", singleKnightBoard.getNameOfPieceAtPosition("e2"));
			
			resetBoardToInitial();
			singleKnightBoard.move("d4", "c2", players[0]);
			assertEquals("Knight", singleKnightBoard.getNameOfPieceAtPosition("c2"));
			
			resetBoardToInitial();
			singleKnightBoard.move("d4", "b3", players[0]);
			assertEquals("Knight", singleKnightBoard.getNameOfPieceAtPosition("b3"));
			
			resetBoardToInitial();
			singleKnightBoard.move("d4", "b5", players[0]);
			assertEquals("Knight", singleKnightBoard.getNameOfPieceAtPosition("b5"));
		} catch (InvalidMoveException e) {
			fail("Move should have been valid");
		}
	}

	@Test
	/**
	 * Try a multitude of illegal moves for the knight
	 */
	public void testKnightCannotMakeIllegalMovement() {
		try {
			singleKnightBoard.move("d4", "d5", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleKnightBoard.move("d4", "e5", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleKnightBoard.move("d4", "a6", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	
	@Test
	/**
	 * Test that knight can jump over enemy
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … ♟ ♟ … … …
	 *	2 ║… … … ♙ ♟ … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testKnightCanJumpEnemy() {
		// Setup the board such that it looks like above
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d2", new Knight(Color.WHITE));
		customMapping.put("d3", new Knight(Color.BLACK));
		customMapping.put("e3", new Knight(Color.BLACK));
		customMapping.put("e2", new Knight(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		// Test that the knight can jump successfully
		try {
			board.move("d2", "f3", players[0]);
			assertEquals("Knight", board.getNameOfPieceAtPosition("f3"));
		} catch (InvalidMoveException e) {
			fail("Move should have been successful");
		}
		
	}
	
	@Test
	/**
	 * Test that knight can jump over pieces of the same color
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … ♙ ♙ … … …
	 *	2 ║… … … ♙ ♙ … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testKnightCanJumpSameColor() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d2", new Knight(Color.WHITE));
		customMapping.put("d3", new Knight(Color.WHITE));
		customMapping.put("e3", new Knight(Color.WHITE));
		customMapping.put("e2", new Knight(Color.WHITE));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			board.move("d2", "f3", players[0]);
			assertEquals("Knight", board.getNameOfPieceAtPosition("f3"));
		} catch (InvalidMoveException e) {
			fail("Move should have been successful");
		}
	}
	
	@Test
	/**
	 * Test that knight can capture enemy pieces
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … … ♟ … …
	 *	2 ║… … … ♙ … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testKnightCanCaptureEnemy() {
		// Setup the board such that it looks like above
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d2", new Knight(Color.WHITE));
		customMapping.put("f3", new Knight(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		// Test that capture is successful
		try {
			board.move("d2", "f3", players[0]);
			assertEquals("Knight", board.getNameOfPieceAtPosition("f3"));
		} catch (InvalidMoveException e) {
			fail("Move should have been successful");
		}
	}
	
	@Test
	/**
	 * Test that knight cannot land on a piece of the same color
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … … ♙ … …
	 *	2 ║… … … ♙ … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testKnightCannotCaptureSameColor() {
		// Setup the board such that it looks like above
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d2", new Knight(Color.WHITE));
		customMapping.put("f3", new Knight(Color.WHITE));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		// Test that move is considered invalid
		try {
			board.move("d2", "f3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
}
