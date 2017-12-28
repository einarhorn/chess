package main.controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controller.ChessController;
import main.core.ChessModel;

/**
 * Handles the event where the redo button is clicked
 * @author einar
 *
 */
public class RedoButtonListener implements ActionListener {

	private ChessModel chessModel;
	private ChessController chessController;
	
	public RedoButtonListener(ChessModel chessModel, ChessController chessController) {
		this.chessModel = chessModel;
		this.chessController = chessController;
	}
	
	@Override
	/**
	 * Calls the model and un-does the previous move
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Call model to undo move
		chessModel.redo();
		
		// Call main controller to update board
		chessController.redrawChessGamePanel();
	}

}
