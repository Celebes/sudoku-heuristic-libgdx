package io.github.celebes.sudoku.history;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveTest {

	@Test
	public void testEquals() {
		Move m1 = new Move(1,2,3);
		Move m2 = new Move(1,2,3);
		
		assertTrue(m1.equals(m2));
	}

}
