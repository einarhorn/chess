package main.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.exceptions.InvalidMoveException;
import main.pieces.*;
import main.pieces.Piece.Color;
import main.ui.MainGamePanel;

public class ChessModel {
	
	// Player variables
	private Player[] players;
	private int currentPlayerIndex;
	
	// Board variables
	private Board board;
	private static final int BOARD_ROWS = 8;
	private static final int BOARD_COLUMNS = 8;
	
	// Game scores
	private int[] playerScores;
	
	// Piece mapping
	private Map<String, Piece> pieceMappings;
	
	// Whether to use custom pieces on game restart
	private boolean useCustomPieces = false;
	
	/**
	 * Initialize the game for playing with two players - "Player 1" and "Player 2"
	 * This involves setting up the players, the piece locations, and then the board itself
	 */
	public ChessModel() {
		startGame("Player 1", "Player 2");
		resetPlayerScores();
	}
	/**
	 * Initialize the game for playing
	 * This involves setting up the players, the piece locations, and then the board itself
	 * @param player1Name	name of player 1
	 * @param player2Name	name of player 2
	 */
	public ChessModel(String player1Name, String player2Name) {
		if (player1Name.equals(player2Name)) {
			return;
		}
		startGame(player1Name, player2Name);
		resetPlayerScores();
	}

	/*******************
	 * INITIALIZATION
	 * 
	 *******************/
	/**
	 * Initialize the game for playing
	 * This involves setting up the players, the piece locations, and then the board itself
	 * Also resets the scores to zero for all players
	 * @param player1Name	name of player 1
	 * @param player2Name	name of player 2
	 * @param useCustomPieces 
	 */
	public void startGameWithNewPlayers(String player1Name, String player2Name, boolean useCustomPieces) {
		this.useCustomPieces = useCustomPieces;
		if (useCustomPieces) {
			startGameWithCustomPieces(player1Name, player2Name);
		} else {
			startGame(player1Name, player2Name);
		}
		
		resetPlayerScores();
	}
	
	/**
	 * Initialize the game for playing
	 * This involves setting up the players, the piece locations, and then the board itself
	 * @param player1Name	name of player 1
	 * @param player2Name	name of player 2
	 */
	private void startGame(String player1Name, String player2Name) {
		// Setup piece locations
		generatePieceMappings();
		
		// Setup players
		setupPlayers(player1Name, player2Name);
				
		// Setup board
		setupBoard();
	}
	
	/**
	 * Initialize the game for playing with custom pieces on the board
	 * This involves setting up the players, the piece locations, and then the board itself
	 * @param player1Name	name of player 1
	 * @param player2Name	name of player 2
	 * @param useGui		whether the GUI should be enabled for this game
	 */
	private void startGameWithCustomPieces(String player1Name, String player2Name) {
		// Setup piece locations
		generatePieceMappingsWithCustomPieces();
		
		// Setup players
		setupPlayers(player1Name, player2Name);
				
		// Setup board
		setupBoard();
	}
	
	/**
	 * Initialize a custom game for playing
	 * This involves setting up the players, the piece locations, and then the board itself
	 * @param player1Name	name of player 1
	 * @param player2Name	name of player 2
	 * @param pieceMappings	a custom mapping of location to piece, i.e. sets the location of pieces on board
	 */
	public void startGameWithCustomMapping(String player1Name, String player2Name, Map<String, Piece> pieceMappings) {
		// Use passed in piece mapping for piece locations
		this.pieceMappings = pieceMappings;
		
		// Setup players
		setupPlayers(player1Name, player2Name);
				
		// Setup board
		setupBoard();
	}
		
	/**
	 * Setup each player with the appropriate color and direction
	 * @param player1Name	name of player 1
	 * @param player2Name	name of player 2
	 */
	private void setupPlayers(String player1Name, String player2Name) {
		players = new Player[2];
		
		List<Piece> whitePieceList = generatePieceListForPlayerByColor(Color.WHITE);
		players[0] = new Player(player1Name, Color.WHITE, GameDirection.UPWARDS, whitePieceList);
		
		List<Piece> blackPieceList = generatePieceListForPlayerByColor(Color.BLACK);
		players[1] = new Player(player2Name, Color.BLACK, GameDirection.DOWNWARDS, blackPieceList);
		this.currentPlayerIndex = 0;
	}

