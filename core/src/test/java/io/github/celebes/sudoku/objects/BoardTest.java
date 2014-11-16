package io.github.celebes.sudoku.objects;

import static org.junit.Assert.*;

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

}
