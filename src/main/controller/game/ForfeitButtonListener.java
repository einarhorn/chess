package main.controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controller.ChessController;
import main.core.ChessModel;

/**
 * This listener handles the event when the forfeit button is pressed
 * @author einar
 *
 */
public class ForfeitButtonListener implements ActionListener {

	private ChessModel chessModel;
	private ChessController chessController;
	
	public ForfeitButtonListener(ChessModel chessModel, ChessController chessController) {
		this.chessModel = chessModel;
		this.chessController = chessController;
	}
	
	@Override
	/**
	 * When the forfeit button is clicked, this will forfeit the game for the current player
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Call model to forfeit for current player
		chessModel.forfeit();
		
		// Call main controller to update board
		chessController.redrawChessGamePanel();
	}


}
