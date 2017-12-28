package main.core;

import java.util.ArrayList;
import java.util.List;

import main.pieces.King;
import main.pieces.Piece;
import main.pieces.Piece.Color;

public class Player {
	private String name;
	private Color playerColor;
	private GameDirection direction;
	private int score;
	
	// A list of main.pieces that are not captured
	private List<Piece> availablePieces;
	
	// A list of main.pieces that have been captured by the opponent
	private List<Piece> capturedPieces;
	
	// Player's king
	private King king;
	private boolean isKingCaptured;
	
	/**
	 * Create a new player with the given name
	 * @param String name
	 */
	public Player(String name, Color playerColor, GameDirection direction, List<Piece> pieceList) {
		this.name = name;
		this.playerColor = playerColor;
		this.direction = direction;
		this.capturedPieces = new ArrayList<Piece>();
		this.availablePieces = pieceList;
		this.king = findKingFromPieceList(pieceList);
		this.isKingCaptured = false;
		this.score = 0;
	}
	
	/**
	 * Iterates through the pieceList to find an instance of the king
	 * Having a reference to the king is useful in calculating check and checkmate
	 * @param pieceList		a list of main.pieces the player has
	 * @return
	 */
	private King findKingFromPieceList(List<Piece> pieceList) {
		for (Piece piece : pieceList) {
			if (piece instanceof King) {
				return (King) piece;
			}
		}
		return null;
	}
	
	/**
	 * Add a captured piece to this player's captured piece list
	 * @param capturedPiece	a piece that has been captured by this player
	 */
	public void addCapturedPiece(Piece capturedPiece) {
		capturedPieces.add(capturedPiece);
	}
	
	/**
	 * Remove a captured piece from this player's captured piece list
	 * @param capturedPiece a piece that had been previously captured by this player
	 */
	public void removeCapturedPiece(Piece capturedPiece) {
		capturedPieces.remove(capturedPiece);
	}
	
	/**
	 * Add a available piece to this player's available piece list
	 * @param availablePiece	a piece that is available to play by this player
	 */
	public void addAvailablePiece(Piece availablePiece) {
		availablePieces.add(availablePiece);
	}
	
	/**
	 * Remove a available piece from this player's available piece list
	 * @param availablePiece	a piece that is available to play by this player
	 */
	public void removeAvailablePiece(Piece availablePiece) {
		availablePieces.remove(availablePiece);
	}
	
	/**
	 * Get the name of the current player
	 * @return name of player
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the color of the current player
	 * @return color of the current player
	 */
	public Color getColor() {
		return playerColor;
	}
	
	/**
	 * Get the direction of the current player
	 * @return direction of the current player
	 */
	public GameDirection getDirection() {
		return direction;
	}
	
	/**
	 * Get the list of main.pieces captured by this player
	 * @return list of main.pieces captured by this player
	 */
	public List<Piece> getCapturedPieces() {
		return capturedPieces;
	}
	
	/**
	 * Get the list of main.pieces available to be played by this player
	 * @return list of main.pieces available to be played by this player
	 */
	public List<Piece> getAvailablePieces() {
		return availablePieces;
	}
	
	/**
	 * Gets a reference to the player's king
	 * @return reference to king
	 */
	public King getKing() {
		return king;
	}

	/**
	 * Set king as captured/not captured for the player
	 * @param kingIsCaptured	whether the king is captured for this player
	 */
	public void setKingCaptured(boolean kingIsCaptured) {
		this.isKingCaptured = kingIsCaptured;
	}
	
	/**
	 * Whether the king has been captured for this player
	 * @return	true if this player's king has been captured
	 */
	public boolean isKingCaptured() {
		return isKingCaptured;
	}
	
	/**
	 * Increment the player's score by one
	 */
	public void incrementScore() {
		score++;
	}
	
	/**
	 * Get the player's score
	 * @return integer representation of the player's score
	 */
	public int getScore() {
		return score;
	}
}
