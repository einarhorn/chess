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
import main.pieces.RowMover;
import tests.ChessBaseTest;
import main.pieces.Piece.Color;

public class RowMoverTest extends ChessBaseTest{

	private Board singleRowMoverBoard;
	private static final int NUM_ROWS = 8;
	private static final int NUM_COLUMNS = 8;
	private Map<String, Piece> singleRowMoverBoardMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Setup single RowMover
		RowMover rowMover = new RowMover(Color.WHITE);
		
		// Place RowMover at d4
		singleRowMoverBoardMapping = new HashMap<String, Piece>();
		singleRowMoverBoardMapping.put("d4", rowMover);
		
		// Create two players
		this.players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(rowMover));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board with above information
		singleRowMoverBoard = new Board(NUM_ROWS, NUM_COLUMNS, singleRowMoverBoardMapping, players);
	}

	/**
	 * Reset the board to its initial state
	 */
	private void resetBoardToInitial(Map<String, Piece> pieceMapping) {
		singleRowMoverBoard = new Board(NUM_ROWS, NUM_COLUMNS, pieceMapping, players);
	}

	@Test
	/**
	 * Test that the RowMover can to any location in the next for
	 */
	public void testRowMoverCanMakeLegalMovement() {
		try {
			singleRowMoverBoard.move("d4", "a5", players[0]);
			assertEquals("Row Mover", singleRowMoverBoard.getNameOfPieceAtPosition("a5"));
				
			resetBoardToInitial(singleRowMoverBoardMapping);
			singleRowMoverBoard.move("d4", "b5", players[0]);
			assertEquals("Row Mover", singleRowMoverBoard.getNameOfPieceAtPosition("b5"));
			
			resetBoardToInitial(singleRowMoverBoardMapping);
			singleRowMoverBoard.move("d4", "c5", players[0]);
			assertEquals("Row Mover", singleRowMoverBoard.getNameOfPieceAtPosition("c5"));
			
			resetBoardToInitial(singleRowMoverBoardMapping);
			singleRowMoverBoard.move("d4", "d5", players[0]);
			assertEquals("Row Mover", singleRowMoverBoard.getNameOfPieceAtPosition("d5"));
			
			resetBoardToInitial(singleRowMoverBoardMapping);
			singleRowMoverBoard.move("d4", "e5", players[0]);
			assertEquals("Row Mover", singleRowMoverBoard.getNameOfPieceAtPosition("e5"));
			
			resetBoardToInitial(singleRowMoverBoardMapping);
			singleRowMoverBoard.move("d4", "g5", players[0]);
			assertEquals("Row Mover", singleRowMoverBoard.getNameOfPieceAtPosition("g5"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
	}

	@Test
	/**
	 * Try a multitude of illegal moves for the RowMover, including moving to the previous row
	 */
	public void testRowMoverCannotMakeIllegalMovement() {
		try {
			singleRowMoverBoard.move("d4", "d3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleRowMoverBoard.move("d4", "b2", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		try {
			singleRowMoverBoard.move("d4", "b3", players[0]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	
	@Test
	/**
	 * Test that RowMover can only capture an enemy that is in the row behind it
	 * Setup looks like:
	 * 
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … ⬛ ⬛ ⬛ … … …
	 *	3 ║… … ⬛ ⬜ ⬛ … … …
	 *	2 ║… ⬛ ⬛ ⬛ ⬛ ⬛ ⬛ …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 */
	public void testRowMoverCanOnlyCaptureEnemyWithDiagonalDoubleJump() {
		Map<String, Piece> customMapping = new HashMap<String, Piece>();
		customMapping.put("d3", new RowMover(Color.WHITE));
		customMapping.put("d2", new RowMover(Color.BLACK));
		customMapping.put("d4", new RowMover(Color.BLACK));
		customMapping.put("c2", new RowMover(Color.BLACK));
		customMapping.put("c3", new RowMover(Color.BLACK));
		customMapping.put("c4", new RowMover(Color.BLACK));
		customMapping.put("e2", new RowMover(Color.BLACK));
		customMapping.put("e3", new RowMover(Color.BLACK));
		customMapping.put("e4", new RowMover(Color.BLACK));
		customMapping.put("b2", new RowMover(Color.BLACK));
		customMapping.put("g2", new RowMover(Color.BLACK));

		Board board = new Board(NUM_ROWS, NUM_COLUMNS, customMapping, players);

		
		// Test that pieces in the next row, and current row cannot be captured
		
		
		try {
			board.move("d3", "c4", players[0]);
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
			board.move("d3", "e4", players[0]);
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
			board.move("d3", "e3", players[0]);
			fail("Move should have been invalid");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
		
		
		// Test that capturing pieces in the row behind it is successful
		try {
			
			board.move("d3", "b2", players[0]);
			assertEquals("Row Mover", board.getNameOfPieceAtPosition("b2"));
			
//			board.undoLastMove();
//			board.move("d3", "c2", players[0]);
//			assertEquals("Row Mover", board.getNameOfPieceAtPosition("c2"));
//			
//			board.undoLastMove();
//			board.move("d3", "d2", players[0]);
//			assertEquals("Row Mover", board.getNameOfPieceAtPosition("d2"));
//			
//			board.undoLastMove();
//			board.move("d3", "e2", players[0]);
//			assertEquals("Row Mover", board.getNameOfPieceAtPosition("e2"));
//			
//			board.undoLastMove();
//			board.move("d3", "f2", players[0]);
//			assertEquals("Row Mover", board.getNameOfPieceAtPosition("f2"));
//			
//			board.undoLastMove();
//			board.move("d3", "g2", players[0]);
//			assertEquals("Row Mover", board.getNameOfPieceAtPosition("g2"));
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
	}

}
