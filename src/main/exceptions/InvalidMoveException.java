package main.exceptions;

/**
 * InvalidMoveException occurs when a user attempts to make a move that
 * is considered illegal via the rules of chess.
 * @author einar
 *
 */
public class InvalidMoveException extends Exception {
	public InvalidMoveException() {
		super("Move invalid");
	}
}
