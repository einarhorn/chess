package tests.core;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main.core.GameDirection;
import main.core.Player;
import main.pieces.Piece;
import main.pieces.Rook;
import main.pieces.Piece.Color;

public class PlayerTest {

	private Player player;
	private static final String PLAYER_NAME = "Player Name";
	private static final Color PLAYER_COLOR = Color.WHITE;
	private static final GameDirection PLAYER_DIRECTION = GameDirection.UPWARDS;

	@Before
	// Setup a generic player named "Player Name", with white main.pieces moving upwards
	public void setUp() throws Exception {		
		player = new Player(PLAYER_NAME, PLAYER_COLOR, PLAYER_DIRECTION, new ArrayList<Piece>());
	}

	@Test
	// Test adding a captured piece to the player's captured list
	public void testAddCapturedPiece() {
		// Execute
		Rook capturedRook = new Rook(Color.BLACK);
		player.addCapturedPiece(capturedRook);
		
		// Verify
		assertEquals(capturedRook, player.getCapturedPieces().get(0));
	}

	@Test
	// Test the getter method for the player name
	public void testGetName() {
		assertEquals(PLAYER_NAME, player.getName());
	}

	@Test
	// Test that the player color is correctly retrieved
	public void testGetColor() {
		assertEquals(PLAYER_COLOR, player.getColor());
	}

	@Test
	// Test the the direction of the player is correctly retrieved
	public void testGetDirection() {
		assertEquals(PLAYER_DIRECTION, player.getDirection());
	}

	@Test
	// Test the list of captured main.pieces is correctly retrieved
	public void testGetCapturedPieces() {
		assertEquals(0, player.getCapturedPieces().size());
	}

}
