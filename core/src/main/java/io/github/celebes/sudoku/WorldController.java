package io.github.celebes.sudoku;

import io.github.celebes.sudoku.objects.Board;
import io.github.celebes.sudoku.objects.Cell;
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
	}
	
	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		
		if(!sudokuSolved) {
			testCollisions();
		}
	}
	
	private void testCollisions() {
		// pozycja kursora
		Vector3 touchScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Vector3 touchGame3 = worldRenderer.getCamera().unproject(touchScreen);
		Vector2 touchInGame = new Vector2(touchGame3.x, touchGame3.y);
		
		r1.set(touchInGame.x, touchInGame.y, 0, 0);
		
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
		
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			cameraHelper.setPosition(0, 0);
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
						board.getBoard()[i][j].setSelected(true);
					}
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
			for(int i=0; i<Constants.GRID_SIZE; i++) {
				for(int j=0; j<Constants.GRID_SIZE; j++) {
					if(board.getBoard()[i][j].isSelected() == true) {
						board.getBoard()[i][j].setSelected(false);
					}
				}
			}
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
		for(int i=0; i<Constants.GRID_SIZE; i++) {
			for(int j=0; j<Constants.GRID_SIZE; j++) {
				if(board.getBoard()[i][j].isSelected() == true) {
					board.getBoard()[i][j].setNumber(numberKey);
					board.getBoard()[i][j].setSelected(false);
					board.getBoard()[i][j].setInitial(true);
				}
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
