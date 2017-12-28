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
import main.pieces.Pawn;
import main.pieces.Piece;
import main.pieces.Piece.Color;
import tests.ChessBaseTest;

public class PawnTest extends ChessBaseTest {

	private Board singlePawnBoard;
	private int NUM_ROWS = 8;
	private int NUM_COLUMNS = 8;
	private Map<String, Piece> singlePawnBoardMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Setup single white pawn
		Pawn pawn = new Pawn(Color.WHITE);

		// Setup pawn at d2
		singlePawnBoardMapping = new HashMap<String, Piece>();
		singlePawnBoardMapping.put("d2", pawn);
		
		// Setup 2 players
		this.players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(pawn));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board with above information
		singlePawnBoard = new Board(NUM_ROWS, NUM_COLUMNS, singlePawnBoardMapping, players);
	}

	@Test
	// Test that the pawn can move forwards by a single space
	public void testPawnCanMoveForwardSingleSpace() {
		try {
			singlePawnBoard.move("d2", "d3", players[0]);
			assertEquals("Pawn", singlePawnBoard.getNameOfPieceAtPosition("d3"));
		} catch (InvalidMoveException e) {
			fail("Expected move to be successful");
		}
	}
	
	@Test
	// Test that the pawn can move forwards by a double space on first move only
	public void testPawnCanMoveForwardDoubleSpaceOnInitialOnly() {
		// Make a double jump for the pawn's first move
		try {
			singlePawnBoard.move("d2", "d4", players[0]);
			assertEquals("Pawn", singlePawnBoard.getNameOfPieceAtPosition("d4"));
		} catch (InvalidMoveException e1) {
			fail("Expected move to be successful");
		}
		
		// Attempt to make another double jump
		try {
			singlePawnBoard.move("d4", "c6", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	
	@Test
	/**
	 * Test that pawn cannot jump over another piece on double jump
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … ♟ … … … …
	 *	2 ║… … … ♙ … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testPawnCannotJumpOverOnDoubleSpace() {
		// Setup pieces as shown above
		Map<String, Piece> blackPawnInFrontOfWhitePawnMapping = new HashMap<String, Piece>();
		blackPawnInFrontOfWhitePawnMapping.put("d2", new Pawn(Color.WHITE));
		blackPawnInFrontOfWhitePawnMapping.put("d3", new Pawn(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, blackPawnInFrontOfWhitePawnMapping, players);
		
		// Move d2 to d4, this should not succeed
		try {
			board.move("d2", "d4", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	
	@Test
	/**
	 * Test that pawn cannot capture on the double jump
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♟ … … … …
	 *	3 ║… … … … … … … …
	 *	2 ║… … … ♙ … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testPawnCannotCaptureOnDoubleSpace() {
		// Setup pieces as shown above
		Map<String, Piece> blackPawnInFrontOfWhitePawnMapping = new HashMap<String, Piece>();
		blackPawnInFrontOfWhitePawnMapping.put("d2", new Pawn(Color.WHITE));
		blackPawnInFrontOfWhitePawnMapping.put("d4", new Pawn(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, blackPawnInFrontOfWhitePawnMapping, players);
		
		// Move should not be successful
		try {
			board.move("d2", "d4", players[0]);
			fail("Expected invalid move exception");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}

	@Test
	// Test that the pawn can only move in the forward direction, if not capturing
	public void testPawnCannotMoveAnyDirectionExceptForward() {
		try {
			singlePawnBoard.move("d2", "e2", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singlePawnBoard.move("d2", "e1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singlePawnBoard.move("d2", "d1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singlePawnBoard.move("d2", "c1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singlePawnBoard.move("d2", "c2", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singlePawnBoard.move("d2", "c3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singlePawnBoard.move("d2", "e3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}

	@Test
	/**
	 * Test that the pawn can capture in a diagonal direction
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … ♟ … … …
	 *	2 ║… … … ♙ … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testPawnCanCaptureDiagonalSingleSpace() {
		// Setup board as shown above
		Map<String, Piece> blackPawnCapturableByWhitePawnMapping = new HashMap<String, Piece>();
		blackPawnCapturableByWhitePawnMapping.put("d2", new Pawn(Color.WHITE));
		blackPawnCapturableByWhitePawnMapping.put("e3", new Pawn(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, blackPawnCapturableByWhitePawnMapping, players);
		
		// Move piece and capture diagonally
		try {
			board.move("d2", "e3", players[0]);
			assertEquals("Pawn", board.getNameOfPieceAtPosition("e3"));
			assertEquals("Empty", board.getNameOfPieceAtPosition("d2"));
		} catch (InvalidMoveException e) {
			fail("Expected move to be valid");
		}
		
	}

	@Test
	/**
	 * Test that the pawn cannot capture except in the diagonal direction
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … ♟ … … … …
	 *	2 ║… … ♟ ♙ ♟ … … …
	 *	1 ║… … ♟ ♟ ♟ … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testPawnCannotCaptureUnlessDiagonalSingleSpace() {
		// Setup pieces as shown above
		Map<String, Piece> blackPawnNotCapturableByWhitePawnMapping = new HashMap<String, Piece>();
		blackPawnNotCapturableByWhitePawnMapping.put("d2", new Pawn(Color.WHITE));
		blackPawnNotCapturableByWhitePawnMapping.put("e2", new Pawn(Color.BLACK));
		blackPawnNotCapturableByWhitePawnMapping.put("e1", new Pawn(Color.BLACK));
		blackPawnNotCapturableByWhitePawnMapping.put("d3", new Pawn(Color.BLACK));
		blackPawnNotCapturableByWhitePawnMapping.put("d1", new Pawn(Color.BLACK));
		blackPawnNotCapturableByWhitePawnMapping.put("c1", new Pawn(Color.BLACK));
		blackPawnNotCapturableByWhitePawnMapping.put("c2", new Pawn(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, blackPawnNotCapturableByWhitePawnMapping, players);
		
		// Attempt various captures, all should fail
		try {
			board.move("d2", "e2", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d2", "e1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d2", "d3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d2", "d1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d2", "c1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d2", "c2", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
}
