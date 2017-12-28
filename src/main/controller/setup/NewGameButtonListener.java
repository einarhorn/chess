package main.controller.setup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controller.ChessController;
import main.ui.GameSetupPanel;

/**
 * Sets up a new game when the "Start new game" button is clicked in
 * the setup panel
 * @author einar
 */
public class NewGameButtonListener implements ActionListener {
	private ChessController controller;
	private GameSetupPanel setupView;
	
	public NewGameButtonListener(ChessController controller, GameSetupPanel setupView) {
		this.controller = controller;
		this.setupView = setupView;
	}

	@Override
	/**
	 * Sets up a new game with custom pieces when the button is clicked
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Get names of new players
		String whitePlayer = setupView.getWhitePlayerName();
		String blackPlayer = setupView.getBlackPlayerName();
		
		// If either of the names are only whitespace, or blank, show an error
		if (whitePlayer.trim().equals("") || blackPlayer.trim().equals("")) {
			setupView.showMissingNameError();
			controller.redrawView();
			return;
		} else {
			setupView.hideMissingNameError();
		}
		
		// Hide chess game window and show new game window
		controller.startNewGameWithNewPlayers(whitePlayer, blackPlayer, false);
		controller.switchToGameView();
	}

}
