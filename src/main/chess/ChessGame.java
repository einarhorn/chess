package main.chess;

import main.controller.ChessController;
import main.core.ChessModel;
import main.ui.ChessView;

/**
 * This is the only class that should be interacted with when running chess
 * Simply create an instance of this class, and the game will begin
 * @author einar
 *
 */
public class ChessGame {
	
	/**
	 * Creates each of the MVC components for the chess game,
	 * then starts the game.
	 */
	public ChessGame() {
		ChessModel      model      = new ChessModel();
		ChessView       view       = new ChessView(model.getRows(), model.getCols());
        ChessController controller = new ChessController(model, view);
        controller.startGame();
	}
	
	/**
	 * Start a game of chess
	 * @param args	No arguments are taken for chess
	 */
	public static void main(String[] args) {
		new ChessGame();
	}

}
