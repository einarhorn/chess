package main.ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;

import main.controller.game.NewGameWithNewPlayersButtonListener;
import main.controller.game.RedoButtonListener;
import main.controller.game.RestartButtonListener;
import main.controller.game.SquareClickListener;
import main.core.ChessModel;

import java.util.List;
 
public class MainGamePanel extends JPanel {
	// Reference to chessBoard panel
	private JPanel chessBoard;
	
	// Reference to button panel
	private JPanel buttonPanel;
	
	// Reference to score panel
	private JPanel scorePanel;
	
	// Reference to the game info panel
	private JPanel infoPanel;
	
	// Reference to the game status panel
	private JPanel statusPanel;
	
	// Reference to the current player info panel
	private JPanel currentPlayerPanel;
	
	// Shape of chess board UI
	private static final int BOARD_WIDTH = 500;
	private static final int BOARD_HEIGHT = 500;
  
	// Number of squares in grid
	private int gridRows, gridCols;
	
	// Indexes of components
	private static int NEW_GAME_WITH_NEW_PLAYERS_BUTTON_INDEX = 0;
	private static int NEW_GAME_WITH_SAME_PLAYERS_BUTTON_INDEX = 1;
	private static int FORFEIT_BUTTON_INDEX = 2;
	private static int UNDO_BUTTON_INDEX = 3;
	private static int REDO_BUTTON_INDEX = 4;
	private static int SCORE_LABEL_INDEX = 0;
	private static int CURRENT_PLAYER_LABEL_INDEX = 0;
	private static int STATUS_LABEL_INDEX = 0;
	
	/**
	 * Setup a new instance of the provided chess game, hidden by default
	 * @param rows			number of rows on chess board
	 * @param cols			number of columns on chess board
	 */
	public MainGamePanel(int rows, int cols){
		super(new BorderLayout());
		this.gridRows = rows;
		this.gridCols = cols;
		
		// Initialize the various components
		initChessPanel();
		initButtons();
		initInfoPanel();
	}
  
	/**
	 * Sets up the chess board and pieces within the current JPanel
	 */
	private void initChessPanel() {
		//Add a chess board panel to the overall window
		chessBoard = new JPanel();
		this.add(chessBoard, BorderLayout.CENTER);
		
		// Setup the dimension of the chess board
		Dimension boardSize = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
		chessBoard.setLayout(new GridLayout(gridRows, gridCols));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
		
		// Add each square to the board
		for (int i = 0; i < 64; i++) {
			// Calculate the row and column of the current square
			int row = i / gridRows;
			int col = i % gridCols;
			
			// Setup the new square to add to board
			JPanel square = new SquarePanel(i, row, col, new BorderLayout());
			chessBoard.add(square);

			// Assign colors to squares
			// Check if the row index is odd or even
			if (row % 2 == 0) {
				// Check if the column index is odd or even
				if (col % 2 == 0) {
					square.setBackground(Color.WHITE);
				} else {
					square.setBackground(Color.GRAY);
				}  
			} else {
				// Check if the column index is odd or even
				if (col % 2 == 0) {
					square.setBackground(Color.GRAY);
				} else {
					square.setBackground(Color.WHITE);
				}
			}
		}
	}
	