	/**
	 * Extract the main.pieces for a player of a certain color from the board map
	 * @param color	color of main.pieces to return
	 * @return	a list of main.pieces for a player of a certain color
	 */
	private List<Piece> generatePieceListForPlayerByColor(Color color) {
		List<Piece> playerPieces = new ArrayList<Piece>();
		for (Piece piece : pieceMappings.values()) {
		    if (piece.getColor() == color) {
		    	playerPieces.add(piece);
		    }
		}
		return playerPieces;
	}
	
	/**
	 * Generate the initial mapping of location to main.pieces on the board
	 * i.e. the initial layout of main pieces on the board
	 * This is the traditional chess layout
	 * @return	return the map of location to piece on the board
	 */
	private void generatePieceMappings() {
		pieceMappings = new HashMap<String, Piece>();
		
		// Map the string location to a piece
		
		// WHITE PIECES
		// Setup pawns
		pieceMappings.put("a2", new Pawn(Color.WHITE));
		pieceMappings.put("b2", new Pawn(Color.WHITE));
		pieceMappings.put("c2", new Pawn(Color.WHITE));
		pieceMappings.put("d2", new Pawn(Color.WHITE));
		pieceMappings.put("e2", new Pawn(Color.WHITE));
		pieceMappings.put("f2", new Pawn(Color.WHITE));
		pieceMappings.put("g2", new Pawn(Color.WHITE));
		pieceMappings.put("h2", new Pawn(Color.WHITE));
		
		// Setup rooks
		pieceMappings.put("a1", new Rook(Color.WHITE));
		pieceMappings.put("h1", new Rook(Color.WHITE));
		
		// Setup knights
		pieceMappings.put("b1", new Knight(Color.WHITE));
		pieceMappings.put("g1", new Knight(Color.WHITE));
				
		// Setup bishops
		pieceMappings.put("c1", new Bishop(Color.WHITE));
		pieceMappings.put("f1", new Bishop(Color.WHITE));
		
		// Setup king and queen
		pieceMappings.put("e1", new King(Color.WHITE));
		pieceMappings.put("d1", new Queen(Color.WHITE));
		
		
		// BLACK PIECES
		
		// Setup pawns
		pieceMappings.put("a7", new Pawn(Color.BLACK));
		pieceMappings.put("b7", new Pawn(Color.BLACK));
		pieceMappings.put("c7", new Pawn(Color.BLACK));
		pieceMappings.put("d7", new Pawn(Color.BLACK));
		pieceMappings.put("e7", new Pawn(Color.BLACK));
		pieceMappings.put("f7", new Pawn(Color.BLACK));
		pieceMappings.put("g7", new Pawn(Color.BLACK));
		pieceMappings.put("h7", new Pawn(Color.BLACK));
		
		// Setup rooks
		pieceMappings.put("a8", new Rook(Color.BLACK));
		pieceMappings.put("h8", new Rook(Color.BLACK));
		
		// Setup knights
		pieceMappings.put("b8", new Knight(Color.BLACK));
		pieceMappings.put("g8", new Knight(Color.BLACK));
				
		// Setup bishops
		pieceMappings.put("c8", new Bishop(Color.BLACK));
		pieceMappings.put("f8", new Bishop(Color.BLACK));
		
		// Setup king and queen
		pieceMappings.put("e8", new King(Color.BLACK));
		pieceMappings.put("d8", new Queen(Color.BLACK));
	}
	
