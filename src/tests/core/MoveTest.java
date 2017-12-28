package tests.core;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.core.Board;
import main.core.GameDirection;
import main.core.Move;
import main.core.Player;
import main.core.Square;
import main.pieces.Pawn;
import main.pieces.Piece;
import main.pieces.Piece.Color;

public class MoveTest {

	private Move move;
	private Square startSquare;
	private Square endSquare;
	private Player player;
	private Board board;
	private Piece startPiece;
	private static final int START_ROW = 0;
	private static final int END_ROW = 1;
	private static final int START_COL = 0;
	private static final int END_COL = 1;
	private static final int BOARD_ROWS = 8;
	private static final int BOARD_COLS = 8;
	
	@Before
	public void setUp() throws Exception {
		// Setup player
		player = new Player("PlayerName", Color.WHITE, GameDirection.DOWNWARDS, Arrays.asList());
		
		// Setup board
		Map<String, Piece> pieceMapping = new HashMap<String, Piece>();
		Player[] players = new Player[2];
		players[0] = player;
		board = new Board(BOARD_ROWS, BOARD_COLS, pieceMapping, players);
		
		// Setup the start and end squares
		startSquare = new Square(START_ROW, START_COL, board);
		endSquare = new Square(END_ROW, END_COL, board);
		
		// Place a white piece on startSquare
		startPiece = new Pawn(Color.WHITE);
		startSquare.setPiece(startPiece);
		
		// Setup move object with above information
		move = new Move(startSquare, endSquare, player);
	}

	@Test
	/**
	 * Test that the function successfully checks whether this is a capture move
	 */
	public void testIsCaptureMove() {
		// Since there is nothing on endsSquare by default, should not be capture move
		assertEquals(false, move.isCaptureMove());
		
		// Place a black piece on endSquare
		Pawn blackPawn = new Pawn(Color.BLACK);
		endSquare.setPiece(blackPawn);
		move = new Move(startSquare, endSquare, player);
		assertEquals(true, move.isCaptureMove());
		
		// Remove the black piece on endSquare and place a white piece there
		endSquare.removePiece();
		Pawn whitePawn = new Pawn(Color.WHITE);
		endSquare.setPiece(whitePawn);
		move = new Move(startSquare, endSquare, player);
		assertEquals(false, move.isCaptureMove());
	}

	@Test
	/**
	 * Test that the check for valid movement pattern succeeds
	 * Since the calculation for movement patterns are only done in the constructor,
	 * we reconstruct our move object after changing the setup of the board
	 */
	public void testHasValidMovementPattern() {
		// Pawn moving diagonally with nothing at the target should not be considered a valid move
		assertEquals(false, move.hasValidMovementPattern());
		
		// Place a piece at the diagonal, 1 step away
		Pawn blackPawn = new Pawn(Color.BLACK);
		endSquare.setPiece(blackPawn);
		move = new Move(startSquare, endSquare, player);
		assertEquals(true, move.hasValidMovementPattern());
	}

	@Test
	/**
	 * Test that to retrieve movement pattern
	 * Since the calculation for movement patterns are only done in the constructor,
	 * we reconstruct our move object after changing the setup of the board
	 */
	public void testGetMovementPattern() {
		// Pawn moving diagonally with nothing at the target should not be considered a valid move
		assertEquals(null, move.getMovementPattern());
		
		// Place a piece at the diagonal, 1 step away
		Pawn blackPawn = new Pawn(Color.BLACK);
		endSquare.setPiece(blackPawn);
		move = new Move(startSquare, endSquare, player);
		Assert.assertNotNull(move.hasValidMovementPattern());
	}

	@Test
	public void testGetStartRow() {
		assertEquals(START_ROW, move.getStartRow());
	}

	@Test
	public void testGetStartCol() {
		assertEquals(START_COL, move.getStartCol());
	}

	@Test
	public void testGetEndRow() {
		assertEquals(END_ROW, move.getEndRow());
	}

	@Test
	public void testGetEndCol() {
		assertEquals(END_COL, move.getEndCol());
	}

	@Test
	public void testGetStartRowFromPlayerPerspective() {
		assertEquals(startSquare.getRowFromDirectionPerspective(player.getDirection()), move.getStartRowFromPlayerPerspective());
	}

	@Test
	public void testGetStartColFromPlayerPerspective() {
		assertEquals(startSquare.getColFromDirectionPerspective(player.getDirection()), move.getStartColFromPlayerPerspective());
	}

	@Test
	public void testGetEndRowFromPlayerPerspective() {
		assertEquals(endSquare.getRowFromDirectionPerspective(player.getDirection()), move.getEndRowFromPlayerPerspective());
	}

	@Test
	public void testGetEndColFromPlayerPerspective() {
		assertEquals(endSquare.getColFromDirectionPerspective(player.getDirection()), move.getEndColFromPlayerPerspective());
	}

	@Test
	/**
	 * Calculation should simply be a difference of the endRowFromPlayerPerspective() and startRowFromPlayerPerspective() calls
	 */
	public void testGetRowMovementFromPlayerPerspective() {
		int expectedRowMovement = move.getEndRowFromPlayerPerspective() - move.getStartRowFromPlayerPerspective();
		assertEquals(expectedRowMovement, move.getRowMovementFromPlayerPerspective());
	}

	@Test
	/**
	 * Calculation should simply be a difference of the endColFromPlayerPerspective() and startColFromPlayerPerspective() calls
	 */
	public void testGetColMovementFromPlayerPerspective() {
		int expectedColMovement = move.getEndColFromPlayerPerspective() - move.getStartColFromPlayerPerspective();
		assertEquals(expectedColMovement, move.getColMovementFromPlayerPerspective());
	}

	@Test
	public void testGetStartSquare() {
		assertEquals(startSquare, move.getStartSquare());
	}

	@Test
	public void testGetEndSquare() {
		assertEquals(endSquare, move.getEndSquare());
	}

	@Test
	public void testGetPiece() {
		assertEquals(startPiece, move.getPiece());
	}

	@Test
	public void testGetPlayer() {
		assertEquals(player, move.getPlayer());
	}

	@Test
	public void testGetCapturedPiece() {
		// Since there is nothing on endsSquare by default, should not return anything
		assertEquals(null, move.getCapturedPiece());
		
		// Place a black piece on endSquare and test again
		Pawn blackPawn = new Pawn(Color.BLACK);
		endSquare.setPiece(blackPawn);
		
		// Reset the move, since the captured piece information is constructed on initialization
		move = new Move(startSquare, endSquare, player);
		
		// Verify move finds a capturable piece at the destination
		Assert.assertNotNull(move.getCapturedPiece());
	}

}
