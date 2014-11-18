package io.github.celebes.sudoku.objects;

import io.github.celebes.sudoku.Assets;
import io.github.celebes.sudoku.enums.ButtonType;
import io.github.celebes.sudoku.enums.MenuLevel;
import io.github.celebes.sudoku.utils.Constants;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Menu {
	
	private GuiButton btnCancelEdit;
	private GuiButton btnCancelPlay;
	private GuiButton btnCancelSolve;
	
	private GuiButton btnClearEdit;
	private GuiButton btnClearSolve;
	
	private GuiButton btnConfirm;
	private GuiButton btnContinue;
	private GuiButton btnEasy;
	private GuiButton btnEdit;
	private GuiButton btnHard;
	private GuiButton btnMedium;
	private GuiButton btnPlay;
	private GuiButton btnSolve;
	private GuiButton btnStart;
	private GuiButton btnStop;
	private GuiButton btnValidate;
	
	private GuiButton[] allButtons;
	
	private MenuLevel menuLevel;

	public Menu() {		
		initGui();
		setMenuLevel(MenuLevel.MAIN_MENU);
	}
	
	private void initGui() {
		
		/*
		 * MAIN MENU
		 */
		
		btnEdit = new GuiButton(Assets.instance.gui.btnEdit);
		btnEdit.setButtonType(ButtonType.EDIT);
		btnEdit.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnEdit.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 1;
		
		btnPlay = new GuiButton(Assets.instance.gui.btnPlay);
		btnPlay.setButtonType(ButtonType.PLAY);
		btnPlay.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnPlay.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 2;
		
		btnSolve = new GuiButton(Assets.instance.gui.btnSolve);
		btnSolve.setButtonType(ButtonType.SOLVE);
		btnSolve.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnSolve.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 3;
		
		/*
		 * EDIT
		 */
		
		btnEasy = new GuiButton(Assets.instance.gui.btnEasy);
		btnEasy.setButtonType(ButtonType.EASY);
		btnEasy.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnEasy.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 1;
		
		btnMedium = new GuiButton(Assets.instance.gui.btnMedium);
		btnMedium.setButtonType(ButtonType.MEDIUM);
		btnMedium.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnMedium.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 2;
		
		btnHard = new GuiButton(Assets.instance.gui.btnHard);
		btnHard.setButtonType(ButtonType.HARD);
		btnHard.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnHard.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 3;
		
		btnClearEdit = new GuiButton(Assets.instance.gui.btnClear);
		btnClearEdit.setButtonType(ButtonType.CLEAR_EDIT);
		btnClearEdit.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnClearEdit.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 4;
		
		btnConfirm = new GuiButton(Assets.instance.gui.btnConfirm);
		btnConfirm.setButtonType(ButtonType.CONFIRM);
		btnConfirm.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnConfirm.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 5;
		
		btnCancelEdit = new GuiButton(Assets.instance.gui.btnCancel);
		btnCancelEdit.setButtonType(ButtonType.CANCEL_EDIT);
		btnCancelEdit.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnCancelEdit.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 6;
		
		/*
		 * PLAY
		 */
		
		btnValidate = new GuiButton(Assets.instance.gui.btnValidate);
		btnValidate.setButtonType(ButtonType.VALIDATE);
		btnValidate.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnValidate.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 1;
		
		btnCancelPlay = new GuiButton(Assets.instance.gui.btnCancel);
		btnCancelPlay.setButtonType(ButtonType.CANCEL_PLAY);
		btnCancelPlay.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnCancelPlay.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 2;
		
		/*
		 * SOLVE
		 */
		
		btnStart = new GuiButton(Assets.instance.gui.btnStart);
		btnStart.setButtonType(ButtonType.START);
		btnStart.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnStart.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 1;
		
		btnStop = new GuiButton(Assets.instance.gui.btnStop);
		btnStop.setButtonType(ButtonType.STOP);
		btnStop.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnStop.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 1;
		
		btnContinue = new GuiButton(Assets.instance.gui.btnContinue);
		btnContinue.setButtonType(ButtonType.CONTINUE);
		btnContinue.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnContinue.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 1;
		
		btnClearSolve = new GuiButton(Assets.instance.gui.btnClear);
		btnClearSolve.setButtonType(ButtonType.CLEAR_SOLVE);
		btnClearSolve.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnClearSolve.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 2;
		
		btnCancelSolve = new GuiButton(Assets.instance.gui.btnCancel);
		btnCancelSolve.setButtonType(ButtonType.CANCEL_SOLVE);
		btnCancelSolve.position.x = Constants.GRID_SIZE + Constants.BUTTON_MARGIN_LEFT;
		btnCancelSolve.position.y = Constants.GRID_SIZE - Constants.BUTTON_MARGIN_TOP - 3;
		
		allButtons = new GuiButton[16];
		allButtons[0] = btnCancelEdit;
		allButtons[1] = btnCancelPlay;
		allButtons[2] = btnCancelSolve;
		allButtons[3] = btnClearEdit;
		allButtons[4] = btnClearSolve;
		allButtons[5] = btnConfirm;
		allButtons[6] = btnContinue;
		allButtons[7] = btnEasy;
		allButtons[8] = btnEdit;
		allButtons[9] = btnHard;
		allButtons[10] = btnMedium;
		allButtons[11] = btnPlay;
		allButtons[12] = btnSolve;
		allButtons[13] = btnStart;
		allButtons[14] = btnStop;
		allButtons[15] = btnValidate;
	}
	
	public void render(ShapeRenderer shapeRenderer) {

		shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.begin(ShapeType.Filled);
		
		for(GuiButton b : allButtons) {
			if(b.isHoveredOver() == true && b.isVisible() == true) {
				shapeRenderer.rect(b.position.x, b.position.y, b.bounds.width, b.bounds.height);
			}
		}
		
		shapeRenderer.end();

	}
	
	public void render(SpriteBatch batch) {
		batch.begin();
		
		TextureRegion reg = Assets.instance.gui.logo;
		reg.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		batch.draw(reg.getTexture(), Constants.GRID_SIZE + 1.5f, Constants.GRID_SIZE - 3, 0.0f,
				0.0f, 5.0f, 3.0f, 1.0f, 1.0f, 0.0f,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
		
		switch(menuLevel) {
		case MAIN_MENU:
			renderButton(btnEdit, batch);
			renderButton(btnPlay, batch);
			renderButton(btnSolve, batch);
			break;
			
		case EDIT:
			renderButton(btnEasy, batch);
			renderButton(btnMedium, batch);
			renderButton(btnHard, batch);
			renderButton(btnClearEdit, batch);
			renderButton(btnConfirm, batch);
			renderButton(btnCancelEdit, batch);
			break;
			
		case PLAY:
			renderButton(btnValidate, batch);
			renderButton(btnCancelPlay, batch);
			break;
			
		case SOLVE:
			renderButton(btnStart, batch);
			renderButton(btnClearSolve, batch);
			renderButton(btnCancelSolve, batch);
			break;
		}
		
		batch.end();
	}
	
	private void renderButton(GuiButton b, SpriteBatch batch) {
		TextureRegion reg = b.getReg();
		reg.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		batch.draw(reg.getTexture(), b.position.x, b.position.y, 0.0f,
				0.0f, 5.0f, 1.0f, 1.0f, 1.0f, 0.0f,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	public GuiButton[] getAllButtons() {
		return allButtons;
	}

	public MenuLevel getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(MenuLevel menuLevel) {
		this.menuLevel = menuLevel;
		
		for(GuiButton b : allButtons) {
			b.setVisible(false);
		}
		
		switch(this.menuLevel) {
		case MAIN_MENU:
			btnEdit.setVisible(true);
			btnPlay.setVisible(true);
			btnSolve.setVisible(true);
			break;
			
		case EDIT:
			btnEasy.setVisible(true);
			btnMedium.setVisible(true);
			btnHard.setVisible(true);
			btnClearEdit.setVisible(true);
			btnConfirm.setVisible(true);
			btnCancelEdit.setVisible(true);
			break;
			
		case PLAY:
			btnValidate.setVisible(true);
			btnCancelPlay.setVisible(true);
			break;
			
		case SOLVE:
			btnStart.setVisible(true);
			btnClearSolve.setVisible(true);
			btnCancelSolve.setVisible(true);
			break;
		}
		
	}
	
}