	/**
	 * Generate the initial mapping of location to pieces on the board
	 * i.e. the initial layout of main pieces on the board
	 * This is the traditional chess layout with RowMover and
	 * DoubleJumper added for each player
	 * @return	return the map of location to piece on the board
	 */
	private void generatePieceMappingsWithCustomPieces() {
		pieceMappings = new HashMap<String, Piece>();
		
		// Map the string location to a piece
		
		// WHITE PIECES
		// Setup pawns
		pieceMappings.put("a2", new Pawn(Color.WHITE));
		pieceMappings.put("b2", new Pawn(Color.WHITE));
		pieceMappings.put("c2", new Pawn(Color.WHITE));
		pieceMappings.put("d2", new Pawn(Color.WHITE));
		pieceMappings.put("e2", new Pawn(Color.WHITE));
		pieceMappings.put("f2", new Pawn(Color.WHITE));
		pieceMappings.put("g2", new Pawn(Color.WHITE));
		pieceMappings.put("h2", new Pawn(Color.WHITE));
		
		// Setup rooks
		pieceMappings.put("a1", new Rook(Color.WHITE));
		pieceMappings.put("h1", new Rook(Color.WHITE));
		
		// Setup knights
		pieceMappings.put("b1", new Knight(Color.WHITE));
		pieceMappings.put("g1", new Knight(Color.WHITE));
				
		// Setup bishops
		pieceMappings.put("c1", new Bishop(Color.WHITE));
		pieceMappings.put("f1", new Bishop(Color.WHITE));
		
		// Setup king and queen
		pieceMappings.put("e1", new King(Color.WHITE));
		pieceMappings.put("d1", new Queen(Color.WHITE));
		
		// Setup RowMover and DoubleJumper
		pieceMappings.put("a3", new RowMover(Color.WHITE));
		pieceMappings.put("h3", new DoubleJumper(Color.WHITE));
		
		
		// BLACK PIECES
		// Setup pawns
		pieceMappings.put("a7", new Pawn(Color.BLACK));
		pieceMappings.put("b7", new Pawn(Color.BLACK));
		pieceMappings.put("c7", new Pawn(Color.BLACK));
		pieceMappings.put("d7", new Pawn(Color.BLACK));
		pieceMappings.put("e7", new Pawn(Color.BLACK));
		pieceMappings.put("f7", new Pawn(Color.BLACK));
		pieceMappings.put("g7", new Pawn(Color.BLACK));
		pieceMappings.put("h7", new Pawn(Color.BLACK));
		
		// Setup rooks
		pieceMappings.put("a8", new Rook(Color.BLACK));
		pieceMappings.put("h8", new Rook(Color.BLACK));
		
		// Setup knights
		pieceMappings.put("b8", new Knight(Color.BLACK));
		pieceMappings.put("g8", new Knight(Color.BLACK));
				
		// Setup bishops
		pieceMappings.put("c8", new Bishop(Color.BLACK));
		pieceMappings.put("f8", new Bishop(Color.BLACK));
		
		// Setup king and queen
		pieceMappings.put("e8", new King(Color.BLACK));
		pieceMappings.put("d8", new Queen(Color.BLACK));
		
		// Setup RowMover and DoubleJumper
		pieceMappings.put("a6", new RowMover(Color.BLACK));
		pieceMappings.put("h6", new DoubleJumper(Color.BLACK));
	}
	
	/**
	 * Setup a board object with the provided initial piece mapping
	 * @param pieceMappings	map of location to piece
	 */
	private void setupBoard() {
		this.board = new Board(BOARD_ROWS, BOARD_COLUMNS, pieceMappings, players);
	}
	
	
	/*******************
	 * PUBLIC METHODS
	 * 
	 *******************/
		
	/**
	 * Get the name of the player who's turn it currently is
	 * @return String name of current player
	 */
	public String getCurrentPlayerName() {
		return players[currentPlayerIndex].getName();
	}
	
	/**
	 * Move a piece from one position to another, for the current player
	 * @param startPosition		start position of piece, in string representation
	 * @param endPosition		end position of piece, in string representation
	 * @throws InvalidMoveException 
	 */
	public void move(String startPosition, String endPosition) throws InvalidMoveException {	
		board.move(startPosition, endPosition, players[currentPlayerIndex]);
		currentPlayerIndex = (currentPlayerIndex + 1) % 2;
		if (isCheckmate()) {
			// Increment the score for the player who just made the move
			int oppositePlayer = (currentPlayerIndex + 1) % 2;
			incrementPlayerScore(oppositePlayer);
		}
		
	}
	
