package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.utils.Constants;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Board extends AbstractGameObject {
	public static final String TAG = Board.class.getName();
	
	private Cell[][] board;
	
	private boolean boardComplete;
	
	public Board() {
		dimension.set(Constants.GRID_SIZE, Constants.GRID_SIZE);
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		
		initBoard();
		initTestNumbers();
		//initTestBoard();
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
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				boolean initial = (numbers[i][j] != 0);
				setBoardNumber(j, Constants.GRID_SIZE-1-i, numbers[i][j], initial);
			}
		}
	}
	
	private void initTestBoard() {
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

		setEntireBoard(sudokuBoard);
	}

	private void initTestNumbers() {
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

		setEntireBoard(sudokuBoard);
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
		renderGrid(shapeRenderer);
	}
	
	public void render(SpriteBatch batch) {
		renderNumbers(batch);
	}

	private void renderGrid(ShapeRenderer shapeRenderer) {
		
		/*
		 * Kolory
		 */
		
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
		
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				Cell cell = board[j][i];
				if(cell.isSelected() == true) {
					shapeRenderer.rect(cell.position.x, cell.position.y, cell.bounds.width, cell.bounds.height);
				}
			}
		}
		
		shapeRenderer.end();

		/*
		 * Linie
		 */
		
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
	
}
