package main.controller;

import java.util.List;

import main.controller.game.ForfeitButtonListener;
import main.controller.game.NewGameWithNewPlayersButtonListener;
import main.controller.game.RedoButtonListener;
import main.controller.game.RestartButtonListener;
import main.controller.game.SquareClickListener;
import main.controller.game.UndoButtonListener;
import main.controller.setup.NewGameButtonListener;
import main.controller.setup.NewGameCustomPiecesButtonListener;
import main.core.ChessModel;
import main.ui.ChessView;
import main.ui.GameSetupPanel;
import main.ui.MainGamePanel;

/**
 * The main controller for the chess game
 * @author einar
 *
 */
public class ChessController {
	// References to the model and view
	private ChessModel model;
    private ChessView  view;
    
    // References to two specific views, for main game and setup
    private MainGamePanel gameView;
    private GameSetupPanel setupPanel;
    
    /**
     * Creates a controller for the given model and view
     * @param model	model of chess game
     * @param view	view of chess game
     */
    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view  = view;
        this.gameView = view.getMainGamePanel();
        this.setupPanel = view.getGameSetupPanel();

    	// Connect the listeners to each panel
    	createListenersForChessPanel();
        createListenersForSetupPanel();
    }

    /**
     * This is called to start the game of chess
     * Note: This should be the only function called by the main game runner
     */
    public void startGame() {
    	view.setVisible(true);
    }
    
    /**
     * Create listeners for buttons in the game setup panel
     */
	private void createListenersForSetupPanel() {
        setupPanel.addNewGameButtonListener(new NewGameButtonListener(this, setupPanel));
        setupPanel.addNewGameNewPiecesButtonListener(new NewGameCustomPiecesButtonListener(this, setupPanel));
	}

	/**
	 * Create listeners for buttons in the chess game panel
	 */
	private void createListenersForChessPanel() {
        gameView.addSquareClickListener(new SquareClickListener(model, this, view));
        gameView.addUndoButtonListener(new UndoButtonListener(model, this));
        gameView.addRedoButtonListener(new RedoButtonListener(model, this));
        gameView.addNewGameWithNewPlayersButtonListener(new NewGameWithNewPlayersButtonListener(this));
        gameView.addNewGameWithSamePlayersButtonListener(new RestartButtonListener(this));
        gameView.addForfeitButtonListener(new ForfeitButtonListener(model, this));
	}
    
	/**
	 * Used to update the chess game panel
	 */
    public void redrawChessGamePanel() {
    	// Draws the updated pieces on the board
    	drawPiecesOnBoard();
    	
    	// Updates the current player text
    	updateCurrentPlayerNameText();
    	
    	// Updates the player scores text
    	updatePlayerScoresText();
    	
    	// Updates whether players are in check, checkmate, or stalemate
    	updateGameStatus();
    	
    	// Update the enable/disable status of the undo button
    	updateUndoButtonStatus();
    	
    	// Update the enable/disable status of the redo button
    	updateRedoButtonStatus();
    	
    	// Redraw and repaint the panel itself
    	redrawView();
    }
    
    /**
     * Redraw and repaint the view frame
     */
    public void redrawView() {
		view.pack();
		view.repaint();
	}
    
    /**
     * Updates the enable/disable status of the undo button
     */
    private void updateUndoButtonStatus() {
    	if (model.canUndo()) {
        	// The button will be enabled if undo's are allowed..
    		gameView.enableUndoButton();
    	} else {
    		// .. and disabled otherwise
    		gameView.disableUndoButton();
    	}
    }
    
    /**
     * Updates the enable/disable status of the redo button
     */
    private void updateRedoButtonStatus() {
    	if (model.canRedo()) {
    		// The button will be enabled if redo's are allowed..
    		gameView.enableRedoButton();
    	} else {
    		// .. and disabled otherwise
    		gameView.disableRedoButton();
    	}
    }
    
    /**
     * Checks for, and updates the check, checkmate, stalemate text
     */
    private void updateGameStatus() {
    	// Check if in check and display "Check" if so
    	gameView.setCheckStatusVisible(model.isCheck());
    	
    	// Check if stalemate and display "Stalemate" if so
    	gameView.setStalemateStatusVisible(model.isStalemate());
    	
    	// Check if checkmate and display "Checkmate" if so
    	gameView.setCheckmateStatusVisible(model.isCheckmate());
    	
    	// Set text to "Game Over" if checkmate or stalemate
    	if (model.isCheckmate() || model.isStalemate()) {
    		gameView.setCurrentPlayerLabelToGameOver();
    	}
    }
    
    /**
     * Queries the model for the piece locations, and updates the board view with those pieces
     */
    public void drawPiecesOnBoard() {
    	List<Character> boardState = model.getBoardStateAsArray();
    	gameView.updateBoardDisplay(boardState);
    }
    
    /**
     * Updates the current player text
     */
    public void updateCurrentPlayerNameText() {
		String currentPlayerName = model.getCurrentPlayerName();
		gameView.setCurrentPlayerLabel(currentPlayerName);
	}
    
    /**
     * Updates the player scores
     */
    public void updatePlayerScoresText() {
    	int player1Score = model.getScoreByPlayerIndex(0);
    	int player2Score = model.getScoreByPlayerIndex(1);
    	String player1Name = model.getPlayerNames()[0];
    	String player2Name = model.getPlayerNames()[1];
    	gameView.updateScoreLabel(player1Score, player2Score, player1Name, player2Name);
    }
    
    /**
     * Starts a new game with new players
     * @param whitePlayer		Name of the white player
     * @param blackPlayer		Name of the black player
     * @param useCustomPieces	Whether or not the custom pieces should be used
     */
	public void startNewGameWithNewPlayers(String whitePlayer, String blackPlayer, boolean useCustomPieces) {
		model.startGameWithNewPlayers(whitePlayer, blackPlayer, useCustomPieces);
	}
	
	/**
	 * Starts a new game with the same players
	 */
	public void startNewGameWithSamePlayers() {
		model.resetGame();
		redrawChessGamePanel();
	}

	/**
	 * Switches the view to the setup panel
	 */
	public void switchToSetupView() {
		view.switchToGameSetupPanel();
	}
	
	/**
	 * Switches the view to the game panel
	 */
	public void switchToGameView() {
		view.switchToMainGamePanel();
		redrawChessGamePanel();
	}
}
