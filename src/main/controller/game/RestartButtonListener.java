package main.controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.controller.ChessController;

/**
 * Handles the event where the restart button is clicked
 * @author einar
 *
 */
public class RestartButtonListener implements ActionListener {
	private ChessController controller;
	
	public RestartButtonListener(ChessController controller) {
		this.controller = controller;
	}

	@Override
	/**
	 * Shows a dialog box to check that both players approve of restarting the game
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Popup box with asks whether both users agree to restart the game
		int selectedOption = JOptionPane.showConfirmDialog(null, 
                "Do both players agree to restart the game?", 
                "Choose", 
                JOptionPane.YES_NO_OPTION); 
		
		// If both users would like to restart the game...
		if (selectedOption == JOptionPane.YES_OPTION) {
			// ...then starts a new game, maintaining the same players
			controller.startNewGameWithSamePlayers();
		}
		
	}


}
