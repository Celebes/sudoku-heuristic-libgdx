package io.github.celebes.sudoku.history;

import static org.junit.Assert.*;

import org.junit.Test;

public class HistoryTreeTest {

	@Test
	public void testAddingMove() {
		Move move = new Move(5, 2, 9);
		HistoryTree historyTree = new HistoryTree(move);
		
		historyTree.add(move);
		
		assertTrue(historyTree.getCurrentMove() == move);
	}
	
	@Test
	public void testMovePossible() {
		Move move = new Move(5, 2, 9);
		HistoryTree historyTree = new HistoryTree(move);
		
		historyTree.add(new Move(1,2,3));
		historyTree.back();
		
		assertTrue(historyTree.movePossible(new Move(1,2,3)) == false);
	}
	
	@Test
	public void testCheckIfMovePossibleRecursive() {
		Move move = new Move(2, 7, 7);
		HistoryTree historyTree = new HistoryTree(move);
		
		historyTree.add(new Move(2,4,6));
		historyTree.add(new Move(5,5,1));
		historyTree.add(new Move(8,1,6));
		
		historyTree.back();
		historyTree.back();
		
		historyTree.add(new Move(8,1,6));
		historyTree.add(new Move(5,5,1));
		
		historyTree.back();
		historyTree.back();
		historyTree.back();
		
		assertTrue(historyTree.checkIfMovePossibleRecursive(new Move(1,2,3), historyTree.getCurrentNode()) == true);
		
		assertTrue(historyTree.checkIfMovePossibleRecursive(new Move(2,4,6), historyTree.getCurrentNode()) == false);
		assertTrue(historyTree.checkIfMovePossibleRecursive(new Move(5,5,1), historyTree.getCurrentNode()) == false);
		assertTrue(historyTree.checkIfMovePossibleRecursive(new Move(8,1,6), historyTree.getCurrentNode()) == false);
	}
	
	@Test
	public void testToTGF() {
		Move move = new Move(2, 7, 7);
		HistoryTree historyTree = new HistoryTree(move);
		
		historyTree.add(new Move(2,4,6));
		historyTree.add(new Move(5,5,1));
		historyTree.add(new Move(8,1,6));
		
		historyTree.back();
		historyTree.back();
		
		historyTree.add(new Move(8,1,6));
		historyTree.add(new Move(5,5,1));
		
		historyTree.back();
		historyTree.back();
		historyTree.back();
		
		String expectedResult = "1 root\n" +
								"2 value [7] at [2,7]\n" +
								"3 value [6] at [2,4]\n" +
								"4 value [1] at [5,5]\n" +
								"5 value [6] at [8,1]\n" +
								"6 value [6] at [8,1]\n" +
								"7 value [1] at [5,5]\n" +
								"#\n" +
								"1 2\n" +
								"2 3\n" +
								"3 4\n" +
								"4 5\n" +
								"3 6\n" +
								"6 7\n";
		
		assertTrue(historyTree.toTGF().equals(expectedResult));
	}

}
