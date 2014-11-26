package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.history.HistoryTree;
import io.github.celebes.sudoku.history.Move;
import io.github.celebes.sudoku.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Board extends AbstractGameObject {
	public static final String TAG = Board.class.getName();
	
	private Cell[][] board;
	private Cell selectedCell;
	private Cell currentCell;
	
	private boolean boardComplete;
	
	private float moveDelay = 0.0f;
	private float timePassed = 0.0f;
	
	private boolean firstMove = true;
	private Random rand = new Random();
	
	// history tree
	private HistoryTree historyTree;
	
	private int counter;
	
	public Board() {
		dimension.set(Constants.GRID_SIZE, Constants.GRID_SIZE);
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		
		initBoard();
		initEasyBoard();
	}
	
	public void update(float deltaTime) {
		if(isBoardComplete() == false) {
			timePassed += deltaTime;
			
			if(timePassed >= moveDelay) {
				timePassed = 0.0f;

				// przypisz komorkom mozliwe do wstawienia cyfry
				setPossibleNumbersForEntireBoard();
				
				// dla kazdego z kwadratow okresl ktore cyfry mozna wstawic w wiecej niz 1 pole
				checkNumbersPossibilitiesForSquares();
				
				// sposrod wszystkich mozliwych ruchow wybierz najlepszy
				pickBestMove();
				
				if(isBoardComplete() == true) {
					System.out.println("board complete in " + counter + " moves");
				}
			}
		}
	}
	
	private double calculateMaxPossibility() {
		double maxPossibility = 0.0;
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				if(board[i][j].getNumber() != 0) {
					continue;
				}
				if(board[i][j].getMaxPossibility() > maxPossibility) {
					maxPossibility = board[i][j].getMaxPossibility();
				}
			}
		}
		
		return maxPossibility;
	}
	
	private List<Cell> getAllCellsWithMaxPossibility(double maxPossibility) {
		List<Cell> bestCells = new ArrayList<Cell>();
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				
				if(board[i][j].getNumber() != 0) {
					continue;
				}
				
				if(board[i][j].getPossibleNumbers().isEmpty()) {
					continue;
				}
				
				double[] possibilities = board[i][j].getPossibleNumberUsefulness();
				
				for(int k=0; k<possibilities.length; k++) {
					if(Math.abs(possibilities[k] - maxPossibility) < 0.0001f) {
						bestCells.add(board[i][j]);
					}
				}
			}
		}
		
		return bestCells;
	}
	
	private void pickBestMove() {
		double maxPossibility = calculateMaxPossibility();
		List<Cell> bestCells = getAllCellsWithMaxPossibility(maxPossibility);
		Cell bestCell = getBestCell(bestCells);
		int bestNumber = getBestNumberFromBestCell(bestCell);
		
		// jesli koniec ruchow, wycofaj sie
		if(bestCell == null) {
			backMove();
		} else {
			// jesli ruch jest mozliwy (nie wykonano go wczesniej w historii) to postaw
			Move newMove = new Move((int)bestCell.position.x, (int)bestCell.position.y, bestNumber);
			
			if(firstMove == true) {
				counter = 0;
				firstMove = false;
				historyTree = new HistoryTree(newMove);
				currentCell = bestCell;
				return;
			}
			
			if(historyTree.movePossible(newMove)) {
				bestCell.setNumber(bestNumber);
				currentCell = bestCell;
				historyTree.add(newMove);
				counter++;
			} else {
				backMove();
			}
		}
	}

	private int getBestNumberFromBestCell(Cell bestCell) {
		int bestNumber = -1;
		
		if(bestCell != null) {
			if(bestCell.getBestNumbers().size() == 1) {
				bestNumber = bestCell.getBestNumbers().get(0);
			} else {
				bestNumber = bestCell.getBestNumbers().get(rand.nextInt(bestCell.getBestNumbers().size()));
			}
		}
		
		return bestNumber;
	}

	private Cell getBestCell(List<Cell> bestCells) {
		Cell bestCell = null;
		if(bestCells.size() > 0) {
			bestCell = bestCells.get(rand.nextInt(bestCells.size()));
		}
		return bestCell;
	}

	private void checkNumbersPossibilitiesForSquares() {
		for(int squareNum=0; squareNum<Constants.GRID_SIZE; squareNum++) {
			int[] multipleNumbers = new int[10];
			Cell[] cellsInSquare = getAllNumbersInSquare(squareNum);
			
			for(Cell c : cellsInSquare) {
				if(!c.getPossibleNumbers().isEmpty()) {
					for(Integer number : c.getPossibleNumbers()) {
						multipleNumbers[number]++;
					}
				}
			}
			
			// nastepnie dokoncz obliczanie funkcje oceny
			for(Cell c : cellsInSquare) {
				if(!c.getPossibleNumbers().isEmpty()) {
					for(Integer number : c.getPossibleNumbers()) {
						c.getPossibleNumberUsefulness()[number] /= multipleNumbers[number];
					}
				}
			}
		}
	}

	private void setPossibleNumbersForEntireBoard() {
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				
				if(board[i][j].getNumber() != 0) {
					continue;
				}
				
				Arrays.fill(board[i][j].getPossibleNumberUsefulness(), 0.0);
				board[i][j].getPossibleNumbers().clear();
				
				board[i][j].setPossibleNumbers(getPossibleNumbers(i, j));
				
				// i przypisz wstepnie funkcje oceny
				int rowCount = getAllNumbersInRow(j).length;
				int columnCount = getAllNumbersInColumn(i).length;
				int squareCount = getAllNumbersInSquare(i, j).length;
				
				for(Integer integer : board[i][j].getPossibleNumbers()) {
					board[i][j].getPossibleNumberUsefulness()[integer] = rowCount + columnCount + squareCount;
				}
			}
		}
	}

	private void backMove() {
		currentCell.setNumber(0);
		historyTree.back();
		Move currentMove = historyTree.getCurrentMove();
		if(currentMove != null) {
			currentCell = board[currentMove.getColumn()][currentMove.getRow()];
		}
	}
	
	private void initBoard() {
		board = new Cell[Constants.GRID_SIZE][Constants.GRID_SIZE];
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				board[i][j] = new Cell();
				board[i][j].position.x = i;
				board[i][j].position.y = j;
			}
		}
	}
	
	public void setBoardNumber(int column, int row, int val, boolean initial) {
		board[column][row].setNumber(val);
		board[column][row].setInitial(initial);
	}
	
	public void setEntireBoard(int[][] numbers) {
		clearBoard(true);
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				boolean initial = (numbers[i][j] != 0);
				setBoardNumber(j, Constants.GRID_SIZE-1-i, numbers[i][j], initial);
			}
		}
	}
	
	public void clearBoard(boolean cleanInitial) {
		firstMove = true;
		boardComplete = false;
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				if(cleanInitial == true) {
					setBoardNumber(i, j, 0, false);
				} else {
					if(board[i][j].isInitial() == false) {
						setBoardNumber(i, j, 0, false);
					}
				}
			}
		}
	}
	
	public void initEasyBoard() {
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

		setEntireBoard(sudokuBoard);
	}
	
	public void initMediumBoard() {
		int[][] sudokuBoard = {
			{0, 4, 0,	0, 7, 0,	0, 0, 0},
			{0, 0, 3,	2, 0, 6,	0, 0, 7},
			{9, 0, 0,	0, 3, 0,	1, 0, 0},
			
			{1, 0, 0,	0, 4, 0,	0, 8, 5},
			{0, 8, 0,	3, 0, 5,	0, 7, 0},
			{7, 5, 0,	0, 2, 0,	0, 0, 1},
			
			{0, 0, 4,	0, 8, 0,	0, 0, 9},
			{2, 0, 0,	4, 0, 7,	3, 0, 0},
			{0, 0, 0,	0, 6, 0,	0, 5, 0}
		};

		setEntireBoard(sudokuBoard);
	}
	
	public void initHardBoard() {
		int[][] sudokuBoard = {
			{0, 1, 0,	6, 0, 0,	3, 0, 0},
			{5, 0, 0,	0, 3, 0,	0, 1, 8},
			{0, 2, 0,	5, 0, 0,	0, 0, 0},
			
			{3, 0, 0,	0, 0, 0,	0, 2, 0},
			{0, 0, 0,	7, 0, 4,	0, 0, 0},
			{0, 9, 0,	0, 0, 0,	0, 0, 7},
			
			{0, 0, 0,	0, 0, 6,	0, 7, 0},
			{1, 5, 0,	0, 9, 0,	0, 0, 2},
			{0, 0, 6,	0, 0, 3,	0, 5, 0}
		};

		setEntireBoard(sudokuBoard);
	}
	
	private void initTestBoard() {
		int[][] sudokuBoard = {
			{0, 4, 8,	3, 9, 5,	7, 1, 6},	// zamiast 0 ma byc 2
			{5, 7, 1,	6, 2, 8,	3, 4, 9},
			{9, 3, 6,	7, 4, 1,	5, 8, 2},
			
			{6, 8, 2,	5, 3, 9,	1, 7, 4},
			{3, 5, 9,	1, 7, 4,	6, 2, 8},
			{7, 1, 4,	8, 6, 2,	9, 5, 3},
			
			{8, 6, 3,	4, 1, 7,	2, 9, 5},
			{1, 9, 5,	2, 8, 6,	4, 3, 7},
			{4, 2, 7,	9, 5, 3,	8, 6, 1}
		};

		setEntireBoard(sudokuBoard);
	}
	
	public List<Integer> getPossibleNumbers(int columnNum, int rowNum) {
		List<Integer> possibleOverall = new ArrayList<Integer>();
		
		if(board[columnNum][rowNum].getNumber() != 0 || board[columnNum][rowNum].isInitial() == true) {
			return possibleOverall;
		}
		
		Cell[] row = getAllNumbersInRow(rowNum);
		Cell[] column = getAllNumbersInColumn(columnNum);
		Cell[] square = getAllNumbersInSquare(columnNum, rowNum);
		
		List<Integer> possibleForRow = findPossibleNumbers(row);
		List<Integer> possibleForColumn = findPossibleNumbers(column);
		List<Integer> possibleForSquare = findPossibleNumbers(square);
		
		for(int i=1; i<10; i++) {
			if(possibleForRow.contains(Integer.valueOf(i)) && possibleForColumn.contains(Integer.valueOf(i)) && possibleForSquare.contains(Integer.valueOf(i))) {
				possibleOverall.add(i);
			}
		}
		
		return possibleOverall;
	}
	
	private List<Integer> findPossibleNumbers(Cell[] cells) {
		List<Integer> possibleNumbers = new ArrayList<Integer>();
		
		int[] array = new int[10];
		
		for(Cell c : cells) {
			if(c.getNumber() > 0) {
				array[c.getNumber()]++;
			}
		}
		
		for(int i=1; i<10; i++) {
			if(array[i] == 0) {
				possibleNumbers.add(i);
			}
		}
		
		return possibleNumbers;
	}
	
	private int[] listToArray(List<Integer> list) {
		int[] array = new int[list.size()];
		
		for(int i=0; i<array.length; i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public Cell[] getAllNumbersInSquare(int columnNum, int rowNum) {
		Cell[] square = new Cell[9];
		
		/*
		 * Numery kwadratow
		 * 2 5 8
		 * 1 4 7
		 * 0 3 6
		 */
		
		int squareNum = -1;
		
		if(rowNum <= 2) {
			if(columnNum <= 2) {
				squareNum = 0;
			} else if(columnNum > 2 && columnNum <= 5) {
				squareNum = 3;
			} else if(columnNum > 5 && columnNum <= 8) {
				squareNum = 6;
			}
		} else if(rowNum > 2 && rowNum <= 5) {
			if(columnNum <= 2) {
				squareNum = 1;
			} else if(columnNum > 2 && columnNum <= 5) {
				squareNum = 4;
			} else if(columnNum > 5 && columnNum <= 8) {
				squareNum = 7;
			}
		} else if(rowNum > 5 && rowNum <= 8) {
			if(columnNum <= 2) {
				squareNum = 2;
			} else if(columnNum > 2 && columnNum <= 5) {
				squareNum = 5;
			} else if(columnNum > 5 && columnNum <= 8) {
				squareNum = 8;
			}
		}
		
		return getAllNumbersInSquare(squareNum);
	}
	
	public Cell[] getAllNumbersInSquare(int squareNum) {
		Cell[] square = new Cell[9];
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			square[i] = board[(squareNum / 3) * 3 + i / 3][squareNum * 3 % 9 + i % 3];
		}
		
		return square;
	}
	
	public Cell[] getAllNumbersInRow(int rowNum) {
		Cell[] row = new Cell[9];
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			row[i] = board[i][rowNum];
		}
		
		return row;
	}
	
	public Cell[] getAllNumbersInColumn(int columnNum) {
		Cell[] column = board[columnNum].clone();
		return column;
	}
	
	public boolean validateBoard() {
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			Cell[] row = new Cell[9];
			Cell[] square = new Cell[9];
			Cell[] column = board[i].clone();
			
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				row[j] = board[j][i];
				square[j] = board[(i / 3) * 3 + j / 3][i * 3 % 9 + j % 3];
			}
			
			if(!(validate(column) && validate(row) && validate(square))) {
				return false;
			}
			
		}
		
		return true;
	}
	
	private boolean validate(Cell[] boardElement) {
		Arrays.sort(boardElement);
		
		for(int i=0; i<boardElement.length-1; i++) {
			Cell c = boardElement[i];
			Cell cNext = boardElement[i+1];
			
			if(c.getNumber() == 0) {
				continue;
			}
			
			if(c.getNumber() == cNext.getNumber()) {
				return false;
			}
		}
		
		return true;
	}

	public void render(ShapeRenderer shapeRenderer) {
		renderColors(shapeRenderer);
		renderGrid(shapeRenderer);
	}
	
	private void renderColors(ShapeRenderer shapeRenderer) {
		
		/*
		 * Pola inicjalne, ktorych nie mozna zmienic
		 */
		
		shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
		shapeRenderer.begin(ShapeType.Filled);
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				Cell cell = board[j][i];
				if(cell.isInitial() == true) {
					shapeRenderer.rect(cell.position.x, cell.position.y, cell.bounds.width, cell.bounds.height);
				}
			}
		}
		
		shapeRenderer.end();

		/*
		 * Pola nad ktorymi jest kursor
		 */
		
		shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.begin(ShapeType.Filled);
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				Cell cell = board[j][i];
				if(cell.isHoveredOver() == true) {
					shapeRenderer.rect(cell.position.x, cell.position.y, cell.bounds.width, cell.bounds.height);
				}
			}
		}
		
		shapeRenderer.end();
		
		/*
		 * Pola zaznaczone
		 */
		
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.begin(ShapeType.Filled);
		
		if(selectedCell != null) {
			shapeRenderer.rect(selectedCell.position.x, selectedCell.position.y, selectedCell.bounds.width, selectedCell.bounds.height);
		}
		
		shapeRenderer.end();
	}

	public void render(SpriteBatch batch) {
		batch.begin();
		renderNumbers(batch);
		batch.end();
	}
	
	public void saveState() {
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				board[i][j].setPrevNumber(board[i][j].getNumber());
			}
		}
	}
	
	public void loadState() {
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				board[i][j].setNumber(board[i][j].getPrevNumber());
				
				if(board[i][j].getNumber() == 0) {
					board[i][j].setInitial(false);
				} else {
					board[i][j].setInitial(true);
				}
			}
		}
	}

	private void renderGrid(ShapeRenderer shapeRenderer) {
		
		shapeRenderer.setColor(0, 0, 0, 1);
		
		/*
		 * Cienkie
		 */
		
		Gdx.gl.glLineWidth(2);
		shapeRenderer.begin(ShapeType.Line);
		
		// linie pionowe
		for(int i=1; i<Constants.GRID_SIZE; i++) {
			if(i % 3 != 0) {
				shapeRenderer.line(i, 0, i, Constants.VIEWPORT_HEIGHT);
			}
		}
		
		// linie poziome
		for(int i=1; i<Constants.GRID_SIZE; i++) {
			if(i % 3 != 0) {
				shapeRenderer.line(0, i, Constants.VIEWPORT_HEIGHT, i);
			}
		}
		
		shapeRenderer.end();
		
		/*
		 * Grube
		 */
		
		Gdx.gl.glLineWidth(6);
		shapeRenderer.begin(ShapeType.Line);

		// ramka
		shapeRenderer.line(0, 0, 0, Constants.VIEWPORT_HEIGHT);
		shapeRenderer.line(Constants.GRID_SIZE, 0, Constants.GRID_SIZE, Constants.VIEWPORT_HEIGHT);
		shapeRenderer.line(0, 0, Constants.VIEWPORT_HEIGHT, 0);
		shapeRenderer.line(0, Constants.GRID_SIZE, Constants.VIEWPORT_HEIGHT, Constants.GRID_SIZE);
		
		// linie pionowe
		for(int i=1; i<Constants.GRID_SIZE; i++) {
			if(i % 3 == 0) {
				shapeRenderer.line(i, 0, i, Constants.VIEWPORT_HEIGHT);
			}
		}
		
		// linie poziome
		for(int i=1; i<Constants.GRID_SIZE; i++) {
			if(i % 3 == 0) {
				shapeRenderer.line(0, i, Constants.VIEWPORT_HEIGHT, i);
			}
		}
		
		shapeRenderer.end();
	}
	
	private void renderNumbers(SpriteBatch batch) {
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				
				board[i][j].render(batch);
				
			}
		}
	}
	
	public void checkIfComplete() {
		int sum = 0;
		
		for(Cell[] tabC : board) {
			for(Cell c : tabC) {
				sum += c.getNumber();
			}
		}
		
		if(sum == 405) {
			boardComplete = true;
		}
	}

	public boolean isBoardComplete() {
		checkIfComplete();
		return boardComplete;
	}

	public void setBoardComplete(boolean boardComplete) {
		this.boardComplete = boardComplete;
	}

	public Cell[][] getBoard() {
		return board;
	}

	public void setBoard(Cell[][] board) {
		this.board = board;
	}

	public Cell getSelectedCell() {
		return selectedCell;
	}

	public void setSelectedCell(Cell selectedCell) {
		this.selectedCell = selectedCell;
	}

	public HistoryTree getHistoryTree() {
		return historyTree;
	}
	
	

}
