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
		
		if(Gdx.input.isKeyPressed(Keys.R)) {
			Gdx.app.log(TAG, "RESET!");
			init();
		}
		
		if(Gdx.input.isKeyPressed(Keys.V)) {
			Gdx.app.log(TAG, "VALIDATION RESULT = " + (board.validateBoard() ? "OK" : "BAD"));
		}
	}
	
	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
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