	/**
	 * Sets up the buttons within the current JPanel
	 */
	private void initButtons() {
		// Instantiate the panel itself
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// Buttons to add
		JButton startNewGameButton = new JButton("New Game (New Players)");
		JButton restartButton = new JButton("Restart");		
		JButton forfeitButton = new JButton("Forfeit");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        
    	// Add each of the buttons to the panel
        buttonPanel.add(startNewGameButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(forfeitButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
       
        // Add the panel to the JPanel
        this.add(buttonPanel, BorderLayout.NORTH);
	}

	/**
	 * Sets up the info panel text within the current JPanel
	 */
	private void initInfoPanel() {
		// Set up a panel for the current player name
		currentPlayerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel currentPlayerLabel = new JLabel("Player 1 to move");
		currentPlayerPanel.add(currentPlayerLabel);
		
		// Set up a panel to show the current score
		scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel scoreLabel = new JLabel("Scores - Player 1: 1 win(s) Player 2: 2 win(s)");
		scorePanel.add(scoreLabel);
		
		// Set up a panel to show checkmate, check, stalemate status
		statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel statusLabel = new JLabel("Checkmate");
		statusLabel.setForeground(Color.RED);
		statusPanel.add(statusLabel);
		
		// Add each of the above panels in one main info panel
		infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		infoPanel.add(scorePanel, BorderLayout.EAST);
		infoPanel.add(statusPanel, BorderLayout.CENTER);
		infoPanel.add(currentPlayerPanel, BorderLayout.WEST);
		
		// Add this info panel to the main JPanel
		this.add(infoPanel, BorderLayout.SOUTH);
	}

	/*
	 * Sets the checkmate text as visible or not
	 */
	public void setCheckmateStatusVisible(boolean isVisible) {
		JLabel statusLabel = (JLabel) statusPanel.getComponent(STATUS_LABEL_INDEX);
		if (isVisible) {
			statusLabel.setText("Checkmate!");
		}
		
		statusLabel.setVisible(isVisible);
	}
	
	/*
	 * Sets the check text as visible or not
	 */
	public void setCheckStatusVisible(boolean isVisible) {
		JLabel statusLabel = (JLabel) statusPanel.getComponent(STATUS_LABEL_INDEX);
		if (isVisible) {
			statusLabel.setText("Check!");
		}
		
		statusLabel.setVisible(isVisible);
	}
	
	/*
	 * Sets the stalemate text as visible or not
	 */
	public void setStalemateStatusVisible(boolean isVisible) {
		JLabel statusLabel = (JLabel) statusPanel.getComponent(STATUS_LABEL_INDEX);
		if (isVisible) {
			statusLabel.setText("Stalemate!");
		}
		statusLabel.setVisible(isVisible);
	}
	
	/**
	 * Update the score label with the given info
	 * @param player1Score	Score of player 1
	 * @param player2Score	Score of player 2
	 * @param player1Name	Name of player 1
	 * @param player2Name	Name of player 2
	 */
	public void updateScoreLabel(int player1Score, int player2Score, String player1Name, String player2Name) {
		JLabel scoreLabel = (JLabel) scorePanel.getComponent(SCORE_LABEL_INDEX);
		scoreLabel.setText("Scores - " + player1Name + ": " + player1Score + " win(s) " + player2Name + ": " + player2Score + " win(s)");
	}
	
	/**
	 * Enables the undo button
	 */
	public void enableUndoButton() {
		JButton undoButton = (JButton) buttonPanel.getComponent(UNDO_BUTTON_INDEX);
		undoButton.setEnabled(true);
	}
	
	/**
	 * Disables the undo button
	 */
	public void disableUndoButton() {
		JButton undoButton = (JButton) buttonPanel.getComponent(UNDO_BUTTON_INDEX);
		undoButton.setEnabled(false);
	}
	
	/**
	 * Enables the redo button
	 */
	public void enableRedoButton() {
		JButton undoButton = (JButton) buttonPanel.getComponent(REDO_BUTTON_INDEX);
		undoButton.setEnabled(true);
	}
	
	/**
	 * Disables the redo button
	 */
	public void disableRedoButton() {
		JButton undoButton = (JButton) buttonPanel.getComponent(REDO_BUTTON_INDEX);
		undoButton.setEnabled(false);
	}
	
	/**
	 * Sets the name of the current player
	 * @param playerName	name of current player
	 */
	public void setCurrentPlayerLabel(String playerName) {
		JLabel currentPlayerLabel = (JLabel) currentPlayerPanel.getComponent(CURRENT_PLAYER_LABEL_INDEX);
		currentPlayerLabel.setText(playerName + " to move");
	}
	
	/**
	 * Sets the current player label to display "Game Over"
	 */
	public void setCurrentPlayerLabelToGameOver() {
		JLabel currentPlayerLabel = (JLabel) currentPlayerPanel.getComponent(CURRENT_PLAYER_LABEL_INDEX);
		currentPlayerLabel.setText("Game Over");
	}
  
	/**
	 * Update the display of the pieces on the board
	 * @param boardState	a list of characters, where each character corresponds to a piece on the board
	 */
	public void updateBoardDisplay(List<Character> boardState) {
		// Iterate over all squares on the board
		for (int squareIndex=0; squareIndex<boardState.size(); squareIndex++) {
			Character squareAsCharacter = boardState.get(squareIndex);
			JPanel panel = (JPanel) chessBoard.getComponent(squareIndex);
			panel.removeAll();
			// If there is a piece at the given square, set the label at
			// that square to display the unicode representation of the piece
			if (!squareAsCharacter.equals('E')) {
				JLabel piece = new JLabel(squareAsCharacter.toString());
				piece.setFont(new Font("Serif", Font.PLAIN, 70));
				piece.setHorizontalAlignment(JLabel.CENTER);
				panel.add(piece);
			}
			panel.repaint();
		}
	}
	
	/**
	 * Add a listener to each square on the board
	 * @param squareClickListener	listener for squares
	 */
	public void addSquareClickListener(MouseListener squareClickListener) {
		for (Component panelAsComponent : chessBoard.getComponents()) {
			JPanel panel = (JPanel) panelAsComponent;
			panel.addMouseListener(squareClickListener);
		}
	}
	
	/**
	 * Add a listener to the undo button
	 * @param undoButtonListener	listener for undo buttons
	 */
	public void addUndoButtonListener(ActionListener undoButtonListener) {
		JButton undoButton = (JButton) buttonPanel.getComponent(UNDO_BUTTON_INDEX);
		undoButton.addActionListener(undoButtonListener);
	}

	/**
	 * Adds a listener to the new game with new players button
	 * @param newGameButtonListener	listener for new game button
	 */
	public void addNewGameWithNewPlayersButtonListener(ActionListener newGameButtonListener) {
		JButton startNewGameWithNewPlayerButton = (JButton) buttonPanel.getComponent(NEW_GAME_WITH_NEW_PLAYERS_BUTTON_INDEX);
		startNewGameWithNewPlayerButton.addActionListener(newGameButtonListener);
	}

	/**
	 * Adds a listener to the new game button
	 * @param newGameButtonListener	listener for new game button
	 */
	public void addNewGameWithSamePlayersButtonListener(ActionListener newGameButtonListener) {
		JButton startNewGameWithNewPlayerButton = (JButton) buttonPanel.getComponent(NEW_GAME_WITH_SAME_PLAYERS_BUTTON_INDEX);
		startNewGameWithNewPlayerButton.addActionListener(newGameButtonListener);		
	}

	/**
	 * Adds a listener to the redo game button
	 * @param redoButtonListener	listener for redo game button
	 */
	public void addRedoButtonListener(RedoButtonListener redoButtonListener) {
		JButton redoButton = (JButton) buttonPanel.getComponent(REDO_BUTTON_INDEX);
		redoButton.addActionListener(redoButtonListener);		
	}
	
	/**
	 * Adds a listener to the forfeit game button
	 * @param forfeitButtonListener	listener for forfeit game button
	 */
	public void addForfeitButtonListener(ActionListener forfeitButtonListener) {
		JButton forfeitButton = (JButton) buttonPanel.getComponent(FORFEIT_BUTTON_INDEX);
		forfeitButton.addActionListener(forfeitButtonListener);	
	}

	/**
	 * Highlights each of the squares at the given indexes
	 * @param availableLocations	list of indexes to highlight
	 * @param border				border style to use in highlight
	 */
	public void highlightSquares(List<Integer> availableLocations, Border border) {
		for (Integer highlightIndex : availableLocations) {
			Component panelAsComponent = chessBoard.getComponent(highlightIndex);
			JPanel panel = (JPanel) panelAsComponent;
			panel.setBorder(border);
		}
	}
	
	/**
	 * Remove highlight from all squares
	 */
	public void unhighlightSquares() {
		for (Component panelAsComponent : chessBoard.getComponents()) {
			JPanel panel = (JPanel) panelAsComponent;
			panel.setBorder(null);
		}
	}
}
 