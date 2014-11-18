package io.github.celebes.sudoku;

import io.github.celebes.sudoku.enums.MenuLevel;
import io.github.celebes.sudoku.objects.Board;
import io.github.celebes.sudoku.objects.GuiButton;
import io.github.celebes.sudoku.objects.Menu;
import io.github.celebes.sudoku.objects.Popup;
import io.github.celebes.sudoku.utils.CameraHelper;
import io.github.celebes.sudoku.utils.Constants;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class WorldController extends InputAdapter {
	public static final String TAG = WorldController.class.getSimpleName();
	
	private Game game;
	private CameraHelper cameraHelper;
	private WorldRenderer worldRenderer;
	
	private boolean sudokuSolved;
	
	public Board board;
	public Menu menu;
	public Popup popup;
	
	boolean buttonDelay = false;
	float buttonWaitTime = 0.1f;
	float elapsedButtonWaitTime = 0.0f;
	
	// Detekcja kolizji
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	
	public WorldController(Game game) {
		this.game = game;
		cameraHelper = new CameraHelper();
		init();
		Gdx.input.setInputProcessor(this);
	}
	
	private void init() {
		board = new Board();
		menu = new Menu();
		popup = new Popup();
	}
	
	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		
		testCollisions();
		
		if(buttonDelay == true) {
			elapsedButtonWaitTime += deltaTime;
			
			if(elapsedButtonWaitTime >= buttonWaitTime) {
				elapsedButtonWaitTime = 0.0f;
				buttonDelay = false;
			}
		}
	}
	
	private void testCollisions() {
		// pozycja kursora
		Vector3 touchScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Vector3 touchGame3 = worldRenderer.getCamera().unproject(touchScreen);
		Vector2 touchInGame = new Vector2(touchGame3.x, touchGame3.y);
		
		r1.set(touchInGame.x, touchInGame.y, 0, 0);
		
		if(popup.isPopupVisible() == true) {
			GuiButton[] okButtons = popup.getOkButtons();
			
			for(GuiButton b : okButtons) {
				r2.set(b.position.x, b.position.y, b.dimension.x, b.dimension.y);
				
				if(r1.overlaps(r2)) {
					if(b.isHoveredOver() == false) {
						b.setHoveredOver(true);
					}
				} else {
					if(b.isHoveredOver() == true) {
						b.setHoveredOver(false);
					}
				}
			}
			
		} else {
			if(!sudokuSolved) {
				if(menu.getMenuLevel() == MenuLevel.EDIT || menu.getMenuLevel() == MenuLevel.PLAY) {
					for(int i=0; i<Constants.GRID_SIZE; i++) {
						for(int j=0; j<Constants.GRID_SIZE; j++) {
							r2.set(board.getBoard()[i][j].position.x, board.getBoard()[i][j].position.y, board.getBoard()[i][j].dimension.x, board.getBoard()[i][j].dimension.y);
							
							if(r1.overlaps(r2)) {
								if(board.getBoard()[i][j].isHoveredOver() == false) {
									board.getBoard()[i][j].setHoveredOver(true);
								}
							} else {
								if(board.getBoard()[i][j].isHoveredOver() == true) {
									board.getBoard()[i][j].setHoveredOver(false);
								}
							}
						}
					}
				}
				
				GuiButton[] allButtons = menu.getAllButtons();
				
				for(GuiButton b : allButtons) {
					r2.set(b.position.x, b.position.y, b.dimension.x, b.dimension.y);
					
					if(r1.overlaps(r2)) {
						if(b.isHoveredOver() == false) {
							b.setHoveredOver(true);
						}
					} else {
						if(b.isHoveredOver() == true) {
							b.setHoveredOver(false);
						}
					}
				}
			}
		}
	}

	private void handleDebugInput(float deltaTime) {

		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		}
		
		if (Gdx.input.isKeyPressed(Keys.A)) {
			moveCamera(-camMoveSpeed, 0);
		}
		
		if (Gdx.input.isKeyPressed(Keys.D)) {
			moveCamera(camMoveSpeed, 0);
		}
		
		if (Gdx.input.isKeyPressed(Keys.W)) {
			moveCamera(0, camMoveSpeed);
		}
		
		if (Gdx.input.isKeyPressed(Keys.S)) {
			moveCamera(0, -camMoveSpeed);
		}
		
		// przyblizanie kamery
		float camZoomSpeed = 10 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		}
		
		if (Gdx.input.isKeyPressed(Keys.COMMA)) {
			cameraHelper.addZoom(camZoomSpeed);
		}
		
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) {
			cameraHelper.addZoom(-camZoomSpeed);
		}
		
		if (Gdx.input.isKeyPressed(Keys.SLASH)) {
			cameraHelper.setZoom(1);
		}
		
		// klikanie
		if(Gdx.input.isTouched()) {
			for(int i=0; i<Constants.GRID_SIZE; i++) {
				for(int j=0; j<Constants.GRID_SIZE; j++) {
					if(board.getBoard()[i][j].isHoveredOver() == true) {
						if(menu.getMenuLevel() == MenuLevel.EDIT || (menu.getMenuLevel() == MenuLevel.PLAY && board.getBoard()[i][j].isInitial() == false)) {
							board.setSelectedCell(board.getBoard()[i][j]);
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		guiButtonTouched();
		return false;
	}
	
	private void guiButtonTouched() {
		
		if(popup.isPopupVisible() == true) {
			for(GuiButton b : popup.getOkButtons()) {
				if(b.isHoveredOver() == true && b.isVisible() == true && buttonDelay == false) {
					switch(b.getButtonType()) {
					case OK_INVALID:
						popup.setPopupInvalidBoardVisible(false);
						break;
						
					case OK_CORRECT:
						popup.setPopupCorrectBoardVisible(false);
						break;
						
					case OK_SOLVED:
						popup.setPopupSudokuSolvedVisible(false);
						break;
					}
					
					buttonDelay = true;
				}
			}
		} else {
			for(GuiButton b : menu.getAllButtons()) {
				if(b.isHoveredOver() == true && b.isVisible() == true && buttonDelay == false) {
					
					switch(menu.getMenuLevel()) {
					
					case MAIN_MENU:
						
						switch(b.getButtonType()) {
						case EDIT:
							menu.setMenuLevel(MenuLevel.EDIT);
							board.saveState();
							break;
							
						case PLAY:
							menu.setMenuLevel(MenuLevel.PLAY);
							break;
							
						case SOLVE:
							menu.setMenuLevel(MenuLevel.SOLVE);
							break;
						}
						
						break;
						
					case EDIT:
						
						switch(b.getButtonType()) {
						case EASY:
							board.initEasyBoard();
							break;
							
						case MEDIUM:
							board.initMediumBoard();
							break;
							
						case HARD:
							board.initHardBoard();
							break;
							
						case CLEAR_EDIT:
							board.clearBoard(true);
							board.setSelectedCell(null);
							break;
							
						case CONFIRM:
							if(board.validateBoard() == true) {
								menu.setMenuLevel(MenuLevel.MAIN_MENU);
								board.setSelectedCell(null);
							} else {
								popup.setPopupInvalidBoardVisible(true);
							}
							
							break;
							
						case CANCEL_EDIT:
							menu.setMenuLevel(MenuLevel.MAIN_MENU);
							board.loadState();
							board.setSelectedCell(null);
							break;
						}
						
						break;
						
					case PLAY:
						
						switch(b.getButtonType()) {
						case VALIDATE:
							if(board.validateBoard() == true) {
								popup.setPopupCorrectBoardVisible(true);
							} else {
								popup.setPopupInvalidBoardVisible(true);
							}
							
							break;
							
						case CANCEL_PLAY:
							menu.setMenuLevel(MenuLevel.MAIN_MENU);
							board.clearBoard(false);
							board.setSelectedCell(null);
							break;
						}
						
						break;
						
					case SOLVE:
						
						switch(b.getButtonType()) {
						case START:
							break;
							
						case STOP:
							break;
							
						case CONTINUE:
							break;
							
						case CLEAR_SOLVE:
							board.clearBoard(false);
							break;
							
						case CANCEL_SOLVE:
							menu.setMenuLevel(MenuLevel.MAIN_MENU);
							board.clearBoard(false);
							break;
						}
						
						break;
					}
					
					buttonDelay = true;
				}
			}
		}
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.R) {
			Gdx.app.log(TAG, "RESET!");
			init();
		}
		
		if(keycode == Keys.V) {
			Gdx.app.log(TAG, "VALIDATION RESULT = " + (board.validateBoard() ? "OK" : "BAD"));
		}
		
		if(keycode == Keys.ENTER || keycode == Keys.ESCAPE) {
			board.setSelectedCell(null);
		}
		
		if(keycode == Keys.NUM_0 || keycode == Keys.NUMPAD_0 || keycode == Keys.BACKSPACE) {
			numberKeyDown(0);
		} else if(keycode == Keys.NUM_1 || keycode == Keys.NUMPAD_1) {
			numberKeyDown(1);
		} else if(keycode == Keys.NUM_2 || keycode == Keys.NUMPAD_2) {
			numberKeyDown(2);
		} else if(keycode == Keys.NUM_3 || keycode == Keys.NUMPAD_3) {
			numberKeyDown(3);
		} else if(keycode == Keys.NUM_4 || keycode == Keys.NUMPAD_4) {
			numberKeyDown(4);
		} else if(keycode == Keys.NUM_5 || keycode == Keys.NUMPAD_5) {
			numberKeyDown(5);
		} else if(keycode == Keys.NUM_6 || keycode == Keys.NUMPAD_6) {
			numberKeyDown(6);
		} else if(keycode == Keys.NUM_7 || keycode == Keys.NUMPAD_7) {
			numberKeyDown(7);
		} else if(keycode == Keys.NUM_8 || keycode == Keys.NUMPAD_8) {
			numberKeyDown(8);
		} else if(keycode == Keys.NUM_9 || keycode == Keys.NUMPAD_9) {
			numberKeyDown(9);
		}
		
		return false;
	}
	
	private void numberKeyDown(int numberKey) {
		if(board.getSelectedCell() != null) {
			board.getSelectedCell().setNumber(numberKey);
			
			if(numberKey == 0 || menu.getMenuLevel() != MenuLevel.EDIT) {
				board.getSelectedCell().setInitial(false);
			} else {
				board.getSelectedCell().setInitial(true);
			}
			
			board.setSelectedCell(null);
			
			if(board.isBoardComplete() == true && board.validateBoard() == true) {
				popup.setPopupSudokuSolvedVisible(true);
			}
		}
	}

	public CameraHelper getCameraHelper() {
		return cameraHelper;
	}

	public void setCameraHelper(CameraHelper cameraHelper) {
		this.cameraHelper = cameraHelper;
	}

	public boolean isSudokuSolved() {
		return sudokuSolved;
	}
	
	public WorldRenderer getWorldRenderer() {
		return worldRenderer;
	}
	
	public void setWorldRenderer(WorldRenderer worldRenderer) {
		this.worldRenderer = worldRenderer;
	}
	
}
