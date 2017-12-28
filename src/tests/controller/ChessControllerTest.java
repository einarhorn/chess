package tests.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.controller.ChessController;
import main.core.ChessModel;
import main.ui.ChessView;
import main.ui.ChessView.AvailableViews;

public class ChessControllerTest {
	private ChessController controller;
	private ChessModel model;
	private ChessView view;
	
	@Before
	public void setUp() throws Exception {
		model = new ChessModel();
		view = new ChessView(8, 8);
		controller = new ChessController(model, view);
	}

	@Test
	public void testStartGame() {
		controller.startGame();
		assertEquals(true, view.isVisible());
	}
	
	@Test
	public void testStartNewGameWithNewPlayers() {
		controller.startNewGameWithNewPlayers("White", "Black", false);
		assertEquals("White", model.getPlayerNames()[0]);
		assertEquals("Black", model.getPlayerNames()[1]);
	}

	@Test
	public void testStartNewGameWithSamePlayers() {
		controller.startNewGameWithNewPlayers("White", "Black", false);
		controller.startNewGameWithSamePlayers();
		assertEquals("White", model.getPlayerNames()[0]);
		assertEquals("Black", model.getPlayerNames()[1]);
	}

	@Test
	public void testSwitchToSetupView() {
		controller.switchToSetupView();
		assertEquals(AvailableViews.GameSetup, view.getCurrentPanel());
	}

	@Test
	public void testSwitchToGameView() {
		controller.switchToGameView();
		assertEquals(AvailableViews.MainGame, view.getCurrentPanel());
	}

}
