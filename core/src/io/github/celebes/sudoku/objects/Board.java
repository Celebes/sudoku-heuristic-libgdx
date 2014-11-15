package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.Assets;
import io.github.celebes.sudoku.utils.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

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

	private void initTestNumbers() {
		board[0][0].setNumber(1);
		board[1][1].setNumber(2);
		board[2][2].setNumber(3);
		board[3][3].setNumber(4);
		board[4][4].setNumber(5);
		board[5][5].setNumber(6);
		board[6][6].setNumber(7);
		board[7][7].setNumber(8);
		board[8][8].setNumber(9);
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
