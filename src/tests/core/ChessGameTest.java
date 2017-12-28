package tests.core;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.core.ChessModel;
import main.exceptions.InvalidMoveException;
import main.pieces.King;
import main.pieces.Piece;
import main.pieces.Queen;
import main.pieces.Rook;
import main.pieces.Piece.Color;

public class ChessGameTest {

	private ChessModel game;
	private boolean useGUI = false;
	
	private String defaultBoardState = 
		"8 ║♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜ \n" +
		"7 ║♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟ \n" +
		"6 ║… … … … … … … … \n" +
		"5 ║… … … … … … … … \n" +
		"4 ║… … … … … … … … \n" +
		"3 ║… … … … … … … … \n" +
		"2 ║♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙ \n" +
		"1 ║♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖ \n" +
		"—╚════════════════\n" +
		"—— a b c d e f g h ";

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		game = new ChessModel();
	}

	
	@Test
	// Test that players are correctly set up, and main.pieces are set up correctly on board
	public void testInitializeGame() {
		// Execute
		game.startGameWithNewPlayers("Player 1", "Player 2", false);
		
		// Verify game is setup with correct players
		String[] players = game.getPlayerNames();
		assertEquals("Player 1", players[0]);
		assertEquals("Player 2", players[1]);
		
		// Verify game is setup with correct board state
		String boardState = game.getGameAsString();
		assertEquals(defaultBoardState, boardState);
	}

	@Test
	/**
	 * Test that custom board map is successful
	 */
	public void testInitializeGameWithCustomMapping() {
		// Setup
		Map<String, Piece> customMap = new HashMap<String, Piece>();
		customMap.put("a8", new King(Color.BLACK));
		customMap.put("d8", new Queen(Color.BLACK));
		game.startGameWithCustomMapping("Player 1", "Player 2", customMap);
		
		// Verify main.pieces are at expected locations
		assertEquals("King", game.getNameOfPieceAtPosition("a8"));
		assertEquals("Queen", game.getNameOfPieceAtPosition("d8"));
	}

	@Test
	public void testGetCurrentPlayerName_firstPlayerCurrentByDefault() {
		// Setup
		game.startGameWithNewPlayers("Player 1", "Player 2", false);
		
		// Verify first player is current by default
		assertEquals("Player 1", game.getCurrentPlayerName());
	}

	
	@Test
	public void testGetCurrentPlayerName_switchesCurrentPlayerAfterMove() {
		// Setup
		game.startGameWithNewPlayers("Player 1", "Player 2", false);
		
		// Verify first player is current
		assertEquals("Player 1", game.getCurrentPlayerName());
		
		// Verify second player is current after move
		try {
			game.move("b2", "b3");
			assertEquals("Player 2", game.getCurrentPlayerName());
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
		
		// Verify first player is current after move
		try {
			game.move("b7",  "b6");
			assertEquals("Player 1", game.getCurrentPlayerName());
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}		
	}

	@Test
	// General tests to check that move works
	// More thorough tests are done for each piece in the piece subclasses
	public void testMove() {
		// Setup
		game.startGameWithNewPlayers("Player 1", "Player 2", false);
		String initialPosition = "b2";
		String newPosition = "b3";
		String pieceAtInitialPosition = game.getNameOfPieceAtPosition(initialPosition);
		
		// Execute
		try {
			game.move(initialPosition, newPosition);
		} catch (InvalidMoveException e) {
			fail("Unexpected invalid move exception");
		}
		
		// Verify
		String pieceAtNewPosition = game.getNameOfPieceAtPosition(newPosition);
		assertEquals(pieceAtInitialPosition, pieceAtNewPosition);
	}

	@Test
	public void testGetGameAsString() {
		// Setup
		game.startGameWithNewPlayers("Player 1", "Player 2", false);

		// Execute
		String gameString = game.getGameAsString();

		// Verify
		assertEquals(defaultBoardState, gameString);
	}

	@Test
	/**
	 * Test that the following board shows as being in check
	 * 	 * 
	 * 	8 ║… … … ♛ … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … ♔ … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 */
	public void testIsCheck() {
		// Setup custom board
		Map<String, Piece> customMap = new HashMap<String, Piece>();
		customMap.put("d1", new King(Color.WHITE));
		customMap.put("d8", new Queen(Color.BLACK));
		game.startGameWithCustomMapping("Player 1", "Player 2", customMap);
		
		// Verify in check
		assertEquals("Player 1", game.getCurrentPlayerName());
		assertEquals(true, game.isCheck());
	}
	
	@Test
	/**
	 * Test that the following board does not show as being in check
	 * 	 * 
	 * 	8 ║… … … … ♛ … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … ♔ … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 */
	public void testIsCheckTrueOnlyWhenInCheck() {
		// Setup custom board
		Map<String, Piece> customMap = new HashMap<String, Piece>();
		customMap.put("d1", new King(Color.WHITE));
		customMap.put("e8", new Queen(Color.BLACK));
		game.startGameWithCustomMapping("Player 1", "Player 2", customMap);
		
		// Verify in check
		assertEquals("Player 1", game.getCurrentPlayerName());
		assertEquals(false, game.isCheck());
	}


	@Test
	/**
	 * Test that the following is counted as checkmate
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … ♚ … ♔
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … ♜
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 */
	public void testIsCheckmate() {
		// Setup custom board
		Map<String, Piece> customMap = new HashMap<String, Piece>();
		customMap.put("h5", new King(Color.WHITE));
		customMap.put("f5", new King(Color.BLACK));
		customMap.put("h1", new Rook(Color.BLACK));
		game.startGameWithCustomMapping("Player 1", "Player 2", customMap);
		
		// Verify in check
		assertEquals("Player 1", game.getCurrentPlayerName());
		assertEquals(true, game.isCheckmate());
	}
	
	@Test
	/**
	 * Test that the following is not counted as checkmate
	 * 	8 ║… … … … … … … …
	 *	7 ║… … … … … … … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … ♚ … … ♔
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … ♜
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 */
	public void testIsCheckmateReturnsTrueOnlyWhenInCheck() {
		// Setup custom board
		Map<String, Piece> customMap = new HashMap<String, Piece>();
		customMap.put("h5", new King(Color.WHITE));
		customMap.put("e5", new King(Color.BLACK));
		customMap.put("h1", new Rook(Color.BLACK));
		game.startGameWithCustomMapping("Player 1", "Player 2", customMap);
		
		// Verify in check
		assertEquals("Player 1", game.getCurrentPlayerName());
		assertEquals(false, game.isCheckmate());
	}

	@Test
	/**
	 * Test that the following is counted as stalemate
	 * 	8 ║… … … … … … … ♔
	 *	7 ║… … … … … ♚ … …
	 *	6 ║… … … … … … ♛ …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … … … … …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 */
	public void testIsStalemate() {
		// Setup custom board
		Map<String, Piece> customMap = new HashMap<String, Piece>();
		customMap.put("h8", new King(Color.WHITE));
		customMap.put("g6", new Queen(Color.BLACK));
		customMap.put("f7", new King(Color.BLACK));
		game.startGameWithCustomMapping("Player 1", "Player 2", customMap);
		
		// Verify in check
		assertEquals("Player 1", game.getCurrentPlayerName());
		assertEquals(true, game.isStalemate());
	}

	
	@Test
	/**
	 * Test that the following is not counted as stalemate
	 * 	8 ║… … … … … … … ♔
	 *	7 ║… … … … … ♚ … …
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … … … … … ♛ …
	 *	2 ║… … … … … … … …
	 *	1 ║… … … … … … … …
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 */
	public void testIsStalemateReturnsTrueOnlyWhenStalemate() {
		// Setup custom board
		Map<String, Piece> customMap = new HashMap<String, Piece>();
		customMap.put("h8", new King(Color.WHITE));
		customMap.put("g3", new King(Color.BLACK));
		customMap.put("f7", new Rook(Color.BLACK));
		game.startGameWithCustomMapping("Player 1", "Player 2", customMap);
		
		// Verify in check
		assertEquals("Player 1", game.getCurrentPlayerName());
		assertEquals(false, game.isStalemate());
	}

}
