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
import main.pieces.King;
import main.pieces.Piece;
import main.pieces.Piece.Color;
import tests.ChessBaseTest;

public class KingTest extends ChessBaseTest{

	private Board singleKingBoard;
	private int NUM_ROWS = 8;
	private int NUM_COLUMNS = 8;
	private Map<String, Piece> singleKingBoardMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Setup a single white king
		King king = new King(Color.WHITE);
		
		// Place this king at d4
		singleKingBoardMapping = new HashMap<String, Piece>();
		singleKingBoardMapping.put("d4", king);
		
		// Setup two players
		this.players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(king));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board with above information
		singleKingBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleKingBoardMapping, players);
	}

	/**
	 * Reset the board to its initial state
	 */
	private void resetBoardToInitial() {
		singleKingBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleKingBoardMapping, players);
	}

	@Test
	/**
	 * Test that the rook can move in all 8 legal directions by only 1 step
	 */
	public void testKingCanMakeLegalMovement() {
		try {
			singleKingBoard.move("d4", "c5", players[0]);
			assertEquals("King", singleKingBoard.getNameOfPieceAtPosition("c5"));
			
			resetBoardToInitial();
			singleKingBoard.move("d4", "d5", players[0]);
			assertEquals("King", singleKingBoard.getNameOfPieceAtPosition("d5"));
			
			resetBoardToInitial();
			singleKingBoard.move("d4", "e5", players[0]);
			assertEquals("King", singleKingBoard.getNameOfPieceAtPosition("e5"));
			
			resetBoardToInitial();
			singleKingBoard.move("d4", "e4", players[0]);
			assertEquals("King", singleKingBoard.getNameOfPieceAtPosition("e4"));
			
			resetBoardToInitial();
			singleKingBoard.move("d4", "e3", players[0]);
			assertEquals("King", singleKingBoard.getNameOfPieceAtPosition("e3"));
			
			resetBoardToInitial();
			singleKingBoard.move("d4", "d3", players[0]);
			assertEquals("King", singleKingBoard.getNameOfPieceAtPosition("d3"));
			
			resetBoardToInitial();
			singleKingBoard.move("d4", "c3", players[0]);
			assertEquals("King", singleKingBoard.getNameOfPieceAtPosition("c3"));
			
			resetBoardToInitial();
			singleKingBoard.move("d4", "c4", players[0]);
			assertEquals("King", singleKingBoard.getNameOfPieceAtPosition("c4"));
		} catch (InvalidMoveException e) {
			fail("Move should have been valid");
		}
	}

	@Test
	/**
	 * Try a multitude of illegal moves for the King
	 */
	public void testKingCannotMakeIllegalMovement() {
		try {
			singleKingBoard.move("d4", "f5", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleKingBoard.move("d4", "e1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleKingBoard.move("d4", "b2", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	

	@Test
	/**
	 * Test that King can capture enemy pieces
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♚ … … … …
	 *	3 ║… … … ♔ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testKingCanCaptureEnemy() {
		// Setup the board with the custom mapping shown above
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new King(Color.WHITE));
		customMapping.put("d4", new King(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			// Test that the capture was successful
			board.move("d3", "d4", players[0]);
			assertEquals("King", board.getNameOfPieceAtPosition("d4"));
			assertEquals("Empty", board.getNameOfPieceAtPosition("d3"));
		} catch (InvalidMoveException e) {
			fail("Move should have been successful");
		}
		
	}
	
	@Test
	/**
	 * Test that King cannot land on a piece of the same color
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♔ … … … …
	 *	3 ║… … … ♔ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testKingCannotCaptureSameColor() {
		// Setup the board with the custom mapping shown above
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new King(Color.WHITE));
		customMapping.put("d4", new King(Color.WHITE));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			// Test that the move was considered invalid
			board.move("d3", "d4", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
}
