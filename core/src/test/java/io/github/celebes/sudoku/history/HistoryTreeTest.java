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
		
		System.out.println(historyTree);
		
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
		
		System.out.println(historyTree);
		System.out.println(historyTree.getCurrentMove());
		
		assertTrue(historyTree.checkIfMovePossibleRecursive(new Move(1,2,3), historyTree.getCurrentNode()) == true);
		
		assertTrue(historyTree.checkIfMovePossibleRecursive(new Move(2,4,6), historyTree.getCurrentNode()) == false);
		assertTrue(historyTree.checkIfMovePossibleRecursive(new Move(5,5,1), historyTree.getCurrentNode()) == false);
		assertTrue(historyTree.checkIfMovePossibleRecursive(new Move(8,1,6), historyTree.getCurrentNode()) == false);
	}

}
