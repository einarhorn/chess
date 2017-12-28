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
import main.pieces.Piece;
import main.pieces.Rook;
import tests.ChessBaseTest;
import main.pieces.Piece.Color;

public class RookTest extends ChessBaseTest {

	private Board singleRookBoard;
	private int NUM_ROWS = 8;
	private int NUM_COLUMNS = 8;
	private Map<String, Piece> singleRookBoardMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Setup a single white rook
		Rook rook = new Rook(Color.WHITE);
		
		// Place rook at d4
		singleRookBoardMapping = new HashMap<String, Piece>();
		singleRookBoardMapping.put("d4", rook);
		
		// Setup two players
		this.players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(rook));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board with above information
		singleRookBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleRookBoardMapping, players);
	}

	/**
	 * Reset the board to its initial state
	 */
	private void resetBoardToInitial() {
		singleRookBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleRookBoardMapping, players);
	}

	@Test
	/**
	 * Test that the rook can move in all cardinal directions by any amount
	 */
	public void testRookCanMakeLegalMovement() {
		try {
			singleRookBoard.move("d4", "d8", players[0]);
			assertEquals("Rook", singleRookBoard.getNameOfPieceAtPosition("d8"));
			
			resetBoardToInitial();
			singleRookBoard.move("d4", "a4", players[0]);
			assertEquals("Rook", singleRookBoard.getNameOfPieceAtPosition("a4"));
			
			resetBoardToInitial();
			singleRookBoard.move("d4", "d1", players[0]);
			assertEquals("Rook", singleRookBoard.getNameOfPieceAtPosition("d1"));
			
			resetBoardToInitial();
			singleRookBoard.move("d4", "h4", players[0]);
			assertEquals("Rook", singleRookBoard.getNameOfPieceAtPosition("h4"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
		
	}

	@Test
	/**
	 * Try a multitude of illegal moves for the Rook
	 */
	public void testRookCannotMakeIllegalMovement() {
		try {
			singleRookBoard.move("d4", "e5", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleRookBoard.move("d4", "e3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleRookBoard.move("d4", "b3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	
	@Test
	/**
	 * Test that Rook cannot jump over enemy
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♜ … … … …
	 *	3 ║… … … ♖ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testRookCannotJumpEnemy() {
		// Setup board as shown above
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Rook(Color.WHITE));
		customMapping.put("d4", new Rook(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		// Should be unable to jump over enemy
		try {
			board.move("d3", "d7", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}

	@Test
	/**
	 * Test that Rook can capture opposition pieces
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♜ … … … …
	 *	3 ║… … … ♖ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testRookCanCaptureEnemy() {
		// Setup board as shown above
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Rook(Color.WHITE));
		customMapping.put("d4", new Rook(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		// Should successfully capture enemy
		try {
			board.move("d3", "d4", players[0]);
			assertEquals("Rook", board.getNameOfPieceAtPosition("d4"));
			assertEquals("Empty", board.getNameOfPieceAtPosition("d3"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
		
	}
	
	@Test
	/**
	 * Test that Rook cannot land on a piece of the same color
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♖ … … … …
	 *	3 ║… … … ♖ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testRookCannotCaptureSameColor() {
		// Setup board as shown above
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Rook(Color.WHITE));
		customMapping.put("d4", new Rook(Color.WHITE));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		// Attempt to capture same color piece should fail
		try {
			board.move("d3", "d4", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
}
