package tests;

import static org.junit.Assert.assertEquals;

import main.exceptions.InvalidMoveException;

public abstract class ChessBaseTest {

	protected void assertInvalidMove(InvalidMoveException e) {
		assertEquals("Move invalid", e.getMessage());
	}

}
