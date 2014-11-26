package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.Assets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	private double[] possibleNumberUsefulness;		// oceny dla poszczegolnych wartosci 0-9
	private List<Integer> possibleNumbers;
	
	public Cell() {
		dimension.set(1.0f, 1.0f);
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		initial = false;
		
		possibleNumberUsefulness = new double[10];
		possibleNumbers = new ArrayList<Integer>();
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
	
	public double getMaxPossibility() {
		if(possibleNumbers.isEmpty()) {
			return -1;
		}
		
		double maxPossibility = 0.0;
		
		for(int i=1; i<possibleNumberUsefulness.length; i++) {
			if(possibleNumberUsefulness[i] > maxPossibility) {
				maxPossibility = possibleNumberUsefulness[i];
			}
		}
		
		return maxPossibility;
	}
	
	public List<Integer> getBestNumbers() {
		if(possibleNumbers.isEmpty()) {
			return null;
		}
		
		List<Integer> bestNumbers = new ArrayList<Integer>();
		double maxPossibility = getMaxPossibility();
		
		for(int i=1; i<possibleNumberUsefulness.length; i++) {
			if(Math.abs(possibleNumberUsefulness[i] - maxPossibility) < 0.0001f) {
				bestNumbers.add(Integer.valueOf(i));
			}
		}
		
		return bestNumbers;
	}
	
	@Override
	public String toString() {
		return "Cell [" + (int)position.x + "][" + (int)position.y + "]";
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

	public double[] getPossibleNumberUsefulness() {
		return possibleNumberUsefulness;
	}

	public void setPossibleNumberUsefulness(double[] possibleNumberUsefulness) {
		this.possibleNumberUsefulness = possibleNumberUsefulness;
	}
	
	public void clearPossibleNumberUsefulness() {
		Arrays.fill(possibleNumberUsefulness, 0.0);
	}

	public List<Integer> getPossibleNumbers() {
		return possibleNumbers;
	}

	public void setPossibleNumbers(List<Integer> possibleNumbers) {
		this.possibleNumbers = possibleNumbers;
	}
	
}
