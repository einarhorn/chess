package main.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.controller.setup.NewGameButtonListener;
import main.core.ChessModel;

public class GameSetupPanel extends JPanel {
	// References to each of the subpanels
	private JPanel playerNamePanel;
	private JPanel startGamePanel;
	private JPanel errorMessagePanel;
	
	// Indexes of buttons on this panel
	private static int NEW_GAME_BUTTON_INDEX = 0;
	private static int NEW_GAME_NEW_PIECES_BUTTON_INDEX = 1;
	
	public GameSetupPanel(){
		super(new BorderLayout());
		initGameSetupPanel();
	}
  
	/**
	 * Initialize this panel
	 */
	private void initGameSetupPanel() {
		// The panel which contains text fields for player names
		playerNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// The labels and textfields for player names
		JLabel player1NameLabel = new JLabel("White Name: ");
		JTextField player1Name = new JTextField("Player 1", 20);
		JLabel player2NameLabel = new JLabel("Black Name: ");
		JTextField player2Name = new JTextField("Player 2", 20);
		
		// Add each of these components to the player name panel
		playerNamePanel.add(player1NameLabel);
		playerNamePanel.add(player1Name);
		playerNamePanel.add(player2NameLabel);
		playerNamePanel.add(player2Name);
		
		// The panel which contains buttons for starting the game
		startGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// Buttons for starting a new game
		JButton startNewGame = new JButton("Start new game");
		JButton newGameCustomPieces = new JButton("Start new game with custom pieces");
		
		// Add each of the buttons to the start game panel
		startGamePanel.add(startNewGame);
		startGamePanel.add(newGameCustomPieces);
		
		// Panel for showing error messages
		errorMessagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// The label containing the error message
		JLabel missingNameLabel = new JLabel("Invalid name(s)");
		
		// Add the label to the panel, as hidden
		errorMessagePanel.add(missingNameLabel);
		errorMessagePanel.setVisible(false);
		
		// Add each of the above panels to this class's main panel
        this.add(playerNamePanel, BorderLayout.NORTH);
        this.add(startGamePanel, BorderLayout.CENTER);
        this.add(errorMessagePanel, BorderLayout.SOUTH);
        
	}

	/**
	 * Add a listener for the new game button
	 * @param newGameButtonListener	listener for the new game button
	 */
	public void addNewGameButtonListener(ActionListener newGameButtonListener) {
		JButton newGameButton = (JButton) startGamePanel.getComponent(NEW_GAME_BUTTON_INDEX);
		newGameButton.addActionListener(newGameButtonListener);
	}
	
	/**
	 * Add a listener for the new game with new pieces button
	 * @param newGameNewPiecesButtonListener	listener for the new game button
	 */
	public void addNewGameNewPiecesButtonListener(ActionListener newGameNewPiecesButtonListener) {
		JButton newGameNewPiecesButton = (JButton) startGamePanel.getComponent(NEW_GAME_NEW_PIECES_BUTTON_INDEX);
		newGameNewPiecesButton.addActionListener(newGameNewPiecesButtonListener);
	}
	
	/**
	 * Gets the entered white player name
	 * @return	name of white player
	 */
	public String getWhitePlayerName() {
		JTextField player1Name = (JTextField) playerNamePanel.getComponent(1);
		return player1Name.getText();
	}
	
	/**
	 * Gets the entered black player name
	 * @return	name of black player
	 */
	public String getBlackPlayerName() {
		JTextField player2Name = (JTextField) playerNamePanel.getComponent(3);
		return player2Name.getText();
	}
	
	/**
	 * Will make the missing name error visible
	 */
	public void showMissingNameError() {
		errorMessagePanel.setVisible(true);
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Will make the missing name error hidden
	 */
	public void hideMissingNameError() {
		errorMessagePanel.setVisible(false);
		this.repaint();
		this.revalidate();
	}
}