	/**
	 * Move a piece from one position to another, for the current player
	 * @param startRow		row of piece to move
	 * @param startCol		column of piece to move
	 * @param endRow		row of square to move to
	 * @param endCol		column of square to move to
	 * @throws InvalidMoveException 
	 */
	public void move(int startRow, int startCol, int endRow, int endCol) throws InvalidMoveException {	
		board.move(startRow, startCol, endRow, endCol, players[currentPlayerIndex]);
		currentPlayerIndex = (currentPlayerIndex + 1) % 2;
		if (isCheckmate()) {
			// Increment the score for the player who just made the move
			int oppositePlayer = (currentPlayerIndex + 1) % 2;
			incrementPlayerScore(oppositePlayer);
		}
	}
	
	/**
	 * Get the current state of the game as a string
	 * Prints out a String representation of the board, and main.pieces captured
	 * @return string representation of board
	 */
	public String getGameAsString() {
		return board.getGameAsString();
	}
	
	/**
	 * Gets the name of the piece at a specific board position
	 * Used primarily for testing purposes
	 * @param position	position of piece
	 * @return name of piece at position
	 */
	public String getNameOfPieceAtPosition(String position) {
		return board.getNameOfPieceAtPosition(position);
	}
	
	/**
	 * Check whether the current player is in check
	 * @return true if the current player is in check
	 */
	public boolean isCheck() {		
		return board.isCheck(getCurrentPlayer());
	}
	
	/**
	 * Check whether the current player is in checkmate
	 * @return true if the current player is in checkmate
	 */
	public boolean isCheckmate() {
		return board.isCheckmate(getCurrentPlayer());
	}
	
	/**
	 * Check whether the game is in a stalemate
	 * @return true if the game is in stalemate
	 */
	public boolean isStalemate() {
		return board.isStalemate(getCurrentPlayer());
	}

	
	/**
	 * Get an array of the names of the players currently playing
	 * @return String array of player names
	 */
	public String[] getPlayerNames() {
		String[] playerNames = new String[2];
		playerNames[0] = players[0].getName();
		playerNames[1] = players[1].getName();
		return playerNames;
	}
	
	/**
	 * Return a list of captured pieces for each player
	 * @return list of captured pieces for each player
	 */
	public String getCapturedPiecesAsString() {
		String returnString = new String();
		returnString += getCapturedPiecesForSinglePlayerAsString(players[0]);
		returnString += "\n";
		returnString += getCapturedPiecesForSinglePlayerAsString(players[1]);
		return returnString;
	}
	
	/**
	 * Return which player has won the game, or whether the game is ongoing
	 * @return	String containing game status
	 */
	public String getGameStatus() {
		if (players[0].isKingCaptured()) {
			return players[1].getName() + " won";
		} else if (players[1].isKingCaptured()) {
			return players[0].getName() + " won";
		} else {
			return "Game is ongoing";
		}
	}
	
	/**
	 * Get the number of rows on the board
	 * @return
	 */
	public int getRows() {
		return BOARD_ROWS;
	}
	
	/**
	 * Get the number of columns on the board
	 * @return
	 */
	public int getCols() {
		return BOARD_COLUMNS;
	}
	
	/**
	 * Return a list of character representation of squares, in row-major order
	 * Empty square is represented by 'E'
	 * @return
	 */
	public List<Character> getBoardStateAsArray() {
		return board.getBoardStateAsArray();
	}
	
