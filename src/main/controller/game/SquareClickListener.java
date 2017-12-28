package main.controller.game;

import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import main.controller.ChessController;
import main.core.ChessModel;
import main.exceptions.InvalidMoveException;
import main.ui.ChessView;
import main.ui.SquarePanel;

/**
 * Handles the event where a square on the chess board is clicked
 * @author einar
 *
 */
public class SquareClickListener implements MouseListener {
	// The border design to use around selected squares
	private Border squareSelectionBorder;
	
	// The border design to use around squares that can be moved to
	private Border availableMoveSelectionBorder;
	
	// References to the model and controller
	private ChessModel chessModel;
	private ChessController controller;
	private ChessView view;
	
	// Reference to the panel that is currently selected
	private SquarePanel selectedSquarePanel;
	
	public SquareClickListener(ChessModel chessModel, ChessController controller, ChessView view) {
		// Creates a new red line border style, used for selected squares
		this.squareSelectionBorder = BorderFactory.createLineBorder(Color.RED, 2);
		this.availableMoveSelectionBorder = BorderFactory.createLineBorder(Color.BLUE, 2);
		
		// References to the model, controller
		this.chessModel = chessModel;
		this.controller = controller;
		this.view = view;
		
		// The selected square is null by default
		this.selectedSquarePanel = null;
	}
	
	@Override
	/**
	 * On mouse click, we select the square, or make a move if another
	 * square was previously selected
	 * @param arg0	Reference to the event behind the click
	 */
	public void mouseClicked(MouseEvent arg0) {
		// If the player is in checkmate/stalemate, they cannot make a move
		if (chessModel.isCheckmate() || chessModel.isStalemate()) {
			return;
		}
		
		// Get a reference to the square that was clicked
		SquarePanel square = (SquarePanel) arg0.getComponent();
		int squareIndex = square.getIndex();
		
		// Check if a square has already been selected
		if (isSquareAlreadySelected()) {
			// If the previously selected square is the same as
			// the currently selected square, deselect that square
			if (selectedSquarePanel.getIndex() == squareIndex) {
				deselectSquare();
			} else {
				// Another square is already selected, so
				// this is a movement of a piece
				boolean moveSuccessful = movePiece(square);
				if (moveSuccessful) {
					// Only if the movement succeeded, we deselect
					// the currently selected square
					deselectSquare();
				}
			}
		} else {
			// This is the first time selecting this square,
			// so we need to make sure this is a valid square
			// for the current player to click on
			if (squareHasCurrentPlayerPiece(square)) {
				selectSquare(square);
				highlightAvailableSquares(square);
			}
		}
	}
	
	/**
	 * Check if there is a square already selected by the player
	 * @return	True if there is a square already selected
	 */
	private boolean isSquareAlreadySelected() {
		return selectedSquarePanel != null;
	}

	/**
	 * Checks whether the given square has a current player piece
	 * on it
	 * @param square	Square to check if there is a current player piece
	 * @return	True if there is a current player piece on the given square
	 */
	private boolean squareHasCurrentPlayerPiece(SquarePanel square) {
		return chessModel.squareBelongsToCurrentPlayer(square.getRow(), square.getCol());
	}
	
	/**
	 * Selects a square
	 * @param square	Square to select
	 */
	private void selectSquare(SquarePanel square) {
		square.setBorder(squareSelectionBorder);
		this.selectedSquarePanel = square;
	}
	
	/**
	 * Deselects the selected square
	 */
	private void deselectSquare() {
		selectedSquarePanel.setBorder(null);
		this.selectedSquarePanel = null;
		view.getMainGamePanel().unhighlightSquares();
	}
	
	/**
	 * Highlights all squares that are available to be highlighted
	 * @param square
	 */
	private void highlightAvailableSquares(SquarePanel square) {
		List<Integer> availableLocations = chessModel.getAvailableLocationsForMove(square.getRow(), square.getCol());
		view.getMainGamePanel().highlightSquares(availableLocations, availableMoveSelectionBorder);
	}

	/**
	 * Moves a piece to the given square
	 * @param endSquarePanel	Square to move a piece to
	 * @return	True if the movement was successful
	 */
	private boolean movePiece(SquarePanel endSquarePanel) {
		try {
			chessModel.move(selectedSquarePanel.getRow(),  selectedSquarePanel.getCol(), endSquarePanel.getRow(), endSquarePanel.getCol());
			controller.redrawChessGamePanel();
			return true;
		} catch (InvalidMoveException e) {
			return false;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
