package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.enums.ButtonType;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GuiButton extends AbstractGameObject {

	private boolean hoveredOver;
	private boolean visible;
	
	private TextureRegion reg;
	
	private ButtonType buttonType;
	
	public GuiButton(TextureRegion reg) {
		this.reg = reg;
		
		dimension.set(5.0f, 1.0f);
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
	}

	public boolean isHoveredOver() {
		return hoveredOver;
	}

	public void setHoveredOver(boolean hoveredOver) {
		this.hoveredOver = hoveredOver;
	}

	public TextureRegion getReg() {
		return reg;
	}

	public void setReg(TextureRegion reg) {
		this.reg = reg;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public ButtonType getButtonType() {
		return buttonType;
	}

	public void setButtonType(ButtonType buttonType) {
		this.buttonType = buttonType;
	}
	
}
