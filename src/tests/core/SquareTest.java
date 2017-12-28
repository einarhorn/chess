package tests.core;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import main.core.Board;
import main.core.GameDirection;
import main.core.Player;
import main.core.Square;
import main.pieces.King;
import main.pieces.Piece;
import main.pieces.Piece.Color;

public class SquareTest {
	
	private Square square;
	private int row, column;
	private int TOTAL_ROWS = 8, TOTAL_COLS = 8;
	private Player[] players;
	private Map<String, Piece> pieceMapping;
	private Board board;
	private King testKing;
	
	@Before
	public void setUp() throws Exception {
		this.row = 5;
		this.column = 5;
		this.pieceMapping = new HashMap<String, Piece>();
		this.board = new Board(TOTAL_ROWS, TOTAL_COLS, pieceMapping, players);
		this.square = new Square(row, column, board);
		this.testKing = new King(Color.WHITE);
	}

	@Test
	public void testToString() {
		assertEquals("f3", square.toString());
	}

	@Test
	public void testHasPiece() {
		// Square should not have piece by default
		assertEquals(false, square.hasPiece());
		
		// Set a piece on the square
		square.setPiece(testKing);
		assertEquals(true, square.hasPiece());
		
		// Remove a piece
		square.removePiece();
		assertEquals(false, square.hasPiece());
	}

	@Test
	public void testCanPlacePiece() {
		// By default square should be empty
		assertEquals(true, square.canPlacePiece());
		
		// Add a piece
		square.setPiece(testKing);
		assertEquals(false, square.canPlacePiece());
	}

	@Test
	public void testRemovePiece() {
		// Set a piece on the square
		square.setPiece(testKing);
		assertEquals(true, square.hasPiece());
		
		// Remove a piece
		square.removePiece();
		assertEquals(false, square.hasPiece());
	}

	@Test
	public void testGetPiece() {
		// By default square should be empty
		assertEquals(null, square.getPiece());
		
		// Add a piece
		square.setPiece(testKing);
		assertEquals(testKing, square.getPiece());
	}

	@Test
	public void testGetRow() {
		assertEquals(row, square.getRow());
	}

	@Test
	public void testGetCol() {
		assertEquals(column, square.getCol());
	}

	@Test
	public void testGetRowFromDirectionPerspective() {
		assertEquals(row, square.getRowFromDirectionPerspective(GameDirection.DOWNWARDS));
		assertEquals(3, square.getRowFromDirectionPerspective(GameDirection.UPWARDS));
	}

	@Test
	public void testGetColFromPlayerPerspective() {
		assertEquals(3, square.getColFromDirectionPerspective(GameDirection.DOWNWARDS));
		assertEquals(5, square.getColFromDirectionPerspective(GameDirection.UPWARDS));
	}

	@Test
	public void testSetPiece() {
		// Set a piece on the square
		square.setPiece(testKing);
		assertEquals(true, square.hasPiece());
		
		// Remove a piece
		square.removePiece();
		assertEquals(false, square.hasPiece());
	}

}
