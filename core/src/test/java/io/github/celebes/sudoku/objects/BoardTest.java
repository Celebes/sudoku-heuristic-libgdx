package io.github.celebes.sudoku.objects;

import static org.junit.Assert.assertTrue;
import io.github.celebes.sudoku.history.Move;
import io.github.celebes.sudoku.utils.Constants;

import java.util.Arrays;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	private Board board;
	
	@Before
	public void setUp() {
		board = new Board();
	}
	
	@After
	public void tearDown() {
		board = null;
	}
	
	@Test
	public void validateCorrectBoard() {
		int[][] sudokuBoard = {
				{2, 4, 8,	3, 9, 5,	7, 1, 6},
				{5, 7, 1,	6, 2, 8,	3, 4, 9},
				{9, 3, 6,	7, 4, 1,	5, 8, 2},
				
				{6, 8, 2,	5, 3, 9,	1, 7, 4},
				{3, 5, 9,	1, 7, 4,	6, 2, 8},
				{7, 1, 4,	8, 6, 2,	9, 5, 3},
				
				{8, 6, 3,	4, 1, 7,	2, 9, 5},
				{1, 9, 5,	2, 8, 6,	4, 3, 7},
				{4, 2, 7,	9, 5, 3,	8, 6, 1}
		};
		
		board.setEntireBoard(sudokuBoard);
		
		assertTrue(board.validateBoard() == true);
	}
	
	@Test
	public void validateBoardWithIncorrectRow() {
		int[][] sudokuBoard = {
				{2, 4, 8,	3, 9, 5,	7, 1, 2},
				{5, 7, 1,	6, 2, 8,	3, 4, 9},
				{9, 3, 6,	7, 4, 1,	5, 8, 2},
				
				{6, 8, 2,	5, 3, 9,	1, 7, 4},
				{3, 5, 9,	1, 7, 4,	6, 2, 8},
				{7, 1, 4,	8, 6, 2,	9, 5, 3},
				
				{8, 6, 3,	4, 1, 7,	2, 9, 5},
				{1, 9, 5,	2, 8, 6,	4, 3, 7},
				{4, 2, 7,	9, 5, 3,	8, 6, 1}
		};
		
		board.setEntireBoard(sudokuBoard);
		
		assertTrue(board.validateBoard() == false);
	}
	
	@Test
	public void validateBoardWithIncorrectColumn() {
		int[][] sudokuBoard = {
				{2, 4, 8,	3, 9, 5,	7, 1, 6},
				{5, 7, 1,	6, 2, 8,	3, 4, 9},
				{9, 3, 6,	7, 4, 1,	5, 8, 2},
				
				{6, 8, 2,	5, 3, 9,	1, 7, 4},
				{3, 5, 9,	1, 7, 4,	6, 2, 8},
				{7, 1, 4,	8, 6, 2,	9, 5, 3},
				
				{8, 6, 3,	4, 1, 7,	2, 9, 5},
				{1, 9, 5,	2, 8, 6,	4, 3, 7},
				{2, 2, 7,	9, 5, 3,	8, 6, 1}
		};
		
		board.setEntireBoard(sudokuBoard);
		
		assertTrue(board.validateBoard() == false);
	}
	
	@Test
	public void validateBoardWithIncorrectSquare() {
		int[][] sudokuBoard = {
				{2, 4, 8,	3, 9, 5,	7, 1, 6},
				{2, 7, 1,	6, 2, 8,	3, 4, 9},
				{9, 3, 6,	7, 4, 1,	5, 8, 2},
				
				{6, 8, 2,	5, 3, 9,	1, 7, 4},
				{3, 5, 9,	1, 7, 4,	6, 2, 8},
				{7, 1, 4,	8, 6, 2,	9, 5, 3},
				
				{8, 6, 3,	4, 1, 7,	2, 9, 5},
				{1, 9, 5,	2, 8, 6,	4, 3, 7},
				{4, 2, 7,	9, 5, 3,	8, 6, 1}
		};
		
		board.setEntireBoard(sudokuBoard);
		
		assertTrue(board.validateBoard() == false);
	}
	
	@Test
	public void checkIfCompleteBoardIsComplete() {
		int[][] sudokuBoard = {
				{2, 4, 8,	3, 9, 5,	7, 1, 6},
				{5, 7, 1,	6, 2, 8,	3, 4, 9},
				{9, 3, 6,	7, 4, 1,	5, 8, 2},
				
				{6, 8, 2,	5, 3, 9,	1, 7, 4},
				{3, 5, 9,	1, 7, 4,	6, 2, 8},
				{7, 1, 4,	8, 6, 2,	9, 5, 3},
				
				{8, 6, 3,	4, 1, 7,	2, 9, 5},
				{1, 9, 5,	2, 8, 6,	4, 3, 7},
				{4, 2, 7,	9, 5, 3,	8, 6, 1}
		};
		
		board.setEntireBoard(sudokuBoard);
		board.checkIfComplete();
		
		assertTrue(board.isBoardComplete() == true);
	}
	
	@Test
	public void checkIfIncompleteBoardIsComplete() {
		int[][] sudokuBoard = {
			{0, 0, 0,	0, 0, 0,	0, 0, 9},
			{0, 0, 0,	0, 0, 0,	0, 8, 0},
			{0, 0, 0,	0, 0, 0,	7, 0, 0},
			
			{0, 0, 0,	0, 0, 6,	0, 0, 0},
			{0, 0, 0,	0, 5, 0,	0, 0, 0},
			{0, 0, 0,	4, 0, 0,	0, 0, 0},
			
			{0, 0, 3,	0, 0, 0,	0, 0, 0},
			{0, 2, 0,	0, 0, 0,	0, 0, 0},
			{1, 0, 0,	0, 0, 0,	0, 0, 0}
		};
		
		board.setEntireBoard(sudokuBoard);
		board.checkIfComplete();
		
		assertTrue(board.isBoardComplete() == false);
	}
	
	@Test
	public void getAllNumbersInRow() {
		int[][] sudokuBoard = {
			{9, 0, 0,	6, 0, 0,	3, 0, 1},
			{0, 5, 0,	3, 0, 0,	4, 6, 0},
			{0, 0, 0,	0, 0, 5,	9, 0, 2},
			
			{5, 0, 4,	0, 8, 0,	0, 0, 0},
			{3, 0, 0,	7, 0, 4,	0, 0, 5},
			{0, 0, 0,	0, 3, 0,	2, 0, 9},
			
			{4, 0, 9,	8, 0, 0,	0, 0, 0},
			{0, 3, 2,	0, 0, 7,	0, 1, 0},
			{7, 0, 5,	0, 0, 3,	0, 0, 4}
		};
		
		board.setEntireBoard(sudokuBoard);
		Cell[] cells = board.getAllNumbersInRow(3);
		int[] result = new int[9];
		
		int[] expectedResult = {
			0, 0, 0, 0, 3, 0, 2, 0, 9
		};
		
		for(int i=0; i<9; i++) {
			result[i] = cells[i].getNumber();
		}
		
		assertTrue(Arrays.equals(result, expectedResult) == true);
	}
	
	@Test
	public void getAllNumbersInColumn() {
		int[][] sudokuBoard = {
			{9, 0, 0,	6, 0, 0,	3, 0, 1},
			{0, 5, 0,	3, 0, 0,	4, 6, 0},
			{0, 0, 0,	0, 0, 5,	9, 0, 2},
			
			{5, 0, 4,	0, 8, 0,	0, 0, 0},
			{3, 0, 0,	7, 0, 4,	0, 0, 5},
			{0, 0, 0,	0, 3, 0,	2, 0, 9},
			
			{4, 0, 9,	8, 0, 0,	0, 0, 0},
			{0, 3, 2,	0, 0, 7,	0, 1, 0},
			{7, 0, 5,	0, 0, 3,	0, 0, 4}
		};
		
		board.setEntireBoard(sudokuBoard);
		Cell[] cells = board.getAllNumbersInColumn(2);
		int[] result = new int[9];
		
		int[] expectedResult = {
			5, 2, 9, 0, 0, 4, 0, 0, 0
		};
		
		for(int i=0; i<9; i++) {
			result[i] = cells[i].getNumber();
		}
		
		assertTrue(Arrays.equals(result, expectedResult) == true);
	}
	
	
	@Test
	public void getAllNumbersInSquare() {
		int[][] sudokuBoard = {
			{9, 0, 0,	6, 0, 0,	3, 0, 1},
			{0, 5, 0,	3, 0, 0,	4, 6, 0},
			{0, 0, 0,	0, 0, 5,	9, 0, 2},
			
			{5, 0, 4,	0, 8, 0,	0, 0, 0},
			{3, 0, 0,	7, 0, 4,	0, 0, 5},
			{0, 0, 0,	0, 3, 0,	2, 0, 9},
			
			{4, 0, 9,	8, 0, 0,	0, 0, 0},
			{0, 3, 2,	0, 0, 7,	0, 1, 0},
			{7, 0, 5,	0, 0, 3,	0, 0, 4}
		};
		
		board.setEntireBoard(sudokuBoard);
		Cell[] cells = board.getAllNumbersInSquare(6, 3);		// kwadrat 7
		int[] result = new int[9];
		
		int[] expectedResult = {
			2, 0, 0, 0, 0, 0, 9, 5, 0
		};
		
		for(int i=0; i<9; i++) {
			result[i] = cells[i].getNumber();
		}
		
		assertTrue(Arrays.equals(result, expectedResult) == true);
	}
	
	@Test
	public void getPossibleNumbersForEmptyBoard() {
		int[][] sudokuBoard = new int[Constants.GRID_SIZE][Constants.GRID_SIZE];
		
		for(int[] array : sudokuBoard) {
			Arrays.fill(array, 0);
		}
		
		board.setEntireBoard(sudokuBoard);
		
		List<Integer> possibleNumbers = board.getPossibleNumbers(0, 0);
		
		Integer[] expectedNumbersArray = {
			1, 2, 3, 4, 5, 6, 7, 8, 9
		};
		
		List<Integer> expectedNumbers = Arrays.asList(expectedNumbersArray);
		
		assertTrue(compareTwoLists(possibleNumbers, expectedNumbers));
	}
	
	@Test
	public void getPossibleNumbersForEmptyCell() {
		int[][] sudokuBoard = {
			{9, 0, 0,	6, 0, 0,	3, 0, 1},
			{0, 5, 0,	3, 0, 0,	4, 6, 0},
			{0, 0, 0,	0, 0, 5,	9, 0, 2},
			
			{5, 0, 4,	0, 8, 0,	0, 0, 0},
			{3, 0, 0,	7, 0, 4,	0, 0, 5},
			{0, 0, 0,	0, 3, 0,	2, 0, 9},
			
			{4, 0, 9,	8, 0, 0,	0, 0, 0},
			{0, 3, 2,	0, 0, 7,	0, 1, 0},
			{7, 0, 5,	0, 0, 3,	0, 0, 4}
		};
		
		board.setEntireBoard(sudokuBoard);
		
		List<Integer> possibleNumbers = board.getPossibleNumbers(1, 0);
		
		Integer[] expectedNumbersArray = {
			1, 6, 8
		};
		
		List<Integer> expectedNumbers = Arrays.asList(expectedNumbersArray);
		
		assertTrue(compareTwoLists(possibleNumbers, expectedNumbers));
	}
	
	@Test
	public void getPossibleNumbersForFilledCell() {
		int[][] sudokuBoard = {
			{9, 0, 0,	6, 0, 0,	3, 0, 1},
			{0, 5, 0,	3, 0, 0,	4, 6, 0},
			{0, 0, 0,	0, 0, 5,	9, 0, 2},
			
			{5, 0, 4,	0, 8, 0,	0, 0, 0},
			{3, 0, 0,	7, 0, 4,	0, 0, 5},
			{0, 0, 0,	0, 3, 0,	2, 0, 9},
			
			{4, 0, 9,	8, 0, 0,	0, 0, 0},
			{0, 3, 2,	0, 0, 7,	0, 1, 0},
			{7, 0, 5,	0, 0, 3,	0, 0, 4}
		};
		
		board.setEntireBoard(sudokuBoard);
		
		List<Integer> possibleNumbers = board.getPossibleNumbers(0, 0);
		
		assertTrue(possibleNumbers.isEmpty() == true);
	}
	
	private boolean compareTwoLists(List<Integer> current, List<Integer> expected) {
		if(current.size() != expected.size()) {
			return false;
		}
		
		Integer[] a = current.toArray(new Integer[current.size()]);
		Integer[] b = expected.toArray(new Integer[expected.size()]);
		
		Arrays.sort(a);
		Arrays.sort(b);
		
		for(int i=0; i<a.length; i++) {
			if(a[i] != b[i]) {
				return false;
			}
		}
		
		return true;
	}
	
}
