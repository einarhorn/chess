package tests.core;

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

public class BoardTest extends ChessBaseTest {

	private Board board;
	private int NUM_ROWS = 8;
	private int NUM_COLUMNS = 8;
	private Map<String, Piece> standardPieceMapping;
	private Player[] players;
	
	@Before
	public void setUp() throws Exception {
		// Set up two kings on the board
		standardPieceMapping = new HashMap<String, Piece>();
		King whiteKing = new King(Color.WHITE);
		King blackKing = new King(Color.BLACK);
		
		// One king at a1, one at f3
		standardPieceMapping.put("a1", whiteKing);
		standardPieceMapping.put("f3", blackKing);
		
		// Setup two players, one king each
		players = new Player[2];
		players[0] = new Player("Player 1", Color.WHITE, GameDirection.UPWARDS, Arrays.asList(whiteKing));
		players[1] = new Player("Player 2", Color.BLACK, GameDirection.DOWNWARDS, Arrays.asList(blackKing));
		
		// Setup board with above information
		board = new Board(NUM_ROWS, NUM_COLUMNS, standardPieceMapping, players);
	}

	@Test
	public void testGetNumRows() {
		// Execute
		int numRows = board.getNumRows();
		
		// Verify
		assertEquals(NUM_ROWS, numRows);
	}

	@Test
	public void testGetNumCols() {
		// Execute
		int numCols = board.getNumCols();
		
		// Verify
		assertEquals(NUM_COLUMNS, numCols);
	}

	@Test
	/**
	 * Test that a white king can be moved from a1 to a2
	 */
	public void testMove() {
		// Setup
		String initialPosition = "a1";
		String newPosition = "a2";
		String pieceAtInitialPosition = board.getNameOfPieceAtPosition(initialPosition);
		
		// Execute
		try {
			board.move(initialPosition, newPosition, players[0]);
		} catch (InvalidMoveException e) {
			fail("Unexpected move error");
		}
		
		// Verify
		String pieceAtNewPosition = board.getNameOfPieceAtPosition(newPosition);
		assertEquals(pieceAtInitialPosition, pieceAtNewPosition);
	}
	
	@Test
	/**
	 * Test that a white king can be moved from a1 to a2
	 */
	public void testMoveViaRowAndColumnEndpoints() {
		// Setup
		String initialPosition = "a1";
		int initialRow = 7;
		int initialCol = 0;
		
		int endRow = 6;
		int endCol = 0;
		String newPosition = "a2";
		String pieceAtInitialPosition = board.getNameOfPieceAtPosition(initialPosition);
		
		// Execute
		try {
			board.move(initialRow, initialCol, endRow, endCol, players[0]);
		} catch (InvalidMoveException e) {
			fail("Unexpected move error");
		}
		
		// Verify
		String pieceAtNewPosition = board.getNameOfPieceAtPosition(newPosition);
		assertEquals(pieceAtInitialPosition, pieceAtNewPosition);
	}
	
	
	@Test
	/**
	 * Test that a white king cannot be moved by the black player
	 */
	public void testMoveCanOnlyBeMovedByOwnPlayer() {
		// Setup for moving a white piece
		String initialPosition = "a1";
		String newPosition = "a2";
		
		// Execute move with black player, should throw
		try {
			board.move(initialPosition, newPosition, players[1]);
			fail("Expected invalid move error");
		} catch (InvalidMoveException e) {
			assertInvalidMove(e);
		}
	}
	

	@Test
	public void testGetGameAsString() {
		String defaultBoardState = 
				"8 ║… … … … … … … … \n" +
				"7 ║… … … … … … … … \n" +
				"6 ║… … … … … … … … \n" +
				"5 ║… … … … … … … … \n" +
				"4 ║… … … … … … … … \n" +
				"3 ║… … … … … ♚ … … \n" +
				"2 ║… … … … … … … … \n" +
				"1 ║♔ … … … … … … … \n" +
				"—╚════════════════\n" +
				"—— a b c d e f g h ";

		// Execute
		String gameString = board.getGameAsString();
		
		// Verify
		assertEquals(defaultBoardState, gameString);
	}

	@Test
	public void testCanUndoMove() {
		// Since there are no moves yet, undo is not possible
		assertEquals(false, board.canUndoMove());
		
		// Move piece, so that undo is posssible
		String initialPosition = "a1";
		String newPosition = "a2";
		try {
			board.move(initialPosition, newPosition, players[0]);
		} catch (InvalidMoveException e) {
			fail("Move should have been successful");
		}
		
		// Undo should now be possible
		assertEquals(true, board.canUndoMove());
	}
	
	@Test
	public void testCanRedoMove() {
		// Since there are no moves yet, redo is not possible
		assertEquals(false, board.canRedoMove());
		
		// Move piece, so that undo is posssible
		String initialPosition = "a1";
		String newPosition = "a2";
		try {
			board.move(initialPosition, newPosition, players[0]);
		} catch (InvalidMoveException e) {
			fail("Move should have been successful");
		}
		board.undoLastMove();
		
		// Redo should now be possible
		assertEquals(true, board.canRedoMove());
	}
	
	
	@Test
	public void testRedo() {
		// Since there are no moves yet, redo is not possible
		assertEquals(false, board.canRedoMove());
		
		// Move piece, so that undo is posssible
		String initialPosition = "a1";
		String newPosition = "a2";
		String pieceAtInitialPosition = board.getNameOfPieceAtPosition(initialPosition);
		try {
			board.move(initialPosition, newPosition, players[0]);
		} catch (InvalidMoveException e) {
			fail("Move should have been successful");
		}
		board.undoLastMove();
		board.redoMove();
		
		// Verify move
		String pieceAtNewPosition = board.getNameOfPieceAtPosition(newPosition);
		assertEquals(pieceAtInitialPosition, pieceAtNewPosition);
	}
	
	
}
