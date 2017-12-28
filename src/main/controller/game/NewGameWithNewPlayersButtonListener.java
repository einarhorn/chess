package main.controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controller.ChessController;

/**
 * Handles the event on the chess game screen where "New game with new players" is clicked
 * @author einar
 *
 */
public class NewGameWithNewPlayersButtonListener implements ActionListener {
	private ChessController controller;
	
	public NewGameWithNewPlayersButtonListener(ChessController controller) {
		this.controller = controller;
	}

	@Override
	/**
	 * Hides the chess game window and shows the game setup window
	 */
	public void actionPerformed(ActionEvent arg0) {
		controller.switchToSetupView();
	}

}
