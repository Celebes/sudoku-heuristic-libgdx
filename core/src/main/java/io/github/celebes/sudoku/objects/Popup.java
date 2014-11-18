package io.github.celebes.sudoku.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import io.github.celebes.sudoku.Assets;
import io.github.celebes.sudoku.enums.ButtonType;

public class Popup {
	private GuiButton btnOkInvalid;
	private GuiButton btnOkCorrect;
	private GuiButton btnOkSolved;
	
	private GuiButton[] okButtons;
	
	private boolean popupSudokuSolvedVisible;
	private boolean popupInvalidBoardVisible;
	private boolean popupCorrectBoardVisible;
	
	public Popup() {
		initGui();
	}
	
	private void initGui() {
		btnOkInvalid = new GuiButton(Assets.instance.gui.btnOk);
		btnOkInvalid.setButtonType(ButtonType.OK_INVALID);
		btnOkInvalid.position.x = 6.25f;
		btnOkInvalid.position.y = 3.25f;
		btnOkInvalid.dimension.x = 4.0f;
		btnOkInvalid.origin.x = 2.0f;
		btnOkInvalid.bounds.width = 4.0f;
		
		btnOkCorrect = new GuiButton(Assets.instance.gui.btnOk);
		btnOkCorrect.setButtonType(ButtonType.OK_CORRECT);
		btnOkCorrect.position.x = 6.25f;
		btnOkCorrect.position.y = 3.25f;
		btnOkCorrect.dimension.x = 4.0f;
		btnOkCorrect.origin.x = 2.0f;
		btnOkCorrect.bounds.width = 4.0f;
		
		btnOkSolved = new GuiButton(Assets.instance.gui.btnOk);
		btnOkSolved.setButtonType(ButtonType.OK_SOLVED);
		btnOkSolved.position.x = 6.25f;
		btnOkSolved.position.y = 3.25f;
		btnOkSolved.dimension.x = 4.0f;
		btnOkSolved.origin.x = 2.0f;
		btnOkSolved.bounds.width = 4.0f;
		
		okButtons = new GuiButton[3];
		okButtons[0] = btnOkInvalid;
		okButtons[1] = btnOkCorrect;
		okButtons[2] = btnOkSolved;
	}
	
	public void render(ShapeRenderer shapeRenderer) {
		if(isPopupVisible() == true) {
			
			// tlo okienka
			
			if(isPopupInvalidBoardVisible() == true) {
				shapeRenderer.setColor(Color.RED);
			} else {
				shapeRenderer.setColor(Color.GREEN);
			}
			
			shapeRenderer.begin(ShapeType.Filled);
			
			shapeRenderer.rect(5.75f, 3.0f, 5.0f, 3.0f);
			
			shapeRenderer.end();
			
			// tlo przycisku bez najechania kursorem
			
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.begin(ShapeType.Filled);
			
			for(GuiButton b : okButtons) {
				if(b.isVisible() == true) {
					shapeRenderer.rect(b.position.x, b.position.y, b.bounds.width, b.bounds.height);
				}
			}
			
			shapeRenderer.end();
			
			// tlo po najechaniu kursorem
			
			shapeRenderer.setColor(Color.YELLOW);
			shapeRenderer.begin(ShapeType.Filled);
			
			for(GuiButton b : okButtons) {
				if(b.isHoveredOver() == true && b.isVisible() == true) {
					shapeRenderer.rect(b.position.x, b.position.y, b.bounds.width, b.bounds.height);
				}
			}
			
			shapeRenderer.end();
		}
	}
	
	public void render(SpriteBatch batch) {
		TextureRegion reg;
		batch.begin();
		
		if(isPopupVisible() == true) {
			if(popupInvalidBoardVisible == true) {
				reg = Assets.instance.gui.popupInvalidBoard;
			} else if(popupCorrectBoardVisible == true) {
				reg = Assets.instance.gui.popupCorrectBoard;
			} else {
				reg = Assets.instance.gui.popupSudokuSolved;
			}
			
			reg.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
			batch.draw(reg.getTexture(), 5.75f, 3.0f, 0.0f,
					0.0f, 5.0f, 3.0f, 1.0f, 1.0f, 0.0f,
					reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
			
			if(popupInvalidBoardVisible == true) {
				renderOkButton(btnOkInvalid, batch);
			} else if(popupCorrectBoardVisible == true) {
				renderOkButton(btnOkCorrect, batch);
			} else {
				renderOkButton(btnOkSolved, batch);
			}
		}
		
		batch.end();
	}
	
	private void renderOkButton(GuiButton b, SpriteBatch batch) {
		TextureRegion reg = b.getReg();
		reg.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		batch.draw(reg.getTexture(), b.position.x, b.position.y, 0.0f,
				0.0f, 4.0f, 1.0f, 1.0f, 1.0f, 0.0f,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}
	
	public boolean isPopupVisible() {
		return (popupInvalidBoardVisible == true || popupSudokuSolvedVisible == true || popupCorrectBoardVisible == true);
	}
	
	public boolean isPopupSudokuSolvedVisible() {
		return popupSudokuSolvedVisible;
	}

	public void setPopupSudokuSolvedVisible(boolean popupSudokuSolvedVisible) {
		this.popupSudokuSolvedVisible = popupSudokuSolvedVisible;
		
		if(popupSudokuSolvedVisible == true) {
			btnOkSolved.setVisible(true);
		} else {
			btnOkSolved.setVisible(false);
		}
		
	}

	public boolean isPopupInvalidBoardVisible() {
		return popupInvalidBoardVisible;
	}

	public void setPopupInvalidBoardVisible(boolean popupInvalidBoardVisible) {
		this.popupInvalidBoardVisible = popupInvalidBoardVisible;
		
		if(popupInvalidBoardVisible == true) {
			btnOkInvalid.setVisible(true);
		} else {
			btnOkInvalid.setVisible(false);
		}
		
	}
	
	public boolean isPopupCorrectBoardVisible() {
		return popupCorrectBoardVisible;
	}

	public void setPopupCorrectBoardVisible(boolean popupCorrectBoardVisible) {
		this.popupCorrectBoardVisible = popupCorrectBoardVisible;
		
		if(popupCorrectBoardVisible == true) {
			btnOkCorrect.setVisible(true);
		} else {
			btnOkCorrect.setVisible(false);
		}
	}

	public GuiButton[] getOkButtons() {
		return okButtons;
	}
	
}
