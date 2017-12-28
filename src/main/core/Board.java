package main.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import main.exceptions.InvalidMoveException;
import main.exceptions.LocationOutsideBoardException;
import main.pieces.King;
import main.pieces.Piece;

/**
 * Board class
 * Contains information about the main.core board
 * @author einar
 *
 */
public class Board {
	
	private Square[][] squares;
	private int numRows, numColumns;
	private Stack<Move> moveHistory;
	private Stack<Move> futureMoveHistory;
	private Player[] players;
	
	/**
	 * Generates a new main.core board with the given parameters and main.pieces
	 * There are two systems for coordinates of squares on the board:
	 * 	Traditional main.core notation:
	 * 	  The horizontal axis goes from left to right, starting at 'a'
	 *    The vertical axis goes from bottom to top, starting at 1
	 *  (x,y) coordinate notation:
	 *   The top left of the board is considered the origin, and indexes are 0-based
	 *   
	 * @param numRows 		number of rows on the board - 1-based
	 * @param numColumns 	number of columns on the board - 1-based
	 * @param pieceMapping	a mapping of string coordinate to piece
	 */
	public Board(int numRows, int numColumns, Map<String, Piece> pieceMapping, Player[] players) {
		// Store height and width of board
		this.numRows = numRows;
		this.numColumns = numColumns;
		
		// Store players
		this.players = players;
		
		// Initialize the board with appropriate number of squares
		this.squares = new Square[numRows][numColumns];
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numColumns; col++) {
				squares[row][col] = new Square(row, col, this);
			}
		}
		
		// Places main.pieces on each square by checking mapping
		placePieces(pieceMapping);
		
		moveHistory = new Stack<Move>();
		futureMoveHistory = new Stack<Move>();
	}
	
	/*******************
	 * INITIALIZATION
	 * 
	 *******************/
	
	/**
	 * Place main.pieces on the board by checking provided mapping
	 * @param pieceMapping
	 */
	private void placePieces(Map<String, Piece> pieceMapping) {
		//Iterate over the entire board
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numColumns; col++) {
				// Get current square we are iterating over
				Square currSquare = squares[row][col];
				
				// Get the string representation of the current square
				String currSquareString = currSquare.toString();
				
				// Check the mapping to see if there is a piece to place at this square
				if (pieceMapping.containsKey(currSquareString)) {
					// Get the piece from the map
					Piece currentPiece = pieceMapping.get(currSquareString);

					// Place this piece on the square
					currSquare.setPiece(currentPiece);
				}				
			}
		}
	}
	
	
	/*******************
	 * PUBLIC METHODS
	 * 
	 *******************/

	/**
	 * Move a piece for the given player. Positions are given in row and columns
	 * @param startRow		row of piece to move
	 * @param startCol		column of piece to move
	 * @param endRow		row of square to move to
	 * @param endCol		column of square to move to
	 * @throws InvalidMoveException 
	 */
	public void move(int startRow, int startCol, int endRow, int endCol, Player currentPlayer) throws InvalidMoveException {
		// Get the start and end squares of the move
		if (!isRowOnBoard(startRow) || !isColOnBoard(startCol)) {
			throw new InvalidMoveException();
		}
		Square startSquare = squares[startRow][startCol];
		
		if (!isRowOnBoard(endRow) || !isColOnBoard(endCol)) {
			throw new InvalidMoveException();
		}
		Square endSquare = squares[endRow][endCol];
		
		// Move the piece
		moveHelper(startSquare, endSquare, currentPlayer);
		
		// Remove the future move history, since redo's cannot be done anymore
		futureMoveHistory.clear();
	}
	
	/**
	 * Move a piece. Positions are given in string coordinates
	 * @param startPosition		position to move piece from, in string representation
	 * @param endPosition		position to move piece to, in string representation
	 * @param currentPlayer		current player that is making the move
	 * @throws InvalidMoveException 
	 */
	public void move(String startPosition, String endPosition, Player currentPlayer) throws InvalidMoveException {
		// Get the start and end squares of the move
		Square startSquare, endSquare;
		try {
			startSquare = convertPositionStringToSquare(startPosition);
			endSquare = convertPositionStringToSquare(endPosition);
		} catch (LocationOutsideBoardException e) {
			throw new InvalidMoveException();
		}
		
		// Move the piece
		moveHelper(startSquare, endSquare, currentPlayer);
		
		// Remove the future move history, since redo's cannot be done anymore
		futureMoveHistory.clear();
	}
	
	/**
	 * Helper function for the move method. Moves a piece, from startSquare to endSquare for the current player
	 * @param startSquare		square to move the piece from
	 * @param endSquare			square to move the piece to
	 * @param currentPlayer		current player that is making the move
	 */
	private void moveHelper(Square startSquare, Square endSquare, Player currentPlayer) throws InvalidMoveException {
		// Validate the starting square has a piece
		if (!startSquare.hasPiece()) {
			throw new InvalidMoveException();
		}
		// Validate the piece belongs to the current player
		if (startSquare.getPiece().getColor() != currentPlayer.getColor()) {
			throw new InvalidMoveException();
		}
		
		// Generate a move object, encapsulating the start, end, and player information
		Move currentMove = new Move(startSquare, endSquare, currentPlayer);
		if (isMoveValid(currentMove)) {
			makeMove(currentMove);
		} else {
			throw new InvalidMoveException();
		}
		
		// If the player is in check after the move, we undo it
		if (isCheck(currentPlayer)) {
			undoLastMoveHelper();
			throw new InvalidMoveException();
		}
	}
	
	/**
	 * Generalized method to check if a movement is valid
	 * @param move		movement within the board to validate
	 * @return
	 */
	public boolean isMoveValid(Move move) {
		// Validate if the destination is valid
		if (!destinationIsEmptyOrDifferentColor(move)) { 
			return false;
		}

		// Validate that the move has a valid movement pattern
		if (!move.hasValidMovementPattern()) {
			return false;
		}

		// Validate if the path the move would take is valid
		if (!isValidPath(move)) {
			return false;
		}

		return true;
	}
	
	/**
	 * Whether there are moves to be undone
	 * @return	True if a move can be undone
	 */
	public boolean canUndoMove() {
		return moveHistory.size() != 0;
	}
	
	/**
	 * Whether there are moves that can be redone
	 * @return	True if there are moves that can be redone
	 */
	public boolean canRedoMove() {
		return futureMoveHistory.size() != 0;
	}

	/**
	 * Undo the last made move, restoring any piece that was captured
	 * Also store this undo, for future redo's
	 */
	public void undoLastMove() {
		// Make sure that there are moves to undo
		if (moveHistory.size() == 0) {
			return;
		}
		
		// Get last move reference
		Move lastMove = moveHistory.peek();
		
		// Undo last move
		undoLastMoveHelper();
		
		// Add this move to the futureMoveHistory, for redo purposes
		futureMoveHistory.push(lastMove);
	}
	
	/**
	 * Undo the last made move, restoring any piece that was captured
	 */
	private void undoLastMoveHelper() {
		// Get last move
		Move lastMove = moveHistory.peek();
		moveHistory.pop();
		
		// Move last moved piece back to original spot
		Piece pieceToMove = lastMove.getPiece();
		pieceToMove.decrementMoveCount();
		
		lastMove.getEndSquare().removePiece();
		lastMove.getStartSquare().setPiece(pieceToMove);
		// Check if the last move captured a piece
		if (lastMove.isCaptureMove()) {
			// Reset captured piece
			lastMove.getEndSquare().setPiece(lastMove.getCapturedPiece());
			
			// Get reference to opposite player
			Player oppositePlayer = getOppositePlayer(lastMove.getPlayer());
			oppositePlayer.addAvailablePiece(lastMove.getCapturedPiece());
		}
	}
	
	/**
	 * Redo the last move that was undone
	 */
	public void redoMove() {
		Move nextMove = futureMoveHistory.peek();
		futureMoveHistory.pop();
		makeMove(nextMove);
	}
	
	/**
	 * Generate the current state of the board as a string
	 * Will look like this:
	 * "8 ║♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜
	 *	7 ║♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟
	 *	6 ║… … … … … … … …
	 *	5 ║… … … … … … … …
	 *	4 ║… … … … … … … …
	 *	3 ║… … ♘ … … … … …
	 *	2 ║♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙
	 *	1 ║♖ … ♗ ♕ ♔ ♗ ♘ ♖
	 *	—╚════════════════
	 *	—— a b c d e f g h"
	 *
	 * @return	string representation of current board state
	 */
	public String getGameAsString() {
		// String to return
		String gameString = new String();
		
		// Generate the squares and main.pieces on the board
		for (int row=0; row<numRows; row++) {
			// String representing the current row being iterated
			String rowString = new String();
			
			// Number on left hand side of row followed by space to represent current row idx
			rowString += (numRows - row) + " ";
			
			// "║" on leftmost section to represent border
			rowString += "║";
			
			// Iterate through the row, generating squares and main.pieces
			for (int col=0; col<numColumns; col++) {
				// Get the current square being iterated
				Square currSquare = squares[row][col];
				
				// Check if this square has a piece or not, get character to generate accordingly
				if (currSquare.hasPiece()) {
					rowString += currSquare.getPiece().getCharacterRepresentation();
				} else {
					rowString += "…";
				}
				
				// Each square on the board followed by a space
				rowString += " ";		
			}
						
			// Append this row information string to the overall game string
			gameString += rowString;
			if (row != numRows - 1) {
				gameString += "\n";
			}
		}
	
		// Generate a border of '═' characters at the bottom
		gameString += "\n";
		gameString += "—╚";
		for (int col=0; col<numColumns; col++) {
			gameString += "══";
		}
		
		// Place horizontal indexes on the bottom
		gameString += "\n";
		gameString += "—— ";
		for (int idx=0; idx<numColumns; idx++) {
			gameString += (char) ('a' + idx);
			gameString += " ";
		}
		
		return gameString;
	}
	
	/**
	 * Gets the name of the piece at a specific board position, otherwise returns "Empty"
	 * If the location provided does not exist, "Invalid" is returned
	 * Used primarily for testing purposes
	 * @param position	position of piece
	 * @return name of piece at position
	 */
	public String getNameOfPieceAtPosition(String position) {
		Square squareAtPosition;
		try {
			squareAtPosition = convertPositionStringToSquare(position);
		} catch (LocationOutsideBoardException e) {
			return "Invalid";
		}
		
		if (squareAtPosition.hasPiece()) {
			return squareAtPosition.getPiece().getName();
		} else {
			return "Empty";
		}
	}
	
	/**
	 * Check whether the current player is in check
	 * @param currentPlayer		player to verify if in check
	 * @return true if the current player is in check
	 */
	public boolean isCheck(Player currentPlayer) {
		// Get a reference to the current player's king
		King king = currentPlayer.getKing();
		
		// Get a list of each piece of the opposite player
		Player oppositePlayer = getOppositePlayer(currentPlayer);
		List<Piece> availablePieces = oppositePlayer.getAvailablePieces();
		
		// Try to move each available to the position of the king
		Iterator<Piece> it = availablePieces.iterator();
		while (it.hasNext()) {
			Piece currentPiece = it.next();
			
			// Check if this piece can be moved to the king
			Square startSquare = currentPiece.getSquare();
			Square endSquare = king.getSquare();
			Move currentMove = new Move(startSquare, endSquare, currentPlayer);
			boolean moveIsValid = isMoveValid(currentMove);
			
			// If this is possible, the current player is in check
			if (moveIsValid) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check whether the current player is in checkmate
	 * @param currentPlayer		player to verify if in checkmate
	 * @return true if the current player is in checkmate
	 */	
	public boolean isCheckmate(Player currentPlayer) {
		// Verify if we are currently in check
		if (!isCheck(currentPlayer) || isStalemate(currentPlayer)) {
			return false;
		}
	
		// Get a list of all main.pieces available to the player
		List<Piece> availablePieces = currentPlayer.getAvailablePieces();
	
		// Iterate over every piece, seeing if there is a move that can be made
		// to get out of check
		for (Piece piece : availablePieces) {
			if (pieceHasValidMove(piece, currentPlayer)) {
				return false;
			}
		}
		return true; 

	}

	
	/**
	 * Check whether the game is in a stalemate
	 * @param currentPlayer	player to verify if in stalemate
	 * @return
	 */
	public boolean isStalemate(Player currentPlayer) {
		// Cannot be a stalemate if the player is in check
		if (isCheck(currentPlayer)) {
			return false;
		}
		
		// Get a list of all pieces available to the player
		List<Piece> availablePieces = currentPlayer.getAvailablePieces();
	
		// Iterate over every piece, seeing if there is a move that can be made
		for (Piece piece : availablePieces) {
			if (pieceHasValidMove(piece, currentPlayer)) {
				return false;
			}
		}
		return true; 
	}
	

	
	/*******************
	 * PRIVATE METHODS
	 * 
	 *******************/
	
	/**
	 * Check whether the given piece has a valid move it can make
	 * @param piece		piece to attempt to move to a location out of check
	 * @param currentPlayer		player who is attempting to move the piece
	 * @return
	 */
	private boolean pieceHasValidMove(Piece piece, Player currentPlayer) {
		for (int row=0; row<getNumRows(); row++) {
			for (int col=0; col<getNumCols(); col++) {
				Square startSquare = piece.getSquare();
				Square endSquare = squares[row][col];

				try {
					moveHelper(startSquare, endSquare, currentPlayer);
				} catch (InvalidMoveException e) {
					continue;
				}

				boolean isInCheck = isCheck(currentPlayer);
				undoLastMoveHelper();
				if (!isInCheck) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if the given square is a valid destination for this piece
	 * A valid destination refers to a square that is empty, or has a piece of a different color
	 * @param move		movement within the board to validate
	 */
	private boolean destinationIsEmptyOrDifferentColor(Move move) {
		Square destination = move.getEndSquare();
		Piece pieceToMove = move.getPiece();

		// Check if destination already has piece
		if (destination.hasPiece()) {
			// Get piece at destination
			Piece destinationPiece = destination.getPiece();
			
			// Check if the destination piece is of the same color as current piece
			if (destinationPiece.getColor() == pieceToMove.getColor()) {
				// Since the colors are the same, this is not a valid destination
				return false;
			}
		}
		
		// In any other case, the destination is valid
		return true;
	}
	
	private boolean isValidPath(Move move) {
		MovementPattern movementPattern = move.getMovementPattern();
		// If the piece can jump, the path is automatically valid
		if (move.getPiece().canJump()) {
			return true;
		}
		
		GameDirection direction = move.getPlayer().getDirection();

		int startRow = move.getStartRow();
		int startCol = move.getStartCol();
		Square currentSquare = move.getStartSquare();
		
		// Simulate each of the iterations of the movement, except the last
		while (true) {
			// Get the square that the piece should be at for this iteration
			if (direction == GameDirection.UPWARDS) {
				startRow -= movementPattern.getRowMovement();
				startCol += movementPattern.getColMovement();
			} else {
				startRow += movementPattern.getRowMovement();
				startCol -= movementPattern.getColMovement();
			}
			
			currentSquare = squares[startRow][startCol];
			
			// Once we reach the final square, we no longer need to check if there are main.pieces
			if (currentSquare == move.getEndSquare()) {
				break;
			}
			// If current square does have piece, this is not a valid path
			
			if (currentSquare.hasPiece()) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Makes a move on the board
	 * @param move	the move to make
	 */
	private void makeMove(Move move) {
		// Get reference to piece
		Piece pieceToMove = move.getPiece();

		// Remove piece from current square
		move.getStartSquare().removePiece();
		
		// Check if new square already has a piece (to capture)
		if (move.getEndSquare().hasPiece()) {
			Player playerMakingMove = move.getPlayer();
			
			// Get reference to opposite player
			Player oppositePlayer = getOppositePlayer(move.getPlayer());

			// Get reference to piece to move
			Piece pieceToCapture = move.getEndSquare().getPiece();
			
			if (pieceToCapture instanceof King) {
				oppositePlayer.setKingCaptured(true);
			}
			
			// Remove piece from end square
			move.getEndSquare().removePiece();
			
			// Remove piece from player's availablePiece list
			oppositePlayer.removeAvailablePiece(pieceToCapture);
			
			// Capture piece
			playerMakingMove.addCapturedPiece(pieceToCapture);
			
		} 
		
		// Place piece on end square
		move.getEndSquare().setPiece(pieceToMove);
		
		// Some main.pieces have custom functions that should run after every move
		pieceToMove.afterMove();
		pieceToMove.incrementMoveCount();
		
		// Store this move, in case we decide to undo the move later
		moveHistory.add(move);
	}
	
	/**
	 * Return the player that is not currentPlayer
	 * @param currentPlayer	the opposite of the player to return
	 * @return 				return the player that is not currentPlayer
	 */
	private Player getOppositePlayer(Player currentPlayer) {
		if (players[0] == currentPlayer) {
			return players[1];
		} else {
			return players[0];
		}
	}
	
	/**
	 * Given valid string coordinates, returns the corresponding Square object instance on the board
	 * @param position	string representation of the location of a square on the board
	 * @return 			square at given position
	 * @throws LocationOutsideBoardException 
	 */
	private Square convertPositionStringToSquare(String position) throws LocationOutsideBoardException {
		assert(position.length() == 2);
		
		// Extract the coordinate values from the string
		int row = getRowFromPositionString(position);
		int col = getColFromPositionString(position);
		
		// Verifies that the coordinates are on the board
		boolean positionOnBoard = isRowOnBoard(row) && isColOnBoard(col);
		if (!positionOnBoard) {
			throw new LocationOutsideBoardException();
		}
		
		// Returns the corresponding square
		return squares[row][col];
	}
	
	/**
	 * Calculate the 0-based row value from a position string
	 * @param position	string representation of the location of a square on the board
	 * @return
	 */
	private int getRowFromPositionString(String position) {
		char rowChar = position.charAt(1);
		int rowValue = rowChar - '1';
		return getNumRows() - rowValue - 1;
	}
	
	/**
	 * Calculate the 0-based column value from a position string
	 * @param position	string representation of the location of a square on the board
	 * @return
	 */
	private int getColFromPositionString(String position) {
		char colChar = position.charAt(0);
		return colChar - 'a';
	}
	
	/**
	 * Validates that the given row exists on the board
	 * @param row	0-based row on board
	 * @return 		true if this row exists on the board
	 */
	private boolean isRowOnBoard(int row) {
		return (row >= 0 && row <= numRows-1);
	}
	
	/**
	 * Validates that the given column exists on the board
	 * @param col	0-based column on board
	 * @return		true if this column exists on the board
	 */
	private boolean isColOnBoard(int col) {
		return (col >= 0 && col <= numColumns-1);
	}
	
	
	
	/*******************
	 * GET/SET
	 * 
	 *******************/
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numColumns;
	}
	
	public Square[][] getAllSquares() {
		return squares;
	}

	/**
	 * Return a list of board squares in row major order.
	 * Each board square is represented by a character
	 * The string is either the character representation of the piece,
	 * or "E" if there is no piece at that square
	 * @return
	 */
	public List<Character> getBoardStateAsArray() {
		List<Character> boardStateString = new ArrayList<Character>();
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numColumns; col++) {
				if (squares[row][col].hasPiece()) {
					boardStateString.add(squares[row][col].getPiece().getCharacterRepresentation());
				} else {
					boardStateString.add(new Character('E'));
				}
				
			}
		}
		return boardStateString;
	}
	
	/**
	 * Returns the square at the given row and column, null if the square does not exist
	 * @param row		row at which the square is
	 * @param column	column at which the square is
	 * @return	The square at the given row and column, null if the square does not exist
	 */
	public Square getSquare(int row, int column) {
		if (isRowOnBoard(row) && isColOnBoard(column)) {
			return squares[row][column];
		}
		return null;
	}

	/**
	 * Get a list of indexes which can be moved to by the given piece
	 * @param pieceRow			row of the given piece
	 * @param pieceCol			column of the given piece
	 * @param currentPlayer		player that is making the move
	 * @return list of available locations
	 */
	public List<Integer> getAvailableLocationsForMove(int pieceRow, int pieceCol, Player currentPlayer) {
		// List of indexes which can be moved to
		List<Integer> availableLocationsForMove = new ArrayList<Integer>();
		
		// Get the current square that is being referred to
		Square currentSquare = squares[pieceRow][pieceCol];

		// Iterate over all squares, checking if the current piece can move to any of those squares
		int currentIndex = 0;
		for (int row=0; row<numRows; row++) {
			for (int col=0; col<numColumns; col++) {
				Square endSquare = squares[row][col];
				
				// Create a move instance, to test if the move is valid
				Move testMove = new Move(currentSquare, endSquare, currentPlayer);
				if (isMoveValid(testMove)) {
					availableLocationsForMove.add(currentIndex);
				}
				currentIndex++;
			}
		}
		
		return availableLocationsForMove;
	}


}
