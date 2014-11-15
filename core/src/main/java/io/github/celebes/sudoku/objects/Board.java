package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.utils.Constants;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Board extends AbstractGameObject {
	public static final String TAG = Board.class.getName();
	
	private Cell[][] board;
	
	public Board() {
		dimension.set(Constants.GRID_SIZE, Constants.GRID_SIZE);
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		
		initBoard();
		initTestNumbers();
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
	
	public void setBoardNumber(int column, int row, int val) {
		board[column][row].setNumber(val);
	}
	
	public void setEntireBoard(int[][] numbers) {
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				setBoardNumber(i, j, numbers[i][j]);
			}
		}
	}

	private void initTestNumbers() {
		setBoardNumber(0, 0, 1);
		setBoardNumber(1, 1, 2);
		setBoardNumber(2, 2, 3);
		setBoardNumber(3, 3, 4);
		setBoardNumber(4, 4, 5);
		setBoardNumber(5, 5, 6);
		setBoardNumber(6, 6, 7);
		setBoardNumber(7, 7, 8);
		setBoardNumber(8, 8, 9);
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
	
}
