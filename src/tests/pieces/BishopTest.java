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
import main.pieces.Bishop;
import tests.ChessBaseTest;
import main.pieces.Piece.Color;

public class BishopTest extends ChessBaseTest{

	private Board singleBishopBoard;
	private int NUM_ROWS = 8;
	private int NUM_COLUMNS = 8;
	private Map<String, Piece> singleBishopBoardMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Setup single Bishop
		Bishop Bishop = new Bishop(Color.WHITE);
		
		// Place Bishop at d4
		singleBishopBoardMapping = new HashMap<String, Piece>();
		singleBishopBoardMapping.put("d4", Bishop);
		
		// Create two players
		this.players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(Bishop));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board with above information
		singleBishopBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleBishopBoardMapping, players);
	}

	/**
	 * Reset the board to its initial state
	 */
	private void resetBoardToInitial() {
		singleBishopBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleBishopBoardMapping, players);
	}

	@Test
	/**
	 * Test that the bishop can move in all 4 legal directions by any amount
	 */
	public void testBishopCanMakeLegalMovement() {
		try {
			singleBishopBoard.move("d4", "f6", players[0]);
			assertEquals("Bishop", singleBishopBoard.getNameOfPieceAtPosition("f6"));
			
			resetBoardToInitial();
			singleBishopBoard.move("d4", "f2", players[0]);
			assertEquals("Bishop", singleBishopBoard.getNameOfPieceAtPosition("f2"));
			
			resetBoardToInitial();
			singleBishopBoard.move("d4", "c3", players[0]);
			assertEquals("Bishop", singleBishopBoard.getNameOfPieceAtPosition("c3"));
			
			resetBoardToInitial();
			singleBishopBoard.move("d4", "c5", players[0]);
			assertEquals("Bishop", singleBishopBoard.getNameOfPieceAtPosition("c5"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
	}

	@Test
	/**
	 * Try a multitude of illegal moves for the Bishop
	 */
	public void testBishopCannotMakeIllegalMovement() {
		try {
			singleBishopBoard.move("d4", "d6", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleBishopBoard.move("d4", "e1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleBishopBoard.move("d4", "b3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	
	@Test
	/**
	 * Test that Bishop cannot jump over enemy
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … ♝ … … …
	 *	3 ║… … … ♗ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testBishopCannotJumpEnemy() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Bishop(Color.WHITE));
		customMapping.put("e4", new Bishop(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			board.move("d3", "f5", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}

	@Test
	/**
	 * Test that Bishop can capture enemy pieces
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … ♝ … … …
	 *	3 ║… … … ♗ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testBishopCanCaptureEnemy() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Bishop(Color.WHITE));
		customMapping.put("e4", new Bishop(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			board.move("d3", "e4", players[0]);
			assertEquals("Bishop", board.getNameOfPieceAtPosition("e4"));
			assertEquals("Empty", board.getNameOfPieceAtPosition("d3"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
		
	}
	
	@Test
	/**
	 * Test that Bishop cannot land on a piece of the same color
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … ♗ … … …
	 *	3 ║… … … ♗ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testBishopCannotCaptureSameColor() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Bishop(Color.WHITE));
		customMapping.put("e4", new Bishop(Color.WHITE));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			board.move("d3", "e4", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
}
