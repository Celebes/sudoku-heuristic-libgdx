package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.Assets;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Cell extends AbstractGameObject implements Comparable<Cell> {
	public static final String TAG = Cell.class.getName();
	
	private int number;
	private boolean initial;
	private boolean hoveredOver;
	private boolean selected;
	
	private int prevNumber;
	
	public Cell() {
		dimension.set(1.0f, 1.0f);
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		initial = false;
	}
	
	public void render(SpriteBatch batch) {
		if(number == 0) return;
		
		TextureRegion reg = Assets.instance.numbers.getNumber(number);
		reg.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, 0.0f,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}
	
	public boolean isHoveredOver() {
		return hoveredOver;
	}

	public void setHoveredOver(boolean hoveredOver) {
		this.hoveredOver = hoveredOver;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public int getPrevNumber() {
		return prevNumber;
	}

	public void setPrevNumber(int prevNumber) {
		this.prevNumber = prevNumber;
	}

	@Override
	public int compareTo(Cell cell) {
		return this.number - cell.number;
	}
	
}
