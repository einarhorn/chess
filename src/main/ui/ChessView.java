package main.ui;


import javax.swing.JFrame;

public class ChessView extends JFrame {
	public enum AvailableViews {
        GameSetup, MainGame;
	} 
	
	// References to the two panels
	private GameSetupPanel gameSetupPanel;
	private MainGamePanel mainGamePanel;
	
	// The currently selected panel
	private AvailableViews currentView;
	
	/**
	 * Sets up a view with the given rows and columns
	 * @param numRows
	 * @param numCols
	 */
	public ChessView(int numRows, int numCols) {
		// Setup the two panels
		gameSetupPanel = new GameSetupPanel();
		mainGamePanel = new MainGamePanel(numRows, numCols);
		
		// Current view is game setup by default
		currentView = AvailableViews.GameSetup;
		this.setContentPane(gameSetupPanel);

		// Initialize the view
		this.setTitle("Chess Game");
		this.pack();
		this.setResizable(true);
	}
	
	/**
	 * Get a reference to the game setup panel
	 * @return	game setup panel
	 */
	public GameSetupPanel getGameSetupPanel() {
		return gameSetupPanel;
	}
	
	/**
	 * Get a reference to the main game panel
	 * @return	main game panel
	 */
	public MainGamePanel getMainGamePanel() {
		return mainGamePanel;
	}
	
	/**
	 * Get a reference to the currently selected panel
	 * @return	current panel
	 */
	public AvailableViews getCurrentPanel() {
		return currentView;
	}
	
	/**
	 * Switches the current panel to the game setup panel
	 */
	public void switchToGameSetupPanel() {
		this.setContentPane(gameSetupPanel);
		this.pack();
		this.revalidate(); 
		this.repaint();
		currentView = AvailableViews.GameSetup;
	}
	
	/**
	 * Switches the current panel to the main game panel
	 */
	public void switchToMainGamePanel() {
		this.setContentPane(mainGamePanel);
		this.pack();
		this.revalidate(); 
		this.repaint();
		currentView = AvailableViews.MainGame;
	}
}
