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
import main.pieces.DoubleJumper;
import tests.ChessBaseTest;
import main.pieces.Piece.Color;

public class DoubleJumperTest extends ChessBaseTest{

	private Board singleDoubleJumperBoard;
	private int NUM_ROWS = 8;
	private int NUM_COLUMNS = 8;
	private Map<String, Piece> singleDoubleJumperBoardMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Setup single DoubleJumper
		DoubleJumper doubleJumper = new DoubleJumper(Color.WHITE);
		
		// Place DoubleJumper at d4
		singleDoubleJumperBoardMapping = new HashMap<String, Piece>();
		singleDoubleJumperBoardMapping.put("d4", doubleJumper);
		
		// Create two players
		this.players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(doubleJumper));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board with above information
		singleDoubleJumperBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleDoubleJumperBoardMapping, players);
	}

	/**
	 * Reset the board to its initial state
	 */
	private void resetBoardToInitial(Map<String, Piece> pieceMapping) {
		singleDoubleJumperBoard = new Board(NUM_ROWS, NUM_COLUMNS, pieceMapping, players);
	}

	@Test
	/**
	 * Test that the DoubleJumper can move in all horizontal/vertical directions by unlimited double jumps
	 */
	public void testDoubleJumperCanMakeLegalMovement() {
		try {
			singleDoubleJumperBoard.move("d4", "d8", players[0]);
			assertEquals("Double Jumper", singleDoubleJumperBoard.getNameOfPieceAtPosition("d8"));
			
			resetBoardToInitial(singleDoubleJumperBoardMapping);
			singleDoubleJumperBoard.move("d4", "d6", players[0]);
			assertEquals("Double Jumper", singleDoubleJumperBoard.getNameOfPieceAtPosition("d6"));
			
			resetBoardToInitial(singleDoubleJumperBoardMapping);
			singleDoubleJumperBoard.move("d4", "d2", players[0]);
			assertEquals("Double Jumper", singleDoubleJumperBoard.getNameOfPieceAtPosition("d2"));
			
			resetBoardToInitial(singleDoubleJumperBoardMapping);
			singleDoubleJumperBoard.move("d4", "h4", players[0]);
			assertEquals("Double Jumper", singleDoubleJumperBoard.getNameOfPieceAtPosition("h4"));
			
			resetBoardToInitial(singleDoubleJumperBoardMapping);
			singleDoubleJumperBoard.move("d4", "f4", players[0]);
			assertEquals("Double Jumper", singleDoubleJumperBoard.getNameOfPieceAtPosition("f4"));
			
			resetBoardToInitial(singleDoubleJumperBoardMapping);
			singleDoubleJumperBoard.move("d4", "b4", players[0]);
			assertEquals("Double Jumper", singleDoubleJumperBoard.getNameOfPieceAtPosition("b4"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
	}

	@Test
	/**
	 * Try a multitude of illegal moves for the DoubleJumper
	 */
	public void testDoubleJumperCannotMakeIllegalMovement() {
		try {
			singleDoubleJumperBoard.move("d4", "f5", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleDoubleJumperBoard.move("d4", "b2", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleDoubleJumperBoard.move("d4", "b3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	
	@Test
	/**
	 * Test that DoubleJumper can only capture enemies with a diagonal double jump
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … ▓
	 *	6 ║… … … … … … ▓ …
	 *	5 ║… ▓ … ▓ … ▓ … …
	 *	4 ║… … ▓ ▓ ▓ … … …
	 *	3 ║… ▓ ▓ ░ ▓ ▓ … …
	 *	2 ║… … ▓ ▓ ▓ … … …
	 *	1 ║… ▓ … ▓ … ▓ … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testDoubleJumperCanOnlyCaptureEnemyWithDiagonalDoubleJump() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new DoubleJumper(Color.WHITE));
		customMapping.put("d4", new DoubleJumper(Color.BLACK));
		customMapping.put("d5", new DoubleJumper(Color.BLACK));
		customMapping.put("d1", new DoubleJumper(Color.BLACK));
		customMapping.put("d2", new DoubleJumper(Color.BLACK));
		customMapping.put("c4", new DoubleJumper(Color.BLACK));
		customMapping.put("c3", new DoubleJumper(Color.BLACK));
		customMapping.put("c2", new DoubleJumper(Color.BLACK));
		customMapping.put("e4", new DoubleJumper(Color.BLACK));
		customMapping.put("e3", new DoubleJumper(Color.BLACK));
		customMapping.put("e2", new DoubleJumper(Color.BLACK));
		customMapping.put("b5", new DoubleJumper(Color.BLACK));
		customMapping.put("b3", new DoubleJumper(Color.BLACK));
		customMapping.put("b1", new DoubleJumper(Color.BLACK));
		customMapping.put("f5", new DoubleJumper(Color.BLACK));
		customMapping.put("f3", new DoubleJumper(Color.BLACK));
		customMapping.put("f1", new DoubleJumper(Color.BLACK));
		customMapping.put("g6", new DoubleJumper(Color.BLACK));
		customMapping.put("h7", new DoubleJumper(Color.BLACK));

		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);

		
		// Test all noncapturable pieces
		
		
		try {
			board.move("d3", "d5", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "d4", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "d2", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "d1", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "c4", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "c3", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "c2", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "b3", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "e4", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "e3", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "e2", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "f3", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			board.move("d3", "g6", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		// Only iterations of diagonal double jumps should be successful captures
		try {
			
			board.move("d3", "b5", players[0]);
			assertEquals("Double Jumper", board.getNameOfPieceAtPosition("b5"));
			
			board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
			board.move("d3", "b1", players[0]);
			assertEquals("Double Jumper", board.getNameOfPieceAtPosition("b1"));
			
			board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
			board.move("d3", "f5", players[0]);
			assertEquals("Double Jumper", board.getNameOfPieceAtPosition("f5"));
			
			board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
			board.move("d3", "f1", players[0]);
			assertEquals("Double Jumper", board.getNameOfPieceAtPosition("f1"));
			
			board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
			board.move("d3", "h7", players[0]);
			assertEquals("Double Jumper", board.getNameOfPieceAtPosition("h7"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
	}

}