	/**
	 * Check whether the square at the given row and column belong to the current player
	 * @param row	row of square to query for
	 * @param col	column of square to query for
	 * @return	True if the square at the given row and column belong to the current player
	 */
	public boolean squareBelongsToCurrentPlayer(int row, int col) {
		// Get a reference to the requested square
		Square square = board.getSquare(row, col);
		
		// If the row and column do not correspond to a valid square, return false
		if (square == null) {
			return false;
		}
		
		// If there is no piece at the square, return false
		if (!square.hasPiece()) {
			return false;
		}
		
		// Get a reference to the requested piece
		Piece currentPiece = square.getPiece();
		
		// Check if the color of the piece and current player match
		if (currentPiece.getColor() == getCurrentPlayer().getColor()) { 
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Undo the previous move that has been made in the game,
	 * and switches the current player to the player that made that move
	 */
	public void undo() {
		// Since we increase the winner's score when in checkmate,
		// we decrease that score again since we undo the move
		if (isCheckmate()) {
			int playerToLowerScore = getOppositePlayerIndex();
			decrementPlayerScore(playerToLowerScore);
		}
		// Undo move
		board.undoLastMove();
		
		// Switch current player
		currentPlayerIndex = (currentPlayerIndex + 1) % 2;
	}
	
	/**
	 * Redo the previous move that was undone, switching the current player
	 */
	public void redo() {
		board.redoMove();
		currentPlayerIndex = (currentPlayerIndex + 1) % 2;
		if (isCheckmate()) {
			// Increment the score for the player who just made the move
			int oppositePlayer = getOppositePlayerIndex();
			incrementPlayerScore(oppositePlayer);
		}
	}
	/**
	 * Get the score of a player by index
	 * @param playerIndex	Index of the player for whom to get the score
	 * @return	score of a player by index
	 */
	public int getScoreByPlayerIndex(int playerIndex) {
		return playerScores[playerIndex];
	}
	
	/**
	 * Increment the score of a player by 1
	 * The player is accessed by its index
	 * @param playerIndex	Index of the player for whom to increment the score
	 */
	public void incrementPlayerScore(int playerIndex) {
		playerScores[playerIndex]++;
	}
	
	/**
	 * Decrements the score of a player by 1
	 * The player is accessed by its index
	 * @param playerIndex	Index of the player for whom to decrement the score
	 */
	public void decrementPlayerScore(int playerIndex) {
		playerScores[playerIndex]--;
	}

	/**
	 * Resets the current game, placing all pieces back in their original spot
	 * Will keep the same player objects, so scores will be unchanged
	 */
	public void resetGame() {
		if (useCustomPieces) {
			startGameWithCustomPieces(players[0].getName(), players[1].getName());
		} else {
			startGame(players[0].getName(), players[1].getName());
		}
		
	}
	
	/**
	 * Whether or not moves can be undone at the current game state
	 * @return True, if the previous move can be undone
	 */
	public boolean canUndo() {
		return board.canUndoMove();
	}
	
	/**
	 * Whether or not moves can be redone at the current game state
	 * @return True, if a move can be redone
	 */
	public boolean canRedo() {
		return board.canRedoMove();
	}
	
	/**
	 * Forfeits for the current player, increasing the score
	 * of the opposite player by one, and resetting the game
	 */
	public void forfeit() {
		incrementPlayerScore(getOppositePlayerIndex());
		resetGame();
	}
	
	/**
	 * For the piece at the given row and col, finds locations the piece can move to
	 * @param row	row of piece
	 * @param col	col of piece
	 * @return list of available locations
	 */
	public List<Integer> getAvailableLocationsForMove(int row, int col) {
		return board.getAvailableLocationsForMove(row, col, getCurrentPlayer());
	}
	
	/*******************
	 * PRIVATE METHODS
	 * 
	 *******************/
	
	/**
	 * Get the index of the player who is not playing
	 * @return	integer index of the player who is not playing
	 */
	private int getOppositePlayerIndex() {
		return (currentPlayerIndex + 1) % 2;
	}

	/**
	 * Return player name followed by captured pieces for that player
	 * @param player	player for whom to get the captured piece information
	 * @return string with information about captured pieces for one player
	 */
	private String getCapturedPiecesForSinglePlayerAsString(Player player) {
		String capturedPieceInformation = new String();
		capturedPieceInformation += player.getName();
		capturedPieceInformation += "\t";
		List<Piece> capturedPieces = player.getCapturedPieces();
		for (Piece capturedPiece : capturedPieces) {
			capturedPieceInformation += capturedPiece.getCharacterRepresentation();
			capturedPieceInformation += " ";
		}
		return capturedPieceInformation;
	}
	
	private Player getCurrentPlayer() {
		return players[currentPlayerIndex];
	}
	
	private Player getOppositePlayer() {
		int oppositePlayerIndex = (currentPlayerIndex + 1) % 2;
		return players[oppositePlayerIndex];
	}
	
	/**
	 * Reset player scores to zero
	 */
	private void resetPlayerScores() {
		playerScores = new int[2];
		playerScores[0] = 0;
		playerScores[1] = 0;
	}

	

}
