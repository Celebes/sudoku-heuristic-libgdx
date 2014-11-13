package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.utils.Constants;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Board {
	
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	public Board() {
		position = new Vector2();
		dimension = new Vector2(Constants.GRID_SIZE, Constants.GRID_SIZE);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
	}
	
	public void render(ShapeRenderer shapeRenderer) {
		// na wypadek gdyby trzeba bylo walnac jakies jednokolorowe tlo
	}
	
}
