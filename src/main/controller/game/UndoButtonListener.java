package main.controller.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controller.ChessController;
import main.core.ChessModel;

/**
 * Handles the event where the undo button is clicked
 * @author einar
 *
 */
public class UndoButtonListener implements ActionListener {

	private ChessModel chessModel;
	private ChessController chessController;
	
	public UndoButtonListener(ChessModel chessModel, ChessController chessController) {
		this.chessModel = chessModel;
		this.chessController = chessController;
	}

	@Override
	/**
	 * Calls the model to undo the event, on button click
	 */
	public void actionPerformed(ActionEvent arg0) {
		// Call model to undo move
		chessModel.undo();
		
		// Call main controller to update board
		chessController.redrawChessGamePanel();
	}

}
