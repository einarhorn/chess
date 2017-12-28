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
import main.pieces.Queen;
import tests.ChessBaseTest;
import main.pieces.Piece.Color;

public class QueenTest extends ChessBaseTest{

	private Board singleQueenBoard;
	private int NUM_ROWS = 8;
	private int NUM_COLUMNS = 8;
	private Map<String, Piece> singleQueenBoardMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Setup single queen
		Queen queen = new Queen(Color.WHITE);
		
		// Place queen at d4
		singleQueenBoardMapping = new HashMap<String, Piece>();
		singleQueenBoardMapping.put("d4", queen);
		
		// Create two players
		this.players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(queen));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board with above information
		singleQueenBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleQueenBoardMapping, players);
	}

	/**
	 * Reset the board to its initial state
	 */
	private void resetBoardToInitial() {
		singleQueenBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleQueenBoardMapping, players);
	}

	@Test
	/**
	 * Test that the queen can move in all 8 legal directions by any amount
	 */
	public void testQueenCanMakeLegalMovement() {
		try {
			singleQueenBoard.move("d4", "d8", players[0]);
			assertEquals("Queen", singleQueenBoard.getNameOfPieceAtPosition("d8"));
			
			resetBoardToInitial();
			singleQueenBoard.move("d4", "a4", players[0]);
			assertEquals("Queen", singleQueenBoard.getNameOfPieceAtPosition("a4"));
			
			resetBoardToInitial();
			singleQueenBoard.move("d4", "d1", players[0]);
			assertEquals("Queen", singleQueenBoard.getNameOfPieceAtPosition("d1"));
			
			resetBoardToInitial();
			singleQueenBoard.move("d4", "h4", players[0]);
			assertEquals("Queen", singleQueenBoard.getNameOfPieceAtPosition("h4"));
			
			resetBoardToInitial();
			singleQueenBoard.move("d4", "f6", players[0]);
			assertEquals("Queen", singleQueenBoard.getNameOfPieceAtPosition("f6"));
			
			resetBoardToInitial();
			singleQueenBoard.move("d4", "f2", players[0]);
			assertEquals("Queen", singleQueenBoard.getNameOfPieceAtPosition("f2"));
			
			resetBoardToInitial();
			singleQueenBoard.move("d4", "c3", players[0]);
			assertEquals("Queen", singleQueenBoard.getNameOfPieceAtPosition("c3"));
			
			resetBoardToInitial();
			singleQueenBoard.move("d4", "c5", players[0]);
			assertEquals("Queen", singleQueenBoard.getNameOfPieceAtPosition("c5"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
	}

	@Test
	/**
	 * Try a multitude of illegal moves for the Queen
	 */
	public void testQueenCannotMakeIllegalMovement() {
		try {
			singleQueenBoard.move("d4", "f5", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleQueenBoard.move("d4", "e1", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleQueenBoard.move("d4", "b3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	
	@Test
	/**
	 * Test that Queen cannot jump over enemy
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♛ … … … …
	 *	3 ║… … … ♕ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testQueenCannotJumpEnemy() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Queen(Color.WHITE));
		customMapping.put("d4", new Queen(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			board.move("d3", "d7", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}

	@Test
	/**
	 * Test that Queen can capture enemy pieces
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♛ … … … …
	 *	3 ║… … … ♕ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testQueenCanCaptureEnemy() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Queen(Color.WHITE));
		customMapping.put("d4", new Queen(Color.BLACK));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			board.move("d3", "d4", players[0]);
			assertEquals("Queen", board.getNameOfPieceAtPosition("d4"));
			assertEquals("Empty", board.getNameOfPieceAtPosition("d3"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
		
	}
	
	@Test
	/**
	 * Test that Queen cannot land on a piece of the same color
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … ♕ … … … …
	 *	3 ║… … … ♕ … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testQueenCannotCaptureSameColor() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new Queen(Color.WHITE));
		customMapping.put("d4", new Queen(Color.WHITE));
		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);
		
		try {
			board.move("d3", "d4", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
}
